package com.destiny.elephant.base;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证码校验过滤器
 */
public class CaptchaFormAuthenticationFilter extends FormAuthenticationFilter {


    private Logger logger = LoggerFactory.getLogger(CaptchaFormAuthenticationFilter.class);

    /**
     * 覆盖默认实现, 用sendRedirect 直接跳出框架,以免造成js框架重复加载js出错。
     *
     * @param token
     * @param subject
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @see FormAuthenticationFilter#onLoginSuccess(AuthenticationToken, Subject, ServletRequest, ServletResponse)
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject,
                                     ServletRequest request, ServletResponse response) throws Exception {
        //issueSuccessRedirect(request, response);
        //we handled the success redirect directly, prevent the chain from continuing:
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String url = httpServletRequest.getContextPath() + this.getSuccessUrl();
        logger.info("url is {}", url);
        httpServletResponse.sendRedirect(url);
        return true;
    }
}
