package com.hb.rimi.angel.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.activity.marker.ShopCarActivity;
import com.hb.rimi.angel.activity.marker.ShopSearchActivity;
import com.hb.rimi.angel.adapter.BusListAdapter;
import com.hb.rimi.angel.adapter.PopListAdapter;
import com.hb.rimi.angel.bean.BusListBean;
import com.hb.rimi.angel.bean.BusStyleBean;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.ShareInfoUtil;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.view.DividerItemDecoration;
import com.hb.rimi.angel.view.PrgDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 商城
 */
public class ShopFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public PopupWindow popupWindow;
    @Bind(R.id.ma_toolbar)
    Toolbar maToolbar;
    @Bind(R.id.iv_car_bus)
    ImageView ivCarBus;
    @Bind(R.id.iv_bus_style)
    ImageView ivBusStyle;
    @Bind(R.id.et_search)
    EditText btnSearch;
    @Bind(R.id.shop_recyclerView)
    RecyclerView shopRecyclerView;
    @Bind(R.id.shop_swipe_refresh)
    SwipeRefreshLayout shopSwipeRefresh;
    GridLayoutManager manager;
//    private PrgDialog prgDialog;
    private boolean isDown = false;
    private int pageNum = 1;
    private int pageSize = 10;
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private ListView popList;
    //商品种类
    private List<BusStyleBean.ResultBean> result1 = new ArrayList<>();
    private PopListAdapter adapter;
    private String token;
    //商品列表
    private List<BusListBean.ResultBean.ListBean> result2List = new ArrayList<>();
    private BusListAdapter busListAdapter;
    private String searchContent = "";
    private Context mContext;
    private String catagory = "";
    private int total;
    private boolean isLoad = false;
    private String is_hot="";
    private String is_new="";


    public ShopFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShopFragment.
     */
    public static ShopFragment newInstance(String param1, String param2) {
        ShopFragment fragment = new ShopFragment();
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
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        ButterKnife.bind(this, view);
        mContext = getContext();
        token = ShareInfoUtil.readToken(getContext());
        initView(view);
        loadGoods("", pageNum);
        //initPopupWindowView();
        return view;
    }


    private void initView(View view) {

        manager = new GridLayoutManager(mContext, 2);
        shopRecyclerView.setLayoutManager(manager);
        //添加默认分割线
        shopRecyclerView.addItemDecoration(new DividerItemDecoration(mContext));

        shopSwipeRefresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        shopSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                if (result2List != null) {
                    result2List.clear();
                }
                if (!isLoad) {
                    loadGoods(catagory, pageNum);
                    isLoad = true;
                }

            }
        });


        shopRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItem + 2 >= busListAdapter.getItemCount()) {
                    // TODO 加载更多
                    if (result2List.size() < total) {
                        shopSwipeRefresh.setRefreshing(true);
                        pageNum++;
                        if (!isLoad) {
                            loadGoods(catagory, pageNum);
                            isLoad = true;
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastItem = manager.findLastVisibleItemPosition();
            }
        });


        //点击购物车图标事件
        ivCarBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                Intent intent = new Intent(getContext(), ShopCarActivity.class);
                intent.putExtra(ShopCarActivity.fromTypeKey, ShopCarActivity.fromTypeCarValue);
                startActivity(intent);
            }
        });
        //点击搜索按钮事件
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                Intent intent = new Intent(getContext(), ShopSearchActivity.class);
                startActivity(intent);
            }
        });
        //点击商品类型列表事件
        ivBusStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    initPopupWindowView();
                    popupWindow.showAsDropDown(maToolbar, 0, 0);
                }

            }
        });

    }

    private void loadGoods(String category_id, final int pageNum) {
//        prgDialog = new PrgDialog(getContext());

        HttpUtil.doHttp(HttpContanst.BUS_LIST + token +"&is_hot="+is_hot+"&is_new="+is_new+ "&category_id=" + category_id + "&pageSize=" + pageSize + "&pageNumber=" + pageNum + "&searchContent=" + searchContent, null, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                BusListBean busListBean = GsonTools.getBean(result, BusListBean.class);
                int status = busListBean.getStatus();
                String message = busListBean.getMessage();
                if (status == 0) {
                    isLoad = false;
                    shopSwipeRefresh.setRefreshing(false);
                    BusListBean.ResultBean result2 = busListBean.getResult();
//                    result2List = result2.getList();
                    result2List.addAll(result2.getList());

                    total = result2.getTotalCount();
                    busListAdapter = new BusListAdapter(mContext, result2List, getActivity());
                    shopRecyclerView.setAdapter(busListAdapter);
//                    prgDialog.closeDialog();
                } else {
//                    prgDialog.closeDialog();
                    T.ShowToast(getContext(), message);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
//                prgDialog.closeDialog();
                T.ShowToast(getContext(), ex.getMessage());
            }
        });
    }

    private void initPopupWindowView() {
        if (popupWindow == null) {
            View popView = getLayoutInflater(null).inflate(R.layout.poplist, null, false);
            popList = (ListView) popView.findViewById(R.id.pop_list);
            popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            popList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    popupWindow.dismiss();
                    if (result1.get(position).getId() == -1) {
                        catagory = "";
                        is_hot = "";
                        is_new = "";
                    } else if (result1.get(position).getId() == -2) {
                        is_hot = "true";
                        is_new = "";
                        catagory = "";
                    } else if (result1.get(position).getId() == -3) {
                        is_hot = "";
                        is_new = "true";
                        catagory = "";
                    } else {
                        is_hot = "";
                        is_new = "";
                        catagory = "" + result1.get(position).getId();
                    }
                    pageNum = 1;
                    if (result2List != null) {
                        result2List.clear();
                    }
                    loadGoods(catagory, pageNum);
                }
            });
        }
        loadCategory();
    }

    private void loadCategory() {
        HttpUtil.doHttp(HttpContanst.BUS_STYLE + token, null, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                BusStyleBean busStyleBean = GsonTools.getBean(result, BusStyleBean.class);
                int status = busStyleBean.getStatus();
                String message = busStyleBean.getMessage();
                if (status == 0) {
                    if (result1 != null && result1.size() > 0) {
                        result1.clear();
                    }
                    BusStyleBean.ResultBean allBean = new BusStyleBean.ResultBean();
                    allBean.setName("全部宝贝");
                    allBean.setId(-1);
                    allBean.setSortIndex("-1");
                    BusStyleBean.ResultBean hotBean = new BusStyleBean.ResultBean();
                    hotBean.setName("热销推荐");
                    hotBean.setId(-2);
                    hotBean.setSortIndex("-2");
                    BusStyleBean.ResultBean newBean = new BusStyleBean.ResultBean();
                    newBean.setName("新品推荐");
                    newBean.setId(-3);
                    newBean.setSortIndex("-3");

                    result1.add(allBean);
                    result1.add(hotBean);
                    result1.add(newBean);

                    result1.addAll(busStyleBean.getResult());

                    adapter = new PopListAdapter(result1, getContext());
                    popList.setAdapter(adapter);

                } else {
                    T.ShowToast(getContext(), message);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.ShowToast(getContext(), ex.getMessage());
            }
        });
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
}
