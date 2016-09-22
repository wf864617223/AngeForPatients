package com.hb.rimi.angel.activity.home;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.bean.AddUserBean;
import com.hb.rimi.angel.bean.ResBean;
import com.hb.rimi.angel.bean.UserInfo;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.util.DateChooseUtils;
import com.hb.rimi.angel.util.DateUtils;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.IntentManager;
import com.hb.rimi.angel.util.ShareInfoUtil;
import com.hb.rimi.angel.util.StringUtil;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.view.PrgDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

@ContentView(R.layout.activity_adduser_info)
public class AddUserInfoActivity extends AppCompatActivity implements View.OnClickListener {

    public double item_id;//id
    @Bind(R.id.imci_toolbar)
    Toolbar imci_toolbar;
    @Bind(R.id.rb_man)
    RadioButton rbMan;
    @Bind(R.id.rb_woman)
    RadioButton rbWoman;
    @Bind(R.id.btn_addUser_next)
    Button btnAddUserNext;
    @Bind(R.id.tv_add_userName)
    EditText tvAddUserName;
    @Bind(R.id.tv_add_userBir)
    TextView tvAddUserBir;
    @Bind(R.id.imci_ll_last_menses)
    LinearLayout imciLlLastMenses;
    @Bind(R.id.imci_temp_icno)
    TextView imci_temp_icno;
    Context mContext;
    //B超支付界面
    String item_code;
    String item_name;
    String item_price;
    //类型
    String targetType;
    @Bind(R.id.baa_et_last_menses)
    TextView baaEtLastMenses;
    @Bind(R.id.ll_date_choose)
    LinearLayout llDateChoose;


