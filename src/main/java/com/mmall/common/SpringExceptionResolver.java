package com.mmall.common;

import com.mmall.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 接口请求全局异常处理
 *
 * @Author: wb-yxk397023
 * @Date: Created in 2018/9/6
 */
@Slf4j
public class SpringExceptionResolver implements HandlerExceptionResolver {

    private static final String DEFAULT_MESSAGE = "System Errot";

    private static final String REQUEST_URL_JSON = ".json";

    private static final String REQUEST_URL_PAGE = ".page";

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        // url
        String url = httpServletRequest.getRequestURL().toString();
        // 定义ModelAndView对象
        ModelAndView mv;
        // 定义默认msg
        String defuaultMsg = DEFAULT_MESSAGE;
        /**
         * 根据请求后缀判断异常类型
         * 项目中所有请求json数据的请求都使用.json结尾
         * 所有请求page页面的请求都使用.page结尾
         */
        if (url.endsWith(REQUEST_URL_JSON)) {
            // 判断异常是否为自定义异常
            if (e instanceof PermissionException) {
                // 获取自定义的异常信息
                JsonData result = JsonData.fail(e.getMessage());
                // 重新给ModelAndView赋值
                mv = new ModelAndView("jsonView", result.toMap());
            } else {
                log.error("unknown json exception, url: " + url, e);
                JsonData result = JsonData.fail(DEFAULT_MESSAGE);
                mv = new ModelAndView("jsonView", result.toMap());
            }
        } else if (url.endsWith(REQUEST_URL_PAGE)) {
            log.error("unknown page exception, url: " + url, e);
            // 如果是page类型异常
            JsonData result = JsonData.fail(DEFAULT_MESSAGE);
            mv = new ModelAndView("exception", result.toMap());
        } else {
            log.error("unknow exception, url: " + url, e);
            // 兜底异常处理
            JsonData result = JsonData.fail(DEFAULT_MESSAGE);
            mv = new ModelAndView("jsonView", result.toMap());
        }
        return mv;
    }
}
