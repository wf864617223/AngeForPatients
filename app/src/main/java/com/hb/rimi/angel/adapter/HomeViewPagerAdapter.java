package com.hb.rimi.angel.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hb.rimi.angel.HttpUtils.HttpLoadImg;

import java.util.List;

/**
 * Created by hp on 2016/5/31.
 */
public class HomeViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<String> pager;

    public HomeViewPagerAdapter(Context context, List<String> pager) {
        this.context = context;
        this.pager = pager;
    }

    @Override
    public int getCount() {
        return pager.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        imageView.setImageResource(pager.get(position));
        HttpLoadImg.loadImg(context,pager.get(position),imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
