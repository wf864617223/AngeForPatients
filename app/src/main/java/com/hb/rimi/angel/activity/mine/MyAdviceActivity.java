package com.hb.rimi.angel.activity.mine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.util.T;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 投诉建议
 */
@ContentView(R.layout.activity_my_advice)
public class MyAdviceActivity extends AppCompatActivity {
    @Bind(R.id.maa_toolbar)
    Toolbar maa_toolbar;
    @Bind(R.id.et_myAdvice)
    EditText etMyAdvice;
    @Bind(R.id.btn_commit)
    Button btnCommit;
    private String myAdvice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        maa_toolbar.setTitle("");
        setSupportActionBar(maa_toolbar);
        maa_toolbar.setNavigationIcon(R.mipmap.icon_reg_back);
        maa_toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        maa_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initView() {

        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAdvice = etMyAdvice.getText().toString();
                String replace = myAdvice.replace(" ", "");
                if (TextUtils.isEmpty(replace)) {
                    T.ShowToast(MyAdviceActivity.this, "您没有输入意见", 0);
                    etMyAdvice.setText("");
                } else {
                    HashMap<String, String> parms = new HashMap<String, String>();
                    parms.put("title", "建议");
                    parms.put("content", replace);
                    int length = replace.length();
                    if(length>140){
                        T.ShowToast(MyAdviceActivity.this,"字数超过140字上限，建议精简后再提交");
                    }else{
                        //这里提交反馈建议
                        HttpUtil.doHttp(HttpContanst.ADVICE_POST, parms, new HttpUtil.IHttpResult() {
                            @Override
                            public void onSuccess(String result) {
                                T.ShowToast(MyAdviceActivity.this, "您的建议已经提交，感谢您的反馈", 0);
                                etMyAdvice.setText("");
                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                T.ShowToast(MyAdviceActivity.this, "服务器出错了，请稍后再试");
                            }
                        });
                    }


                }
            }
        });
    }
}
