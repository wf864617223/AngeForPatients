package com.hb.rimi.angel.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.bean.ReportImg;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.view.PrgDialog;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hp on 2016/6/13.
 */
@ContentView(R.layout.frag_type_img)
public class TypeThreeFragment extends Fragment {


    @Bind(R.id.img_web)
    WebView imgWeb;
    @Bind(R.id.pb_web)
    ProgressBar pbWeb;
    //@Bind(R.id.iv_report)
    //ImageView ivReport;
    private PrgDialog prgDialog;
    private Context context;
    private ImageOptions options;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = x.view().inject(this, inflater, container);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        context = getContext();
        Bundle bundle = getArguments();
        String id = bundle.getString("id");
        prgDialog = new PrgDialog(getContext());
        HashMap<String, String> parms = new HashMap<>();
        parms.put("type", "3");
        parms.put("id", id);
        HttpUtil.doHttp(HttpContanst.REPORT_INFO, parms, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                if (!TextUtils.isEmpty(result)) {
                    ReportImg reportImg = GsonTools.getBean(result, ReportImg.class);
                    int status = reportImg.getStatus();
                    if (status == 0) {
                        ReportImg.ResultBean result1 = reportImg.getResult();
                        String imgUrl = result1.getImgUrl();

//                        imgUrl = "http://www.newasp.net/attachment/soft/2013/0927/104053_82681198.jpg";
                        //HttpLoadImg.loadImg(context, imgUrl, ivReport);
                        /*options = new ImageOptions.Builder()
                                .setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))
                                .setRadius(DensityUtil.dip2px(5))
                                // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                                .setCrop(true)
                                // 加载中或错误图片的ScaleType
                                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                                //设置加载过程中的图片
                                .setLoadingDrawableId(R.mipmap.ic_launcher)
                                //设置加载失败后的图片
                                .setFailureDrawableId(R.mipmap.banner)
                                //设置使用缓存
                                .setUseMemCache(true)
                                //设置支持gif
                                .setIgnoreGif(false)
                                .build();
                        x.image().bind(ivReport,imgUrl,options);*/
                        imgWeb.loadUrl(imgUrl);
                        WebSettings settings = imgWeb.getSettings();
                        settings.setJavaScriptEnabled(true);
                        settings.setLoadWithOverviewMode(true);
                        settings.setUseWideViewPort(true);
                        imgWeb.setWebChromeClient(new WebChromeClient(){
                            @Override
                            public void onProgressChanged(WebView view, int newProgress) {
                                if(newProgress == 100){
                                    pbWeb.setVisibility(View.INVISIBLE);
                                }else{
                                    if(View.INVISIBLE == pbWeb.getVisibility()){
                                        pbWeb.setVisibility(View.VISIBLE);
                                    }
                                    pbWeb.setProgress(newProgress);
                                }
                                super.onProgressChanged(view, newProgress);
                            }
                        });
                        prgDialog.closeDialog();
                    } else {
                        T.ShowToast(getContext(), "请求错误，请稍后再试");
                    }
                } else {
                    T.ShowToast(getContext(), "请求数据错误，请稍后再试");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.ShowToast(getContext(), "请求失败，请重试");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
