package com.hb.rimi.angel.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.activity.MainActivity;
import com.hb.rimi.angel.activity.mine.AboutAppActivity;
import com.hb.rimi.angel.activity.mine.ChangePhoneActivity;
import com.hb.rimi.angel.activity.mine.ChangePwdActivity;
import com.hb.rimi.angel.activity.mine.LoginActivity;
import com.hb.rimi.angel.activity.mine.MineInformationActivity;
import com.hb.rimi.angel.activity.mine.MyAdviceActivity;
import com.hb.rimi.angel.activity.mine.MyMessageActivity;
import com.hb.rimi.angel.activity.mine.MyOrderActivity;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.bean.CustomPhone;
import com.hb.rimi.angel.bean.HasNewMessage;
import com.hb.rimi.angel.bean.ResBean;
import com.hb.rimi.angel.bean.UserInfo;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.util.CallManager;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.ShareInfoUtil;
import com.hb.rimi.angel.util.StringUtil;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.view.PrgDialog;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的
 */
@ContentView(R.layout.fragment_my)
public class MineFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.mine_img_header)
    ImageView mineImgHeader;
    @Bind(R.id.imfb_iv_phone_logo)
    ImageView imfbIvPhoneLogo;
    @Bind(R.id.imfb_tv_phone)
    TextView imfbTvPhone;
    @Bind(R.id.imfb_iv_customer_specialist)
    ImageView imfbIvCustomerSpecialist;
    @Bind(R.id.imfb_tv_customer_specialist)
    TextView imfbTvCustomerSpecialist;
    @Bind(R.id.imfb_iv_myorder)
    ImageView imfbIvMyorder;
    @Bind(R.id.imfb_tv_myorder)
    TextView imfbTvMyorder;
    @Bind(R.id.imfb_iv_edit_psd)
    ImageView imfbIvEditPsd;
    @Bind(R.id.imfb_tv_edit_psd)
    TextView imfbTvEditPsd;
    @Bind(R.id.change_pwd)
    RelativeLayout changePwd;
    @Bind(R.id.imfb_iv_complaints_suggestions)
    ImageView imfbIvComplaintsSuggestions;
    @Bind(R.id.imfb_tv_complaints_suggestions)
    TextView imfbTvComplaintsSuggestions;
    @Bind(R.id.my_advice)
    RelativeLayout myAdvice;
    @Bind(R.id.imfb_iv_about_app)
    ImageView imfbIvAboutApp;
    @Bind(R.id.imfb_tv_about_app)
    TextView imfbTvAboutApp;
    @Bind(R.id.btn_loginOut)
    Button btnLoginOut;
    @Bind(R.id.relative_changePhone)
    RelativeLayout relativeChangePhone;
    @Bind(R.id.my_customService)
    RelativeLayout myCustomService;
    @Bind(R.id.my_order)
    RelativeLayout myOrder;
    @Bind(R.id.tv_mine_userName)
    TextView tvMineUserName;
    @Bind(R.id.tv_userType)
    TextView tvUserType;
    @Bind(R.id.ma_toolbar)
    Toolbar ma_toolbar;
    @Bind(R.id.iv_sex)
    ImageView ivSex;
    @Bind(R.id.about_app)
    RelativeLayout aboutApp;
    @Bind(R.id.my_message)
    RelativeLayout myMessage;
    @Bind(R.id.iv_has_new_message)
    ImageView ivHasNewMessage;
    //加载数据对话框
    private PrgDialog prgDialog, prgDialog2;
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private Context mContext;
    @ViewInject(R.id.include_body)
    private View includeBody;
    @ViewInject(R.id.include_top)
    private View includeTop;
    private String myCustomtTelephone;
    private String mobile;
    private String customerManagerMobile;


    public MineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MineFragment.
     */
    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View view = inflater.inflate(R.layout.fragment_my, container, false);
        View view = x.view().inject(this, inflater, container);
        ButterKnife.bind(this, view);
        initData();
        initView(view);
        //T.ShowToast(getContext(),"OK",0);
        return view;
    }

    private void initView(View view) {
        //includeTop = view.findViewById(R.id.include_top);
        //includeBody = view.findViewById(R.id.include_body);
        String s = ShareInfoUtil.readCid(getContext());
        String s1 = ShareInfoUtil.readToken(getContext());
        //T.ShowToast(getContext(), "==s=>" + s, 1);
        //System.out.println("==s=>" + s);
        //System.out.println("==s1=>" + s1);
        /*if (s.equals("")) {
            tvMineUserName.setText("");
            ivSex.setVisibility(View.GONE);
        }*/
        //修改手机号码
        relativeChangePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChangePhoneActivity.class);
                startActivity(intent);
            }
        });
        //我的客服专员
        myCustomService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringUtil.isBlank(customerManagerMobile)) {
                    CallManager.callPhone(mContext, ShareInfoUtil.readPhone(mContext));
                } else {
                    CallManager.callPhone(mContext, customerManagerMobile);
                }
                //CallManager.callPhone(mContext, ShareInfoUtil.readPhone(mContext));
            }
        });
        //我的订单
        myOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MyOrderActivity.class);
                startActivity(intent);
            }
        });
        //点击显示个人信息
        includeTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MineInformationActivity.class);
                startActivity(intent);
            }
        });
        //修改密码的控件
        //changePwd = (RelativeLayout) includeBody.findViewById(R.id.change_pwd);
        //设置监听
        changePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChangePwdActivity.class);
                intent.putExtra("phone", mobile);
                startActivity(intent);
            }
        });
        //我的消息
        myMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivHasNewMessage.setVisibility(View.GONE);
                Intent intent = new Intent(getContext(), MyMessageActivity.class);
                startActivity(intent);
            }
        });
        //投诉建议的控件
        //myAdvice = (RelativeLayout) includeBody.findViewById(R.id.my_advice);
        //设置监听
        myAdvice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MyAdviceActivity.class);
                startActivity(intent);
            }
        });
        //关于APP
        aboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AboutAppActivity.class);
                startActivity(intent);
            }
        });
        //退出登录
        btnLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext()).setTitle("确认").setMessage("确定要退出登录吗？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Intent intent = new Intent(getContext(), LoginActivity.class);
//                                startActivity(intent);
//                                getActivity().finish();
                                Intent logoutIntent = new Intent(getContext(),
                                        LoginActivity.class);
                                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(logoutIntent);
                                ShareInfoUtil.saveLoginPwd(mContext, "");
                                ShareInfoUtil.saveParams(mContext, "isLogin", "false");
                                //退出环信 此方法为异步方法+
                                EMClient.getInstance().logout(true, new EMCallBack() {
                                    @Override
                                    public void onSuccess() {
                                        System.out.println("退出环信suc");
                                    }

                                    @Override
                                    public void onError(int i, String s) {
                                        System.out.println("退出环信error");
                                    }

                                    @Override
                                    public void onProgress(int i, String s) {

                                    }
                                });

                                getActivity().finish();


                            }
                        })
                        .setNegativeButton("否", null).show();
            }
        });
    }

    private void initData() {
        this.mContext = getContext();
        //设置标题栏
//        ma_toolbar.setTitle("");
//        ((MainActivity) getActivity()).setSupportActionBar(ma_toolbar);
        //圆角头像
        //imft_iv_header.setImageBitmap(ImageUtil.toRoundBitmap(ImageUtil.convertResToBm(this.mContext, R.mipmap.ic_launcher)));

        //HashMap<String, String> params = new HashMap<String, String>();
        //params.put("cid","951249");


        prgDialog = new PrgDialog(getContext());
        prgDialog2 = new PrgDialog(getContext());
        HashMap<String, String> params = new HashMap<String, String>();
        String cid = ShareInfoUtil.readCid(getContext());
        //T.ShowToast(getContext(), "cid==>" + cid, 1);
        params.put("cid", cid);
        //params.put("cid","951249");
        HttpUtil.doHttp(HttpContanst.USER_INFO, params, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                //T.ShowToast(mContext, "获取用户信息==>" + result);
                ProjectApplication.logUtil.d(result);
                //result.replaceAll("\r\n", "");
                //Log.i("info","====result==>"+result);
                if (StringUtil.isNotBlank(result)) {
                    UserInfo userInfo = GsonTools.getBean(result, UserInfo.class);
                    String message = userInfo.getMessage();
                    int status = userInfo.getStatus();
                    if (status == 0) {
                        final UserInfo.ResultBean result1 = userInfo.getResult();
                        //客户姓名
                        String name = result1.getName();
                        String sex = result1.getSex();
                        customerManagerMobile = result1.getCustomerManagerMobile();
                        if ("0".equals(sex)) {
                            mineImgHeader.setImageResource(R.mipmap.woman);
                            ivSex.setImageResource(R.mipmap.women);
                        } else if ("1".equals(sex)) {
                            mineImgHeader.setImageResource(R.mipmap.man);
                            ivSex.setImageResource(R.mipmap.men);
                        } else {
                            mineImgHeader.setImageResource(R.mipmap.photo);
                        }

                        if (StringUtil.isBlank(result1.getIC_NO())) {
                            String token = ShareInfoUtil.readToken(getContext());
                            HttpUtil.doHttp(HttpContanst.LOCAT_ICNO + token, null, new HttpUtil.IHttpResult() {
                                @Override
                                public void onSuccess(String result) {

                                    if (StringUtil.isNotBlank(result)) {
                                        ResBean resBean = GsonTools.getBean(result, ResBean.class);
                                        if (resBean.getStatus() == 0) {
                                            String res = resBean.getResult().toString();
                                            try {
                                                JSONObject jsonObject = new JSONObject(res);
                                                if (jsonObject.has("ic_no")) {
                                                    String ic_No = jsonObject.getString("ic_no");
                                                    result1.setIC_NO(ic_No);
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }
                                    ShareInfoUtil.saveReaultBean(getContext(), result1);
                                }

                                @Override
                                public void onError(Throwable ex, boolean isOnCallback) {
                                    ShareInfoUtil.saveReaultBean(getContext(), result1);
                                }
                            });
                        } else {
                            ShareInfoUtil.saveReaultBean(getContext(), result1);
                        }
                        mobile = result1.getMobile();
//                        int length = name.length();
//                        if (length == 2) {
//                            String[] split = name.split("");
//                            System.out.println("===split[0]==>" + split[0]);
//                            System.out.println("===split[1]==>" + split[1]);
//                            tvMineUserName.setText(" " + split[1] + " " + split[2]);
//                        } else {
                        tvMineUserName.setText(name);
//                        }

                    } else {
                        ShareInfoUtil.saveReaultBean(mContext);
                        //T.ShowToast(getContext(), "请求数据错误", 0);
                    }

                } else {
                    T.ShowToast(getContext(), "返回数据为空", 0);
                }
                prgDialog.closeDialog();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.ShowToast(getContext(), "信息请求失败，请重试", 0);
                prgDialog.closeDialog();
            }
        });
        HttpUtil.doHttp(HttpContanst.CUSTOM_PHONE, null, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                ProjectApplication.logUtil.d(result);
                if (StringUtil.isNotBlank(result)) {
                    CustomPhone customPhone = GsonTools.getBean(result, CustomPhone.class);
                    String message = customPhone.getMessage();
                    if (message.equals("Success")) {
                        CustomPhone.ResultBean result1 = customPhone.getResult();
                        CustomPhone.ResultBean.ContactTelephoneBean contact_telephone = result1.getContact_telephone();
                        myCustomtTelephone = contact_telephone.getTelephone();
                        //myCustomtTelephone = contact_telephone.getTelephone();
                        ShareInfoUtil.savePhone(getContext(), myCustomtTelephone);
                    } else {
                        T.ShowToast(getContext(), "请求数据错误", 0);
                    }


                } else {
                    T.ShowToast(mContext, "返回数据为空", 0);
                }
                prgDialog2.closeDialog();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.ShowToast(getContext(), "请求失败，请重试", 0);
                prgDialog2.closeDialog();
            }
        });
        String token = ShareInfoUtil.readToken(getContext());
        HttpUtil.doHttp(HttpContanst.HAS_NEW_MESSAGE + "token=" + token, null, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                HasNewMessage hasNewMessage = GsonTools.getBean(result, HasNewMessage.class);
                int status = hasNewMessage.getStatus();
                String message = hasNewMessage.getMessage();
                if (status == 0 || "success".equals(message)) {
                    HasNewMessage.ResultBean result1 = hasNewMessage.getResult();
                    boolean has_new = result1.isHas_new();
                    if (has_new) {
                        ivHasNewMessage.setVisibility(View.VISIBLE);
                    } else {
                        ivHasNewMessage.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.ShowToast(getContext(), "新消息获取失败");
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        void onFragmentInteraction(Uri uri);
    }
}
