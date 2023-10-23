package com.github.admin.api.utils;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

public class JSONUtils {
    public static boolean isJsonObject(String content) {
        if ("".equals(content) || null == content) {
            return false;
        }
        try {
            JSONObject.parseObject(content);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isJsonArray(String content) {
        if ("".equals(content) || null == content) {
            return false;
        }
        try {
            JSONArray.parseArray(content);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
