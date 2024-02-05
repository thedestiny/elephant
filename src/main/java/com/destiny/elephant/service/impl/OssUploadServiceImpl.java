package com.destiny.elephant.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.destiny.elephant.entity.Resource;
import com.destiny.elephant.entity.UploadInfo;
import com.destiny.elephant.exception.MyException;
import com.destiny.elephant.service.ResourceService;
import com.destiny.elephant.service.UploadInfoService;
import com.destiny.elephant.service.UploadService;
import com.destiny.elephant.util.QETag;
import com.destiny.elephant.util.ToolUtil;
import com.xiaoleilu.hutool.util.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

@Service("ossService")
public class OssUploadServiceImpl implements UploadService {

    private Logger logger = LoggerFactory.getLogger(OssUploadServiceImpl.class);

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private UploadInfoService uploadInfoService;

    private UploadInfo getUploadInfo() {
        return uploadInfoService.getOneInfo();
    }


    private OSSClient getOSSClient() {
        return new OSSClient(getUploadInfo().getOssEndpoint(), getUploadInfo().getOssKeyId(), getUploadInfo().getOssKeySecret());
    }

    @Override
    public String upload(MultipartFile file) throws IOException, NoSuchAlgorithmException {
        String fileName = null, realNames = "";

        UploadInfo uploadInfo = getUploadInfo();

        if (uploadInfo == null) {
            uploadInfo = new UploadInfo();
            uploadInfo.setOssDir("upload");
            uploadInfo.setOssBasePath("path");
            uploadInfo.setOssBucketName("qingtengjia");
            uploadInfo.setOssEndpoint("http://oss-cn-beijing.aliyuncs.com");
            uploadInfo.setOssKeyId("LTAILtNPBCgi0Lwx");
            uploadInfo.setOssKeySecret("C97JOyyDvdE3dF9hNgDWF9CX3DLWiU");

        }


        StringBuffer returnUrl = new StringBuffer(uploadInfo.getOssBasePath());
        String ossDir = uploadInfo.getOssDir();
        try {
            fileName = file.getOriginalFilename();
            // 上传文件名
            StringBuffer realName = new StringBuffer(UUID.randomUUID().toString());
            String fileExtension = fileName.substring(fileName.lastIndexOf("."));
            realName.append(fileExtension);
            QETag tag = new QETag();
            String hash = tag.calcETag(file);
            Resource rescource = new Resource();
            QueryWrapper<Resource> wrapper = new QueryWrapper<>();
            wrapper.eq("hash", hash);
            wrapper.eq("source", "oss");
            rescource = rescource.selectOne(wrapper);
            if (rescource != null) {
                return rescource.getWebUrl();
            }

            InputStream is = file.getInputStream();

            Long fileSize = file.getSize();
            //创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(is.available());
            metadata.setCacheControl("no-cache");
            metadata.setHeader("Pragma", "no-cache");
            metadata.setContentEncoding("utf-8");
            metadata.setContentType(ToolUtil.getContentType(fileName));
            StringBuffer description = new StringBuffer("filename/filesize=");
            description.append(realNames).append("/").append(fileSize).append("Byte.");
            metadata.setContentDisposition(description.toString());

            StringBuffer key = new StringBuffer();
            if (ossDir != null && !"".equals(ossDir)) {
                key.append(ossDir).append("/");
                returnUrl.append("/").append(ossDir).append("/");
            }
            key.append(realName);
            returnUrl.append(realName);


            PutObjectResult putResult = getOSSClient().putObject(uploadInfo.getOssBucketName(), key.toString(), is, metadata);
            //解析结果
            logger.info("md5码为" + putResult.getETag());
            rescource = new Resource();
            rescource.setFileName(realName.toString());
            rescource.setFileSize(new java.text.DecimalFormat("#.##").format(file.getSize() / 1024) + "kb");
            rescource.setHash(hash);
            rescource.setFileType(StringUtils.isBlank(fileExtension) ? "unknown" : fileExtension);
            rescource.setWebUrl(returnUrl.toString());
            rescource.setSource("oss");
            rescource.insert();
            getOSSClient().shutdown();
            is.close();
        } catch (Exception e) {
            throw new MyException("上传阿里云OSS服务器异常." + e.getMessage());
        }
        return returnUrl.toString();
    }

