package com.hb.rimi.angel.fragment;

import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.activity.MainActivity;
import com.hb.rimi.angel.adapter.HomeViewPagerAdapter;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.bean.HomeMenu;
import com.hb.rimi.angel.bean.HomeTopPic;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.util.DisplayUtil;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.T;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首页
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @Bind(R.id.fh_ll_appointment)
    LinearLayout fh_ll_appointment;
    @Bind(R.id.fh_ll_b_examination)
    LinearLayout fh_ll_b_examination;
    @Bind(R.id.fh_check_payment)
    LinearLayout fh_check_payment;
    @Bind(R.id.fh_ll_medical_voucher)
    LinearLayout fh_ll_medical_voucher;
    @Bind(R.id.fh_ll_medical_reminder)
    LinearLayout fh_ll_medical_reminder;
    @Bind(R.id.fh_ll_electronic_report)
    LinearLayout fh_ll_electronic_report;
  /*  @Bind(R.id.home_viewPager)
    ViewPager home_viewPager;*/
    @Bind(R.id.iv_home_pic)
    ImageView ivHomePic;
    @Bind(R.id.item_home_menu3)
    ImageView itemHomeMenu3;
    @Bind(R.id.item_tv_home_menu3)
    TextView itemTvHomeMenu3;
    @Bind(R.id.item_home_menu4)
    ImageView itemHomeMenu4;
    @Bind(R.id.item_tv_home_menu4)
    TextView itemTvHomeMenu4;
    @Bind(R.id.item_home_menu5)
    ImageView itemHomeMenu5;
    @Bind(R.id.item_tv_home_menu5)
    TextView itemTvHomeMenu5;
    @Bind(R.id.item_home_menu6)
    ImageView itemHomeMenu6;
    @Bind(R.id.item_tv_home_menu6)
    TextView itemTvHomeMenu6;
//    @Bind(R.id.ha_toolbar)
//    Toolbar ha_toolbar;


    private String mParam1;
    private String mParam2;

    private List<String> pageImageUrls;
    private HomeViewPagerAdapter pageradapter;
    private String[] tvMenus = {"预约挂号", "B超预约", "检查缴费", "就诊凭证", "就诊提醒", "电子报告"};
    private String[] ivUrls = {"" + R.mipmap.menu_menu_appointment, "" + R.mipmap.menu_b_examination, "" + R.mipmap.menu_check_payment, "" + R.mipmap.menu_medical_voucher, "" + R.mipmap.menu_medical_reminder, "" + R.mipmap.menu_electronic_report};
    private OnFragmentInteractionListener mListener;
    private Context mContent;
   /* private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            if (what == 1) {
                final int curItem = msg.arg1;//0,1,2,3
                final int nextItem = (curItem + 1) % pageImageUrls.size();
                *//**//*

                //设置viewager现在要展示的页面位置
                //home_viewPager.setCurrentItem(nextItem);
                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                Message message = Message.obtain();
                                message.what = 8;
                                message.arg1 = nextItem;
                                handler.sendMessage(message);
                            }
                        }
                ).start();
            }
        }
    };
*/
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        // 此处载入布局被坑了
        View view = inflater.inflate(R.layout.fragment_home,container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        mContent = getContext();
        //设置标题栏
//        ha_toolbar.setTitle("");
//        ((MainActivity) getActivity()).setSupportActionBar(ha_toolbar);
        //填充数据
        List<HomeMenu> menus = new ArrayList<HomeMenu>();
        HomeMenu menu = null;
        for (int i = 0; i < 6; i++) {
            menu = new HomeMenu();
            menu.setImgUrl(ivUrls[i]);
            menu.setTitle(tvMenus[i]);
            menus.add(menu);
        }


        //顶部幻灯片
        //initPager();
        //pageradapter = new HomeViewPagerAdapter(mContent, pageImageUrls);
        //home_viewPager.setAdapter(pageradapter);

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

    @Override
    public void onResume() {
        super.onResume();
        /*Message message = handler.obtainMessage();
        message.what = 1;//做标记
       // int currentItem = home_viewPager.getCurrentItem();//获取到viewager现在展示的页面的位置信息
        //message.arg1 = currentItem;
        handler.sendMessage(message);*/
    }

    @OnClick({R.id.fh_ll_appointment, R.id.fh_ll_b_examination, R.id.fh_check_payment, R.id.fh_ll_medical_voucher, R.id.fh_ll_medical_reminder, R.id.fh_ll_electronic_report})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fh_ll_appointment:
                ProjectApplication.intentManager.toAppointmentActivity(null);
                break;
            case R.id.fh_ll_b_examination:
                ProjectApplication.intentManager.toBOptionalCheckActivity(null);
                break;
            case R.id.fh_check_payment:
                ProjectApplication.intentManager.toVisitListActivity(null);
                break;
            case R.id.fh_ll_medical_voucher:
                ProjectApplication.intentManager.toMedicalVoucherActivity(null);
                break;
            case R.id.fh_ll_medical_reminder:
                ProjectApplication.intentManager.toMedicalReminderActivity(null);
                break;
            case R.id.fh_ll_electronic_report:
                ProjectApplication.intentManager.toElectorivRecoptActivity(null);
                break;

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = space;
            }
        }
    }
}
