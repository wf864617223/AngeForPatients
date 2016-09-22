package com.hb.rimi.angel.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.activity.mine.LoginActivity;
import com.hb.rimi.angel.bean.HomeTopPic;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.util.DisplayUtil;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.view.PrgDialog;

import java.util.List;

/**
 * Created by hp on 2016/6/23.
 */
public class AdFragment extends Fragment {

    private ImageView ivAdImg;
    private PrgDialog prgDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_ad_img,null);
        ivAdImg = (ImageView) view.findViewById(R.id.iv_ad_img);
        initPager();
        return view;
    }

    private void initPager() {
        prgDialog = new PrgDialog(getContext());
        HttpUtil.doHttp(HttpContanst.SYS_DATA+"&property=app_banner", null, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                HomeTopPic homeTopPic = GsonTools.getBean(result,HomeTopPic.class);
                String message = homeTopPic.getMessage();
                if("Success".equals(message)){
                    HomeTopPic.ResultBean result1 = homeTopPic.getResult();
                    HomeTopPic.ResultBean.AppBannerBean app_banner = result1.getApp_banner();
                    List<HomeTopPic.ResultBean.AppBannerBean.FolderBean> folder = app_banner.getFolder();
                    String path = folder.get(0).getPath();
                    HomeTopPic.ResultBean.AppBannerBean.ImagesBean images = app_banner.getImages();
                    String mdpi = images.getMdpi();
                    String hdpi = images.getHdpi();
                    String idpi = images.getIdpi();
                    String xhdpi = images.getXhdpi();
                    String xxhdpi = images.getXxhdpi();
                    int mobileHeight = DisplayUtil.getMobileHeight(getContext());
                    int mobileWidth = DisplayUtil.getMobileWidth(getContext());
                    String img_url = HttpContanst.SERVER_ADD;
                    String s = "";
                    if(mobileHeight==480){
                        s = img_url + path + mdpi;
                    }else if(mobileHeight==640){
                        s = img_url + path + hdpi;
                    }else if(mobileHeight==800){
                        s = img_url + path + idpi;
                    }else if(mobileHeight==640){
                        s = img_url + path + xhdpi;
                    }else if(mobileHeight==1280){
                        s = img_url + path + mdpi;
                    }else{
                        s = img_url + path + xxhdpi;
                    }
                    //T.ShowToast(getContext(),"==>"+s,1);
                    Glide.with(getContext()).load(s).into(ivAdImg);
                    prgDialog.closeDialog();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.ShowToast(getContext(),"图片获取失败"+ex.getMessage());
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


    }
}