    //预约支付界面
    private String id;
    private String name;
    private String code;
    private String url;
    private String position;
    private String date;
    private String week;
    private String dptId;//科室ID
    private String dptName;//科室名称
    private String userSex;
    private String icNo;
    private Calendar calendar;// 用来装日期的
    private DatePickerDialog dialog;
    private PrgDialog prgDialog;
//    private String ic_no;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProjectApplication.getInstanceApp().addActivity(this);
        mContext = AddUserInfoActivity.this;
        x.view().inject(this);
        ButterKnife.bind(this);
        initIntent();
        getIcNo();
        initData();
    }


    private void initIntent() {
        targetType = getIntent().getExtras().getString("targetType");
        if ("0".equals(targetType)) {
            imciLlLastMenses.setVisibility(View.VISIBLE);
            item_code = getIntent().getExtras().getString("item_code");
            item_name = getIntent().getExtras().getString("item_name");
            item_price = getIntent().getExtras().getString("item_price");
            item_id = getIntent().getExtras().getDouble("item_id");

        } else if ("1".equals(targetType)) {
            imciLlLastMenses.setVisibility(View.GONE);
            id = getIntent().getExtras().getString("id");
            name = getIntent().getExtras().getString("name");
            code = getIntent().getExtras().getString("code");
            url = getIntent().getExtras().getString("url");
            position = getIntent().getExtras().getString("position");
            dptId = getIntent().getExtras().getString("dptId");
            dptName = getIntent().getExtras().getString("dptName");
            date = getIntent().getExtras().getString("date");
            week = getIntent().getExtras().getString("week");
        }

    }


    private void initData() {
        imci_toolbar.setTitle("");
        setSupportActionBar(imci_toolbar);
        tvAddUserBir.setEnabled(false);
        imci_toolbar.setNavigationIcon(R.mipmap.icon_reg_back);
        imci_toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        imci_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
        rbWoman.setChecked(true);
        llDateChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateChooseUtils dateChooseUtils = new DateChooseUtils(AddUserInfoActivity.this, tvAddUserBir);
                dateChooseUtils.getdate();
            }
        });
        imciLlLastMenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateChooseUtils dateChooseUtils = new DateChooseUtils(AddUserInfoActivity.this, baaEtLastMenses);
                dateChooseUtils.getdate();
            }
        });

    }


    @OnClick({R.id.btn_addUser_next})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_addUser_next:
                Date date1 = new Date();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                //将当前日期转成String
                String format1 = format.format(date1);

                //末次月经
                //判断本地ICNO是否存在
                String menses = baaEtLastMenses.getText().toString();
                String userBirth = tvAddUserBir.getText().toString();
                String userName = tvAddUserName.getText().toString();
                String mobile = ShareInfoUtil.readLoginPhone(mContext);

                if(userName.length()>20){
                    T.ShowToast(mContext,"您输入的用户名过长");
                    return;
                }
                if (rbMan.isChecked()) {
                    userSex = "1";
                } else if (rbWoman.isChecked()) {
                    userSex = "0";
                }
                UserInfo.ResultBean userInfo = ShareInfoUtil.readResultBean(mContext);
                // 如果存在本地ICNO
                if (StringUtil.isNotBlank(userInfo.getIC_NO())) {
                    //如果在B超检查页面，那么直接跟新末次已经
                    if ("0".equals(targetType)) {
                        if (StringUtil.isNotBlank(userInfo.getMenses())) {
                            //如果有末次月经
                            Bundle bundle = new Bundle();
                            bundle.putString("item_code", item_code);
                            bundle.putString("item_name", item_name);
                            bundle.putString("item_price", item_price);
                            bundle.putDouble("item_id", item_id);
                            ProjectApplication.intentManager.toBAppointmentPayActivity(bundle);
                        } else {
                            //如果没有则更新末次月经
                            if (DateUtils.compare_date(format1, menses) == -1) {
                                T.ShowToast(AddUserInfoActivity.this, "末次月经日期选择有误");
                                break;
                            }

                            HashMap<String, String> params = new HashMap<>();
                            params.put("menses", menses);
                            params.put("cid", ShareInfoUtil.readCid(mContext));
                            updateLastMenses(params);
                        }
                    } else if ("1".equals(targetType)) {
                        //如果在预约挂号界面，那么无需做判断直接跳转支付
                        Bundle bundle = new Bundle();
                        bundle.putString("id", id);
                        bundle.putString("name", name);
                        bundle.putString("code", code);
                        bundle.putString("url", url);
                        bundle.putString("dptId", dptId);
                        bundle.putString("dptName", dptName);
                        bundle.putString("position", position);
                        bundle.putString("date", date);
                        bundle.putString("week", week);
                        ProjectApplication.intentManager.toAppointmentPayActivity(bundle);
                    }

                } else {
                    //如果不存在本地ICNO
                    // 如果在B超页面，那么直接新增会员卡信息带上末次月经
                    if ("0".equals(targetType)) {

                        if (TextUtils.isEmpty(userName)) {
                            T.ShowToast(AddUserInfoActivity.this, "请输入姓名");
                            return;
                        }
                        if (TextUtils.isEmpty(userBirth)) {
                            T.ShowToast(AddUserInfoActivity.this, "请选择出生日期");
                            return;
                        }
                        if (DateUtils.compare_date(format1, userBirth) == -1) {
                            T.ShowToast(AddUserInfoActivity.this, "出生日期选择有误");
                            break;
                        }
                        if(TextUtils.isEmpty(menses)){
                            T.ShowToast(AddUserInfoActivity.this, "请选择末次月经日期");
                            return;
                        }

                        if (DateUtils.compare_date(format1, menses) == -1) {
                            T.ShowToast(AddUserInfoActivity.this, "末次月经日期选择有误");
                            return;
                        }

                        HashMap<String, String> params = new HashMap<>();

                        params.put("action", "Add");
                        params.put("name", userName);
                        params.put("tel", mobile);
                        params.put("birthday", userBirth);
                        params.put("icNo", icNo);
                        params.put("sex", userSex);
                        params.put("menses", menses);
                        //T.ShowToast(mContext, params.toString());
                        addVipInfo(params);
                        System.out.println("-----" + params);
                    } else if ("1".equals(targetType)) {

                        if (TextUtils.isEmpty(userName)) {
                            T.ShowToast(AddUserInfoActivity.this, "请输入姓名");
                            return;
                        }
                        if (TextUtils.isEmpty(userBirth)) {
                            T.ShowToast(AddUserInfoActivity.this, "请选择出生日期");
                            return;
                        }
                        if (DateUtils.compare_date(format1, userBirth) == -1) {
                            T.ShowToast(AddUserInfoActivity.this, "出生日期选择有误");
                            break;
                        }

                        // 如果在预约挂号界面，那么新增会员卡信息
                        HashMap<String, String> params = new HashMap<>();
                        params.put("action", "Add");
                        params.put("name", userName);
                        params.put("tel", mobile);
                        params.put("birthday", userBirth);
                        params.put("icNo", icNo);
                        params.put("sex", userSex); //0 ,1
                        //T.ShowToast(mContext, params.toString());
                        addVipInfo(params);
                        System.out.println("-----" + params);
                    }
                }
                break;
        }
    }

    public void getIcNo() {

        String token = ShareInfoUtil.readToken(mContext);
        UserInfo.ResultBean userInfo = ShareInfoUtil.readResultBean(mContext);

        if (StringUtil.isNotBlank(userInfo.getIC_NO())) {
            icNo = userInfo.getIC_NO();
            imci_temp_icno.setText(userInfo.getIC_NO());
            tvAddUserName.setText(userInfo.getName());
            tvAddUserBir.setText(userInfo.getBirthday());
            rbMan.setEnabled(false);
            rbWoman.setEnabled(false);
            tvAddUserName.setEnabled(false);
            tvAddUserName.setEnabled(false);

            if ("0".equals(targetType)) {
                T.ShowToast(mContext, "请补填末次月经");
            }
        } else {
            HttpUtil.doHttp(HttpContanst.TEMP_ICNO + "?token=" + token, null, new HttpUtil.IHttpResult() {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int status = jsonObject.getInt("status");
                        if (status == 0) {
                            String result1 = jsonObject.getString("result");
                            JSONObject jObject = new JSONObject(result1);
                            icNo = jObject.getString("ic_no");
                            imci_temp_icno.setText(icNo);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    T.ShowToast(mContext, "临时就诊卡号生成失败，请稍后重试");
                    btnAddUserNext.setClickable(false);
                    btnAddUserNext.setBackgroundColor(getResources().getColor(R.color.color_bg_normal));
                }
            });
        }

    }

    /**
     * 直接更新末次月经
     */
    public void updateLastMenses(final HashMap<String, String> params) {

        HttpUtil.doHttp(HttpContanst.UPDATE_LAST_MENSES, params, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                ResBean resBean = GsonTools.getBean(result, ResBean.class);
                int status = resBean.getStatus();
                if (status == 0) {
//                    Bundle bundle = new Bundle();
                    if ("0".equals(targetType)) {//检查缴费
//                        bundle.putString("item_code", item_code);
//                        bundle.putString("item_name", item_name);
//                        bundle.putString("item_price", item_price);
//                        bundle.putDouble("item_id", item_id);


                        ShareInfoUtil.saveParams(mContext, "b_menses", params.get("menses"));
                        ShareInfoUtil.saveParams(mContext, "b_name", params.get("name"));
                        ShareInfoUtil.saveParams(mContext, "b_birthday", params.get("birthday"));
                        ShareInfoUtil.saveParams(mContext, "b_userSex", params.get("sex"));

                        //保存一次卡信息
                        UserInfo.ResultBean resultBean = ShareInfoUtil.readResultBean(mContext);
                        resultBean.setMenses(params.get("menses"));
                        ShareInfoUtil.saveReaultBean(mContext, resultBean);

//                        ProjectApplication.intentManager.toBAppointmentPayActivity(bundle);
                    } else if ("1".equals(targetType)) {  //预约挂号
//                        bundle.putString("id", id);
//                        bundle.putString("name", name);
//                        bundle.putString("code", code);
//                        bundle.putString("url", url);
//                        bundle.putString("dptId", dptId);
//                        bundle.putString("dptName", dptName);
//                        bundle.putString("position", position);
//                        bundle.putString("date", date);
//                        bundle.putString("week", week);


//                        ShareInfoUtil.saveParams(mContext, "b_menses", params.get("menses"));
//                        ShareInfoUtil.saveParams(mContext, "b_name", params.get("name"));
//                        ShareInfoUtil.saveParams(mContext, "b_birthday", params.get("birthday"));
//                        ShareInfoUtil.saveParams(mContext, "b_userSex", params.get("sex"));
//
//                        //保存一次卡信息
//                        UserInfo.ResultBean resultBean = ShareInfoUtil.readResultBean(mContext);
//                        resultBean.setIC_NO(icNo);
//                        resultBean.setName(params.get("name"));
//                        resultBean.setBirthday( params.get("birthday"));
//                        resultBean.setSex(params.get("sex"));
//                        resultBean.setMenses(params.get("menses"));
//                        ShareInfoUtil.saveReaultBean(mContext,resultBean);


//                        ProjectApplication.intentManager.toAppointmentPayActivity(bundle);
                    }
                    T.ShowToast(AddUserInfoActivity.this, "会员资料更新成功");

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("updateSuc", "true");
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    String message = resBean.getMessage();
                    T.ShowToast(AddUserInfoActivity.this, message);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.ShowToast(mContext, "请求失败，请稍后重试");
            }
        });
    }

    /**
     * 新增会员信息
     */
    public void addVipInfo(final HashMap<String, String> params) {

        HttpUtil.doHttp(HttpContanst.ADD_USER_INFO, params, new HttpUtil.IHttpResult() {


            @Override
            public void onSuccess(String result) {
                AddUserBean addUserBean = GsonTools.getBean(result, AddUserBean.class);
                int status = addUserBean.getStatus();
                if (status == 0) {
//                    Bundle bundle = new Bundle();
                    if ("0".equals(targetType)) {

                        ShareInfoUtil.saveParams(mContext, "d_name", params.get("name"));
                        ShareInfoUtil.saveParams(mContext, "d_birthday", params.get("birthday"));
                        ShareInfoUtil.saveParams(mContext, "d_userSex", params.get("sex"));

                        //保存一次卡信息
                        UserInfo.ResultBean resultBean = ShareInfoUtil.readResultBean(mContext);
                        resultBean.setIC_NO(icNo);
                        resultBean.setName(params.get("name"));
                        resultBean.setBirthday(params.get("birthday"));
                        resultBean.setSex(params.get("sex"));

                        ShareInfoUtil.saveReaultBean(mContext, resultBean);


//                        bundle.putString("item_code", item_code);
//                        bundle.putString("item_name", item_name);
//                        bundle.putString("item_price", item_price);
//                        bundle.putDouble("item_id", item_id);
//                        ProjectApplication.intentManager.toBAppointmentPayActivity(bundle);
                    } else if ("1".equals(targetType)) {

                        ShareInfoUtil.saveParams(mContext, "b_menses", params.get("menses"));
                        ShareInfoUtil.saveParams(mContext, "b_name", params.get("name"));
                        ShareInfoUtil.saveParams(mContext, "b_birthday", params.get("birthday"));
                        ShareInfoUtil.saveParams(mContext, "b_userSex", params.get("sex"));

                        //保存一次卡信息
                        UserInfo.ResultBean resultBean = ShareInfoUtil.readResultBean(mContext);
                        resultBean.setIC_NO(icNo);
                        resultBean.setName(params.get("name"));
                        resultBean.setBirthday(params.get("birthday"));
                        resultBean.setSex(params.get("sex"));
                        resultBean.setMenses(params.get("menses"));
                        ShareInfoUtil.saveReaultBean(mContext, resultBean);
//                        bundle.putString("id", id);
//                        bundle.putString("name", name);
//                        bundle.putString("code", code);
//                        bundle.putString("url", url);
//                        bundle.putString("dptId", dptId);

//                        bundle.putString("dptName", dptName);
//                        bundle.putString("position", position);
//                        bundle.putString("date", date);
//                        bundle.putString("week", week);
//                        ProjectApplication.intentManager.toAppointmentPayActivity(bundle);

                    }

                    AddUserBean.ResultBean result1 = addUserBean.getResult();
                    String cid = result1.getCid();
                    ShareInfoUtil.saveCid(mContext, cid);
                    T.ShowToast(AddUserInfoActivity.this, "会员资料新增成功");


                    HashMap<String, String> params = new HashMap<String, String>();
                    String cid2 = ShareInfoUtil.readCid(mContext);
                    params.put("cid", cid2);
                    HttpUtil.doHttp(HttpContanst.USER_INFO, params, new HttpUtil.IHttpResult() {
                        @Override
                        public void onSuccess(String result) {
                            ProjectApplication.logUtil.d(result);
                            if (StringUtil.isNotBlank(result)) {
                                UserInfo userInfo = GsonTools.getBean(result, UserInfo.class);
                                int status = userInfo.getStatus();
                                if (status == 0) {
                                    final UserInfo.ResultBean result1 = userInfo.getResult();

                                    if (StringUtil.isBlank(result1.getIC_NO())) {
                                        String token = ShareInfoUtil.readToken(mContext);
                                        HttpUtil.doHttp(HttpContanst.LOCAT_ICNO + token, null, new HttpUtil.IHttpResult() {
                                            @Override
                                            public void onSuccess(String result) {

                                                if (StringUtil.isNotBlank(result)) {
                                                    ResBean resBean = GsonTools.getBean(result, ResBean.class);
                                                    if (resBean.getStatus() == 0) {
                                                        String res = resBean.getResult().toString();
                                                        try {
                                                            JSONObject jsonObject = new JSONObject(res);
                                                            if (jsonObject.has("ic_no")) {
                                                                String ic_No = jsonObject.getString("ic_no");
                                                                result1.setIC_NO(ic_No);
                                                            }
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

                                                    }
                                                }
                                                ShareInfoUtil.saveReaultBean(mContext, result1);
                                            }

                                            @Override
                                            public void onError(Throwable ex, boolean isOnCallback) {
                                                ShareInfoUtil.saveReaultBean(mContext, result1);
                                            }
                                        });
                                    } else {
                                        ShareInfoUtil.saveReaultBean(mContext, result1);
                                    }
                                } else {
                                    ShareInfoUtil.saveReaultBean(mContext);
                                }

                            } else {
                                T.ShowToast(mContext, "返回数据为空", 0);
                            }
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            T.ShowToast(mContext, "用户信息请求失败，请重试", 0);
                        }
                    });



                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("addSuc", "true");
                    setResult(RESULT_OK, resultIntent);
                    finish();

                    //finish();
                } else {
                    String message = addUserBean.getMessage();
                    T.ShowToast(AddUserInfoActivity.this, message);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.ShowToast(mContext, "请求失败，请稍后重试");
            }
        });


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent resultIntent = new Intent();
            this.setResult(RESULT_OK, resultIntent);
            this.finish();
            //这里不需要执行父类的点击事件，所以直接return
            return true;
        }
        //继续执行父类的其他点击事件
        return super.onKeyDown(keyCode, event);
    }
}