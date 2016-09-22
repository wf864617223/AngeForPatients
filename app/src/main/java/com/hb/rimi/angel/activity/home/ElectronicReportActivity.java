package com.hb.rimi.angel.activity.home;
/**
 * 电子报告页面
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.adapter.ElectorReportAdapter;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.bean.ListReport;
import com.hb.rimi.angel.bean.UserInfo;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.util.DisplayUtil;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.ShareInfoUtil;
import com.hb.rimi.angel.util.StringUtil;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.view.PrgDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

@ContentView(R.layout.activity_electronic_report)
public class ElectronicReportActivity extends AppCompatActivity {

    @Bind(R.id.report_list)
    ListView reportList;
    @Bind(R.id.toolbar_check_report)
    Toolbar toolbarCheckReport;
    Context mContext;
    private PrgDialog prgDialog;
    private ElectorReportAdapter adapter;
    private List<ListReport.ResultBean> result1 = new ArrayList<>();
    private AlertDialog myDialog = null;
    private EditText inputServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        x.view().inject(this);
        ButterKnife.bind(this);
        mContext = ElectronicReportActivity.this;
        initData();
        initView();

    }

    private void initView() {
        reportList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*EditText inputServer = new EditText(mContext);
                inputServer.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                //inputServer.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

                inputServer.setHint("为保护您的隐私，请输入登录密码");*/
                int mobileHeight = DisplayUtil.getMobileHeight(mContext);
                int mobileWidth = DisplayUtil.getMobileWidth(mContext);
                final int id = result1.get(i).getId();
                final int type = result1.get(i).getType();
                myDialog = new AlertDialog.Builder(mContext, R.style.CustomProgressDialog).create();
                myDialog.show();
                Window alertWindow = myDialog.getWindow();
                WindowManager.LayoutParams lParams = alertWindow.getAttributes();
                myDialog.getWindow().setContentView(R.layout.alertdialog_show);

                myDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                myDialog.getWindow().findViewById(R.id.btn_alert_ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inputServer = (EditText) myDialog.getWindow().findViewById(R.id.et_alert_pwd);
                        String input = inputServer.getText().toString();
                        if (TextUtils.isEmpty(input)) {
                            T.ShowToast(mContext, "输入不能为空", 0);
                        } else {
                            //判断密码是否输入正确
                            String pwd = ShareInfoUtil.readLoginPwd(mContext);
                            if (pwd.equals(input)) {
                                Intent intent = new Intent(mContext, ReportInfoActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("id", id);
                                bundle.putInt("type", type);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                            } else {
                                T.ShowToast(ElectronicReportActivity.this, "密码输入不正确");
                            }
                        }
                    }
                });
                lParams.width = mobileWidth;
                lParams.height = mobileHeight * 1 / 3;


                /*builder.setTitle("查看报告").setIcon(R.mipmap.warning_inpwd).setView(inputServer)
                        .setNegativeButton("123",null)
                        .setPositiveButton("456",null)
                .setNeutralButton("ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        String input = inputServer.getText().toString();

                        if (TextUtils.isEmpty(input)) {
                            T.ShowToast(mContext, "输入不能为空", 0);
                        } else {
                            //判断密码是否输入正确
                            String pwd = ShareInfoUtil.readLoginPwd(mContext);
                            if (pwd.equals(input)) {
                                Intent intent = new Intent(mContext, ReportInfoActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("id", id);
                                bundle.putInt("type", type);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            } else {
                                T.ShowToast(ElectronicReportActivity.this, "密码输入不正确");
                            }
                        }
                    }
                });*/
                //builder.show();

                //T.ShowToast(mContext,"点了"+i,0);


            }
        });

    }

    private void initData() {
        toolbarCheckReport.setTitle("");
        setSupportActionBar(toolbarCheckReport);
        toolbarCheckReport.setNavigationIcon(R.mipmap.icon_reg_back);
        toolbarCheckReport.setTitleTextColor(getResources().getColor(R.color.white));
        toolbarCheckReport.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        prgDialog = new PrgDialog(mContext);
        UserInfo.ResultBean resultBean = ShareInfoUtil.readResultBean(ElectronicReportActivity.this);
        String ic_no = resultBean.getIC_NO();
        HashMap<String, String> params = new HashMap<String, String>();
        //params.put("IcNo","201480619");//62010622
//        params.put("IcNo", "62010622");
        params.put("IcNo", ic_no);
        if (ic_no.equals("")) {
            T.ShowToast(ElectronicReportActivity.this, "您没有就诊卡号，请先填写您的信息");
            prgDialog.closeDialog();
        } else {
            HttpUtil.doHttp(HttpContanst.ELECTROIC_REPORT, params, new HttpUtil.IHttpResult() {
                @Override
                public void onSuccess(String result) {
                    ProjectApplication.logUtil.d(result);
                    if (StringUtil.isNotBlank(result)) {
                        ListReport listReport = GsonTools.getBean(result, ListReport.class);
                        int status = listReport.getStatus();
                        if (status == 0) {
                            result1 = listReport.getResult();
                            adapter = new ElectorReportAdapter(result1, mContext);
                            reportList.setAdapter(adapter);
                        } else {
                            T.ShowToast(mContext, "暂无数据", 0);
                        }
                    }
                    prgDialog.closeDialog();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    T.ShowToast(mContext, "请求失败，请重试", 0);
                    prgDialog.closeDialog();
                }
            });
        }
    }
}
