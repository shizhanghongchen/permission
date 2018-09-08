package com.mmall.controller;

import com.mmall.model.SysUser;
import com.mmall.service.SysUserService;
import com.mmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户登录Controller
 *
 * @Author: wb-yxk397023
 * @Date: Created in 2018/9/8
 */
@Controller
public class UserController {

    @Resource
    private SysUserService sysUserService;

    /**
     * 用户登录
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @RequestMapping("login.page")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 获取用户登录信息
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        // 查询用户是否存在
        SysUser sysUser = sysUserService.findByKeyword(username);
        // 定义异常情况下需要返回的错误值
        String errorMsg = "";
        // 定义登录成功后的跳转链接
        String ret = request.getParameter("ret");
        // 填充判断逻辑
        if (StringUtils.isBlank(username)) {
            errorMsg = "用户名不可以为空";
        } else if (StringUtils.isBlank(password)) {
            errorMsg = "密码不可以为空";
        } else if (sysUser == null) {
            errorMsg = "查询不到指定用户";
        } else if (!sysUser.getPassword().equals(MD5Util.encrypt(password))) {
            errorMsg = "用户名或密码错误";
        } else if (sysUser.getStatus() != 1) {
            errorMsg = "用户已被冻结,请联系管理员";
        } else {
            // 将用户信息放入到session中
            request.getSession().setAttribute("user", sysUser);
            if (StringUtils.isNotBlank(ret)) {
                response.sendRedirect(ret);
            } else {
                response.sendRedirect("/admin/index.page");
            }
        }
        // 如果用户未登录成功
        request.setAttribute("error", errorMsg);
        request.setAttribute("username", username);
        if (StringUtils.isNotBlank(ret)) {
            request.setAttribute("ret", ret);
        }
        String path = "signin.jsp";
        // 跳转
        request.getRequestDispatcher(path).forward(request, response);
    }

    /**
     * 用户注销
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @RequestMapping("logout.page")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getSession().invalidate();
        String path = "signin.jsp";
        response.sendRedirect(path);
    }
}
