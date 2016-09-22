package com.hb.rimi.angel.activity.home;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.hb.rimi.angel.R;
import com.hb.rimi.angel.fragment.BSuperFragment;
import com.hb.rimi.angel.fragment.UserYuyueFragment;
import com.hb.rimi.angel.fragment.UserYuyueStyleTwo;
import com.hb.rimi.angel.fragment.YuyueInfoFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import butterknife.Bind;
import butterknife.ButterKnife;

@ContentView(R.layout.activity_visit_remind)
public class VisitRemindActivity extends AppCompatActivity {

    @Bind(R.id.tv_jiuzhen)
    TextView tvJiuzhen;
    @Bind(R.id.tool_finish)
    Toolbar toolFinish;
    private FragmentTransaction transaction;
    private UserYuyueStyleTwo userYuyueFragment;
    private YuyueInfoFragment yuyueInfoFragment;
    private BSuperFragment bSuperFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        ButterKnife.bind(this);
        //getSupportActionBar().hide();
        initData();
    }

    private void initData() {
        toolFinish.setTitle("");
        setSupportActionBar(toolFinish);
        toolFinish.setNavigationIcon(R.mipmap.icon_reg_back);
        toolFinish.setTitleTextColor(getResources().getColor(R.color.white));
        toolFinish.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Bundle bundle = this.getIntent().getExtras();
        String title = bundle.getString("title");
        tvJiuzhen.setText(title);

        if (title.equals("会员预约信息")) {
            transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

            userYuyueFragment = new UserYuyueStyleTwo();
            transaction.add(R.id.frame_useryuyue, userYuyueFragment);
            transaction.show(userYuyueFragment).commit();
            Bundle bundle1 = new Bundle();
            bundle1.putString("title", title);
            userYuyueFragment.setArguments(bundle1);
        } else if (title.equals("预约挂号信息")) {
            transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

            yuyueInfoFragment = new YuyueInfoFragment();
            transaction.add(R.id.frame_yuyuegh, yuyueInfoFragment);
            transaction.show(yuyueInfoFragment).commit();
            Bundle bundle1 = new Bundle();
            bundle1.putString("title", title);
            yuyueInfoFragment.setArguments(bundle1);
        } else if (title.equals("胎监检查信息")) {
            transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

            bSuperFragment = new BSuperFragment();
            transaction.add(R.id.frame_bsuper, bSuperFragment);
            transaction.show(bSuperFragment).commit();
            Bundle bundle1 = new Bundle();
            bundle1.putString("title", title);
            bSuperFragment.setArguments(bundle1);
        }

    }
}
