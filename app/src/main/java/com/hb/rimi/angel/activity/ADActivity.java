package com.hb.rimi.angel.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.activity.mine.LoginActivity;
import com.hb.rimi.angel.adapter.AdImgAdapter;
import com.hb.rimi.angel.bean.HomeTopPic;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.fragment.AdFragment;
import com.hb.rimi.angel.util.DisplayUtil;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.T;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

@ContentView(R.layout.activity_ad)
public class ADActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,RadioGroup.OnCheckedChangeListener{

    @Bind(R.id.ad_viewPager)
    ViewPager adViewPager;
    @Bind(R.id.rb_img01)
    RadioButton rbImg01;
    @Bind(R.id.rb_img02)
    RadioButton rbImg02;
    @Bind(R.id.rb_img03)
    RadioButton rbImg03;
    @Bind(R.id.rg_img)
    RadioGroup rgImg;
    Context context;
    private AdImgAdapter adImgAdapter;
    private List<Fragment> pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        x.view().inject(this);
        ButterKnife.bind(this);
        context = ADActivity.this;
        rbImg01.setChecked(true);
        initView();
        //initPager();
        adImgAdapter = new AdImgAdapter(getSupportFragmentManager(),pager);
        adViewPager.setAdapter(adImgAdapter);
        adViewPager.addOnPageChangeListener(this);

        rgImg.setOnCheckedChangeListener(this);

    }

    private void initView() {
        pager = new ArrayList<>();
        Bundle bundle = null;
        AdFragment fragment = null;
        for (int i = 0; i < 3; i++) {
            fragment = new AdFragment();
            bundle = new Bundle();
            bundle.putString("msg","data"+i);
            fragment.setArguments(bundle);
            pager.add(fragment);
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        if(position == pager.size()-1){
            Intent intent = new Intent(context,LoginActivity.class);
            startActivity(intent);
            //finish();
        }
    }

    @Override
    public void onPageSelected(int position) {
        //获取到radioGoup对应位置上的子控件RadioButton
        RadioButton radioButton = (RadioButton)rgImg.getChildAt(position);
        radioButton.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //获取到RadioGroup里面子控件的个数
        int count = group.getChildCount();
        for (int i = 0; i < count; i++) {
            RadioButton button = (RadioButton)group.getChildAt(i);
            if (button.isChecked()){
                //说明这个button就是被选中的按钮，当前的位置就是需要显示的viewpager的页面位置
                adViewPager.setCurrentItem(i);
            }
        }
    }
}
