package com.mmall.common;

import com.mmall.model.SysUser;

import javax.servlet.http.HttpServletRequest;

/**
 * 将对象与当前进程绑定
 *
 * @Author: wb-yxk397023
 * @Date: Created in 2018/9/9
 */
public class RequestHolder {

    /**
     * 定义线程安全的userHolder
     */
    private static final ThreadLocal<SysUser> userHolder = new ThreadLocal<SysUser>();

    /**
     * 定义线程安全的requestHolder
     */
    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<HttpServletRequest>();

    /**
     * 添加user
     *
     * @param sysUser
     */
    public static void add(SysUser sysUser) {
        userHolder.set(sysUser);
    }

    /**
     * 添加request
     *
     * @param request
     */
    public static void add(HttpServletRequest request) {
        requestHolder.set(request);
    }

    /**
     * 获取user
     *
     * @return
     */
    public static SysUser getCurrentUser() {
        return userHolder.get();
    }

    /**
     * 获取request
     *
     * @return
     */
    public static HttpServletRequest getCurrentRequest() {
        return requestHolder.get();
    }

    /**
     * 移除信息
     */
    public static void remove() {
        userHolder.remove();
        requestHolder.remove();
    }
}
