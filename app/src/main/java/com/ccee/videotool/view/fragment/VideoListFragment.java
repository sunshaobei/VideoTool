package com.ccee.videotool.view.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ccee.videotool.R;
import com.ccee.videotool.adapter.VideoAdapter;
import com.sunsh.baselibrary.base.fragment.LazyLoadFragment;
import com.sunsh.baselibrary.widgets.overscroll.OverScrollLayout;
import com.sunsh.baselibrary.widgets.overscroll.OverScrollUtils;

import java.util.ArrayList;
import java.util.List;

public class VideoListFragment extends LazyLoadFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video_list
                ;
    }

    @Override
    protected void initUi() {
        RecyclerView recyclerView = rootView.findViewById(R.id.rv);
        OverScrollLayout overScrollLayout = rootView.findViewById(R.id.overScrollLayout);
        OverScrollUtils.defaultConfig2(overScrollLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        recyclerView.setAdapter(new VideoAdapter(getContext(),list));
        overScrollLayout.setOnRefreshListener(new OverScrollLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                overScrollLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        overScrollLayout.refreshComplete();
                    }
                },1000);
            }

            @Override
            public void onLoading() {
                overScrollLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        overScrollLayout.loadMoreComplete();
                    }
                },1000);
            }
        });
    }

    @Override
    protected void loadData() {

    }
}
