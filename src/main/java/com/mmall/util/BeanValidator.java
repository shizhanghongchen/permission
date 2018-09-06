package com.mmall.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mmall.exception.ParamException;
import org.apache.commons.collections.MapUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

/**
 * 校验工具-BeanValidator
 *
 * @Author: wb-yxk397023
 * @Date: Created in 2018/9/6
 */
public class BeanValidator {

    /**
     * 定义Validator工厂
     */
    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    /**
     * 校验方法(核心逻辑)
     *
     * @param t
     * @param grous
     * @param <T>
     * @return
     */
    public static <T> Map<String, String> validate(T t, Class... grous) {
        // 从工厂中获取validator
        Validator validator = validatorFactory.getValidator();
        // 获取校验结果
        Set validateResult = validator.validate(t, grous);
        // 判断结果validateResult是否为空
        if (validateResult.isEmpty()) {
            // 如果为空返回一个空
            return Collections.emptyMap();
        } else {
            // 创建容器
            LinkedHashMap errors = Maps.newLinkedHashMap();
            // 定义iterator
            Iterator iterator = validateResult.iterator();
            // 循环遍历
            while (iterator.hasNext()) {
                // 获取错误
                ConstraintViolation violation = (ConstraintViolation) iterator.next();
                // 将错误信息放到容器中
                errors.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            return errors;
        }
    }

    /**
     * 校验方法(入参为List类型)
     *
     * @param collection
     * @return
     */
    public static Map<String, String> validateList(Collection<?> collection) {
        // 判断入参是否为空
        Preconditions.checkNotNull(collection);
        Iterator<?> iterator = collection.iterator();
        Map errors;
        // 遍历iterator
        do {
            // 如果当前iterator为空则返回空
            if (!iterator.hasNext()) {
                return Collections.emptyMap();
            }
            // 如果不为空则取出当前的值
            Object object = iterator.next();
            // 执行校验逻辑
            errors = validate(object, new Class[0]);
        } while (errors.isEmpty());
        // 返回结果
        return errors;
    }

    /**
     * 校验方法(入参为对象类型)
     *
     * @param first
     * @param objects
     * @return
     */
    public static Map<String, String> validateObject(Object first, Object... objects) {
        // 当入参是一个数组时调用validateList
        if (objects != null && objects.length > 0) {
            return validateList(Lists.asList(first, objects));
        } else {
            return validate(first, new Class[0]);
        }
    }

    /**
     * 校验异常
     *
     * @param param
     * @throws ParamException
     */
    public static void check(Object param) throws ParamException {
        Map<String, String> map = BeanValidator.validateObject(param);
        if (MapUtils.isNotEmpty(map)) {
            throw new ParamException(map.toString());
        }
    }
}