    @Override
    public Boolean delete(String path) {
        path = path.replace(getUploadInfo().getOssBasePath(), "");
        String ossDir = getUploadInfo().getOssDir();
        StringBuffer sb = new StringBuffer();
        if (ossDir != null && !"".equals(ossDir)) {
            sb.append(ossDir).append("/");
        }
        String key = path.replace(sb.toString(), "");
        try {
            getOSSClient().deleteObject(getUploadInfo().getOssBucketName(), path);
            QueryWrapper<Resource> wrapper = new QueryWrapper<>();
            wrapper.eq("file_name", key);
            wrapper.eq("source", "oss");
            resourceService.remove(wrapper);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String uploadNetFile(String url) throws IOException, NoSuchAlgorithmException {
        QueryWrapper<Resource> wrapper = new QueryWrapper<>();
        wrapper.eq("source", "oss");
        wrapper.eq("original_net_url", url);
        Resource rescource = resourceService.getOne(wrapper);
        if (rescource != null) {
            return rescource.getWebUrl();
        }
        String ossDir = getUploadInfo().getOssDir(),
                fileName = RandomUtil.randomUUID();
        StringBuffer returnUrl = new StringBuffer(getUploadInfo().getOssBasePath());
        StringBuffer key = new StringBuffer();
        if (ossDir != null && !"".equals(ossDir)) {
            key.append(ossDir).append("/");
        }
        key.append(fileName).append(".jpg");
        StringBuffer sb = new StringBuffer(fileName);
        InputStream inputStream = new URL(url).openStream();
        PutObjectResult putObjectResult = getOSSClient().putObject(getUploadInfo().getOssBucketName(), key.toString(), inputStream);
        ResponseMessage responseMessage = putObjectResult.getResponse();
        returnUrl.append(key);
        rescource = new Resource();
        rescource.setFileName(sb.append(".jpg").toString());
        rescource.setHash(putObjectResult.getETag());
        rescource.setWebUrl(returnUrl.toString());
        rescource.setOriginalNetUrl(url);
        rescource.setSource("oss");
        rescource.insert();
        inputStream.close();

        getOSSClient().shutdown();
        return returnUrl.toString();
    }

    @Override
    public String uploadLocalImg(String localPath) {
        File file = new File(localPath);
        if (!file.exists()) {
            throw new MyException("本地文件不存在");
        }
        QETag tag = new QETag();
        String hash = null;
        try {
            hash = tag.calcETag(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Resource rescource = new Resource();
        QueryWrapper<Resource> wrapper = new QueryWrapper<>();
        wrapper.eq("hash", hash);
        wrapper.eq("source", "oss");
        rescource = rescource.selectOne(wrapper);
        if (rescource != null) {
            return rescource.getWebUrl();
        }
        String filePath = "",
                extName = "",
                ossDir = getUploadInfo().getOssDir(),
                name = UUID.randomUUID().toString();
        extName = file.getName().substring(
                file.getName().lastIndexOf("."));
        StringBuffer returnUrl = new StringBuffer(getUploadInfo().getOssBasePath());
        StringBuffer key = new StringBuffer();
        if (ossDir != null && !"".equals(ossDir)) {
            key.append(ossDir).append("/");
        }
        key.append(name).append(".").append(extName);
        StringBuffer realName = new StringBuffer(name);
        getOSSClient().putObject(getUploadInfo().getOssBucketName(), key.toString(), file);
        returnUrl.append(realName);
        rescource = new Resource();
        rescource.setFileName(realName.append(".").append(extName).toString());
        rescource.setFileSize(new java.text.DecimalFormat("#.##").format(file.length() / 1024) + "kb");
        rescource.setHash(hash);
        rescource.setFileType(extName);
        rescource.setWebUrl(returnUrl.toString());
        rescource.setSource("oss");
        rescource.insert();
        getOSSClient().shutdown();
        return null;
    }

    @Override
    public String uploadBase64(String base64) {
        //base64数据转换为byte[]类型
        byte[] asBytes = Base64.getDecoder().decode(base64);
        InputStream sbs = new ByteArrayInputStream(asBytes);
        StringBuffer returnUrl = new StringBuffer(getUploadInfo().getOssBasePath());
        StringBuffer key = new StringBuffer();
        StringBuffer fileName = new StringBuffer(RandomUtil.randomUUID());
        String ossDir = getUploadInfo().getOssDir();
        int fileSize = asBytes.length;
        //创建上传Object的Metadata
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(sbs.available());
            metadata.setContentEncoding("utf-8");
            metadata.setContentType("image/png");
            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
            //上传文件
            if (ossDir != null && !"".equals(ossDir)) {
                key.append(ossDir).append("/");
                returnUrl.append(ossDir).append("/");
            }
            key.append(fileName);
            PutObjectResult putResult = getOSSClient().putObject(getUploadInfo().getOssBucketName(), key.toString(), sbs, metadata);
            returnUrl.append(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                sbs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            getOSSClient().shutdown();
        }
        return returnUrl.toString();
    }

    @Override
    public Boolean testAccess(UploadInfo uploadInfo) {
        ClassPathResource classPathResource = new ClassPathResource("static/images/userface1.jpg");
        try {
            OSSClient ossClient = new OSSClient(uploadInfo.getOssEndpoint(), uploadInfo.getOssKeyId(), uploadInfo.getOssKeySecret());
            InputStream inputStream = classPathResource.getInputStream();
            ossClient.putObject(uploadInfo.getOssBucketName(), "test.jpg", inputStream, null);
            ossClient.shutdown();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
