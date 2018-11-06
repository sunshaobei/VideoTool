package com.ccee.videotool.view.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ccee.videotool.R;
import com.ccee.videotool.arouter.Arouter;
import com.ccee.videotool.arouter.RoutePath;
import com.ccee.videotool.event.AllvideoListener;
import com.ccee.videotool.event.Swipe2RefreshListener;
import com.ccee.videotool.event.VideoDeleteListener;
import com.ccee.videotool.event.VideoSaveDraftListener;
import com.ccee.videotool.event.VideoUpLoadSuccessListener;
import com.ccee.videotool.event.VideoUpdateListener;
import com.ccee.videotool.model.typemodel.FragmentTypeVideo;
import com.flyco.tablayout.SlidingTabLayout;
import com.sunsh.baselibrary.base.adapter.BasePageAdapter;
import com.sunsh.baselibrary.rxbus.RxBus;
import com.sunsh.baselibrary.rxbus.Subscribe;
import com.sunsh.baselibrary.utils.SizeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoLibFragment extends VideoToolFragment implements View.OnClickListener
        , AllvideoListener
        , VideoDeleteListener
        , VideoUpdateListener
        , VideoSaveDraftListener
        , VideoUpLoadSuccessListener
        , Swipe2RefreshListener {


    private SlidingTabLayout tabLayout;
    private TextView tv_msg_dot;
    private TextView tv_mine_video;
    private ViewPager viewPager;
    private View relative_msg;
    List<Fragment> fragments = new ArrayList<>();

    public VideoLibFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video_lib;
    }

    @Override
    protected void initView(View rootView) {
        tabLayout = rootView.findViewById(R.id.tabLayout);
        tv_msg_dot = rootView.findViewById(R.id.tv_msg_dot);
        tv_mine_video = rootView.findViewById(R.id.tv_mine_video);
        relative_msg = rootView.findViewById(R.id.relative_msg);
        relative_msg.setOnClickListener(this);
        viewPager = rootView.findViewById(R.id.vp);
        tv_msg_dot.setVisibility(View.GONE);
        setUpVp();
        RxBus.getDefault().register(this);
    }

    private void setUpVp() {
        List<String> titles = new ArrayList<>();
        for (FragmentTypeVideo fragmentTypeVideo : FragmentTypeVideo.values()) {
            try {
                fragments.add(fragmentTypeVideo.getFragmentClass().newInstance());
                titles.add(getResources().getString(fragmentTypeVideo.getTitle()));
            } catch (Exception e) {

            }
        }
        viewPager.setAdapter(new BasePageAdapter(fragments, titles, getChildFragmentManager()));
        tabLayout.setViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TextView titleView = tabLayout.getTitleView(i);
            titleView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            titleView.setMarqueeRepeatLimit(-1);
            titleView.setSelected(true);
            titleView.setPadding(SizeUtils.dp2px(getContext(), 10), 0, SizeUtils.dp2px(getContext(), 10), 0);
        }
        tabLayout.getTitleView(0).getPaint().setFakeBoldText(true);
    }

    @Override
    protected void initData() {
    }

    public void refresh() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_msg:
                Arouter.navigation(RoutePath.AUDIT_NOTICE);
                break;
        }
    }

    @Subscribe
    @Override
    public void allVideo(AllVideo allVideo) {
        if (allVideo.getType() == null && viewPager.getCurrentItem() == 0) {
        } else if (allVideo.getType() != null) {
            if (allVideo.getType() == -1 && viewPager.getCurrentItem() == 1) {
            } else if (allVideo.getType() == 1 && viewPager.getCurrentItem() == 2) {
            } else if (allVideo.getType() == 2 && viewPager.getCurrentItem() == 3) {
            } else if (allVideo.getType() == 3 && viewPager.getCurrentItem() == 4) {
            } else {
                return;
            }
        } else return;
        tv_mine_video.setText(String.format("我的视频 %s", allVideo.getCount()));
    }

    @Subscribe
    @Override
    public void onDelete(DeleteVideo o) {
        for (Fragment fragment : fragments) {
            if (fragment instanceof VideoListFragment) {
                ((VideoListFragment) fragment).onRefresh();
            }
        }
    }

    @Subscribe
    @Override
    public void onUpdate(UpdateVideo o) {
        for (Fragment fragment : fragments) {
            if (fragment instanceof VideoListFragment) {
                ((VideoListFragment) fragment).onRefresh();
            }
        }
        viewPager.setCurrentItem(o.getType());
    }

    @Subscribe
    @Override
    public void onSave(Draft o) {
        viewPager.setCurrentItem(1);
        Fragment fragment = fragments.get(1);
        if (fragment instanceof VideoListFragment) {
            ((VideoListFragment) fragment).onRefresh();
        }
    }

    @Override
    public void onUpLoadSuccess(UpLoadSuccess o) {
        viewPager.setCurrentItem(0);
        for (Fragment fragment : fragments) {
            if (fragment instanceof VideoListFragment) {
                ((VideoListFragment) fragment).onRefresh();
            }
        }
    }

    @Subscribe
    @Override
    public void swipe(Swipe s) {
        VideoListFragment fragment = (VideoListFragment) fragments.get(viewPager.getCurrentItem());
        fragment.swipe();
    }
}
