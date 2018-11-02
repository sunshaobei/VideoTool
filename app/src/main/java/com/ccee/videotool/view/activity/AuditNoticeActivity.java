package com.ccee.videotool.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ccee.videotool.R;
import com.ccee.videotool.adapter.AuditNoticeAdapter;
import com.ccee.videotool.arouter.RoutePath;
import com.sunsh.baselibrary.adapter.recyclerview.base.OnItemClickListener;
import com.sunsh.baselibrary.adapter.recyclerview.base.ViewHolder;
import com.sunsh.baselibrary.base.activity.BaseBarActivity;
import com.sunsh.baselibrary.utils.ToastUtils;
import com.sunsh.baselibrary.widgets.overscroll.OverScrollLayout;
import com.sunsh.baselibrary.widgets.overscroll.OverScrollUtils;

import java.util.ArrayList;
import java.util.List;

@Route(path = RoutePath.AUDIT_NOTICE)
public class AuditNoticeActivity extends BaseBarActivity {

    private OverScrollLayout overScrollLayout;
    private RecyclerView rv;
    private List datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_notice);
        initView();
        initListener();
    }

    private void initView() {
        setTitle(getResources().getString(R.string.audit_notice));
        overScrollLayout = (OverScrollLayout) findViewById(R.id.overScrollLayout);
        OverScrollUtils.defaultConfig2(overScrollLayout);
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        AuditNoticeAdapter auditNoticeAdapter = new AuditNoticeAdapter(this, datas);
        rv.setAdapter(auditNoticeAdapter);
    }

    private void initListener() {
        overScrollLayout.setOnRefreshListener(new OverScrollLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                overScrollLayout.refreshComplete();
            }

            @Override
            public void onLoading() {
                overScrollLayout.loadMoreComplete();
            }
        });
    }
}
