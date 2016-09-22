package com.hb.rimi.angel.activity.mine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hb.rimi.angel.R;
import com.hb.rimi.angel.fragment.OrderedFragment;
import com.hb.rimi.angel.fragment.OrderingFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

@ContentView(R.layout.activity_my_order)
public class MyOrderActivity extends AppCompatActivity {


    @Bind(R.id.rb_ordering)
    RadioButton rbOrdering;
    @Bind(R.id.rb_ordering_down)
    RadioButton rb_ordering_down;
    @Bind(R.id.rg_order)
    RadioGroup rgOrder;
    @Bind(R.id.vp_order)
    ViewPager vpOrder;
    @Bind(R.id.iv_orderfinish)
    ImageView ivOrderfinish;
    private List<Fragment> pager;
    private PagerAdapter adapter;
    private String notpay;//是否是从0已支付
    public static String isPayed="false";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        initIntent();
        initView();
        initPager();
    }

    private void initIntent() {
        try {
            notpay = getIntent().getExtras().getString("notpay");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initPager() {
        pager = new ArrayList<>();
        pager.add(new OrderingFragment());
        pager.add(new OrderedFragment());
        adapter = new com.hb.rimi.angel.adapter.PagerAdapter(getSupportFragmentManager(), pager);
        vpOrder.setAdapter(adapter);
        vpOrder.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                System.out.println(position+" MyOrderActivity.isPayed==>"+ MyOrderActivity.isPayed);
                RadioButton radioButton = (RadioButton) rgOrder.getChildAt(position);
                radioButton.setChecked(true);
                radioButton.setFocusable(true);
                if(position==0){
                    MyOrderActivity.isPayed="false";
                }else if(position==1){
                    MyOrderActivity.isPayed="true";
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if ("1".equals(notpay)) {
            vpOrder.setCurrentItem(1);
        }
    }
    private void initView() {
        ivOrderfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if ("1".equals(notpay)) {
            rb_ordering_down.setChecked(true);
            rb_ordering_down.setFocusable(true);
//            vpOrder.setCurrentItem(1);
        } else {
            rbOrdering.setChecked(true);
            rbOrdering.setFocusable(true);
        }

        rgOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                int count = radioGroup.getChildCount();
                for (int i = 0; i < count; i++) {
                    RadioButton button = (RadioButton) radioGroup.getChildAt(i);
                    if (button.isChecked()) {
                        button.setFocusable(true);
                        vpOrder.setCurrentItem(i);
                    }

                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
