package com.hb.rimi.angel.activity.pay;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hb.rimi.angel.R;
import com.hb.rimi.angel.activity.home.MedicalVoucherActivity;
import com.hb.rimi.angel.activity.mine.MyOrderActivity;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.bean.ResWxpayQuick;
import com.hb.rimi.angel.contanst.Contanst;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.T;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 微信支付
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@links OnFragmentInteractionListenerz } interface
 * to handle interaction events.
 * Use the {@link WxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WxFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.wxf_tv_result)
    TextView wxf_tv_result;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //    private OnFragmentInteractionListener mListener;
    private Context mContext;
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    private String result;
    private int wxPayType = 0;
    private String wxprojectTypeValue;


    public WxFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WxFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WxFragment newInstance(String param1, String param2) {
        WxFragment fragment = new WxFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mContext = getContext();
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(mContext, Contanst.APP_ID, false);
        // 将该app注册到微信
        api.registerApp(Contanst.APP_ID);
        Bundle bundle = getArguments();//从activity传过来的Bundle
        if (bundle != null) {
            result = bundle.getString(PayActivity.keyResult);
            wxPayType = bundle.getInt("payType");
            wxprojectTypeValue = bundle.getString(PayActivity.projectType);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wx, container, false);
        ButterKnife.bind(this, view);
        //调起支付
        if (wxPayType == 1) {
            toWxPay();
        }
        return view;
    }

    private void toWxPay() {
        //调起本地支付
        //检查版本是否支持支付
        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        if (isPaySupported) {
//                    String url = "http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php?plat=android";
//                    String contentParams = "{\"message\":\"Success\",\"result\":{\"wechat\":{\"appid\":\"wx19b0c821e604e397\",\"noncestr\":\"840e5f3a0df24bedb4f2cbb1b8d39132\",\"package\":\"Sign=WXPay\",\"partnerid\":\"1359307702\",\"prepayid\":\"wx201606271611047256c0d8c20312766740\",\"sign\":\"3B28C3854A84F11F45906F9724204F5C\",\"timestamp\":\"1467015176680\"},\"wechatContruct\":\"appid=wx19b0c821e604e397&noncestr=840e5f3a0df24bedb4f2cbb1b8d39132&package=Sign%3DWXPay&partnerid=1359307702&prepayid=wx201606271611047256c0d8c20312766740&timestamp=1467015176680&sign=3B28C3854A84F11F45906F9724204F5C\"},\"status\":0}";
            ResWxpayQuick resWxpayQuick = GsonTools.getBean(result, ResWxpayQuick.class);

            T.ShowToast(mContext, "获取订单中...");
            try {
//
                PayReq req = new PayReq();

                req.appId = resWxpayQuick.getResult().getWechat().getAppid();
                req.partnerId = resWxpayQuick.getResult().getWechat().getPartnerid();
                req.prepayId = resWxpayQuick.getResult().getWechat().getPrepayid();
                req.nonceStr = resWxpayQuick.getResult().getWechat().getNoncestr();

                req.timeStamp = resWxpayQuick.getResult().getWechat().getTimestamp();
                req.packageValue = resWxpayQuick.getResult().getWechat().getPackageX();
                req.sign = resWxpayQuick.getResult().getWechat().getSign();
                req.extData = "app data"; // optional


                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                boolean isSend = api.sendReq(req);
                T.ShowToast(mContext, "正在调起微信支付...");

                getActivity().finish();
            } catch (Exception e) {
                Log.e("PAY_GET", "异常：" + e.getMessage());
                T.ShowToast(mContext, "异常：" + e.getMessage());
            }
        } else {
            T.ShowToast(mContext, "当前微信版本不支持支付，请更新微信");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (wxPayType==1&&result == null) {
            getActivity().finish();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
