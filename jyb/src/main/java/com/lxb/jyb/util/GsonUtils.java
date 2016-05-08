package com.lxb.jyb.util;

import android.text.TextUtils;

import com.google.gson.Gson;

/**
 * Gson工具类
 * Created by zxp on 2015/11/5.
 */
public class GsonUtils {

    /**
     * 将jsonStr转换成cl对象
     *
     * @param jsonStr
     * @return
     */
    public static <T extends Object> T jsonToBean(String jsonStr, Class<?> cl) {

        Object obj = null;
        if (getInstance() != null) {
            if (!TextUtils.isEmpty(jsonStr))
                obj = getInstance().fromJson(jsonStr, cl);
        }
        return (T) obj;
    }

    private GsonUtils() {
    }

    private static class GsonHolder {
        private static final Gson INSTANCE = new Gson();

    }

    public static final Gson getInstance() {
        return GsonHolder.INSTANCE;
    }
}
