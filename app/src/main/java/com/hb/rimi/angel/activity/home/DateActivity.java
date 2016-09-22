package com.hb.rimi.angel.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.hb.rimi.angel.R;

import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.bean.BAppointmentTime;
import com.hb.rimi.angel.util.T;
import com.squareup.timessquare.CalendarPickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 选择日期
 */
public class DateActivity extends AppCompatActivity {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Bind(R.id.desa_toolbar)
    Toolbar desaToolbar;
    @Bind(R.id.calendar_view)
    CalendarPickerView calendar_view;

    String startT;
    String endT;
    private Context mContext;
    private List<BAppointmentTime> bAppointmentTimeList;
    private List<HashMap<String, String>> myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProjectApplication.getInstanceApp().addActivity(this);
        setContentView(R.layout.activity_date);
        ButterKnife.bind(this);
        initIntent();
        initData();
    }

    private void initIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            startT = bundle.getString("startT");
            endT = bundle.getString("endT");
            bAppointmentTimeList = (List<BAppointmentTime>) bundle.getSerializable("bAppointmentTimeList");
            myList = new ArrayList<>();
            for (int i = 0; i < bAppointmentTimeList.size(); i++) {
                HashMap<String, String> maps = new HashMap<>();
                if (bAppointmentTimeList.get(i).getSuccessCount() < bAppointmentTimeList.get(i).getCount()) {
                    maps.put("isFull", "false");
                } else {
                    maps.put("isFull", "true");
                }
                maps.put("date", bAppointmentTimeList.get(i).getDate());
                myList.add(maps);
            }
        }
    }

    private void initData() {
        mContext = DateActivity.this;
        desaToolbar.setTitle("");
        setSupportActionBar(desaToolbar);
        desaToolbar.setNavigationIcon(R.mipmap.icon_reg_back);
        desaToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        desaToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectDate = sdf.format(calendar_view.getSelectedDate());

                boolean isFull = false;
                for (int i = 0; i < bAppointmentTimeList.size(); i++) {
                    if (selectDate.equals(bAppointmentTimeList.get(i).getDate())) {
                        if (bAppointmentTimeList.get(i).getSuccessCount() < bAppointmentTimeList.get(i).getCount()) {
                            isFull = false;
                            break;
                        } else {
                            isFull = true;
                            break;
                        }
                    }
                }
                if (isFull) {
                    T.ShowToast(mContext, "预约人数已满，请重新选择预约日期。");
                    Intent resultIntent = new Intent();

                    Bundle bundle = new Bundle();

                    bundle.putString("selectDate", "");

                    resultIntent.putExtras(bundle);

                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    Intent resultIntent = new Intent();

                    Bundle bundle = new Bundle();

                    bundle.putString("selectDate", selectDate);

                    resultIntent.putExtras(bundle);

                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        Date sT = null;
        Date eT = null;

        try {
            sT = sdf.parse(startT);
            eT = sdf.parse(endT);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(eT);
            calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
            eT = calendar.getTime();   //这个时间就是日期往后推一天的结果
            calendar_view.init(sT, sT, eT, myList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        String selectDate = sdf.format(calendar_view.getSelectedDate());
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            boolean isFull = false;
            for (int i = 0; i < bAppointmentTimeList.size(); i++) {
                if (selectDate.equals(bAppointmentTimeList.get(i).getDate())) {
                    if (bAppointmentTimeList.get(i).getSuccessCount() < bAppointmentTimeList.get(i).getCount()) {
                        isFull = false;
                        break;
                    } else {
                        isFull = true;

                        break;
                    }
                }
            }
            if (isFull) {
                T.ShowToast(mContext, "预约人数已满，请重新选择预约日期。");
                Intent resultIntent = new Intent();

                Bundle bundle = new Bundle();

                bundle.putString("selectDate", "");

                resultIntent.putExtras(bundle);
                this.setResult(RESULT_OK, resultIntent);
                this.finish();
                return true;
            } else {
                Intent resultIntent = new Intent();

                Bundle bundle = new Bundle();

                bundle.putString("selectDate", selectDate);

                resultIntent.putExtras(bundle);
                this.setResult(RESULT_OK, resultIntent);
                this.finish();
                //这里不需要执行父类的点击事件，所以直接return
                return true;
            }

        }
        //继续执行父类的其他点击事件
        return super.onKeyDown(keyCode, event);
    }
}

