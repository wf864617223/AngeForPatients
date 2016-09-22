package com.hb.rimi.angel.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.adapter.YuyueInfoAdapter;
import com.hb.rimi.angel.bean.UserInfo;
import com.hb.rimi.angel.bean.YuyueInfoBean;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.ShareInfoUtil;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.view.PrgDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 预约挂号信息
 * Created by hp on 2016/6/15.
 */
@ContentView(R.layout.frag_yuyueinfo)
public class YuyueInfoFragment extends Fragment {

    @Bind(R.id.lv_userInfo)
    ListView lvUserInfo;
    private List<YuyueInfoBean.ResultBean> result1 = new ArrayList<>();
    private YuyueInfoAdapter adapter;
    private PrgDialog prgDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = x.view().inject(this, inflater, container);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        UserInfo.ResultBean resultBean = ShareInfoUtil.readResultBean(getContext());
        prgDialog = new PrgDialog(getContext());
        String ic_no = resultBean.getIC_NO();
        HashMap<String, String> parms = new HashMap<>();
        parms.put("ic_no", resultBean.getIC_NO());
        parms.put("ensure_flag", "0");  //0代表已挂号  1代表已就诊
        if(!"".equals(ic_no)){
            HttpUtil.doHttp(HttpContanst.USER_REGISTER_INFO, parms, new HttpUtil.IHttpResult() {
                @Override
                public void onSuccess(String result) {
                    YuyueInfoBean yuyueInfoBean = GsonTools.getBean(result,YuyueInfoBean.class);
                    int status = yuyueInfoBean.getStatus();
                    if(status == 0){
                        result1 = yuyueInfoBean.getResult();
                        adapter = new YuyueInfoAdapter(result1,getContext());
                        lvUserInfo.setAdapter(adapter);
                        prgDialog.closeDialog();
                    }else{
                        T.ShowToast(getContext(),"数据错误，请稍后再试"+yuyueInfoBean.getMessage());
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    T.ShowToast(getContext(),"请求失败，请重试");
                    prgDialog.closeDialog();
                }
            });
        }else{
            T.ShowToast(getContext(), "获取就诊卡号失败,或没有卡号");
            getActivity().finish();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
