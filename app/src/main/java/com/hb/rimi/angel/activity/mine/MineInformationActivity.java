package com.hb.rimi.angel.activity.mine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hb.rimi.angel.R;
import com.hb.rimi.angel.bean.UserInfo;
import com.hb.rimi.angel.util.DateUtils;
import com.hb.rimi.angel.util.ShareInfoUtil;
import com.hb.rimi.angel.view.PrgDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 个人信息
 */
@ContentView(R.layout.activity_mine_information)
public class MineInformationActivity extends AppCompatActivity {

    @Bind(R.id.iv_info_finish)
    ImageView ivInfoFinish;
    @Bind(R.id.iv_userHeader)
    ImageView ivUserHeader;
    @Bind(R.id.tv_changeHeader)
    TextView tvChangeHeader;
    @Bind(R.id.tv_userName)
    TextView tvUserName;
    @Bind(R.id.tv_userSex)
    TextView tvUserSex;
    @Bind(R.id.tv_userBirthday)
    TextView tvUserBirthday;
    @Bind(R.id.tv_menses)
    TextView tvMenses;
    @Bind(R.id.tv_lastMenses)
    TextView tvLastMenses;
    @Bind(R.id.tv_huaiyunTime)
    TextView tvHuaiyunTime;
    @Bind(R.id.tv_conceiveTime)
    TextView tvConceiveTime;
    @Bind(R.id.tv_VipNum)
    TextView tvVipNum;
    @Bind(R.id.ll_last_menses)
    LinearLayout llLastMenses;
    @Bind(R.id.ll_huaiyun_time)
    LinearLayout llHuaiyunTime;
    @Bind(R.id.card_no)
    TextView cardNo;
    private String name;
    private String birthday;
    private String edc;
    private String ic_fee;
    private String ic_no;
    private String ic_type;
    private String menses;
    private String mobile;
    private String mWeek;
    private PrgDialog prgDialog;
    private String Int_sex;
    private String rebateType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        //initData();
        initData();
        initView();

    }

    private void initData() {
        UserInfo.ResultBean resultBean = ShareInfoUtil.readResultBean(MineInformationActivity.this);
        //客户姓名
        name = resultBean.getName();
        tvUserName.setText(name);
        Int_sex = resultBean.getSex();
        if ("1".equals(Int_sex)) {
            tvUserSex.setText("男");
            llLastMenses.setVisibility(View.GONE);
            llHuaiyunTime.setVisibility(View.GONE);
        } else if("0".equals(Int_sex)){
            tvUserSex.setText("女");
        }else{
            tvUserSex.setText("");
        }
        rebateType = resultBean.getRebateType();
        if(rebateType.equals("是")){
            cardNo.setText("Vip 卡号");
        }else{
            cardNo.setText("就诊卡号");
        }
        //生日
        birthday = resultBean.getBirthday();
        //预产期
        edc = resultBean.getEDC();
        //余额
        ic_fee = resultBean.getIc_fee();
        //卡号
        ic_no = resultBean.getIC_NO();
        //卡类型
        ic_type = resultBean.getIc_type();
        //末次月经
        menses = resultBean.getMenses();
        //手机
        mobile = resultBean.getMobile();
        //月经周期天数
        mWeek = resultBean.getMWeek();
        tvUserBirthday.setText(birthday);
        tvLastMenses.setText(menses);
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        try {
            int i = DateUtils.daysBetween(menses, time);
            int day = i % 7;
            int weeks;
            if (day != 0) {
                weeks = i / 7;
            } else {
                weeks = i / 7;
            }
            if (weeks <= 40) {
                String week = weeks + "";
                tvConceiveTime.setText(week + "周");
            } else {
                tvConceiveTime.setText("已临盆");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        tvVipNum.setText(ic_no);
    }


    private void initView() {
        ivInfoFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
