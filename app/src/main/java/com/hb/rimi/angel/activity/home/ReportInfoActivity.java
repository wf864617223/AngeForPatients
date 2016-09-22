package com.hb.rimi.angel.activity.home;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hb.rimi.angel.R;
import com.hb.rimi.angel.fragment.TypeOneFragment;
import com.hb.rimi.angel.fragment.TypeThreeFragment;
import com.hb.rimi.angel.fragment.TypeTwoFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;


import butterknife.Bind;
import butterknife.ButterKnife;

@ContentView(R.layout.activity_report_info)
public class ReportInfoActivity extends AppCompatActivity {

    @Bind(R.id.ria_toolbar)
    Toolbar ria_toolbar;
    private FragmentTransaction transaction;
    private TypeOneFragment typeOneFragment;
    private TypeTwoFragment typeTwoFragment;
    private TypeThreeFragment typeThreeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        ButterKnife.bind(this);
        //initPager();
        initData();

    }


    private void initPager() {

        //typeTwoFragment = new TypeTwoFragment();
        //transaction.replace(R.id.frame_type_pause,typeTwoFragment);
        //typeThreeFragment = new TypeThreeFragment();
        //transaction.replace(R.id.frame_type_img,typeThreeFragment);
        transaction.show(typeOneFragment).commit();
        //transaction.hide(typeTwoFragment).hide(typeThreeFragment).show(typeOneFragment).commit();
    }

    private void initData() {

        ria_toolbar.setTitle("");
        setSupportActionBar(ria_toolbar);
        ria_toolbar.setNavigationIcon(R.mipmap.icon_reg_back);
        ria_toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        ria_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Bundle bundle = this.getIntent().getExtras();
        int id = bundle.getInt("id");
        int type = bundle.getInt("type");
        //T.ShowToast(ReportInfoActivity.this,"type===>"+type);
        if (type == 1) {
            transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

            typeOneFragment = new TypeOneFragment();
            transaction.add(R.id.frame_type_list, typeOneFragment);
            transaction.show(typeOneFragment).commit();
            Bundle bundle1 = new Bundle();
            bundle1.putString("id", id + "");
            typeOneFragment.setArguments(bundle1);
        } else if (type == 2) {
            transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

            typeTwoFragment = new TypeTwoFragment();
            transaction.add(R.id.frame_type_pause, typeTwoFragment);
            transaction.show(typeTwoFragment).commit();
            Bundle bundle1 = new Bundle();
            bundle1.putString("id", id + "");
            typeTwoFragment.setArguments(bundle1);

        } else if (type == 3) {
            transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

            typeThreeFragment = new TypeThreeFragment();
            transaction.add(R.id.frame_type_img, typeThreeFragment);
            transaction.show(typeThreeFragment).commit();
            Bundle bundle1 = new Bundle();
            bundle1.putString("id", id + "");
            typeThreeFragment.setArguments(bundle1);

        }

    }
}
