package com.destiny.elephant.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.destiny.elephant.annotation.ElephantLog;
import com.destiny.elephant.entity.QuartzTask;
import com.destiny.elephant.service.QuartzTaskService;
import com.destiny.elephant.util.LayerData;
import com.destiny.elephant.util.RestResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 定时任务  前端控制器
 * </p>
 *
 * @author wangl
 * @since 2018-01-24
 */
@Controller
@RequestMapping("/admin/quartzTask")
public class QuartzTaskController {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzTaskController.class);


    @Autowired
    private QuartzTaskService quartzTaskService;

    // 定时任务页面
    @GetMapping("list")
    @ElephantLog("跳转定时任务列表")
    public String list() {
        return "/admin/quartzTask/list";
    }

    // 定时任务数据
    @RequiresPermissions("quartz:task:list")
    @PostMapping("list")
    @ResponseBody
    public LayerData<QuartzTask> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                      @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                      ServletRequest request) {

        Map map = WebUtils.getParametersStartingWith(request, "s_");
        LayerData<QuartzTask> layerData = new LayerData<>();
        QueryWrapper<QuartzTask> wrapper = new QueryWrapper<>();
        wrapper.eq("del_flag", false);
        if (!map.isEmpty()) {
            String name = (String) map.get("name");
            if (StringUtils.isNotBlank(name)) {
                wrapper.like("name", name);
            } else {
                map.remove("name");
            }

            String status = (String) map.get("status");
            if (StringUtils.isNotBlank(status)) {
                wrapper.eq("status", status);
            } else {
                map.remove("status");
            }

        }
        IPage<QuartzTask> pageData = quartzTaskService.queryList(wrapper, new Page<QuartzTask>(page, limit));
        layerData.setData(pageData.getRecords());
        layerData.setCount((int) pageData.getTotal());
        return layerData;
    }

    @GetMapping("add")
    public String add() {
        return "/admin/quartzTask/add";
    }

    @RequiresPermissions("quartz:task:add")
    @PostMapping("add")
    @ElephantLog("保存新增定时任务数据")
    @ResponseBody
    public RestResponse add(QuartzTask quartzTask) {
        quartzTaskService.saveQuartzTask(quartzTask);
        return RestResponse.success();
    }

    @GetMapping("edit")
    public String edit(Long id, Model model) {
        QuartzTask quartzTask = quartzTaskService.getById(id);
        model.addAttribute("quartzTask", quartzTask);
        return "/admin/quartzTask/edit";
    }

    @RequiresPermissions("quartz:task:edit")
    @PostMapping("edit")
    @ResponseBody
    @ElephantLog("保存编辑定时任务数据")
    public RestResponse edit(QuartzTask quartzTask) {
        if (null == quartzTask.getId() || 0 == quartzTask.getId()) {
            return RestResponse.failure("ID不能为空");
        }
        quartzTaskService.updateQuartzTask(quartzTask);
        return RestResponse.success();
    }

    @RequiresPermissions("quartz:task:delete")
    @PostMapping("delete")
    @ResponseBody
    @ElephantLog("删除定时任务数据")
    public RestResponse delete(@RequestParam(value = "ids[]", required = false) List<Long> ids) {
        if (null == ids || 0 == ids.size()) {
            return RestResponse.failure("ID不能为空");
        }
        quartzTaskService.deleteBatchTasks(ids);
        return RestResponse.success();
    }

    /**
     * 暂停选中的定时任务
     *
     * @param ids 任务ID List
     * @return
     */
    @RequiresPermissions("quartz:task:paush")
    @PostMapping("paush")
    @ResponseBody
    public RestResponse paush(@RequestParam(value = "ids[]", required = false) List<Long> ids) {
        if (null == ids || 0 == ids.size()) {
            return RestResponse.failure("ID不能为空");
        }
        quartzTaskService.pause(ids);
        return RestResponse.success();
    }

    /**
     * 恢复选中的定时任务运行
     *
     * @param ids 任务ID List
     * @return
     */
    @RequiresPermissions("quartz:task:resume")
    @PostMapping("resume")
    @ResponseBody
    public RestResponse resume(@RequestParam(value = "ids[]", required = false) List<Long> ids) {
        if (null == ids || 0 == ids.size()) {
            return RestResponse.failure("ID不能为空");
        }
        quartzTaskService.resume(ids);
        return RestResponse.success();
    }

    /**
     * 立即执行选中的定时任务
     *
     * @param ids 任务ID List
     * @return
     */
    @RequiresPermissions("quartz:task:run")
    @PostMapping("run")
    @ResponseBody
    public RestResponse run(@RequestParam(value = "ids[]", required = false) List<Long> ids) {
        if (null == ids || 0 == ids.size()) {
            return RestResponse.failure("ID不能为空");
        }
        quartzTaskService.run(ids);
        return RestResponse.success();
    }

}