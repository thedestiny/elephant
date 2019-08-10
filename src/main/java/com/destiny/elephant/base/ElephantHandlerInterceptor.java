package com.destiny.elephant.base;

import com.destiny.elephant.entity.User;
import com.destiny.elephant.service.SiteService;
import com.destiny.elephant.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * MyHandlerInterceptor
 */
@Component
public class ElephantHandlerInterceptor implements HandlerInterceptor {

    private Logger LOGGER = LoggerFactory.getLogger(ElephantHandlerInterceptor.class);

    @Autowired
    private SiteService siteService;
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {
        long start = System.currentTimeMillis();
        /*
         *
         * */
        if (siteService == null || userService == null) {
            //解决service为null无法注入问题
            LOGGER.info("siteService is null!!!");
            // spring 无法注入时获取bean
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(httpServletRequest.getServletContext());
            siteService = (SiteService) factory.getBean("siteService");
            userService = (UserService) factory.getBean("userService");

        }
        if(siteService != null){
            httpServletRequest.setAttribute("site", siteService.getCurrentSite());
        }

        User user = userService.findUserById(MySysUser.id());
        long end = System.currentTimeMillis();
        LOGGER.info("start is {} and end is {}, cost time {}. 当前请求路径.. {}", start, end, (end - start), httpServletRequest.getRequestURI());
        if (user != null) {
            httpServletRequest.setAttribute("currentUser", user);
            return true;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) {
        LOGGER.info("elephant post handler ");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        LOGGER.info("elephant handler after completion ");
    }
}
