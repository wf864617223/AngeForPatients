package com.hb.rimi.angel.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Description Gson工具类
 */
public class GsonTools {
    public static <T> T getBean(String jsonString, Class<T> cls) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, cls);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public static <T> List<T> getBeans(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, new TypeToken<List<T>>() {
            }.getType());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 返回列表时请用此方法
     *
     * @param jsonString
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> getBeansNew(String jsonString, Class<T[]> cls) {
        T[] ts = new Gson().fromJson(jsonString, cls);
        return Arrays.asList(ts);
    }

    public static List<String> getList(String jsonString) {
        List<String> list = new ArrayList<String>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, new TypeToken<List<String>>() {
            }.getType());
        } catch (Exception e) {
            // TODO: handle exception  
        }
        return list;

    }

    public static List<Map<String, Object>> listKeyMap(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, new TypeToken<List<Map<String, Object>>>() {
            }.getType());
        } catch (Exception e) {
            // TODO: handle exception  
        }
        return list;
    }

    /**
     * bean转字符串
     *
     * @param cls
     * @return
     */
    public static String getStringOfBean(Object cls) {
        Gson gson = new Gson();
        String strBean = gson.toJson(cls);
        return strBean;
    }
}
