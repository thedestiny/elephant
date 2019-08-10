package com.destiny.elephant.base;

import com.destiny.elephant.realm.ShiroUser;
import org.apache.shiro.SecurityUtils;

/**
 */
public class MySysUser {
    private MySysUser() {
    }

    /**
     * 取出Shiro中的当前用户LoginName.
     */
    public static String icon() {
        return shiroUser().getIcon();
    }

    public static Long id() {
        return shiroUser().getId();
    }

    public static String loginName() {
        return shiroUser().getLoginName();
    }

    public static String nickName(){
        return shiroUser().getNickName();
    }

    public static ShiroUser shiroUser() {
        return  (ShiroUser) SecurityUtils.getSubject().getPrincipal();
    }
}
