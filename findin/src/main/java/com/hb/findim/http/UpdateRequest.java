package com.hb.findim.http;

import com.xiaomi.network.HostManager;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * 更新Request
 *
 * @author hb
 */
public class UpdateRequest {
    public static String sendPost(List<NameValuePair> ps,
                                  String strUrl) {


        HttpParams params = null;
        HttpResponse httpResponse = null;
        String result = null;
        HttpClient client = null;
        HttpPost httpPost = null;
        try {
            client = new DefaultHttpClient();
            // Represents a collection of HTTP protocol and framework parameters
            params = client.getParams();
            if (params == null) {
                params = new BasicHttpParams();
            }
            // set timeout
            HttpConnectionParams.setConnectionTimeout(params, 10 * 1000);// 连接时间
            HttpConnectionParams.setSoTimeout(params, 10 * 1000);// 套接字超时时间

            HttpGet  httpGet= new HttpGet(strUrl);
            httpGet.setParams(params);
            // 设置后不会出现乱码
            httpGet.setHeader("Content-Type",
                    "application/x-www-form-urlencoded; charset=utf-8");

//            if (ps != null) {
//                httpGet.setEntity(new UrlEncodedFormEntity(ps, HTTP.UTF_8));
//            }
            httpResponse = new DefaultHttpClient().execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(httpResponse.getEntity(),
                        HTTP.UTF_8);
                // System.out.println("返回数据:" + result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                try {
                    client.getConnectionManager().shutdown();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        return result;
    }
}
