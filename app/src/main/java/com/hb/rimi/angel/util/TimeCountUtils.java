package com.hb.rimi.angel.util;

import android.os.CountDownTimer;
import android.widget.Button;

import com.hb.rimi.angel.R;

/**
 * Created by hp on 2016/6/2.
 */
public class TimeCountUtils extends CountDownTimer {
    private Button btn;
    /**
     *
     * @param millisInFuture 总时长
     * @param countDownInterval 计时时间间隔
     */
    public TimeCountUtils(long millisInFuture, long countDownInterval,Button btn) {
        super(millisInFuture, countDownInterval);
        this.btn = btn;
    }

    /**
     * 计时过程
     * @param l
     */
    @Override
    public void onTick(long l) {
        btn.setEnabled(false);
        btn.setBackgroundResource(R.mipmap.btn_clicked);
        btn.setText(l/1000 + "");
    }

    /**
     * 计时完成
     */
    @Override
    public void onFinish() {
        btn.setText("重试");
        btn.setEnabled(true);
        btn.setBackgroundResource(R.mipmap.btn_changepwd);
    }
}
