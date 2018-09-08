package com.mmall.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Level计算工具
 *
 * @Author: wb-yxk397023
 * @Date: Created in 2018/9/8
 */
public class LevelUtil {

    /**
     * 定义分隔符
     */
    public final static String SEPARATOR = ".";

    /**
     * 定义root的id
     */
    public final static String ROOT = "0";

    /**
     * level计算规则
     *
     * @param parentLevel
     * @param parentId
     * @return
     */
    public static String calculateLevel(String parentLevel, int parentId) {
        if (StringUtils.isBlank(parentLevel)) {
            return ROOT;
        } else {
            return StringUtils.join(parentLevel, SEPARATOR, parentId);
        }
    }
}
