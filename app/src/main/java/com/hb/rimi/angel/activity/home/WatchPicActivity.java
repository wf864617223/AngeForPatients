package com.hb.rimi.angel.activity.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.adapter.TypePicsAdapter;
import com.hb.rimi.angel.bean.ReportPacs;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.ShareInfoUtil;
import com.hb.rimi.angel.view.PrgDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WatchPicActivity extends AppCompatActivity {


    @Bind(R.id.lv_pacs)
    ListView lvPacs;
    //private List<Fragment> pager;
    private List<ReportPacs.ResultBean.ListBean> list = new ArrayList<>();
    //private WatchPicAdapter adapter;
    //private WatchPicFragment fragment;
    private TypePicsAdapter adapter;
    private PrgDialog prgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_pic);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        //adapter = new WatchPicAdapter(getSupportFragmentManager(),pager);
        //vpWatchPic.setAdapter(adapter);

    }

    private void initView() {
        prgDialog = new PrgDialog(WatchPicActivity.this);
        //list = ShareInfoUtil.readImgList(WatchPicActivity.this);
        System.out.println("list=>" + list.toString());
        Bundle extras = getIntent().getExtras();
        String id = extras.getString("id");
        HashMap<String, String> parms = new HashMap<>();
        parms.put("type", "2");
        parms.put("id", id);
        HttpUtil.doHttp(HttpContanst.REPORT_INFO, parms, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                ReportPacs reportPacs = GsonTools.getBean(result, ReportPacs.class);
                int status = reportPacs.getStatus();
                if (status == 0) {
                    ReportPacs.ResultBean result1 = reportPacs.getResult();
                    list = result1.getList();
                    if (list != null) {
                        adapter = new TypePicsAdapter(list, WatchPicActivity.this);
                        lvPacs.setAdapter(adapter);
                    }
                    prgDialog.closeDialog();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }
        });

        /*Bundle bundle = null;
        for(int i = 0;i<list.size();i++){
            fragment = new WatchPicFragment();
            bundle = new Bundle();
            bundle.putString("img",list.get(i).getImageUrl());
            fragment.setArguments(bundle);
            pager.add(fragment);
        }
    */
    }
}
