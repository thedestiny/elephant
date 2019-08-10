package com.destiny.elephant.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.destiny.elephant.annotation.ElephantLog;
import com.destiny.elephant.base.BaseController;
import com.destiny.elephant.entity.BlogChannel;
import com.destiny.elephant.entity.Site;
import com.destiny.elephant.entity.VO.ZtreeVO;
import com.destiny.elephant.util.RestResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 博客栏目  前端控制器
 * </p>
 *
 * @author wangl
 * @since 2018-01-17
 */
@Controller
@RequestMapping("/admin/blogChannel")
public class BlogChannelController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlogChannelController.class);

    @GetMapping("list")
    @ElephantLog("跳转博客栏目列表")
    public String list() {
        return "/admin/blogChannel/list";
    }

    @RequiresPermissions("blog:channel:list")
    @PostMapping("list")
    @ResponseBody
    public RestResponse list(HttpServletRequest request) {
        List<BlogChannel> blogChannels = blogChannelService.selectChannelList();
        return RestResponse.success().setData(blogChannels);
    }

    @GetMapping("add")
    public String add(@RequestParam(value = "parentId", required = false) Long parentId, Model model) {
        if (parentId != null && parentId != 0) {
            BlogChannel blogChannel = blogChannelService.getById(parentId);
            model.addAttribute("parentChannel", blogChannel);
        }
        QueryWrapper<Site> wrapper = new QueryWrapper<>();
        wrapper.eq("del_flag", false);
        List<Site> siteList = siteService.list(wrapper);
        model.addAttribute("siteList", siteList);
        return "/admin/blogChannel/add";
    }

    @RequiresPermissions("blog:channel:add")
    @PostMapping("add")
    @ElephantLog("保存新增博客栏目数据")
    @ResponseBody
    public RestResponse add(BlogChannel blogChannel) {
        if (StringUtils.isBlank(blogChannel.getName())) {
            return RestResponse.failure("栏目名称不能为空");
        }
        if (blogChannelService.getCountByName(blogChannel.getName()) > 0) {
            return RestResponse.failure("栏目名称已经存在");
        }
        if (blogChannel.getParentId() == null) {
            blogChannel.setLevel(1);

            BlogChannel bl = new BlogChannel();
            bl.setDelFlag(false);
            bl.setParentId(0L);
            Integer count = blogChannelService.selectMaxSort(bl);

           /* Object o = blogChannelService.selectObj(Condition.create()
                    .setSqlSelect("max(sort)").isNull("parent_id")
                    .eq("del_flag", false));*/
            int sort = 0;
            if (count != null) {
                sort = count + 10;
            }
            blogChannel.setSort(sort);
        } else {
            BlogChannel parentMenu = blogChannelService.getById(blogChannel.getParentId());
            if (parentMenu == null) {
                return RestResponse.failure("父栏目不存在");
            }
            blogChannel.setParentIds(parentMenu.getParentIds());
            blogChannel.setLevel(parentMenu.getLevel() + 1);


            BlogChannel bl = new BlogChannel();
            bl.setDelFlag(false);
            bl.setParentId(blogChannel.getParentId());
            Integer count = blogChannelService.selectMaxSort(bl);


            /*Object o = blogChannelService.selectObj(Condition.create()
                    .setSqlSelect("max(sort)")
                    .eq("parent_id", blogChannel.getParentId())
                    .eq("del_flag", false));*/
            int sort = 0;
            if (count != null) {
                sort = count + 10;
            }
            blogChannel.setSort(sort);
        }
        blogChannelService.saveOrUpdateChannel(blogChannel);
        blogChannel.setParentIds(StringUtils.isBlank(blogChannel.getParentIds()) ? blogChannel.getId() + "," : blogChannel.getParentIds() + blogChannel.getId() + ",");
        blogChannelService.saveOrUpdateChannel(blogChannel);
        return RestResponse.success();
    }

    @GetMapping("edit")
    public String edit(Long id, Model model) {
        BlogChannel blogChannel = blogChannelService.getById(id);
        model.addAttribute("blogChannel", blogChannel);

        QueryWrapper<Site> wrapper = new QueryWrapper<>();
        wrapper.eq("del_flag", false);
        List<Site> siteList = siteService.list(wrapper);
        model.addAttribute("siteList", siteList);
        return "/admin/blogChannel/edit";
    }

    @RequiresPermissions("blog:channel:edit")
    @PostMapping("edit")
    @ResponseBody
    @ElephantLog("保存编辑博客栏目数据")
    public RestResponse edit(BlogChannel blogChannel) {
        if (null == blogChannel.getId() || 0 == blogChannel.getId()) {
            return RestResponse.failure("ID不能为空");
        }
        if (StringUtils.isBlank(blogChannel.getName())) {
            return RestResponse.failure("名称不能为空");
        }
        BlogChannel oldChannel = blogChannelService.getById(blogChannel.getId());
        if (!oldChannel.getName().equals(oldChannel.getName())) {
            if (blogChannelService.getCountByName(oldChannel.getName()) > 0) {
                return RestResponse.failure("栏目名称已存在");
            }
        }
        if (oldChannel.getSort() == null) {
            return RestResponse.failure("排序值不能为空");
        }
        blogChannelService.saveOrUpdateChannel(blogChannel);
        return RestResponse.success();
    }

    @RequiresPermissions("blog:channel:delete")
    @PostMapping("delete")
    @ResponseBody
    @ElephantLog("删除博客栏目数据")
    public RestResponse delete(@RequestParam(value = "id", required = false) Long id) {
        if (null == id || 0 == id) {
            return RestResponse.failure("ID不能为空");
        }
        BlogChannel blogChannel = blogChannelService.getById(id);
        blogChannel.setDelFlag(true);
        blogChannelService.saveOrUpdateChannel(blogChannel);
        //清除此栏目所对应的文章的关系
        blogArticleService.removeArticleChannel(id);
        return RestResponse.success();
    }

    /**
     * 栏目ztree树
     *
     * @return
     */
    @PostMapping("ztreeData")
    @ResponseBody
    public RestResponse getZtreeChannelData() {
        List<ZtreeVO> list = blogChannelService.selectZtreeData();
        return RestResponse.success().setData(list);
    }

    /**
     * 验证栏目地址是否重复
     *
     * @param parentId
     * @param href
     * @return
     */
    @PostMapping("checkHref")
    @ResponseBody
    public RestResponse checkHref(@RequestParam(value = "parentId", required = false) Long parentId,
                                  @RequestParam(value = "href", required = false) String href) {
        if (StringUtils.isBlank(href)) {
            return RestResponse.failure("栏目地址不能为空");
        }
        QueryWrapper<BlogChannel> wrapper = new QueryWrapper<>();
        wrapper.eq("del_flag", false);
        wrapper.eq("href", href);
        if (parentId == null) {
            wrapper.isNull("parent_id");
        } else {
            wrapper.eq("parent_id", parentId);
        }
        if (blogChannelService.count(wrapper) > 0) {
            return RestResponse.failure("栏目地址已存在,请重新输入");
        }
        return RestResponse.success();
    }

}