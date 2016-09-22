package com.hb.generalupdate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.hb.update.UpdateManager;
import com.hb.util.SdUtils;
import com.hb.util.StringUtils;

import java.util.Map;

/**
 * 主工程集成使用示例，正式发布时删除此TestTwoActivity及xml里面配置。
 *
 * @author hb 2015-11-23
 */
public class TestTwoActivity extends Activity implements InitUpdateInterface {

    private String TAG = TestTwoActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_two);
        // 调用一次
        checkUpdate();
    }

    @Override
    public void checkUpdate() {
        ConnectivityManager cwjManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cwjManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            CheckVerRunnable chackRunnable = new CheckVerRunnable();
            Thread checkThread = new Thread(chackRunnable);
            checkThread.start();
        } else {
            Log.d("GeneralUpdateLib",
                    "Network connection failed, please check the network.");
        }
    }

    /**
     * request server checkvercode.json file.
     *
     * @author TXG fase参数格式：{ "appid": "hzz1919", "iOS": { "version": "2.0.3",
     *         "note": "1.优化商品销售，库存查询 ,2.提升查询效率", "title": "更新日志:", "url": null
     *         }, "android": { "version": "2.0.8", "note":
     *         "1.新增语音验证码,2.库存新增商品20倍会员价查看,3.以及烟分包/条", "title": "更新日志:", "url":
     *         "http://182.139.182.80:9999/app/tAppUpgradeController.do?download"
     *         } }
     *         <p/>
     *         true 参数格式：{"verName":"V1.0更新日志: ","verCode":"1","downloadUrl":
     *         "http://218.84.107.68:803/trackbackdown/TraceSourceClient.apk"}
     *
     *         http://hzzapp.1919.cn/app/tAppUpgradeController.do?update&appId=hzz1919
     *         http://218.84.107.68:803/trackbackdown/checkvercodejg.json
     */
    class CheckVerRunnable implements Runnable {
        @Override
        public void run() {
            // replace your .json file url.
            boolean isUpdate = UpdateManager
                    .getUpdateInfo(
                            TestTwoActivity.this,
                            "http://218.84.107.68:803/trackbackdown/checkvercodejg.json",
                            false);
            if (isUpdate) {
                updateHandler.sendEmptyMessage(1);
            } else {
                updateHandler.sendEmptyMessage(0);
            }
        }
    }

    /**
     * handler rec.
     */
    @SuppressLint("HandlerLeak")
    Handler updateHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    // To do something.
                    Log.d("GeneralUpdateLib", "There's no new version here.");
                    break;
                case 1:
                    // Find new version.
                    UpdateManager.newUpdate(TestTwoActivity.this);
                    break;
                default:
                    break;
            }
        }
    };

}
