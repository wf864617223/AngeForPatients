package com.hb.rimi.angel.http;

/**
 * Created by hp on 2016/6/8.
 */
public interface YCodeHttp {

    void onCheckSuccess(String result);
    void onCheckError(Throwable ex, boolean isOnCallback);
}
