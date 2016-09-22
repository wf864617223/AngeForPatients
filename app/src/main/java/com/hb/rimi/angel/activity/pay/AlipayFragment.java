package com.hb.rimi.angel.activity.pay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.activity.home.MedicalVoucherActivity;
import com.hb.rimi.angel.activity.mine.MyOrderActivity;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.bean.ResPayQuick;
import com.hb.rimi.angel.util.PayResult;
import com.hb.rimi.angel.util.StringUtil;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.view.PrgDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 支付宝支付
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AlipayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlipayFragment extends Fragment implements View.OnClickListener {
    // 商户PID
    public static final String PARTNER = "2088221885977122";
    // 商户收款账号
    public static final String SELLER = "cdxqangel@angel-hospital.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKq3mEKUpI8sZlb/Hydwvvc+wkrXPlwCXSfN0kU7n0NNTPwiXRoPH+HBZrPQmbP3qMExN5w+3PO4tr4KA7iWTN3vxzx2mySqInQu50aOrEejLTMCIj6TE/0oH0T4masNQujGHTuoVlC9yC8ASs18xDNP+/UEUVWrxzO6rvQWqL2BAgMBAAECgYA/VIxvZqUFV/s/GrV79B9jqSx6hw7jIx1bvsbaWBvk47BDeJwBJss0/IIVArWCRcWcpZ1zoP5mi4d/SEfJGji5IMElT0E+3sGN79QX/00vocOveYsLgas9eyEsKPgFYhFakOcGpr7V3poJddl9DFaS36EWJ+Y1NMAIwt1anVXb4QJBAOHNM1vKpFHVW3uYnV55YRPrKpaz/sPMctYb6aINLQQdOrTDH3shhdyRCsFGnD3o8qE3zreXrqo84CRBz8pQF48CQQDBjHZF9auPIsJtB/yh8zpNr4eQeA1NJQL3eJ+3jBanRpF8vS3OS9M/DspzC2gF9R9CT1Whl2Xb1KadsM/HadHvAkAU9jvCVbmTbz8i5jQVxSbh9n7ppqQMATrX7Zdu19JKA3yjs0mE4MPQihZ5gtiWVr3PgaLLIGWyoBwewY1Mtsq5AkBWpWYRXbDG7F2z595uNfAE9S6wOz5hKc+RL/v4dItzqUDaYeqOrx1L3ng9Vn7kI98xlUiVhayA5EsOzyfFcxcbAkEAzITt0pheD415rlUhA34gLXC+Kxnzn/aad0duBpCnmxruvgIIeVpFzkRK2xd23KkCjHZfhjhBuBQnyyEZD3M+KA==";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int SDK_PAY_FLAG = 1;
    @Bind(R.id.fragment_btn_pay)
    Button fragmentBtnPay;
    @Bind(R.id.alf_tv_result)
    TextView alf_tv_result;
    private String alipayContruct;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //    private OnFragmentInteractionListener mListener;
    private Context mContext;
    private Activity mActivity;
    private PrgDialog alipayDialog;
    private String alipayprojectTypeValue;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        alf_tv_result.setText("支付成功");
                        T.ShowToast(mContext, "支付成功", 1);
//                        //退回到就诊凭证
                        if ("0".equals(alipayprojectTypeValue) || "1".equals(alipayprojectTypeValue)) {
//                            Intent mvaIntent = new Intent(mContext, MedicalVoucherActivity.class);
//                            //将MainAtivity的launchMode设置成SingleTask, 或者在下面flag中加上Intent.FLAG_CLEAR_TOP,
//                            //如果Task栈中有MainActivity的实例，就会把它移到栈顶，把在它之上的Activity都清理出栈，
//                            //如果Task栈不存在MainActivity实例，则在栈顶创建
//                            mvaIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
//                                    | Intent.FLAG_ACTIVITY_NEW_TASK);
//                            mContext.startActivity(mvaIntent);

                            ProjectApplication.getInstanceApp().exit();
                            Intent mvaIntent = new Intent(mContext, MedicalVoucherActivity.class);
                            mContext.startActivity(mvaIntent);

                        } else if ("2".equals(alipayprojectTypeValue)) {
//                            Intent mvaIntent = new Intent(mContext, MyOrderActivity.class);
//                            //将MainAtivity的launchMode设置成SingleTask, 或者在下面flag中加上Intent.FLAG_CLEAR_TOP,
//                            //如果Task栈中有MainActivity的实例，就会把它移到栈顶，把在它之上的Activity都清理出栈，
//                            //如果Task栈不存在MainActivity实例，则在栈顶创建
//                            mvaIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
//                                    | Intent.FLAG_ACTIVITY_NEW_TASK);
//                            mvaIntent.putExtra("notpay","0");//0未支付
//                            mContext.startActivity(mvaIntent);

//                            ProjectApplication.getInstanceApp().exit();
//                            Intent mvaIntent = new Intent(mContext, MyOrderActivity.class);
//                            mvaIntent.putExtra("notpay","1");//0未支付
//                            mContext.startActivity(mvaIntent);

                            ProjectApplication.getInstanceApp().exit();
                            Intent mvaIntent = new Intent(mContext, MedicalVoucherActivity.class);
                            mContext.startActivity(mvaIntent);
                        }

                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            alf_tv_result.setText("支付结果确认中");
                            T.ShowToast(mContext, "支付结果确认中");
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            alf_tv_result.setText("支付失败");
                            T.ShowToast(mContext, "支付失败", 1);

                        }

                    }
                    if (alipayDialog != null) {
                        alipayDialog.closeDialog();
                    }
                    mActivity.finish();
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };
    private boolean isPay = false;
    private int alipayPayType = 0;

    public AlipayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AlipayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlipayFragment newInstance(String param1, String param2) {
        AlipayFragment fragment = new AlipayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Bundle bundle = getArguments();//从activity传过来的Bundle
        if (bundle != null) {
            alipayContruct = bundle.getString("alipayContruct");
            alipayPayType = bundle.getInt("payType");
            alipayprojectTypeValue = bundle.getString(PayActivity.projectType);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alipay, container, false);
        ButterKnife.bind(this, view);
        mContext = getContext();
        mActivity = getActivity();
        //调起支付

        if (alipayPayType == 2) {
            if (alipayDialog == null) {
                alipayDialog = new PrgDialog(mContext);
            }
            toAlipayPay();
        }

        return view;
    }


    @Override
    public void onDestroyView() {

        ButterKnife.unbind(this);
        if (alipayDialog != null) {
            alipayDialog.closeDialog();
        }
        super.onDestroyView();
    }

    //调用支付
    private void toAlipayPay() {
        //支付
        if (StringUtil.isBlank(PARTNER) || StringUtil.isBlank(RSA_PRIVATE)
                || StringUtil.isBlank(SELLER)) {
            new AlertDialog.Builder(mContext)
                    .setTitle("警告")
                    .setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialoginterface, int i) {
                                    //隐藏
                                    dialoginterface.dismiss();
                                }
                            }).show();
            return;
        }
        //订单信息
        /**
         * 完整的符合支付宝参数规范的订单信息
         */

        final String payInfoNew = alipayContruct;
        //final String payInfoNew="_input_charset=\"utf-8\"&body=\"挂号-孔医生\"&it_b_pay=\"30m\"&notify_url=\"http://117.172.47.171:1234/pay/alipay/callback\"&out_trade_no=\"d8d8985f695b4e52b3463ae4a157c0b6\"&partner=\"2088221885977122\"&payment_type=\"1\"&seller_id=\"cdxqangel@angel-hospital.com\"&service=\"mobile.securitypay.pay\"&subject=\"挂号-孔医生\"&total_fee=\"0.01\"&sign=\"YNoFsgvQiieTCr2gIEII0xeBHT8z%2FDlaicaCqeBH1U5W%2FSm5Ll4QbbgJlAytFDd%2BBN7D8Bfb3QpcWuVSBmAMqOzvz88FM1Gbytk6CYH3PRFL2rhebJntWUf5n1kIoKg%2BoIVPbNJ1aie4178OutzeQ%2BeQyr58Ou66JYejQgRd%2Fww%3D\"&sign_type=\"RSA\"";
        System.out.println("payInfoNew====>" + payInfoNew);

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(mActivity);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfoNew, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @OnClick({R.id.fragment_btn_pay})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_btn_pay:
                //支付
                if (StringUtil.isBlank(PARTNER) || StringUtil.isBlank(RSA_PRIVATE)
                        || StringUtil.isBlank(SELLER)) {
                    new AlertDialog.Builder(mContext)
                            .setTitle("警告")
                            .setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                            .setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialoginterface, int i) {
                                            //隐藏
                                            dialoginterface.dismiss();
                                        }
                                    }).show();
                    return;
                }
                //订单信息
                /**
                 * 完整的符合支付宝参数规范的订单信息
                 */

                final String payInfoNew = alipayContruct;
                //final String payInfoNew="_input_charset=\"utf-8\"&body=\"挂号-孔医生\"&it_b_pay=\"30m\"&notify_url=\"http://117.172.47.171:1234/pay/alipay/callback\"&out_trade_no=\"d8d8985f695b4e52b3463ae4a157c0b6\"&partner=\"2088221885977122\"&payment_type=\"1\"&seller_id=\"cdxqangel@angel-hospital.com\"&service=\"mobile.securitypay.pay\"&subject=\"挂号-孔医生\"&total_fee=\"0.01\"&sign=\"YNoFsgvQiieTCr2gIEII0xeBHT8z%2FDlaicaCqeBH1U5W%2FSm5Ll4QbbgJlAytFDd%2BBN7D8Bfb3QpcWuVSBmAMqOzvz88FM1Gbytk6CYH3PRFL2rhebJntWUf5n1kIoKg%2BoIVPbNJ1aie4178OutzeQ%2BeQyr58Ou66JYejQgRd%2Fww%3D\"&sign_type=\"RSA\"";
                System.out.println("payInfoNew====>" + payInfoNew);

                Runnable payRunnable = new Runnable() {

                    @Override
                    public void run() {
                        // 构造PayTask 对象
                        PayTask alipay = new PayTask(mActivity);
                        // 调用支付接口，获取支付结果
                        String result = alipay.pay(payInfoNew, true);

                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };

                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();
                break;
        }

    }

    /**
     * create the order info. 创建订单信息 server返回
     */
    private String getOrderInfo(ResPayQuick.ResultBean.AlipayBean bean) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + bean.getPartner();

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + bean.getSeller_id();

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + bean.getOut_trade_no();

        // 商品名称
        orderInfo += "&subject=" + bean.getSubject();

        // 商品详情
        orderInfo += "&body=" + bean.getBody();

        // 商品金额
        orderInfo += "&total_fee=" + bean.getTotal_fee();

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + bean.getNotify_url()
        ;

        // 服务接口名称， 固定值
        orderInfo += "&service=" + bean.getService();

        // 支付类型， 固定值
        orderInfo += "&payment_type=" + bean.getPayment_type();

        // 参数编码， 固定值
        orderInfo += "&_input_charset=" + bean.get_input_charset();

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=" + bean.getIt_b_pay();

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }


    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
