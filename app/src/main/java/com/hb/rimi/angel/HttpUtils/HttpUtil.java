package com.hb.rimi.angel.HttpUtils;

import android.util.Log;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * TODO :xutils网络请求工具类
 * Created by hb on 2016-04-22.
 */
public class HttpUtil {

    /**
     * 回调接口
     */
    public interface IHttpResult {

        void onSuccess(String result);

        void onError(Throwable ex, boolean isOnCallback);

//        void onCancelled(String canceStr);

//        void onFinished();

    }

    /**
     * 网络请求
     *
     * @param url
     * @param params
     * @param result
     */
    public static void doHttp(String url, HashMap<String, String> params, IHttpResult result) {
        if (params == null || params.size() == 0) {
            doGet(url, result);
        } else if (params.size() > 0) {
            doPost(url, params, result);
        }
    }

    /**
     * post请求
     *
     * @param url
     * @param params
     * @param iHttpResult
     */
    private static void doPost(String url, HashMap<String, String> params, final IHttpResult iHttpResult) {
        final RequestParams requestParams = new RequestParams(url);
        Iterator iter = params.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            String val = (String) entry.getValue();
            requestParams.addBodyParameter(key, val);
        }
        x.http().post(requestParams, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Log.i("info","====链接是==》"+requestParams);
                System.out.println("====链接是==》"+requestParams);
                Log.i("info","====数据是==》"+result);
                System.out.println("====数据是==》"+result);
                iHttpResult.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("====链接是==》"+requestParams);
                System.out.println("====ex==》"+ex.getMessage());
                iHttpResult.onError(ex, isOnCallback);
            }

            @Override
            public void onCancelled(CancelledException cex) {
//                iHttpResult.onCancelled(cex.getMessage());
            }

            @Override
            public void onFinished() {
//                iHttpResult.onFinished();
            }
            });


    }

    /**
     * get 请求
     *
     * @param url
     * @param iHttpResult
     */
    private static void doGet(String url, final IHttpResult iHttpResult) {
        final  RequestParams params = new RequestParams(url);

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("====链接是==》"+params);
                iHttpResult.onSuccess(result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                iHttpResult.onError(ex, isOnCallback);
                System.out.println("====onError==》"+ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
//                iHttpResult.onCancelled(cex.getMessage());
                System.out.println("====onCancelled==》"+cex.getMessage());
            }

            @Override
            public void onFinished() {
//                iHttpResult.onFinished();
                System.out.println("onFinished");

            }
        });
    }

    public static void doArrayPost(String url, HashMap<String, ArrayList<String>> params, final IHttpResult iHttpResult) {
        final RequestParams requestParams = new RequestParams(url);
        Iterator iter = params.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            String val = (String) entry.getValue();
            requestParams.addBodyParameter(key, val);
        }
        x.http().post(requestParams, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Log.i("info","====链接是==》"+requestParams);
                System.out.println("====链接是==》"+requestParams);
                Log.i("info","====数据是==》"+result);
                System.out.println("====数据是==》"+result);
                iHttpResult.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("====链接是==》"+requestParams);
                System.out.println("====ex==》"+ex.getMessage());
                iHttpResult.onError(ex, isOnCallback);
            }

            @Override
            public void onCancelled(CancelledException cex) {
//                iHttpResult.onCancelled(cex.getMessage());
            }

            @Override
            public void onFinished() {
//                iHttpResult.onFinished();
            }
        });


    }

}
