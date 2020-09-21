package com.destiny.elephant.controller.system;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.destiny.elephant.annotation.ElephantLog;
import com.destiny.elephant.entity.Site;
import com.destiny.elephant.service.UploadService;
import com.destiny.elephant.util.QiNiuFileUtil;
import com.destiny.elephant.util.RestResponse;
import com.destiny.elephant.util.ToolUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaoleilu.hutool.log.Log;
import com.xiaoleilu.hutool.log.LogFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.net.www.protocol.http.HttpURLConnection;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**
 文件上传
 */
@Slf4j
@Controller
@RequestMapping("file")
public class FileController {
	

    @Value("uploadType")
    private String uploadType;


    @Value("localUploadPath")
    private String localUploadPath;

    /* 存储七牛云*/
    @Autowired
    @Qualifier("qiniuService")
    private UploadService qiniuService;

    /* 存储阿里云 */
    @Autowired
    @Qualifier("ossService")
    private UploadService ossService;

    /* 存储本地 */
    @Autowired
    @Qualifier("localService")
    private UploadService localService;

    @PostMapping("upload")
    @ResponseBody
    @ElephantLog("文件上传")
    public RestResponse uploadFile(@RequestParam("test") MultipartFile file, HttpServletRequest httpServletRequest) {
        Site site = (Site) httpServletRequest.getAttribute("site");
        
        if(ObjectUtil.isEmpty(site) || ObjectUtil.isEmpty(file)){
	        return RestResponse.failure("加载信息错误或者上传文件为空,请检查!");
        }
        
        String url = null;
        Map m = Maps.newHashMap();
        try {
            if ("local".equals(site.getFileUploadType())) {
                url = localService.upload(file);
            }
            if ("qiniu".equals(site.getFileUploadType())) {
                url = qiniuService.upload(file);
            }
            if ("oss".equals(site.getFileUploadType())) {
                url = ossService.upload(file);
            }
            m.put("url", url);
            m.put("name", file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.failure(e.getMessage());
        }
        return RestResponse.success().setData(m);
    }

    @PostMapping("uploadBase64")
    @ResponseBody
    @ElephantLog("base64格式文件上传")
    public RestResponse uploadBase64(@RequestParam(value = "file", required = false) String file,
                                     @RequestParam(value = "name", required = false) String name) {
    	
        if(StrUtil.isBlank(file) || StrUtil.isBlank(name)){
	        return RestResponse.failure("图片或图片名称不能为空!");
        }
        
        String url = QiNiuFileUtil.uploadBase64(file, name);
        return RestResponse.success().setData(url);
    }

    /**
     * wangEditor批量上传图片
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("uploadWang")
    @ResponseBody
    @ElephantLog("富文本编辑器文件上传")
    public Map<String, Object> uploadWang(@RequestParam("test") MultipartFile[] file, HttpServletRequest httpServletRequest) {
        Site site = (Site) httpServletRequest.getAttribute("site");
      
	    if(ObjectUtil.isEmpty(site) || ObjectUtil.isEmpty(file)){
		    return RestResponse.failure("加载信息错误或者上传文件为空,请检查!");
	    }
        
        List<String> data = Lists.newArrayList();
        Map<String, Object> m = Maps.newHashMap();
        try {
            for (int i = 0; i < file.length; i++) {
                String url = null;
                if ("local".equals(site.getFileUploadType())) {
                    url = localService.upload(file[i]);
                }
                if ("qiniu".equals(site.getFileUploadType())) {
                    url = qiniuService.upload(file[i]);
                }
                if ("oss".equals(site.getFileUploadType())) {
                    url = ossService.upload(file[i]);
                }
                data.add(url);
            }
            m.put("errno", 0);
        } catch (Exception e) {
            e.printStackTrace();
            m.put("errno", 1);
        }
        m.put("data", data);
        return m;
    }

    /**
     * wangEditor复制新闻中包含图片的话吧图片上传到七牛上并更换图片地址
     *
     * @return
     */
    @PostMapping("doContent")
    @ResponseBody
    @ElephantLog("富文本编辑器内容图片上传")
    public RestResponse doContent(@RequestParam(value = "content", required = false) String content,
                                  HttpServletRequest httpServletRequest) throws IOException, NoSuchAlgorithmException {
        Site site = (Site) httpServletRequest.getAttribute("site");
       
	    if(ObjectUtil.isEmpty(site) || StrUtil.isEmpty(content)){
		    return RestResponse.failure("加载信息错误或复制内容为空为空,请检查!");
	    }
        
        Document doc = Jsoup.parseBodyFragment(content);
        Elements links = doc.select("img[src]");
        for (Element e : links) {
            String imgSrc = e.attr("abs:src");
            if ("local".equals(site.getFileUploadType())) {
                if (imgSrc.contains("file:")) {
                    imgSrc.replace("\\", "/");
                    e.attr("src", localService.uploadLocalImg(imgSrc.substring(6)));
                } else {
                    e.attr("src", localService.uploadNetFile(imgSrc));
                }
            }
            if ("qiniu".equals(site.getFileUploadType())) {
                if (imgSrc.contains("file:")) {
                    imgSrc.replace("\\", "/");
                    e.attr("src", qiniuService.uploadLocalImg(imgSrc.substring(6)));
                } else {
                    e.attr("src", qiniuService.uploadNetFile(imgSrc));
                }
            }
            if ("oss".equals(site.getFileUploadType())) {
                if (imgSrc.contains("file:")) {
                    imgSrc.replace("\\", "/");
                    e.attr("src", ossService.uploadLocalImg(imgSrc.substring(6)));
                } else {
                    e.attr("src", ossService.uploadNetFile(imgSrc));
                }
            }
        }
        String outHtml = doc.body().toString();
        return RestResponse.success().setData(outHtml.replace("<body>", "").replace("</body>", ""));
    }

    @PostMapping("downCheck")
    @ResponseBody
    public RestResponse downCheck(@RequestParam(value = "url", required = false) String url,
                                  @RequestParam(value = "name", required = false) String name) {
	    if(StrUtil.isBlank(url) || StrUtil.isBlank(name)){
		    return RestResponse.failure("图片或图片名称不能为空!");
	    }
        return RestResponse.success();
    }

    @GetMapping("download")
    @ResponseBody
    @ElephantLog("文件下载")
    public RestResponse downFile(@RequestParam(value = "url", required = false) String realurl,
                                 @RequestParam(value = "name", required = false) String name,
                                 HttpServletResponse response) throws IOException {
    	
	    if(StrUtil.isBlank(realurl) || StrUtil.isBlank(name)){
		    return RestResponse.failure("图片地址或图片名称不能为空!");
	    }
        
        if ("text/html".equals(ToolUtil.getContentType(name))) {
            return RestResponse.failure("图片格式不正确");
        }
	
	    HttpResponse httpResponse = HttpUtil.createGet(realurl).execute();
	    byte[] body = httpResponse.bodyBytes();
	    name = new String(name.getBytes("GB2312"), "ISO8859-1");
        response.reset();
        response.setHeader("Content-type", "application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + name);
        ServletOutputStream out = response.getOutputStream();
        out.write(body);
        out.flush();
        out.close();
    
        return RestResponse.success();
    }


    public String localUpload(HttpServletRequest request, MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                // 文件保存路径
                if (StringUtils.isBlank(localUploadPath)) {
                    localUploadPath = request.getSession().getServletContext().getRealPath("/") + "/static/upload/" + System.currentTimeMillis() + "/";
                    String filePath = localUploadPath + file.getOriginalFilename();
                    file.transferTo(new File(filePath));
                    return filePath;
                } else {
                    Long t = System.currentTimeMillis();
                    String filePath = localUploadPath + t + "/" + file.getOriginalFilename();
                    file.transferTo(new File(filePath));
                    return "/upload/" + t + filePath;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }
}
