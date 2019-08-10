package com.destiny.elephant.base;

import com.destiny.elephant.entity.User;
import com.destiny.elephant.realm.ShiroUser;
import com.destiny.elephant.service.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * base controller
 * */

public class BaseController {

    public User getCurrentUser() {
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        if (shiroUser == null) {
            return null;
        }
        return userService.getById(shiroUser.getId());

    }

    @Autowired
    protected UserService userService;

    @Autowired
    protected MenuService menuService;

    @Autowired
    protected RoleService roleService;

    @Autowired
    protected DictService dictService;

    @Autowired
    protected ResourceService resourceService;

    @Autowired
    protected TableService tableService;

    @Autowired
    protected SiteService siteService;

    @Autowired
    protected LogService logService;

    @Autowired
    protected BlogArticleService blogArticleService;

    @Autowired
    protected BlogChannelService blogChannelService;

    @Autowired
    protected BlogCommentService blogCommentService;

    @Autowired
    protected BlogTagsService blogTagsService;

    @Autowired
    protected QuartzTaskService quartzTaskService;

    @Autowired
    protected QuartzTaskLogService quartzTaskLogService;

    @Autowired
    protected UploadInfoService uploadInfoService;
}
