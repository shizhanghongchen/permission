package com.mmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 登录成功后的跳转处理
 *
 * @Author: wb-yxk397023
 * @Date: Created in 2018/9/8
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("index.page")
    public ModelAndView index(){
        return new ModelAndView("admin");
    }
}
