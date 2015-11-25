package com.winxuan.ec.support.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 日志拦截器
 * @author juqkai(juqkai@gmail.com)
 */
@Component
public class LogResolver implements HandlerExceptionResolver{
    Log log = LogFactory.getLog(LogResolver.class);
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.error(ex.getMessage(), ex);
        return null;
    }

}
