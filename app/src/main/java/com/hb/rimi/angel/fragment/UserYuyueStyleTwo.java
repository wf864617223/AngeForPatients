package com.hb.rimi.angel.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.adapter.UserYuyueStyleTwoAdapter;
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
 * Created by hp on 2016/7/6.
 */
@ContentView(R.layout.frag_useryuyue_style_two)
public class UserYuyueStyleTwo extends Fragment {
    @Bind(R.id.elv_user_yuyue)
    ExpandableListView elvUserYuyue;
    private PrgDialog prgDialog;


    private UserYuyueStyleTwoAdapter adapter;
    private List<UserYuyue.ResultBean> result1 = new ArrayList<>();//组列表
    private List<UserYuyue.ResultBean> childArray = new ArrayList<>();//子列表

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = x.view().inject(this, inflater, container);
        ButterKnife.bind(this, view);
        initData();

        return view;
    }

    private void initData() {

        //设置 属性 GroupIndicator 去掉默认向下的箭头
        elvUserYuyue.setGroupIndicator(null);

        final UserInfo.ResultBean resultBean = ShareInfoUtil.readResultBean(getContext());
        prgDialog = new PrgDialog(getContext());
        String ic_no = resultBean.getIC_NO();

        HashMap<String, String> params = new HashMap<>();
        params.put("ic_no", ic_no);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();

        c.add(Calendar.DAY_OF_MONTH, 100);
        System.out.println(sf.format(c.getTime()));

        params.put("bespoke_time1", DateUtils.convert2String(new Date().getTime(),"yyyy-MM-dd HH:mm:ss"));
        params.put("bespoke_time2",  sf.format(c.getTime()));

        HttpUtil.doHttp(HttpContanst.USER_YUYUE_INFO, params, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                UserYuyue userYuyue = GsonTools.getBean(result, UserYuyue.class);
                int status = userYuyue.getStatus();
                if(status == 0){
                    result1 = userYuyue.getResult();
                    if(result1!=null&&result1.size()>0){
                        adapter = new UserYuyueStyleTwoAdapter(getContext(),result1);
                        elvUserYuyue.setAdapter(adapter);
                        for(int i = 0;i<adapter.getGroupCount();i++){
                            elvUserYuyue.expandGroup(i);
                        }
                    }else{
                        T.ShowToast(getContext(),"暂无数据");
                    }
                    prgDialog.closeDialog();
                }else{
                    T.ShowToast(getContext(),"暂无数据");
                    prgDialog.closeDialog();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.ShowToast(getContext(),ex.getMessage());
                prgDialog.closeDialog();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    /*private void addInfo(String group,List<String> child) {

        groupArray.add(group);

        List<String>  childItem =new ArrayList<String>();

        for(int index=0;index<child.size();index++)
        {
            childItem.add(child.get(index));
        }
        childArray.add(childItem);
    }*/

}
