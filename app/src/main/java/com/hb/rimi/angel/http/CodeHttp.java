package com.hb.rimi.angel.http;

import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.bean.ResBean;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.ShareInfoUtil;
import com.hb.rimi.angel.util.StringUtil;
import com.hb.rimi.angel.util.T;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 获取验证码和校验验证码的类
 * Created by hp on 2016/6/8.
 */
public class CodeHttp {
    public ICodeHttp iCodeHttp;
    public YCodeHttp yCodeHttp;

    public CodeHttp(ICodeHttp iCodeHttp,YCodeHttp yCodeHttp) {
        this.iCodeHttp = iCodeHttp;
        this.yCodeHttp = yCodeHttp;
    }

    public void sendCode(HashMap<String, String> params) {
        //发验证码
        HttpUtil.doHttp(HttpContanst.SEND_CODE, params, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                iCodeHttp.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                iCodeHttp.onError(ex, isOnCallback);
            }
        });


    }

    public void checkCode(HashMap<String, String> params) {
        HttpUtil.doHttp(HttpContanst.CHECK_CODE, params, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                yCodeHttp.onCheckSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                yCodeHttp.onCheckError(ex, isOnCallback);
            }
        });

    }
}
