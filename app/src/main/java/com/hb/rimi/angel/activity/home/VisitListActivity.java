package com.hb.rimi.angel.activity.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.adapter.BOptionalCheckAdapter;
import com.hb.rimi.angel.adapter.CrmPhoneAdapter;
import com.hb.rimi.angel.adapter.VisitListAdapter;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.bean.OptionalCheck;
import com.hb.rimi.angel.bean.ResCrmPhone;
import com.hb.rimi.angel.bean.ResHisOrderId;
import com.hb.rimi.angel.bean.ResOptionalCheck;
import com.hb.rimi.angel.bean.ResVisitList;
import com.hb.rimi.angel.bean.VisitList;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.util.CallManager;
import com.hb.rimi.angel.util.DateChooseUtils;
import com.hb.rimi.angel.util.DateUtils;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.ShareInfoUtil;
import com.hb.rimi.angel.util.SortTool;
import com.hb.rimi.angel.util.StringUtil;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.view.DividerItemDecoration;
import com.hb.rimi.angel.view.PrgDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 1、就诊列表
 */
public class VisitListActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.vla_toolbar)
    Toolbar vla_toolbar;
    @Bind(R.id.vla_recyclerView)
    RecyclerView vla_recyclerView;
    @Bind(R.id.vla_et_start_date)
    EditText vla_et_start_date;
    @Bind(R.id.vla_et_end_date)
    EditText vla_et_end_date;
    List<VisitList> vList;
    @Bind(R.id.aa_ll_call)
    LinearLayout aaLlCall;
    @Bind(R.id.vla_btn_search)
    Button vlaBtnSearch;
    //    @Bind(R.id.vla_phone_recyclerView)
