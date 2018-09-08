package com.mmall.util;

import java.util.Random;

/**
 * password工具类
 *
 * @Author: wb-yxk397023
 * @Date: Created in 2018/9/8
 */
public class PasswordUtil {

    /**
     * 生成密码的最小长度
     */
    private final static int PASSWORD_MIN_LENGTH = 8;

    /**
     * 生成密码的随机长度
     */
    private final static int PASSWORD_MAX_RANDOM_LENGTH = 3;

    /**
     * 密码可以使用的字符常量
     */
    public final static String[] WORD = {
            "a", "b", "c", "d", "e", "f", "g",
            "h", "j", "k", "m", "n",
            "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G",
            "H", "J", "K", "M", "N",
            "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"
    };

    /**
     * 密码可以使用的数字常量
     */
    public final static String[] NUM = {
            "2", "3", "4", "5", "6", "7", "8", "9"
    };

    /**
     * 生成随机密码
     *
     * @return
     */
    public static String randomPassword() {
        // 定义容器
        StringBuffer stringBuffer = new StringBuffer();
        // 定义随机数
        Random random = new Random(System.currentTimeMillis());
        // 定义标识符
        boolean flag = false;
        // 定义随机长度
        int length = random.nextInt(PASSWORD_MAX_RANDOM_LENGTH) + PASSWORD_MIN_LENGTH;
        // 循环拼装
        for (int i = 0; i < length; i++) {
            if (flag) {
                stringBuffer.append(NUM[random.nextInt(NUM.length)]);
            } else {
                stringBuffer.append(WORD[random.nextInt(WORD.length)]);
            }
            flag = !flag;
        }
        // 返回
        return stringBuffer.toString();
    }

    /**
     * 验证password工具类
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println(randomPassword());
        Thread.sleep(100);
        System.out.println(randomPassword());
        Thread.sleep(100);
        System.out.println(randomPassword());
    }
}
