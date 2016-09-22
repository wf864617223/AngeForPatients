package com.hb.rimi.angel.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hb.rimi.angel.R;
import com.hb.rimi.angel.activity.home.MedicalVoucherActivity;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.contanst.Contanst;
import com.hb.rimi.angel.util.T;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private static final String TAG = "WXPayEntryActivity";
    private IWXAPI api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxpay_entry);
        api = WXAPIFactory.createWXAPI(this, Contanst.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_AUTH_DENIED://认证被否决
                    T.ShowToast(this, "认证被否决");
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_COMM://一般错误
                    T.ShowToast(this, "支付失败，一般错误");
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_OK://正确返回
                    T.ShowToast(this, "支付成功");
                    ProjectApplication.getInstanceApp().exit();
                    Intent mvaIntent = new Intent(this, MedicalVoucherActivity.class);
                    startActivity(mvaIntent);
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_SENT_FAILED://发送失败
                    T.ShowToast(this, "支付失败，发送失败");
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_UNSUPPORT://不支持错误
                    T.ShowToast(this, "支付失败，不支持错误");
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
                    T.ShowToast(this, "支付失败，用户取消");
                    finish();
                    break;
            }
        }
    }
}