//    RecyclerView vlaPhoneRecyclerView;
    private String startTime;
    private String endTime;
    private Context mContext;
    private VisitListAdapter adapter;
    private CrmPhoneAdapter crmPhoneAdapter;
    private PrgDialog prgDialog;
    private List<OptionalCheck> optionals;
    private List<VisitList> resultBeanList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProjectApplication.getInstanceApp().addActivity(this);
        setContentView(R.layout.activity_visit_list);
        ButterKnife.bind(this);
        initData();
        initList();
    }


    private void initList() {
        loadData();
    }

    private void initData() {
        mContext = this;
        vla_toolbar.setTitle("");
        setSupportActionBar(vla_toolbar);
        vla_toolbar.setNavigationIcon(R.mipmap.icon_reg_back);
        vla_toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        vla_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //设置recycelerView的布局管理器
        vla_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //添加默认分割线
        vla_recyclerView.addItemDecoration(new DividerItemDecoration(this));

        //电话挂号列表
//        vlaPhoneRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        vlaPhoneRecyclerView.addItemDecoration(new DividerItemDecoration(this));


        //赋值初始值
        vla_et_start_date.setText(DateUtils.timeToShortString(new Date(), null));
        vla_et_end_date.setText(DateUtils.timeToShortString(new Date(), null));


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.aa_ll_call, R.id.vla_btn_search, R.id.vla_et_end_date, R.id.vla_et_start_date})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aa_ll_call:
                CallManager.callPhone(this, ShareInfoUtil.readPhone(this));
                break;
            case R.id.vla_btn_search:
                loadData();
                break;
            case R.id.vla_et_end_date:
                DateChooseUtils sdateChooseUtils = new DateChooseUtils(mContext, vla_et_end_date);
                sdateChooseUtils.getdate();
                break;
            case R.id.vla_et_start_date:
                DateChooseUtils edateChooseUtils = new DateChooseUtils(mContext, vla_et_start_date);
                edateChooseUtils.getdate();
                break;
        }
    }

    public void loadData() {
        startTime = vla_et_start_date.getText().toString();
        endTime = vla_et_end_date.getText().toString();


        //加载检查项价格
        HttpUtil.doHttp(HttpContanst.OPTIONAL_CHECK_ITEM, null, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                if (StringUtil.isNotBlank(result)) {
                    ResOptionalCheck resBean = GsonTools.getBean(result, ResOptionalCheck.class);
                    if (resBean.getStatus() == 0) {
                        optionals = resBean.getResult();
                        if (optionals != null && optionals.size() > 0) {
                            //适配数据
                            HashMap<String, String> map = new HashMap<String, String>();
                            String mphone = ShareInfoUtil.readLoginPhone(mContext);
//                            map.put("mobile", "15928413323");
                            map.put("mobile", mphone);
                            //加载CRM预约列表
                            HttpUtil.doHttp(HttpContanst.OPTIONAL_CRM_APP, map, new HttpUtil.IHttpResult() {
                                @Override
                                public void onSuccess(String result) {
                                    if (StringUtil.isNotBlank(result)) {
                                        ResVisitList resCrmPhone = GsonTools.getBean(result, ResVisitList.class);
                                        if (resCrmPhone.getStatus() == 0 && resCrmPhone.getResult() != null) {
                                            resultBeanList = resCrmPhone.getResult();

                                            if (resultBeanList != null && resultBeanList.size() > 0) {
                                                List<VisitList> tempResultBeans = new ArrayList<VisitList>();
                                                tempResultBeans = resultBeanList;
                                                for (int i = 0; i < optionals.size(); i++) {
                                                    for (int j = 0; j < resultBeanList.size(); j++) {
                                                        if (("" + (int) (optionals.get(i).getITEM_ID())).equals(resultBeanList.get(j).getType())) {
                                                            tempResultBeans.get(j).setProName(optionals.get(i).getITEM_NAME());
                                                            tempResultBeans.get(j).setPrice(optionals.get(i).getITEM_PRICE());
                                                            tempResultBeans.get(j).setSimpleDsc(optionals.get(i).getDESCRIBE());
                                                            tempResultBeans.get(j).setDetailDsc(optionals.get(i).getDESCRIBE_DETAIL());
                                                        }
                                                    }
                                                }

                                                HashMap<String, String> params = new HashMap<String, String>();
                                                params.put("method", "Visitdetail");
                                                params.put("ic_no", ShareInfoUtil.readResultBean(mContext).getIC_NO());
                                                SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd");
                                                startTime = sdfs.format(new Date()) + " 00:00:00";
                                                endTime = sdfs.format(new Date()) + " 23:59:59";
                                                params.put("visit_date1", startTime);
                                                params.put("visit_date2", endTime);
                                                prgDialog = new PrgDialog(mContext);
                                                final List<VisitList> finalTempResultBeans = tempResultBeans;
                                                HttpUtil.doHttp(HttpContanst.VISIT_LIST, params, new HttpUtil.IHttpResult() {
                                                    @Override
                                                    public void onSuccess(String result) {
                                                        if (StringUtil.isNotBlank(result)) {
                                                            ResVisitList resVisitList = GsonTools.getBean(result, ResVisitList.class);
                                                            if (resVisitList.getStatus() == 0) {
                                                                vList = resVisitList.getResult();
                                                                if (vList != null) {
                                                                    if (vList.size() <= 0) {


                                                                        //组装列表

//                                                                        VisitList v1 = new VisitList();
//                                                                        v1.setSUMCOSTS(200);
//                                                                        v1.setDEPT_NAME("ssdasd");
//                                                                        v1.setDOCTOR_NAME("122121");
//                                                                        v1.setVISIT_NO("12212121");
//                                                                        v1.setVISIT_DATE("2016-02-05");
//                                                                        VisitList v2 = new VisitList();
//                                                                        v2.setSUMCOSTS(200);
//                                                                        v2.setDEPT_NAME("ssdasd");
//                                                                        v2.setDOCTOR_NAME("122121");
//                                                                        v2.setVISIT_NO("12212121");
//                                                                        v2.setVISIT_DATE("2016-02-05");
//                                                                        VisitList v3 = new VisitList();
//                                                                        v3.setSUMCOSTS(200);
//                                                                        v3.setDEPT_NAME("ssdasd");
//                                                                        v3.setDOCTOR_NAME("122121");
//                                                                        v3.setVISIT_NO("12212121");
//                                                                        v3.setVISIT_DATE("2016-02-05");
//                                                                        VisitList v4 = new VisitList();
//                                                                        v4.setSUMCOSTS(200);
//                                                                        v4.setDEPT_NAME("ssdasd");
//                                                                        v4.setDOCTOR_NAME("122121");
//                                                                        v4.setVISIT_NO("12212121");
//                                                                        v4.setVISIT_DATE("2016-02-05");
//                                                                        VisitList v5 = new VisitList();
//                                                                        v5.setSUMCOSTS(200);
//                                                                        v5.setDEPT_NAME("ssdasd");
//                                                                        v5.setDOCTOR_NAME("122121");
//                                                                        v5.setVISIT_NO("12212121");
//                                                                        v5.setVISIT_DATE("2016-02-05");
//                                                                        VisitList v6 = new VisitList();
//                                                                        v6.setSUMCOSTS(200);
//                                                                        v6.setDEPT_NAME("ssdasd");
//                                                                        v6.setDOCTOR_NAME("122121");
//                                                                        v6.setVISIT_NO("12212121");
//                                                                        v6.setVISIT_DATE("2016-02-05");
//
//
//                                                                        vList.add(v1);
//                                                                        vList.add(v2);
//                                                                        vList.add(v3);
//                                                                        vList.add(v4);
//                                                                        vList.add(v5);
//                                                                        vList.add(v6);
                                                                        if (finalTempResultBeans != null && finalTempResultBeans.size() > 0) {
                                                                            vList.addAll(finalTempResultBeans);


                                                                            adapter = new VisitListAdapter(mContext, vList);
                                                                            vla_recyclerView.setAdapter(adapter);
                                                                            adapter.notifyDataSetChanged();
                                                                        } else {
                                                                            T.ShowToast(mContext, "暂无数据");
                                                                        }
                                                                        prgDialog.closeDialog();
                                                                    } else {

                                                                        if (finalTempResultBeans != null && finalTempResultBeans.size() > 0) {
                                                                            vList.addAll(finalTempResultBeans);
                                                                        }
                                                                        //倒序排序
                                                                        SortTool sort = new SortTool();
                                                                        Collections.sort(vList, sort);


                                                                        adapter = new VisitListAdapter(mContext, vList);
                                                                        vla_recyclerView.setAdapter(adapter);
                                                                        adapter.notifyDataSetChanged();
                                                                    }
//                            if (vList.size() == 1) {
//                                //生成订单
//                                HashMap<String, String> params = new HashMap<String, String>();
//                                params.put("method", "addorder");
//                                params.put("visit_no", vList.get(0).getVISIT_NO());
//                                params.put("ic_no", vList.get(0).getIC_CODE());
//                                params.put("token", ShareInfoUtil.readToken(mContext));
//                                HttpUtil.doHttp(HttpContanst.CREATE_ORDERNO, params, new HttpUtil.IHttpResult() {
//                                    @Override
//                                    public void onSuccess(String result) {
//                                        if (StringUtil.isNotBlank(result)) {
//                                            ResHisOrderId resHisOrderId = GsonTools.getBean(result, ResHisOrderId.class);
//                                            if (resHisOrderId.getStatus() == 0) {//如果只有一条那么直接生成订单跳到未交费列表
//                                                ResHisOrderId.ResultBean resultBean = resHisOrderId.getResult();
//                                                String orderId = resultBean.getOrder_id();
//
//                                                adapter = new VisitListAdapter(mContext, vList);
//                                                vla_recyclerView.setAdapter(adapter);
//                                                adapter.notifyDataSetChanged();
//
//                                                //跳转到未付费订单列表
//                                                Bundle bundle = new Bundle();
//                                                bundle.putString("order_id", orderId);
//                                                ProjectApplication.intentManager.toCheckPaymentActivity(bundle);
//
//                                                prgDialog.closeDialog();
//                                            } else {
//
//                                                T.ShowToast(mContext, "暂无数据");
//                                                prgDialog.closeDialog();
//                                            }
//
//                                        } else {
//                                            T.ShowToast(mContext, "返回数据为空，请稍后重试");
//                                            prgDialog.closeDialog();
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onError(Throwable ex, boolean isOnCallback) {
//                                        T.ShowToast(mContext, "请求错误，请稍后重试");
//                                        prgDialog.closeDialog();
//                                    }
//                                });
//                            } else {// 如果有多条那么直接显示一个月的再去缴费

//                            }
                                                                } else {
                                                                    T.ShowToast(mContext, "暂无数据");
                                                                }
                                                            } else {
                                                                T.ShowToast(mContext, "暂无数据.", 0);
                                                            }
                                                        } else {
                                                            T.ShowToast(mContext, "返回数据为空，请稍后重试");
                                                        }
                                                        prgDialog.closeDialog();
                                                    }

                                                    @Override
                                                    public void onError(Throwable ex, boolean isOnCallback) {
                                                        prgDialog.closeDialog();
                                                        T.ShowToast(mContext, "请求错误，请稍后重试。", 0);
                                                    }
                                                });
                                                //填充数据
//                                                crmPhoneAdapter = new CrmPhoneAdapter(mContext, tempResultBeans);
//                                                vlaPhoneRecyclerView.setAdapter(crmPhoneAdapter);
//                                                crmPhoneAdapter.notifyDataSetChanged();
                                            }
                                            //适配数据
                                        } else {
                                            T.ShowToast(mContext, "暂无数据", 0);
                                        }
                                    } else {
                                        T.ShowToast(mContext, "返回数据为空", 0);
                                    }
                                }

                                @Override
                                public void onError(Throwable ex, boolean isOnCallback) {
                                    T.ShowToast(mContext, "请求失败，请稍后重试。", 0);
                                }
                            });
                        }


                    } else {
                        T.ShowToast(mContext, "获取检查项信息失败 " + resBean.getMessage(), 0);
                    }
                } else {
                    T.ShowToast(mContext, "返回数据为空", 0);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.ShowToast(mContext, "请求失败，请稍后重试。", 0);
            }
        });


    }

}
