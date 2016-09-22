package com.hb.rimi.angel.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by hp on 2016/7/1.
 */
public class WatchPicAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> pager;
    public WatchPicAdapter(FragmentManager fm,List<Fragment> pager) {
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
