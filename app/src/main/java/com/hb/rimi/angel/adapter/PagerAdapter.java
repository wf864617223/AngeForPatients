package com.hb.rimi.angel.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hb.rimi.angel.activity.mine.MyOrderActivity;

import java.util.List;

/**
 * Created by hp on 2016/6/3.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> pager;

    public PagerAdapter(FragmentManager fm,List<Fragment> pager) {
        super(fm);
        this.pager = pager;
    }

    @Override
    public Fragment getItem(int position) {

        return pager.get(position);
    }

    @Override
    public int getCount() {
        return pager.size();
    }
}
