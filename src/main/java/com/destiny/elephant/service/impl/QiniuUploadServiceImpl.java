package com.destiny.elephant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.destiny.elephant.entity.Resource;
import com.destiny.elephant.entity.UploadInfo;
import com.destiny.elephant.exception.MyException;
import com.destiny.elephant.service.ResourceService;
import com.destiny.elephant.service.UploadInfoService;
import com.destiny.elephant.service.UploadService;
import com.destiny.elephant.util.QETag;
import com.destiny.elephant.util.RestResponse;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FetchRet;
import com.qiniu.util.Auth;
import com.xiaoleilu.hutool.util.RandomUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

@Service("qiniuService")
public class QiniuUploadServiceImpl implements UploadService {

    @Autowired
    private UploadInfoService uploadInfoService;

    @Autowired
    private ResourceService resourceService;

    private Logger logger = LoggerFactory.getLogger(QiniuUploadServiceImpl.class);

    private UploadInfo getUploadInfo() {
        return uploadInfoService.getOneInfo();
    }

    private UploadManager getUploadManager() {
        Zone z = Zone.zone0();
        Configuration config = new Configuration(z);
        return new UploadManager(config);
    }

    private BucketManager getBucketManager() {
        Zone z = Zone.zone0();
        Configuration config = new Configuration(z);
        Auth auth = Auth.create(getUploadInfo().getQiniuAccessKey(), getUploadInfo().getQiniuSecretKey());
        return new BucketManager(auth, config);
    }

    private String getAuth() {
        if (getUploadInfo() == null) {
            throw new MyException("上传信息配置不存在");
        }
        Auth auth = Auth.create(getUploadInfo().getQiniuAccessKey(), getUploadInfo().getQiniuSecretKey());
        return auth.uploadToken(getUploadInfo().getQiniuBucketName());
    }

    @Override
    public String upload(MultipartFile file) throws IOException, NoSuchAlgorithmException {
        String fileName = "", extName = "", filePath = "";
        StringBuffer key = new StringBuffer();
        StringBuffer returnUrl = new StringBuffer(getUploadInfo().getQiniuBasePath());
        logger.error("return url is {}", returnUrl);
        if (null != file && !file.isEmpty()) {
            extName = file.getOriginalFilename().substring(
                    file.getOriginalFilename().lastIndexOf("."));
            fileName = RandomUtil.randomUUID() + extName;
            byte[] data = file.getBytes();
            QETag tag = new QETag();
            String hash = tag.calcETag(file);
            Resource resource = new Resource();
            QueryWrapper<Resource> wrapper = new QueryWrapper<>();
            wrapper.eq("hash", hash);
            wrapper.eq("source", "qiniu");
            resource = resource.selectOne(wrapper);
            if (resource != null) {
                return resource.getWebUrl();
            }
            String qiniuDir = getUploadInfo().getQiniuDir();

            if (StringUtils.isNotBlank(qiniuDir)) {
                key.append(qiniuDir).append("/");
                returnUrl.append(qiniuDir).append("/");
            }
            key.append(fileName);
            returnUrl.append(fileName);
            Response r = getUploadManager().put(data, key.toString(), getAuth());
            logger.info("response is {}", r);
            if (r.isOK()) {
                // filePath = getUploadInfo().getQiniuBasePath() + fileName;
                resource = new Resource();
                resource.setFileName(fileName);
                resource.setFileSize(new java.text.DecimalFormat("#.##").format(file.getSize() / 1024) + "kb");
                resource.setHash(hash);
                resource.setFileType(StringUtils.isBlank(extName) ? "unknown" : extName);
                resource.setWebUrl(returnUrl.toString());
                resource.setSource("qiniu");
                resourceService.save(resource);
            }
        }
        return returnUrl.toString();
    }

    @Override
    public Boolean delete(String path) {
        QueryWrapper<Resource> wrapper = new QueryWrapper<>();
        wrapper.eq("web_url", path);
        wrapper.eq("del_flag", false);
        wrapper.eq("source", "qiniu");
        Resource Resource = resourceService.getOne(wrapper);
        path = Resource.getOriginalNetUrl();
        try {
            getBucketManager().delete(getUploadInfo().getQiniuBucketName(), path);
            resourceService.removeById(Resource);
            return true;
        } catch (QiniuException e) {
            logger.error("encounter exception e is {} and detail is {}", e.getMessage(), e);
            return false;
        }
    }

