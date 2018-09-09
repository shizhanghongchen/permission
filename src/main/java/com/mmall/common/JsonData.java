package com.mmall.common;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class JsonData {

    private boolean ret;

    private String msg;

    private Object data;

    public JsonData(boolean ret) {
        this.ret = ret;
    }

    public static com.mmall.common.JsonData success(Object object, String msg) {
        com.mmall.common.JsonData jsonData = new com.mmall.common.JsonData(true);
        jsonData.data = object;
        jsonData.msg = msg;
        return jsonData;
    }

    public static com.mmall.common.JsonData success(Object object) {
        com.mmall.common.JsonData jsonData = new com.mmall.common.JsonData(true);
        jsonData.data = object;
        return jsonData;
    }

    public static com.mmall.common.JsonData success() {
        return new com.mmall.common.JsonData(true);
    }

    public static com.mmall.common.JsonData fail(String msg) {
        com.mmall.common.JsonData jsonData = new com.mmall.common.JsonData(false);
        jsonData.msg = msg;
        return jsonData;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("ret", ret);
        result.put("msg", msg);
        result.put("data", data);
        return result;
    }
}
