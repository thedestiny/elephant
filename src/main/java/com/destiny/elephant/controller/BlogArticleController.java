package com.destiny.elephant.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.destiny.elephant.annotation.ElephantLog;
import com.destiny.elephant.base.BaseController;
import com.destiny.elephant.entity.BlogArticle;
import com.destiny.elephant.entity.BlogChannel;
import com.destiny.elephant.entity.BlogTags;
import com.destiny.elephant.entity.VO.ZtreeVO;
import com.destiny.elephant.util.LayerData;
import com.destiny.elephant.util.RestResponse;
import com.google.common.collect.Maps;
import com.xiaoleilu.hutool.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 博客内容  前端控制器
 * </p>
 *
 * @author wangl
 * @since 2018-01-19
 */
@Controller
@RequestMapping("/admin/blogArticle")
public class BlogArticleController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlogArticleController.class);

    @GetMapping("list")
    @ElephantLog("跳转博客内容列表")
    public String list() {
        return "/admin/blogArticle/list";
    }

    @RequiresPermissions("blog:article:list")
    @PostMapping("list")
    @ResponseBody
    public LayerData<BlogArticle> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                       ServletRequest request) {
        Map<String, Object> map = WebUtils.getParametersStartingWith(request, "s_");
        LayerData<BlogArticle> layerData = new LayerData<>();
        QueryWrapper<BlogArticle> wrapper = new QueryWrapper<>();
        wrapper.eq("del_flag", false);
        if (!map.isEmpty()) {
            String title = (String) map.get("title");
            if (StringUtils.isBlank(title)) {
                map.remove("title");
            }
            String category = (String) map.get("category");
            if (StringUtils.isBlank(category)) {
                map.remove("category");
            }
            String beginPublistTime = (String) map.get("beginPublistTime");
            String endPublistTime = (String) map.get("endPublistTime");
            if (StringUtils.isNotBlank(beginPublistTime)) {
                Date begin = DateUtil.parse(beginPublistTime);
                map.put("publist_time", begin);
            } else {
                map.remove("beginPublistTime");
            }
            if (StringUtils.isNotBlank(endPublistTime)) {
                Date end = DateUtil.parse(endPublistTime);
                map.put("publist_time", end);
            } else {
                map.remove("endPublistTime");
            }
            String content = (String) map.get("content");
            if (StringUtils.isBlank(content)) {
                map.remove("content");
            }
            String channelId = (String) map.get("channelId");
            if (StringUtils.isBlank(channelId)) {
                map.remove("channelId");
            }

        }

        Page<BlogArticle> iPage = new Page<>(page, limit);
        Page<BlogArticle> pageData = blogArticleService.selectDetailArticle(map, iPage);
        layerData.setData(pageData.getRecords());
        layerData.setCount((int)pageData.getTotal());
        return layerData;
    }

    @GetMapping("add")
    public String add(@RequestParam(value = "channelId", required = false) Long channelId, Model model) {
        BlogChannel blogChannel = blogChannelService.getById(channelId);
        if (blogChannel != null) {
            model.addAttribute("channel", blogChannel);
        }
        List<ZtreeVO> list = blogChannelService.selectZtreeData();
        model.addAttribute("ztreeData", JSONObject.toJSONString(list));
        List<BlogTags> blogTags = blogTagsService.listAll();
        model.addAttribute("taglist", blogTags);
        return "/admin/blogArticle/add";
    }

    @RequiresPermissions("blog:article:add")
    @PostMapping("add")
    @ElephantLog("保存新增博客内容数据")
    @ResponseBody
    public RestResponse add(@RequestBody BlogArticle blogArticle) {
        if (StringUtils.isBlank(blogArticle.getTitle())) {
            return RestResponse.failure("标题不能为空");
        }
        if (StringUtils.isBlank(blogArticle.getContent())) {
            return RestResponse.failure("内容不能为空");
        }
        if (blogArticle.getChannelId() == null) {
            return RestResponse.failure("栏目ID不能为空");
        }

        BlogChannel article = new BlogChannel();
        article.setId(blogArticle.getChannelId());
        article.setDelFlag(false);

        Integer count = blogChannelService.selectMaxSort(article);

       /* Object o = blogArticleService.selectObj(Condition.create()
                .setSqlSelect("max(sort)")
                .eq("channel_id",blogArticle.getChannelId())
                .eq("del_flag",false));*/
        int sort = 0;
        if (count != null) {
            sort = count + 1;
        }
        blogArticle.setSort(sort);
        blogArticleService.saveOrUpdateArticle(blogArticle);
        if (blogArticle.getBlogTags() != null && blogArticle.getBlogTags().size() > 0) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("articleId", blogArticle.getId());
            map.put("tags", blogArticle.getBlogTags());
            blogArticleService.saveArticleTags(map);
        }
        return RestResponse.success();
    }

    @GetMapping("edit")
    public String edit(Long id, Model model) {
        BlogArticle blogArticle = blogArticleService.selectOneDetailById(id);
        model.addAttribute("blogArticle", blogArticle);
        List<ZtreeVO> list = blogChannelService.selectZtreeData();
        model.addAttribute("ztreeData", JSONObject.toJSONString(list));
        List<BlogTags> blogTags = blogTagsService.listAll();
        model.addAttribute("taglist", blogTags);
        return "/admin/blogArticle/edit";
    }

    @RequiresPermissions("blog:article:edit")
    @PostMapping("edit")
    @ResponseBody
    @ElephantLog("保存编辑博客内容数据")
    public RestResponse edit(@RequestBody BlogArticle blogArticle) {
        if (null == blogArticle.getId() || 0 == blogArticle.getId()) {
            return RestResponse.failure("ID不能为空");
        }
        if (StringUtils.isBlank(blogArticle.getTitle())) {
            return RestResponse.failure("标题不能为空");
        }
        if (StringUtils.isBlank(blogArticle.getContent())) {
            return RestResponse.failure("内容不能为空");
        }
        if (blogArticle.getSort() == null) {
            return RestResponse.failure("排序值不能为空");
        }
        blogArticleService.saveOrUpdateArticle(blogArticle);
        blogArticleService.removeArticleTags(blogArticle.getId());
        if (blogArticle.getBlogTags() != null && blogArticle.getBlogTags().size() > 0) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("articleId", blogArticle.getId());
            map.put("tags", blogArticle.getBlogTags());
            blogArticleService.saveArticleTags(map);
        }
        return RestResponse.success();
    }

    @RequiresPermissions("blog:article:delete")
    @PostMapping("delete")
    @ResponseBody
    @ElephantLog("删除博客内容数据")
    public RestResponse delete(@RequestParam(value = "id", required = false) Long id) {
        if (null == id || 0 == id) {
            return RestResponse.failure("ID不能为空");
        }
        BlogArticle blogArticle = blogArticleService.getById(id);
        blogArticle.setDelFlag(true);
        blogArticleService.saveOrUpdateArticle(blogArticle);
        return RestResponse.success();
    }

    @GetMapping("createIndex")
    @ResponseBody
    public RestResponse createIndex() {
        blogArticleService.createArticleIndex();
        return RestResponse.success();
    }

}