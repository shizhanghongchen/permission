package com.mmall.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取Spring上下文工具-ApplicationContextHelper
 *
 * @Author: wb-yxk397023
 * @Date: Created in 2018/9/6
 */
@Component("applicationContextHelper")
public class ApplicationContextHelper implements ApplicationContextAware {

    /**
     * 定义全局ApplicationContext
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        // 将context赋值给全局的applicationContext
        applicationContext = context;
    }

    /**
     * 从applicationContext中取上下文的Bean
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T popBean(Class<T> clazz) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(clazz);
    }

    /**
     * 从applicationContext中取上下文的Bean
     *
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T popBean(String name, Class<T> clazz) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(name, clazz);
    }
}
