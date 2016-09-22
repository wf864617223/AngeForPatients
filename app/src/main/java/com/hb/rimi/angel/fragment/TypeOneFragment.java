package com.hb.rimi.angel.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.adapter.TypeLisAdapter;
import com.hb.rimi.angel.bean.ListBean;
import com.hb.rimi.angel.bean.ResponseLis;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.MyListView;
import com.hb.rimi.angel.util.StringUtil;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.view.PrgDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hp on 2016/6/12.
 */
@ContentView(R.layout.frag_type_list)
public class TypeOneFragment extends Fragment {


    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.tv_age)
    TextView tvAge;
    @Bind(R.id.lv_resultlist)
    MyListView lvResultlist;
    @Bind(R.id.tv_slavename)
    TextView tvSlavename;
    @Bind(R.id.tv_dept)
    TextView tvDept;
    @Bind(R.id.tv_bedno)
    TextView tvBedno;
    @Bind(R.id.tv_bir)
    TextView tvBir;
    @Bind(R.id.tv_toDocName)
    TextView tvToDocName;
    @Bind(R.id.tv_applyNo)
    TextView tvApplyNo;
    @Bind(R.id.tv_techNo)
    TextView tvTechNo;
    @Bind(R.id.tv_menzhen)
    TextView tvMenzhen;
    @Bind(R.id.tv_zhenduan)
    TextView tvZhenduan;
    @Bind(R.id.tv_execTime)
    TextView tvExecTime;
    @Bind(R.id.tv_execDocName)
    TextView tvExecDocName;
    @Bind(R.id.tv_reportTime)
    TextView tvReportTime;
    @Bind(R.id.tv_VerifierName)
    TextView tvVerifierName;
    @Bind(R.id.iv_user_img)
    ImageView ivUserImg;

    private PrgDialog prgDialog;
    private TypeLisAdapter adapter;
    private List<ListBean> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = x.view().inject(this, inflater, container);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {

        Bundle bundle = getArguments();
        String id = bundle.getString("id");
        prgDialog = new PrgDialog(getContext());
        HashMap<String, String> parms = new HashMap<String, String>();
        parms.put("type", "1");
        parms.put("id", id);

        HttpUtil.doHttp(HttpContanst.REPORT_INFO, parms, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                if (!TextUtils.isEmpty(result)) {
                    try {
                        ResponseLis responseLis = GsonTools.getBean(result, ResponseLis.class);
                        int status = responseLis.getStatus();
                        if (status == 0) {
                            ResponseLis.ResultBean result1 = responseLis.getResult();
                            //tvShengqingdanhao.setText(result1.getApplyNo()+"");
                            //tvTechNo.setText("样本号"+result1.getTechNo());
                            tvName.setText("就诊人：" + result1.getPatName());
                            int sex = result1.getSex();
                            if (sex == 1) {
                                tvSex.setText("性别：男");
                                ivUserImg.setImageResource(R.mipmap.man);
                            } else {
                                tvSex.setText("性别：女");
                                ivUserImg.setImageResource(R.mipmap.woman);
                            }
                            tvAge.setText("年龄：" + result1.getAge());
                            tvApplyNo.setText("病历号：" + result1.getCardNo());
                            //tvCardNo.setText(result1.getCardNo());
                            tvDept.setText("科室名：" + result1.getApplyDeptName());
                            tvBedno.setText("床号：" + result1.getBedNo());
                            tvSlavename.setText("检查样品：" + result1.getSlavename());
                            //tvSampleTime.setText("样品时间："+result1.getSampleTime());
                            //tvReceiveTime.setText("生成时间："+result1.getReceiveTime());
                            tvZhenduan.setText("临床诊断：" + result1.getClinicDesc());
                            tvToDocName.setText("送检医生：" + result1.getToDocName());
                            tvExecDocName.setText("检验医生：" + result1.getExecDocName());
                            String execTime = result1.getExecTime();
                            String[] split = execTime.split(" ");
                            if (split[1].equals("0:00:00")) {
                                tvExecTime.setText("检查时间：" + split[0]);
                            } else {
                                tvExecTime.setText("检查时间：" + result1.getExecTime());
                            }
                            String reportTime = result1.getReportTime();
                            String[] split1 = reportTime.split(" ");
                            if (split1[1].equals("0:00:00")) {
                                tvReportTime.setText("报告时间：" + split1[0]);
                            } else {
                                tvReportTime.setText("报告时间：" + result1.getReportTime());
                            }
                            tvVerifierName.setText("审核人：" + result1.getVerifierName());
                            List<ResponseLis.ResultBean.ListBean> beans = result1.getList();
                            if (beans != null && beans.size() > 0) {//说明有值
                                if (beans.get(0) != null && StringUtil.isNotBlank(beans.get(0).getItemName())) {
                                    adapter = new TypeLisAdapter(beans, getContext());
                                    lvResultlist.setAdapter(adapter);
                                }
                            } else {
                                System.out.println("无list数据");
                            }
                        } else {
                            T.ShowToast(getContext(), "暂无数据", 0);
                            prgDialog.closeDialog();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        prgDialog.closeDialog();
                    }
                } else {
                    T.ShowToast(getContext(), "数据为空，请稍后再试！", 0);
                    prgDialog.closeDialog();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
               /* System.out.println("====ex==》"+ex.getMessage());
                T.ShowToast(getContext(),ex.getMessage());*/
                T.ShowToast(getContext(), "请求失败，请重试！", 0);

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
