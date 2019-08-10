package com.destiny.elephant.base;

import com.destiny.elephant.entity.Site;
import com.destiny.elephant.service.SiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * blog 拦截器
 */
@Component
public class BlogHandlerInterceptor implements HandlerInterceptor {

    private Logger LOGGER = LoggerFactory.getLogger(BlogHandlerInterceptor.class);

    @Autowired
    private SiteService siteService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {
        if (siteService == null) {
            // 解决service为null无法注入问题 注入 bean
            ServletContext servletContext = httpServletRequest.getServletContext();
            LOGGER.info(" servletContext is {}", servletContext.getContextPath());

            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
            siteService = (SiteService) factory.getBean("siteService");
        }
        Site site = siteService.getCurrentSite();
        if (site != null) {
            httpServletRequest.setAttribute("site", site);
            return true;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {
        LOGGER.info("blog post handler ");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        LOGGER.info("blog handler after completion! ");
    }
}
