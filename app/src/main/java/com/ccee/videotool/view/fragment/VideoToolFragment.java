package com.ccee.videotool.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ccee.videotool.model.entities.request.VideoListRequest;
import com.ccee.videotool.model.entities.response.VideoListBean;
import com.ccee.videotool.model.http.HttpManager;
import com.sunsh.baselibrary.base.fragment.BaseFragment;
import com.sunsh.baselibrary.http.ok3.entity.HttpCallBack;
import com.sunsh.baselibrary.http.ok3.entity.HttpResponse;

public abstract class VideoToolFragment extends BaseFragment {


    protected View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);
            initBundle();
            initView(rootView);
            initData();
        }
        return rootView;
    }

    protected abstract int getLayoutId();

    protected void initBundle() {

    }

    protected abstract void initView(View rootView);

    protected abstract void initData();


}
