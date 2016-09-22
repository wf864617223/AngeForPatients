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
import com.hb.rimi.angel.adapter.OrderingNewAdapter;
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
@ContentView(R.layout.frag_ordering)
public class OrderingFragment extends Fragment {


    @Bind(R.id.lv_ordering)
    RecyclerView lvOrdering;
    @Bind(R.id.tv_nodata)
    TextView tvNodata;
    private Context context;
    private String token;

    private OrderingNewAdapter adapter;
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

    private void initView() {
        token = ShareInfoUtil.readToken(context);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        lvOrdering.setLayoutManager(manager);
        //添加默认分割线
        lvOrdering.addItemDecoration(new DividerItemDecoration(context));


    }

    private void loadData() {
        HttpUtil.doHttp(HttpContanst.PAY_ORDER_TWO_LIST + token + "&status=0&types=0", null, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                PayOrderedBean payOrderListBean = GsonTools.getBean(result, PayOrderedBean.class);
                int status = payOrderListBean.getStatus();
                if (status == 0) {
                    PayOrderedBean.ResultBean result1 = payOrderListBean.getResult();
                    list = result1.getList();
                    adapter = new OrderingNewAdapter(list, context);
                    lvOrdering.setAdapter(adapter);
                    if (list.size() == 0 || lvOrdering == null) {
                        tvNodata.setVisibility(View.VISIBLE);
                    }
                } else {
                    T.ShowToast(context, payOrderListBean.getMessage().toString());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
