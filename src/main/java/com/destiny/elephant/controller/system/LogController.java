package com.destiny.elephant.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.destiny.elephant.base.BaseController;
import com.destiny.elephant.entity.Log;
import com.destiny.elephant.util.LayerData;
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
 * Created by wangl on 2018/1/13.
 * todo:
 */
@Controller
@RequestMapping("admin/system/log")
public class LogController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogController.class);

    @GetMapping("list")
    public String list() {
        return "admin/system/log/list";
    }

    @PostMapping("list")
    @ResponseBody
    public LayerData<Log> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                               @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                               ServletRequest request) {
        Map map = WebUtils.getParametersStartingWith(request, "s_");
        LayerData<Log> layerData = new LayerData<>();
        QueryWrapper<Log> wrapper = new QueryWrapper<>();
        if (!map.isEmpty()) {
            String keys = (String) map.get("type");
            if (StringUtils.isNotBlank(keys)) {
                wrapper.eq("type", keys);
            }
            String title = (String) map.get("title");
            if (StringUtils.isNotBlank(title)) {
                wrapper.like("title", title);
            }
            String username = (String) map.get("username");
            if (StringUtils.isNotBlank(username)) {
                wrapper.eq("username", username);
            }
            String httpMethod = (String) map.get("method");
            if (StringUtils.isNotBlank(httpMethod)) {
                wrapper.eq("http_method", httpMethod);
            }
        }
        IPage<Log> logPage = logService.page(new Page<>(page, limit), wrapper);
        layerData.setCount((int)logPage.getTotal());
        layerData.setData(logPage.getRecords());
        return layerData;
    }

    @RequiresPermissions("system:logs:delete")
    @PostMapping("delete")
    @ResponseBody
    public RestResponse delete(@RequestParam("ids[]") List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            return RestResponse.failure("id不能为空");
        }
        logService.removeByIds(ids);
        return RestResponse.success();
    }

    @GetMapping("pvs")
    @ResponseBody
    public RestResponse getPV() {
        List<Integer> pvs = logService.selectSelfMonthData();
        return RestResponse.success().setData(pvs);
    }
}
