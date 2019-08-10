package com.destiny.elephant.realm;

import com.destiny.elephant.entity.Menu;
import com.destiny.elephant.entity.Role;
import com.destiny.elephant.entity.User;
import com.destiny.elephant.service.UserService;
import com.destiny.elephant.util.Constants;
import com.destiny.elephant.util.Encodes;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * Shiro Realm
 */
@Component(value = "authRealm")
public class AuthRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(AuthRealm.class);

    /**
     * @Lazy 默认为懒加载
     */
    @Autowired
    @Lazy(value = true)
    private UserService userService;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        ShiroUser shiroUser = (ShiroUser) principalCollection.getPrimaryPrincipal();

        User user = userService.findUserByLoginName(shiroUser.getLoginName());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        /** 加载角色 */
        Set<Role> roles = user.getRoleLists();
        Set<String> roleNames = Sets.newHashSet();
        for (Role role : roles) {
            if (StringUtils.isNotBlank(role.getName())) {
                roleNames.add(role.getName());
            }
        }
        /** 菜单 */
        Set<Menu> menus = user.getMenus();
        Set<String> permissions = Sets.newHashSet();
        for (Menu menu : menus) {
            if (StringUtils.isNotBlank(menu.getPermission())) {
                permissions.add(menu.getPermission());
            }
        }
        info.setRoles(roleNames);
        info.setStringPermissions(permissions);
        return info;
    }

    /**
     * 鉴权
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        String username = (String) token.getPrincipal();
        User user = userService.findUserByLoginName(username);
        if (user == null) {
            throw new UnknownAccountException();//没找到帐号
        }
        if (Boolean.TRUE.equals(user.getLocked())) {
            throw new LockedAccountException(); //帐号锁定
        }
        byte[] salt = Encodes.decodeHex(user.getSalt());
        // shiro user
        ShiroUser shiroUser = new ShiroUser(user.getId(), user.getLoginName(), user.getNickName(), user.getIcon());
        //密码
        String auth = user.getPassword();
        // salt
        ByteSource byteSource = ByteSource.Util.bytes(salt);
        // realm name
        String realmName = getName();

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(shiroUser, auth, byteSource, realmName);

        logger.info("shiro user {} auth {} salt {} realmName {}", shiroUser, auth, byteSource, realmName);
        return authenticationInfo;
    }

    public void removeUserAuthorizationInfoCache(String username) {
        SimplePrincipalCollection pc = new SimplePrincipalCollection();
        pc.add(username, super.getName());
        super.clearCachedAuthorizationInfo(pc);
    }

    /**
     * 设定Password校验的Hash算法与迭代次数.
     * 被@PostConstruct修饰的方法会在服务器加载Servle的时候运行，并且只会被服务器执行一次。
     * PostConstruct在构造函数之后执行,init()方法之前执行。PreDestroy（）方法在destroy()方法执行执行之后执行
     * 服务器加载servlet-> Servlet构造函数 -> PostConstruct -> init -> service -> destroy -> preDestroy -> 服务器卸载servlet
     * 初始化凭证matcher
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(Constants.HASH_ALGORITHM);
        matcher.setHashIterations(Constants.HASH_INTERATIONS);
        setCredentialsMatcher(matcher);
    }


}
