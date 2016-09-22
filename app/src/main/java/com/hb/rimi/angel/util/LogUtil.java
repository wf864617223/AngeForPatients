package com.hb.rimi.angel.util;

import android.content.Context;
import android.util.Log;

/**
 * 自定义Log类，可先在application里面实例化，方便统一开启和关闭log
 * Created by Administrator on 2016/5/8.
 */
public class LogUtil {
    private Context context;
    private String defaultTag;
    private static volatile LogUtil logUtil;

    private static boolean isDebug = false;

    public LogUtil(Context context, String tagName, boolean isDebug) {
        this.context = context;
        this.isDebug = isDebug;
        if (null == tagName) {
            this.defaultTag = context.getApplicationInfo().packageName;
        } else {
            this.defaultTag = tagName;
        }
    }

    //单例模式
    public static LogUtil getInstance(Context context, String tagName, boolean isDebug) {
        if (logUtil == null) {
            synchronized (LogUtil.class) {
                if (logUtil == null) {
                    return new LogUtil(context, tagName, isDebug);
                }
            }
        }
        return logUtil;
    }

    public void d(String msg) {
        if (isDebug) {
            Log.d(defaultTag, msg);

        }
    }

    public void e(String msg) {
        if (isDebug) {
            Log.e(defaultTag, msg);
        }
    }

    public void i(String msg) {
        if (isDebug) {
            Log.i(defaultTag, msg);
        }
    }

    public void v(String msg) {
        if (isDebug) {
            Log.v(defaultTag, msg);
        }
    }

    public void w(String msg) {
        if (isDebug) {
            Log.w(defaultTag, msg);
        }
    }

}
