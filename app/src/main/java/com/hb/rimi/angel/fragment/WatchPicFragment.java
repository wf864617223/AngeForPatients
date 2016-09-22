package com.hb.rimi.angel.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hb.rimi.angel.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hp on 2016/7/1.
 */
public class WatchPicFragment extends Fragment {
    @Bind(R.id.iv_watch_pic)
    ImageView ivWatchPic;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_watch_pic, null);

        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        Bundle arguments = getArguments();
        String url = arguments.getString("img");
        Glide.with(getContext()).load(url).into(ivWatchPic);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
