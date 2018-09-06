package com.mmall.common;

import com.mmall.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Http请求前后监听工具-HttpInterceptor
 *
 * @Author: wb-yxk397023
 * @Date: Created in 2018/9/6
 */
@Slf4j
public class HttpInterceptor extends HandlerInterceptorAdapter {

    private static final String START_TIME = "requestStartTime";

    /**
     * 请求之前调用
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取当前的请求url
        String url = request.getRequestURI().toString();
        // 获取请求参数
        Map parameterMap = request.getParameterMap();
        log.info("request start. url:{}, params:{}", url, JsonMapper.obj2String(parameterMap));
        // 获取当前时间
        long start = System.currentTimeMillis();
        // 将时间设置到请求中
        request.setAttribute(START_TIME, start);
        return true;
    }

    /**
     * 请求之后调用(正常情况)
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 获取当前的请求url
        String url = request.getRequestURI().toString();
        // 获取开始时间
        long start = (Long) request.getAttribute(START_TIME);
        // 获取当前时间
        long end = System.currentTimeMillis();
        log.info("request finished. url:{}, cost:{}", url, end - start);
    }

    /**
     * 请求之后调用(所有情况通用)
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 获取当前的请求url
        String url = request.getRequestURI().toString();
        // 获取开始时间
        long start = (Long) request.getAttribute(START_TIME);
        // 获取当前时间
        long end = System.currentTimeMillis();
        log.info("request completed. url:{}, cost:{}", url, end - start);
    }
}
