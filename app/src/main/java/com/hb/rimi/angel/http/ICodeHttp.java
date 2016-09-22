package com.hb.rimi.angel.http;

/**
 * Created by hp on 2016/6/8.
 */
public interface ICodeHttp {
    void onSuccess(String result);
    void onError(Throwable ex, boolean isOnCallback);
}
