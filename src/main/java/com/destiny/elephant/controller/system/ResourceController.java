package com.destiny.elephant.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.destiny.elephant.annotation.ElephantLog;
import com.destiny.elephant.base.BaseController;
import com.destiny.elephant.entity.Resource;
import com.destiny.elephant.util.LayerData;
import com.destiny.elephant.util.QiNiuFileUtil;
import com.destiny.elephant.util.RestResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by wangl on 2018/1/14.
 * todo:
 */
@Controller
@RequestMapping("/admin/system/rescource")
public class ResourceController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceController.class);

    @GetMapping("list")
    @ElephantLog("跳转资源展示列表")
    public String list() {
        return "admin/system/rescource/list";
    }

    @RequiresPermissions("sys:rescource:list")
    @PostMapping("list")
    @ResponseBody
    public LayerData<Resource> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                    @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                    ServletRequest request) {
        Map map = WebUtils.getParametersStartingWith(request, "s_");
        LayerData<Resource> layerData = new LayerData<>();
        QueryWrapper<Resource> wrapper = new QueryWrapper<>();
        if (!map.isEmpty()) {
            String type = (String) map.get("type");
            if (StringUtils.isNotBlank(type)) {
                wrapper.like("file_type", type);
            }
            String hash = (String) map.get("hash");
            if (StringUtils.isNotBlank(hash)) {
                wrapper.eq("hash", hash);
            }
            String source = (String) map.get("source");
            if (StringUtils.isNotBlank(source)) {
                wrapper.eq("source", source);
            }
        }
        IPage<Resource> dataPage = resourceService.page(new Page<>(page, limit), wrapper);
        layerData.setCount((int)dataPage.getTotal());
        layerData.setData(dataPage.getRecords());
        return layerData;
    }

    @RequiresPermissions("sys:rescource:delete")
    @PostMapping("delete")
    @ElephantLog("删除系统资源")
    @ResponseBody
    public RestResponse delete(@RequestParam("ids[]") List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            return RestResponse.failure("删除ID不能为空");
        }
        List<Resource> rescources = (List<Resource>)resourceService.listByIds(ids);
        if (rescources == null || rescources.size() == 0) {
            return RestResponse.failure("请求参数不正确");
        }
        for (Resource rescource : rescources) {
            QiNiuFileUtil.deleteQiniuP(rescource.getWebUrl());
        }
        resourceService.removeByIds(ids);
        return RestResponse.success();
    }

    @GetMapping("test")
    @ElephantLog("测试错误请求")
    public String error() throws Exception {
        throw new Exception("测试异常消息");
    }
}
