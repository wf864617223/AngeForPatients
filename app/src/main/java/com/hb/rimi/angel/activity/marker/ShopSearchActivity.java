package com.hb.rimi.angel.activity.marker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.adapter.BusListAdapter;
import com.hb.rimi.angel.bean.BusListBean;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.ShareInfoUtil;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.view.DividerItemDecoration;
import com.hb.rimi.angel.view.PrgDialog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShopSearchActivity extends AppCompatActivity {

    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.shop_search_toolbar)
    Toolbar shopSearchToolbar;

    Context mContext;

    @Bind(R.id.search_recyclerView)
    RecyclerView searchRecyclerView;
    @Bind(R.id.search_swipe_refresh)
    SwipeRefreshLayout searchSwipeRefresh;
    String eCodeContent = "";
    private PrgDialog prgDialog;
    private String token;
    private List<BusListBean.ResultBean.ListBean> result2List = new ArrayList<>();
    private BusListAdapter busListAdapter;
    private String searchContent = "";
    private GridLayoutManager manager;
    private int pageSize = 10;
    private int total;
    private int pageNum = 1;
    private boolean isLoad = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_search);
        ButterKnife.bind(this);
        mContext = ShopSearchActivity.this;
        token = ShareInfoUtil.readToken(mContext);
        initView();
    }

    private void initView() {
        shopSearchToolbar.setTitle("");
        setSupportActionBar(shopSearchToolbar);
        shopSearchToolbar.setNavigationIcon(R.mipmap.icon_reg_back);
        shopSearchToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        shopSearchToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        manager = new GridLayoutManager(mContext, 2);
        searchRecyclerView.setLayoutManager(manager);
        //添加默认分割线
        searchRecyclerView.addItemDecoration(new DividerItemDecoration(mContext));

        searchSwipeRefresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        searchSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                searchContent = etSearch.getText().toString();
                try {
                    eCodeContent = URLEncoder.encode(searchContent, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (result2List != null && result2List.size() > 0) {
                    result2List.clear();
                }
                if (!isLoad) {
                    initBusData("", pageNum, eCodeContent);
                    isLoad = true;
                }
            }
        });


        searchRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            int lastItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItem + 2 >= busListAdapter.getItemCount()) {
                    // TODO 加载更多
                    if (result2List.size() < total) {
                        searchSwipeRefresh.setRefreshing(true);
                        searchContent = etSearch.getText().toString();
                        try {
                            eCodeContent = URLEncoder.encode(searchContent, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        pageNum++;
                        if (!isLoad) {
                            initBusData("", pageNum, eCodeContent);
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
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView,
                                          int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    searchContent = etSearch.getText().toString().trim();
                    if (TextUtils.isEmpty(searchContent)) {
                        T.ShowToast(mContext, "未输入任何内容");

                    } else {
                        try {
                            eCodeContent = URLEncoder.encode(searchContent, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        if (result2List != null && result2List.size() > 0) {
                            result2List.clear();
                        }
                        initBusData("", pageNum, eCodeContent);
                    }
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(
                                    getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_shop, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        searchContent = etSearch.getText().toString().trim();

        if (TextUtils.isEmpty(searchContent)) {
            T.ShowToast(mContext, "未输入任何内容");

        } else {

            try {
                eCodeContent = URLEncoder.encode(searchContent, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (result2List != null && result2List.size() > 0) {
                result2List.clear();
            }
            initBusData("", pageNum, eCodeContent);
        }
        // 先隐藏键盘
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(
                        getCurrentFocus()
                                .getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        return super.onOptionsItemSelected(item);
    }

    private void initBusData(String category_id, final int pageNum, String searchContents) {
        prgDialog = new PrgDialog(mContext);

        HttpUtil.doHttp(HttpContanst.BUS_LIST + token + "&category_id=" + category_id + "&pageSize=" + pageSize + "&pageNumber=" + pageNum + "&searchContent=" + searchContents, null, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                BusListBean busListBean = GsonTools.getBean(result, BusListBean.class);
                int status = busListBean.getStatus();
                String message = busListBean.getMessage();
                if (status == 0) {
                    isLoad = false;
                    searchSwipeRefresh.setRefreshing(false);
                    BusListBean.ResultBean result2 = busListBean.getResult();
                    total = result2.getTotalCount();
                    if (total <= 0) {
                        T.ShowToast(mContext, "该商品不存在");
                        prgDialog.closeDialog();
                        return;
                    }
                    result2List.addAll(result2.getList());
                    if (busListAdapter == null) {
                        busListAdapter = new BusListAdapter(mContext, result2List,null);
                        searchRecyclerView.setAdapter(busListAdapter);
                        busListAdapter.notifyDataSetChanged();
                    } else {
                        busListAdapter.notifyDataSetChanged();
                    }
                } else {
                    T.ShowToast(mContext, message);
                }
                prgDialog.closeDialog();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                prgDialog.closeDialog();
                T.ShowToast(mContext, ex.getMessage());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
