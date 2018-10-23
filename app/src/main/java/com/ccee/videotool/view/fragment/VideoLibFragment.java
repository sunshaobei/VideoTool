package com.ccee.videotool.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ccee.videotool.R;
import com.ccee.videotool.arouter.Arouter;
import com.ccee.videotool.arouter.RoutePath;
import com.ccee.videotool.model.typemodel.FragmentTypeVideo;
import com.flyco.tablayout.SlidingTabLayout;
import com.sunsh.baselibrary.base.adapter.BasePageAdapter;
import com.sunsh.baselibrary.base.fragment.BaseFragment;
import com.sunsh.baselibrary.utils.SizeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoLibFragment extends VideoToolFragment implements View.OnClickListener {


    private SlidingTabLayout tabLayout;
    private TextView tv_msg_dot;
    private TextView tv_mine_video;
    private ViewPager viewPager;
    private View relative_msg;

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
        setUpVp();
    }

    private void setUpVp() {
        List<Fragment> fragments = new ArrayList<>();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_msg:
                Arouter.navigationWhitOption(RoutePath.AUDIT_NOTICE);
                break;
        }
    }
}
