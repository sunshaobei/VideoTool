package com.ccee.videotool.model.typemodel;

import com.ccee.videotool.R;
import com.ccee.videotool.view.fragment.VideoListFragment;

public enum FragmentTypeVideo {
    ALL(R.string.video_all, VideoListFragment.class),
    DRAFT(R.string.video_draft, VideoListFragment.class),
    CHECK_PENDING(R.string.video_check_pending, VideoListFragment.class),
    ALREADY_ON_THE_SHELF(R.string.video_already_on_the_shelf, VideoListFragment.class),
    ALREADY_DOWN(R.string.video_already_down, VideoListFragment.class);
    private int title;
    private Class<VideoListFragment> fragmentClass;

    FragmentTypeVideo(int title, Class<VideoListFragment> fragmentClass) {
        this.title = title;
        this.fragmentClass = fragmentClass;
    }


    public int getTitle() {
        return title;
    }

    public Class<VideoListFragment> getFragmentClass() {
        return fragmentClass;
    }
}
