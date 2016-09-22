package com.hb.rimi.angel.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.adapter.BcheckInfoAdapter;
import com.hb.rimi.angel.bean.ResBcheckInfo;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.ShareInfoUtil;
import com.hb.rimi.angel.util.StringUtil;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.view.PrgDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 就诊提醒-B超检查信息
 * Created by hp on 2016/6/15.
 */
@ContentView(R.layout.frag_bsuper)
public class BSuperFragment extends Fragment {

    @Bind(R.id.bsf_recycler_view)
    RecyclerView bsfRecyclerView;
    private Context mContext;
    private BcheckInfoAdapter adapter;
    private PrgDialog prgDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = x.view().inject(this, inflater, container);
        ButterKnife.bind(this, view);
        initData();
        initList();
        return view;
    }

    private void initList() {
//        action=GetListIcNo=123456&AppTime=2016-06-17
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "GetList");
        params.put("IcNo", ShareInfoUtil.readResultBean(mContext).getIC_NO());
//        params.put("IcNo", "123456");doub
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String  startTime=sdf.format(new Date());
        params.put("AppTime", startTime);
        prgDialog = new PrgDialog(mContext);
        HttpUtil.doHttp(HttpContanst.REMIND_BSUPER, params, new HttpUtil.IHttpResult() {
                    @Override
                    public void onSuccess(String result) {
                        if (StringUtil.isNotBlank(result)) {
                            ResBcheckInfo resBcheckInfo = GsonTools.getBean(result, ResBcheckInfo.class);
                            if (resBcheckInfo.getStatus() == 0) {
                                List<ResBcheckInfo.ResultBean> resultBeanList = resBcheckInfo.getResult();
                                adapter = new BcheckInfoAdapter(mContext, resultBeanList);
                                bsfRecyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            } else {
                                T.ShowToast(mContext, "暂无数据");
                            }
                        } else {
                            T.ShowToast(mContext, "返回数据为空 ");
                        }
                        prgDialog.closeDialog();
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        T.ShowToast(mContext, "请求失败，请稍后重试。", 0);
                        prgDialog.closeDialog();
                    }
                }

        );
    }

    private void initData() {
        mContext = getContext();
        //设置recycelerView的布局管理器
        bsfRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //添加默认分割线
//        bsfRecyclerView.addItemDecoration(new DividerItemDecoration(mContext));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
