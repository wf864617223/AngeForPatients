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
import com.hb.rimi.angel.adapter.UserYuyueAdapter;
import com.hb.rimi.angel.bean.UserInfo;
import com.hb.rimi.angel.bean.UserYuyue;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.util.DateUtils;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.ShareInfoUtil;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.view.PrgDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 会员预约列表
 * Created by hp on 2016/6/15.
 */
@ContentView(R.layout.frag_useryuyue)
public class UserYuyueFragment extends Fragment {

    @Bind(R.id.lv_useryuyue)
    ListView lvUseryuyue;
    private List<UserYuyue.ResultBean> result1 = new ArrayList<>();
    private UserYuyueAdapter adapter;
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
        final UserInfo.ResultBean resultBean = ShareInfoUtil.readResultBean(getContext());
        prgDialog = new PrgDialog(getContext());
        String ic_no = resultBean.getIC_NO();

        HashMap<String, String> params = new HashMap<>();
        params.put("ic_no", ic_no);//62010622

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();

        c.add(Calendar.DAY_OF_MONTH, 100);
        System.out.println(sf.format(c.getTime()));
        //再减7天
        params.put("bespoke_time1", DateUtils.convert2String(new Date().getTime() - (7 * 24 * 60 * 60 * 1000), "yyyy-MM-dd HH:mm:ss"));//2015-12-25 00:00:00
        params.put("bespoke_time2", sf.format(c.getTime())); //查询半年的数据 建议分页

        HttpUtil.doHttp(HttpContanst.USER_YUYUE_INFO, params, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                UserYuyue userYuyue = GsonTools.getBean(result, UserYuyue.class);
                int status = userYuyue.getStatus();
                if (status == 0) {
                    result1 = userYuyue.getResult();
                    adapter = new UserYuyueAdapter(getContext(), result1);
                    lvUseryuyue.setAdapter(adapter);
                    prgDialog.closeDialog();
                } else {
                    T.ShowToast(getContext(), "暂无数据");
                    prgDialog.closeDialog();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.ShowToast(getContext(), "请求失败，请重试");
                prgDialog.closeDialog();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
