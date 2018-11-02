package com.ccee.videotool.model.typemodel;

import com.ccee.videotool.R;
import com.ccee.videotool.view.fragment.AllVideoListFragment;
import com.ccee.videotool.view.fragment.CheckPendingVideoListFragment;
import com.ccee.videotool.view.fragment.DraftVideoListFragment;
import com.ccee.videotool.view.fragment.FailedVideoListFragment;
import com.ccee.videotool.view.fragment.PublishedVideoListFragment;
import com.ccee.videotool.view.fragment.VideoListFragment;

import java.text.DecimalFormat;

public enum FragmentTypeVideo {
    ALL(R.string.video_all, AllVideoListFragment.class),
    DRAFT(R.string.video_draft, DraftVideoListFragment.class),
    CHECK_PENDING(R.string.video_check_pending, CheckPendingVideoListFragment.class),
    PUBLISHED(R.string.video_published, PublishedVideoListFragment.class),
    FAILED(R.string.video_failed, FailedVideoListFragment.class);
    private int title;
    private Class<? extends VideoListFragment> fragmentClass;

    FragmentTypeVideo(int title, Class<? extends VideoListFragment> fragmentClass) {
        this.title = title;
        this.fragmentClass = fragmentClass;
    }


    public int getTitle() {
        return title;
    }

    public Class<? extends VideoListFragment> getFragmentClass() {
        return fragmentClass;
    }
}
