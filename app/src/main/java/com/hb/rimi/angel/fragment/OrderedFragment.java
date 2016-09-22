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
import android.widget.TextView;

import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.adapter.OrderedNewAdapter;
import com.hb.rimi.angel.bean.PayOrderedBean;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.ShareInfoUtil;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.view.DividerItemDecoration;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hp on 2016/6/3.
 */
@ContentView(R.layout.frag_ordered)
public class OrderedFragment extends Fragment {


    @Bind(R.id.tv_nodata)
    TextView tvNodata;
    @Bind(R.id.lv_ordered)
    RecyclerView lvOrdered;


    private Context context;
    private String token;
    private OrderedNewAdapter adapter;
    private List<PayOrderedBean.ResultBean.ListBean> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = x.view().inject(this, inflater, container);
        ButterKnife.bind(this, view);
        context = getContext();
        initView();
        loadData();
        return view;
    }

    private void loadData() {
        token = ShareInfoUtil.readToken(context);
        HttpUtil.doHttp(HttpContanst.PAY_ORDER_TWO_LIST + token + "&status=1&types=0", null, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                PayOrderedBean payOrderedBean = GsonTools.getBean(result, PayOrderedBean.class);
                int status = payOrderedBean.getStatus();
                if (status == 0) {
                    PayOrderedBean.ResultBean result1 = payOrderedBean.getResult();
                    list = result1.getList();
                    adapter = new OrderedNewAdapter(list, context);
                    lvOrdered.setAdapter(adapter);
                    if (list.size() == 0 || lvOrdered == null) {
                        tvNodata.setText("没有支付完成的订单");
                        tvNodata.setVisibility(View.VISIBLE);
                    }
                } else {
                    T.ShowToast(context, payOrderedBean.getMessage().toString());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }
        });
    }

    private void initView() {
        token = ShareInfoUtil.readToken(context);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        lvOrdered.setLayoutManager(manager);
        //添加默认分割线
        lvOrdered.addItemDecoration(new DividerItemDecoration(context));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
