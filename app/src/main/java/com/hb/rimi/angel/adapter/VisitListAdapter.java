package com.hb.rimi.angel.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.bean.ResHisOrderId;
import com.hb.rimi.angel.bean.VisitList;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.ShareInfoUtil;
import com.hb.rimi.angel.util.StringUtil;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.view.PrgDialog;

import java.util.HashMap;
import java.util.List;

/**
 * Created by rimi on 2016/6/1.
 */
public class VisitListAdapter extends RecyclerView.Adapter<VisitListAdapter.MyViewHolder> {
    private Context mContext;
    private List<VisitList> visitLists;
    private LayoutInflater layoutInflater;
    private PrgDialog prgDialog;

    public VisitListAdapter(Context mContext, List<VisitList> visitLists) {
        this.mContext = mContext;
        this.visitLists = visitLists;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_visit_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        if (StringUtil.isNotBlank(visitLists.get(position).getType())) {
            //如果是网电预约
            holder.ivl_label_change_name.setText("项目名称：");
            holder.ivl_ll_doctor_name.setVisibility(View.GONE);
            holder.ivl_tv_dept_name.setText(visitLists.get(position).getProName());
            holder.ivl_tv_visit_date.setText(visitLists.get(position).getDateEnd());
            holder.ivl_tv_sumcosts.setText("" + visitLists.get(position).getPrice());
        } else {
            holder.ivl_ll_doctor_name.setVisibility(View.VISIBLE);
            holder.ivl_label_change_name.setText("部门名称：");
            holder.ivl_tv_doctor_name.setText(visitLists.get(position).getDOCTOR_NAME());
            holder.ivl_tv_visit_no.setText(visitLists.get(position).getVISIT_NO());

            holder.ivl_tv_dept_name.setText(visitLists.get(position).getDEPT_NAME());
            holder.ivl_tv_detp_code.setText(visitLists.get(position).getDETP_CODE());
            holder.ivl_tv_patient_name.setText(visitLists.get(position).getPATIENT_NAME());
            holder.ivl_tv_ic_code.setText(visitLists.get(position).getIC_CODE());
            holder.ivl_tv_visit_date.setText(visitLists.get(position).getVISIT_DATE());
            holder.ivl_tv_doctor_code.setText(visitLists.get(position).getDOCTOR_CODE());
            if (StringUtil.isNotBlank("" + visitLists.get(position).getSUMCOSTS())) {
                holder.ivl_tv_sumcosts.setText("" + visitLists.get(position).getSUMCOSTS());
            }
        }

        holder.ivl_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StringUtil.isNotBlank(visitLists.get(position).getType())) {
                    //TODO 去支付
                    Bundle bundle = new Bundle();
                    bundle.putString("targetType", "0");
                    try {
                        bundle.putDouble("item_id", Double.valueOf(visitLists.get(position).getType()));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    bundle.putString("item_name", visitLists.get(position).getProName());
                    bundle.putString("wdyy_id", "" + visitLists.get(position).getID());
                    bundle.putString("end_date", visitLists.get(position).getDateEnd());
                    bundle.putDouble("item_price", visitLists.get(position).getPrice());
                    bundle.putString("describe", visitLists.get(position).getSimpleDsc());
                    bundle.putString("describe_detail", visitLists.get(position).getDetailDsc());
                    ProjectApplication.intentManager.toBAppointmentPayActivity(bundle);
                } else {
                    //生成订单
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("method", "addorder");
                    params.put("visit_no", visitLists.get(position).getVISIT_NO());
//                params.put("ic_no", "a1606150011");
                    params.put("ic_no", visitLists.get(position).getIC_CODE());
                    params.put("token", ShareInfoUtil.readToken(mContext));
                    prgDialog = new PrgDialog(mContext);
                    HttpUtil.doHttp(HttpContanst.CREATE_ORDERNO, params, new HttpUtil.IHttpResult() {
                        @Override
                        public void onSuccess(String result) {
                            if (StringUtil.isNotBlank(result)) {
                                ResHisOrderId resHisOrderId = GsonTools.getBean(result, ResHisOrderId.class);
                                if (resHisOrderId.getStatus() == 0) {
                                    ResHisOrderId.ResultBean resultBean = resHisOrderId.getResult();
                                    String orderId = resultBean.getOrder_id();

                                    //跳转到未付费订单列表
                                    Bundle bundle = new Bundle();
                                    bundle.putString("order_id", orderId);
                                    bundle.putDouble("sumcosts", visitLists.get(position).getSUMCOSTS());
                                    ProjectApplication.intentManager.toCheckPaymentActivity(bundle);
                                    prgDialog.closeDialog();
                                } else {
                                    T.ShowToast(mContext, "请求失败，请稍后重试。");
                                    prgDialog.closeDialog();
                                }

                            } else {
                                T.ShowToast(mContext, "返回数据为空，请稍后重试");
                                prgDialog.closeDialog();
                            }
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            T.ShowToast(mContext, "请求错误，请稍后重试");
                            prgDialog.closeDialog();
                        }
                    });
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return visitLists == null ? 0 : visitLists.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ivl_tv_doctor_name,
                ivl_tv_visit_no,
                ivl_tv_dept_name,
                ivl_tv_detp_code,
                ivl_tv_patient_name,
                ivl_tv_ic_code,
                ivl_tv_visit_date,
                ivl_tv_doctor_code, ivl_tv_sumcosts, ivl_label_change_name;
        LinearLayout ivl_ll,ivl_ll_doctor_name;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivl_label_change_name = (TextView) itemView.findViewById(R.id.ivl_label_change_name);
            ivl_tv_doctor_name = (TextView) itemView.findViewById(R.id.ivl_tv_doctor_name);
            ivl_tv_visit_no = (TextView) itemView.findViewById(R.id.ivl_tv_visit_no);


            ivl_tv_dept_name = (TextView) itemView.findViewById(R.id.ivl_tv_dept_name);
            ivl_tv_detp_code = (TextView) itemView.findViewById(R.id.ivl_tv_detp_code);
            ivl_tv_patient_name = (TextView) itemView.findViewById(R.id.ivl_tv_patient_name);
            ivl_tv_ic_code = (TextView) itemView.findViewById(R.id.ivl_tv_ic_code);
            ivl_tv_visit_date = (TextView) itemView.findViewById(R.id.ivl_tv_visit_date);
            ivl_tv_doctor_code = (TextView) itemView.findViewById(R.id.ivl_tv_doctor_code);
            ivl_tv_sumcosts = (TextView) itemView.findViewById(R.id.ivl_tv_sumcosts);


            ivl_ll = (LinearLayout) itemView.findViewById(R.id.ivl_ll);
            ivl_ll_doctor_name = (LinearLayout) itemView.findViewById(R.id.ivl_ll_doctor_name);
        }
    }
}
