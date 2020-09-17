package com.destiny.elephant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.destiny.elephant.entity.Resource;
import com.destiny.elephant.entity.UploadInfo;
import com.destiny.elephant.exception.MyException;
import com.destiny.elephant.mapper.ResourceMapper;
import com.destiny.elephant.service.UploadService;
import com.destiny.elephant.util.QETag;
import com.xiaoleilu.hutool.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Slf4j
@Service("localService")
public class LocalUploadServiceImpl implements UploadService {
	
	@Autowired
	private ResourceMapper resourceMapper;
	
	@Override
	public String upload(MultipartFile file) throws IOException, NoSuchAlgorithmException {
		byte[] data = file.getBytes();
		QETag tag = new QETag();
		Resource rescource = new Resource();
		String hash = tag.calcETag(file);
		QueryWrapper<Resource> wrapper = new QueryWrapper<>();
		wrapper.eq("hash", hash);
		wrapper.eq("source", "local");
		rescource = resourceMapper.selectOne(wrapper);
		if (rescource != null) {
			return rescource.getWebUrl();
		}
		String extName = file.getOriginalFilename().substring(
				file.getOriginalFilename().lastIndexOf("."));
		String fileName = UUID.randomUUID() + extName;
		String contentType = file.getContentType();
		StringBuffer sb = new StringBuffer(ResourceUtils.getURL("classpath:").getPath());
		String filePath = sb.append("static/upload/").toString();
		File targetFile = new File(filePath);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		FileOutputStream out = new FileOutputStream(filePath + fileName);
		out.write(data);
		out.flush();
		out.close();
		String webUrl = "/static/upload/" + fileName;
		rescource = new Resource();
		rescource.setFileName(fileName);
		rescource.setFileSize(new java.text.DecimalFormat("#.##").format(file.getSize() / 1024) + "kb");
		rescource.setHash(hash);
		rescource.setFileType(contentType);
		rescource.setWebUrl(webUrl);
		rescource.setSource("local");
		rescource.insert();
		return webUrl;
	}
	
	@Override
	public Boolean delete(String path) {
		path = path.replaceFirst("\\/", "classpath:");
		File file = new File(path);
		if (file.exists()) {
			file.delete();
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String uploadNetFile(String url) throws IOException, NoSuchAlgorithmException {
		Resource rescource = new Resource();
		QueryWrapper<Resource> wrapper = new QueryWrapper<>();
		wrapper.eq("original_net_url", url);
		wrapper.eq("source", "local");
		rescource = rescource.selectOne(wrapper);
		if (rescource != null) {
			return rescource.getWebUrl();
		}
		String extName = url.substring(url.lastIndexOf("."));
		String fileName = UUID.randomUUID() + extName;
		StringBuffer sb = new StringBuffer(ResourceUtils.getURL("classpath:").getPath());
		String filePath = sb.append("static/upload/").toString();
		File uploadDir = new File(filePath);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		URL neturl = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) neturl.openConnection();
		conn.connect();
		BufferedInputStream br = new BufferedInputStream(conn.getInputStream());
		byte[] buf = new byte[1024];
		int len = 0;
		FileOutputStream out = new FileOutputStream(filePath + fileName);
		while ((len = br.read(buf)) > 0) out.write(buf, 0, len);
		File targetFile = new File(filePath + fileName);
		String webUrl = "";
		if (targetFile.exists()) {
			webUrl = "/static/upload/" + fileName;
			rescource = new Resource();
			QETag tag = new QETag();
			rescource.setFileName(fileName);
			rescource.setFileSize(new java.text.DecimalFormat("#.##").format(br.read(buf) / 1024) + "kb");
			rescource.setHash(tag.calcETag(targetFile));
			rescource.setFileType(StringUtils.isBlank(extName) ? "unknown" : extName);
			rescource.setWebUrl(webUrl);
			rescource.setOriginalNetUrl(url);
			rescource.setSource("local");
			rescource.insert();
		}
		br.close();
		out.flush();
		out.close();
		conn.disconnect();
		return webUrl;
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
		rescource = rescource.selectOne(wrapper);
		if (rescource != null) {
			return rescource.getWebUrl();
		}
		StringBuffer sb = null;
		try {
			sb = new StringBuffer(ResourceUtils.getURL("classpath:").getPath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String filePath = sb.append("static/upload/").toString();
		StringBuffer name = new StringBuffer(RandomUtil.randomUUID());
		StringBuffer returnUrl = new StringBuffer("/static/upload/");
		String extName = "";
		extName = file.getName().substring(
				file.getName().lastIndexOf("."));
		sb.append(name).append(extName);
		File uploadDir = new File(filePath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		try {
			InputStream input = new FileInputStream(file);
			byte[] buf = new byte[input.available()];
			int len = 0;
			FileOutputStream out = new FileOutputStream(sb.toString());
			while ((len = input.read(buf)) > 0) out.write(buf, 0, len);
			input.close();
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		returnUrl.append(name).append(extName);
		return returnUrl.toString();
	}
	
	@Override
	public String uploadBase64(String base64) {
		StringBuffer webUrl = new StringBuffer("/static/upload/");
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			//Base64解码
			byte[] b = decoder.decodeBuffer(base64);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {//调整异常数据
					b[i] += 256;
				}
			}
			//生成jpeg图片
			StringBuffer ss = new StringBuffer(ResourceUtils.getURL("classpath:").getPath());
			String filePath = ss.append("static/upload/").toString();
			File targetFileDir = new File(filePath);
			if (!targetFileDir.exists()) {
				targetFileDir.mkdirs();
			}
			StringBuffer sb = new StringBuffer(filePath);
			StringBuffer fileName = new StringBuffer(RandomUtil.randomUUID());
			sb.append(fileName);
			sb.append(".jpg");
			String imgFilePath = sb.toString();//新生成的图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return webUrl.append(sb).toString();
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Boolean testAccess(UploadInfo uploadInfo) {
		return null;
	}
}
