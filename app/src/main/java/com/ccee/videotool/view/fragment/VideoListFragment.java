package com.ccee.videotool.view.fragment;

import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ccee.videotool.R;
import com.ccee.videotool.adapter.VideoAdapter;
import com.ccee.videotool.arouter.Arouter;
import com.ccee.videotool.arouter.RoutePath;
import com.ccee.videotool.event.AllvideoListener;
import com.ccee.videotool.greendao.GreenDaoManager;
import com.ccee.videotool.model.db.DBVideo;
import com.ccee.videotool.model.entities.request.VideoListRequest;
import com.ccee.videotool.model.entities.response.VideoListBean;
import com.ccee.videotool.model.http.HttpManager;
import com.ccee.videotool.utils.ModelCast;
import com.sunsh.baselibrary.adapter.recyclerview.base.OnItemClickListener;
import com.sunsh.baselibrary.adapter.recyclerview.base.ViewHolder;
import com.sunsh.baselibrary.base.fragment.LazyLoadFragment;
import com.sunsh.baselibrary.http.ok3.entity.HttpCallBack;
import com.sunsh.baselibrary.http.ok3.entity.HttpResponse;
import com.sunsh.baselibrary.rxbus.RxBus;
import com.sunsh.baselibrary.utils.ToastUtils;
import com.sunsh.baselibrary.widgets.overscroll.OverScrollLayout;
import com.sunsh.baselibrary.widgets.overscroll.OverScrollUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public abstract class VideoListFragment extends LazyLoadFragment implements OverScrollLayout.OnRefreshListener, OnItemClickListener {

    public static final int CHECK_PENDING = 1;
    public static final int PUBLISHED = 2;
    public static final int FAILED = 3;

    protected List datas = new ArrayList<>();
    protected VideoAdapter videoAdapter;
    protected OverScrollLayout overScrollLayout;
    protected VideoListRequest request;
    protected int page = 1;
    protected int count;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video_list;
    }

    @Override
    protected void initUi() {
        RecyclerView recyclerView = rootView.findViewById(R.id.rv);
        overScrollLayout = rootView.findViewById(R.id.overScrollLayout);
        OverScrollUtils.defaultConfig2(overScrollLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        videoAdapter = new VideoAdapter(getContext(), datas);
        String emptytext = null;
        if (getVideoType() == null) {
            emptytext = "暂无视频";
        } else if (getVideoType() == -1) {
            emptytext = "暂无草稿";
        } else if (getVideoType() == CHECK_PENDING) {
            emptytext = "暂无待审核视频";
        } else if (getVideoType() == PUBLISHED) {
            emptytext = "暂无已发布视频";
        } else if (getVideoType() == FAILED) {
            emptytext = "暂无未通过视频";
        }
        videoAdapter.setEmptyView(emptytext, R.mipmap.icon_empty);
        recyclerView.setAdapter(videoAdapter);
        overScrollLayout.setOnRefreshListener(this);
        videoAdapter.setOnItemClickListener(this);
        overScrollLayout.autoRefresh();
    }

    @Override
    public void onItemClick(Object o, ViewHolder holder, int position) {
        if (o instanceof DBVideo) {
            ARouter.getInstance()
                    .build(RoutePath.VIDEO_UPLOAD)
                    .withOptionsCompat(ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), Pair.create(holder.getView(R.id.icon), ((DBVideo) o).getId() + "")))
                    .withSerializable("dbVideo", (DBVideo) o)
                    .navigation(getContext());

        } else {
            ARouter.getInstance()
                    .build(RoutePath.VIDEO_DETAIL)
                    .withOptionsCompat(ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), Pair.create(holder.getView(R.id.icon), ((VideoListBean.VideoBean) o).getAuditId() + "")))
                    .withInt("auditId", ((VideoListBean.VideoBean) o).getAuditId())
                    .navigation(getContext());
        }
    }

    @Override
    protected void loadData() {
        if (request == null) {
            request = new VideoListRequest();
            request.setAuditStatus(getVideoType());
        }
    }

    @Override
    public void onRefresh() {
        if (isFirstLoad) return;
        request.setPageIndex(1);
        HttpManager.get(request, new HttpCallBack<HttpResponse<VideoListBean>>() {
            @Override
            public void onResponse(HttpResponse<VideoListBean> response, int id) {
                if (response.isResult()) {
                    page = 2;
                    datas.clear();
                    datas.addAll(response.getData().getData());
                    count = response.getData().getCount();
                    RxBus.getDefault().post(new AllvideoListener.AllVideo(response.getData().getCount(), getVideoType()));
                } else {
                    ToastUtils.showShortToast(response.getMessage());
                }
                overScrollLayout.refreshComplete();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                ToastUtils.showShortToast(e.getMessage());
                overScrollLayout.refreshComplete();
            }
        });
    }

    @Override
    public void onLoading() {
        request.setPageIndex(page);
        HttpManager.get(request, new HttpCallBack<HttpResponse<VideoListBean>>() {
            @Override
            public void onResponse(HttpResponse<VideoListBean> response, int id) {
                if (response.isResult()) {
                    datas.addAll(response.getData().getData());
                    if (response.getData().getData().size() > 0) {
                        page += 1;
                    }
                    overScrollLayout.loadMoreComplete(response.getData().getData().size() == 0);
                } else {
                    overScrollLayout.loadMoreComplete();
                    ToastUtils.showShortToast(response.getMessage());
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                ToastUtils.showShortToast(e.getMessage());
                overScrollLayout.loadMoreComplete();
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            RxBus.getDefault().post(new AllvideoListener.AllVideo(getCount(), getVideoType()));
        }
    }

    protected int getCount() {
        return count;
    }

    protected abstract Integer getVideoType();

    public void swipe() {
        overScrollLayout.startRefresh();
    }
}