    @Override
    public String uploadNetFile(String url) throws IOException, NoSuchAlgorithmException {
        String fileName = RandomUtil.randomUUID();
        QueryWrapper<Resource> wrapper = new QueryWrapper<>();
        wrapper.eq("source", "qiniu");
        wrapper.eq("original_net_url", url);
        wrapper.eq("del_flag", false);
        Resource Resource = resourceService.getOne(wrapper);
        if (Resource != null) {
            return Resource.getWebUrl();
        }
        StringBuffer key = new StringBuffer();
        StringBuffer returnUrl = new StringBuffer(getUploadInfo().getQiniuBasePath());
        try {
            String qiniuDir = getUploadInfo().getQiniuDir();

            if (StringUtils.isNotBlank(qiniuDir)) {
                key.append(qiniuDir).append("/");
                returnUrl.append(qiniuDir).append("/");
            }
            key.append(fileName);
            returnUrl.append(fileName);
            FetchRet fetchRet = getBucketManager().fetch(url, getUploadInfo().getQiniuBucketName(), key.toString());
            Resource = new Resource();
            Resource.setFileName(fetchRet.key);
            Resource.setFileSize(new java.text.DecimalFormat("#.##").format(fetchRet.fsize / 1024) + "kb");
            Resource.setHash(fetchRet.hash);
            Resource.setFileType(fetchRet.mimeType);
            Resource.setWebUrl(returnUrl.toString());
            Resource.setSource("qiniu");
            Resource.setOriginalNetUrl(url);
            Resource.insert();
        } catch (QiniuException e) {
            logger.error("encounter exception e is {} and detail is {}", e.getMessage(), e);
        }
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
            logger.error("encounter exception e is {} and detail is {}", e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            logger.error("encounter exception e is {} and detail is {}", e.getMessage(), e);
        }
        Resource Resource = new Resource();
        QueryWrapper<Resource> wrapper = new QueryWrapper<>();
        wrapper.eq("hash", hash);
        wrapper.eq("source", "qiniu");
        Resource = Resource.selectOne(wrapper);
        if (Resource != null) {
            return Resource.getWebUrl();
        }
        String filePath = "",
                extName = "",
                name = RandomUtil.randomUUID();
        extName = file.getName().substring(
                file.getName().lastIndexOf("."));
        StringBuffer key = new StringBuffer();
        StringBuffer returnUrl = new StringBuffer(getUploadInfo().getQiniuBasePath());
        String qiniuDir = getUploadInfo().getQiniuDir();
        if (StringUtils.isNotBlank(qiniuDir)) {
            key.append(qiniuDir).append("/");
            returnUrl.append(qiniuDir).append("/");
        }
        key.append(name).append(extName);
        returnUrl.append(name).append(extName);
        Response response = null;
        try {
            response = getUploadManager().put(file, key.toString(), getAuth());
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        if (response.isOK()) {
            filePath = returnUrl.toString();
            Resource = new Resource();
            Resource.setFileName(name + extName);
            Resource.setFileSize(new java.text.DecimalFormat("#.##").format(file.length() / 1024) + "kb");
            Resource.setHash(hash);
            Resource.setFileType(StringUtils.isBlank(extName) ? "unknown" : extName);
            Resource.setWebUrl(filePath);
            Resource.setSource("qiniu");
            Resource.insert();
        }
        return filePath;
    }

    @Override
    public String uploadBase64(String base64) {
        StringBuffer key = new StringBuffer();
        StringBuffer returnUrl = new StringBuffer(getUploadInfo().getQiniuBasePath());
        String qiniuDir = getUploadInfo().getQiniuDir();
        String fileName = RandomUtil.randomUUID(), filePath;
        if (StringUtils.isNotBlank(qiniuDir)) {
            key.append(qiniuDir).append("/");
            returnUrl.append(qiniuDir).append("/");
        }
        key.append(fileName);
        returnUrl.append(fileName);
        byte[] data = Base64.decodeBase64(base64);
        try {
            getUploadManager().put(data, key.toString(), getAuth());
        } catch (IOException e) {
            logger.error("encounter exception e is {} and detail is {}", e.getMessage(), e);
        }
        return returnUrl.toString();
    }

    @Override
    public Boolean testAccess(UploadInfo uploadInfo) {
        ClassPathResource classPathResource = new ClassPathResource("static/images/userface1.jpg");
        try {
            Auth auth = Auth.create(uploadInfo.getQiniuAccessKey(), uploadInfo.getQiniuSecretKey());
            String authstr = auth.uploadToken(uploadInfo.getQiniuBucketName());
            InputStream inputStream = classPathResource.getInputStream();
            Response response = getUploadManager().put(inputStream, "test.jpg", authstr, null, null);
            if (response.isOK()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error("encounter exception e is {} and detail is {}", e.getMessage(), e);
            return false;
        }
    }
}
