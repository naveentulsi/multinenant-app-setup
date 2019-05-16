package com.saas.multitenantspring.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TenantInterceptor extends HandlerInterceptorAdapter {

    private static Log logger = LogFactory.getLog(TenantInterceptor.class);

    public TenantInterceptor() {
        super();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("Request Intercepted");
        String tenant = request.getHeader("tenant");
        SchemaResolver.setCurrentTenant(tenant);
        logger.info("Tenant Set");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("Post Handle");
        logger.info("Clear Teant");
        SchemaResolver.clear();
    }
}
