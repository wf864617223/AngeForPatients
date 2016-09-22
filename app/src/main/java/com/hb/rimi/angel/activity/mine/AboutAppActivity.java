package com.hb.rimi.angel.activity.mine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hb.generalupdate.InitUpdateInterface;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.util.StringUtil;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.view.PrgDialog;
import com.hb.update.UpdateManager;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutAppActivity extends AppCompatActivity implements InitUpdateInterface {

    @Bind(R.id.iv_finish)
    ImageView ivFinish;
    @Bind(R.id.btn_check_update)
    Button btnCheckUpdate;
    @Bind(R.id.aaa_tv_version_name)
    TextView aaa_tv_version_name;
    private PrgDialog dialog;
    /**
     * handler rec.
     */
    @SuppressLint("HandlerLeak")
    Handler updateHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            btnCheckUpdate.setEnabled(true);
            if (dialog != null) {
                dialog.closeDialog();
            }
            switch (msg.what) {
                case 0:
                    // To do something.
                    T.ShowToast(AboutAppActivity.this, "暂无更新");
                    Log.d("GeneralUpdateLib", "There's no new version here.");
                    break;
                case 1:
                    // Find new version.
                    UpdateManager.newUpdate(AboutAppActivity.this);
                    break;
                default:
                    break;
            }
        }
    };
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        mContext = AboutAppActivity.this;
        initData();
    }

    private void initData() {
        aaa_tv_version_name.setText(StringUtil.getVerName(mContext));
        ivFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnCheckUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里检查更新
                // 调用一次
                v.setEnabled(false);
                dialog = new PrgDialog(mContext);
                checkUpdate();
            }
        });
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

    class CheckVerRunnable implements Runnable {
        @Override
        public void run() {
            // replace your .json file url.
            boolean isUpdate = UpdateManager
                    .getUpdateInfo(
                            AboutAppActivity.this,
                            HttpContanst.UPDATE_URL,
                            true);
            if (isUpdate) {
                updateHandler.sendEmptyMessage(1);
            } else {
                updateHandler.sendEmptyMessage(0);
            }
        }
    }
}
