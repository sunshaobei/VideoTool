package com.ccee.videotool.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ccee.videotool.R;
import com.ccee.videotool.adapter.ChooseArticlesAdapter;
import com.ccee.videotool.arouter.RoutePath;
import com.ccee.videotool.model.entities.request.ArticleListRequest;
import com.ccee.videotool.model.entities.response.ArticleBean;
import com.ccee.videotool.model.entities.response.ArticleListBean;
import com.ccee.videotool.model.http.HttpManager;
import com.sunsh.baselibrary.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.sunsh.baselibrary.base.activity.BaseBarActivity;
import com.sunsh.baselibrary.http.ok3.entity.HttpCallBack;
import com.sunsh.baselibrary.http.ok3.entity.HttpResponse;
import com.sunsh.baselibrary.utils.ToastUtils;
import com.sunsh.baselibrary.widgets.overscroll.OverScrollLayout;
import com.sunsh.baselibrary.widgets.overscroll.OverScrollUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

@Route(path = RoutePath.ARTICLES_CHOOSE)
public class ChooseArticlesActivity extends BaseBarActivity implements OverScrollLayout.OnRefreshListener {


    private String searchTitle = "";

    private RecyclerView mRecyclerView;
    List<ArticleBean> datas = new ArrayList<>();
    List<Integer> selectId = new ArrayList<>();
    private OverScrollLayout mOverScrollLayout;
    private HeaderAndFooterWrapper headerAndFooterWrapper;
    private ChooseArticlesAdapter baseAdapter;
    private ArticleListRequest request;
    private int page = 1;
    private EditText edit_search;
    private View search;
    private String produtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_articles);
        setTitle(getResources().getString(R.string.choose_articles));
        initView();
        initListener();
        initData();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mOverScrollLayout = (OverScrollLayout) findViewById(R.id.overScrollLayout);
        OverScrollUtils.defaultConfig2(mOverScrollLayout);
        mOverScrollLayout.setOnRefreshListener(this);
        baseAdapter = new ChooseArticlesAdapter(this, datas, selectId);
        headerAndFooterWrapper = new HeaderAndFooterWrapper(baseAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        View head = LayoutInflater.from(this).inflate(R.layout.item_search_head, null);
        edit_search = head.findViewById(R.id.edit);
        search = head.findViewById(R.id.tv_search);
        headerAndFooterWrapper.addHeaderView(head);
        mRecyclerView.setAdapter(headerAndFooterWrapper);
    }

    private void initListener() {
        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchTitle = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        baseAdapter.setSelectedListener((id, title) -> {
            selectId.clear();
            selectId.add(id);
            produtTitle = title;
            headerAndFooterWrapper.notifyDataSetChanged();
        });
        search.setOnClickListener(v -> {
            closeKeyboard();
            baseAdapter.setSearchStr(searchTitle);
            onRefresh();
        });
    }


    private void initData() {
        request = new ArticleListRequest();
        onRefresh();
    }


    public void confirm(View view) {
        Intent intent = getIntent();
        if (!selectId.isEmpty()) {
            intent.putExtra("productId", selectId.get(0));
            intent.putExtra("productTitle", produtTitle);
            setResult(110, intent);
        }
        ActivityCompat.finishAfterTransition(this);
    }

    @Override
    public void onRefresh() {
        page = 1;
        request.setPageIndex(1);
        request.setTitle(searchTitle);
        HttpManager.get(request, new HttpCallBack<HttpResponse<ArticleListBean>>() {
            @Override
            public void onResponse(HttpResponse<ArticleListBean> response, int id) {
                if (response.isResult()) {
                    datas.clear();
                    datas.addAll(response.getData().getData());
                    page = 2;
                } else {
                    ToastUtils.showShortToast(response.getMessage());
                }
                mOverScrollLayout.refreshComplete();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                ToastUtils.showShortToast(e.getMessage());
                mOverScrollLayout.refreshComplete();
            }
        });
    }

    @Override
    public void onLoading() {
        request.setPageIndex(page);
        request.setTitle(searchTitle);
        HttpManager.get(request, new HttpCallBack<HttpResponse<ArticleListBean>>() {
            @Override
            public void onResponse(HttpResponse<ArticleListBean> response, int id) {
                if (response.isResult()) {
                    datas.addAll(response.getData().getData());
                    if (!response.getData().getData().isEmpty())
                        page += 1;
                } else {
                    ToastUtils.showShortToast(response.getMessage());
                }
                mOverScrollLayout.loadMoreComplete(response.getData().getData().isEmpty());
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                ToastUtils.showShortToast(e.getMessage());
                mOverScrollLayout.loadMoreComplete();
            }
        });
    }
}
