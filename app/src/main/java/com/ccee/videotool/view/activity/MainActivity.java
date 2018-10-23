package com.ccee.videotool.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.aliyun.demo.snap.SnapRecorderSetting;
import com.ccee.videotool.R;
import com.ccee.videotool.adapter.MainPagerAdapter;
import com.ccee.videotool.arouter.RoutePath;
import com.ccee.videotool.view.fragment.MineFragment;
import com.ccee.videotool.view.fragment.VideoLibFragment;
import com.sunsh.baselibrary.base.activity.BaseActivity;
import com.sunsh.baselibrary.base.adapter.BasePageAdapter;
import com.sunsh.baselibrary.utils.ToastUtils;
import com.sunsh.baselibrary.widgets.swipeback.StackManager;

import java.util.HashMap;
import java.util.Map;

@Route(path = RoutePath.MAIN)
public class MainActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager vp;
    private LinearLayout mLinearVideo;
    private LinearLayout mLinearMine;
    private VideoLibFragment videoLibFragment;
    private MineFragment mineFragment;
    private long exitTime;
    private boolean isEnterAnimationComplete;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void goCamera(View view) {
        startActivity(new Intent(this,SnapRecorderSetting.class));
//        SnapRecorderSetting.startRecordForResult(this, 11);
    }

    public void videoLib(View view) {

    }

    private void initView() {
        vp = (ViewPager) findViewById(R.id.vp);
        mLinearVideo = (LinearLayout) findViewById(R.id.linear_video);
        mLinearVideo.setOnClickListener(this);
        mLinearMine = (LinearLayout) findViewById(R.id.linear_mine);
        mLinearMine.setOnClickListener(this);
        mLinearVideo.setSelected(true);
        videoLibFragment = new VideoLibFragment();
        mineFragment = new MineFragment();
        vp.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), videoLibFragment, mineFragment));
        vp.addOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        tabAnimation(v);
        switch (v.getId()) {
            default:
                break;
            case R.id.linear_video:
                if (mLinearVideo.isSelected()) {
                    videoLibFragment.refresh();
                } else {
                    mLinearVideo.setSelected(true);
                    mLinearMine.setSelected(false);
                    vp.setCurrentItem(0);
                }
                break;
            case R.id.linear_mine:
                if (mLinearMine.isSelected()) {
                    mineFragment.refersh();
                } else {
                    mLinearMine.setSelected(true);
                    mLinearVideo.setSelected(false);
                    vp.setCurrentItem(1);
                }
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            mLinearVideo.setSelected(true);
            mLinearMine.setSelected(false);
        } else if (position == 1) {
            mLinearMine.setSelected(true);
            mLinearVideo.setSelected(false);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    Map<View, AnimationSet> animationMap = new HashMap<>();

    private void tabAnimation(View view) {
        AnimationSet animationSet = animationMap.get(view);
        if (animationSet == null) {
            animationSet = new AnimationSet(true);
            ScaleAnimation scaleAnimation = new ScaleAnimation(
                    0.8f, 1f, 0.8f, 1f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
            );
            scaleAnimation.setDuration(200);
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f, 1);
            alphaAnimation.setDuration(200);
            animationSet.addAnimation(scaleAnimation);
            animationSet.addAnimation(alphaAnimation);
            animationMap.put(view, animationSet);
        }
        view.startAnimation(animationSet);
    }

    @Override
    public void onBackPressed() {
        if (isEnterAnimationComplete) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtils.showShortToast("再按一次退出CCEE视频工具");
                exitTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        isEnterAnimationComplete = true;
    }
}

