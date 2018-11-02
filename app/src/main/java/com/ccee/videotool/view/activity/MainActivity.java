package com.ccee.videotool.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.aliyun.demo.recorder.AliyunVideoRecorder;
import com.aliyun.demo.snap.SnapCropSetting;
import com.aliyun.demo.snap.SnapRecorderSetting;
import com.aliyun.struct.common.CropKey;
import com.aliyun.struct.common.ScaleMode;
import com.aliyun.struct.common.VideoQuality;
import com.aliyun.struct.recorder.CameraType;
import com.aliyun.struct.recorder.FlashType;
import com.aliyun.struct.snap.AliyunSnapVideoParam;
import com.ccee.videotool.R;
import com.ccee.videotool.adapter.MainPagerAdapter;
import com.ccee.videotool.arouter.RoutePath;
import com.ccee.videotool.constants.CCEEConstants;
import com.ccee.videotool.model.db.DBVideo;
import com.ccee.videotool.model.entities.request.BaseConfigRequest;
import com.ccee.videotool.model.entities.response.ConfigBean;
import com.ccee.videotool.model.http.HttpManager;
import com.ccee.videotool.update.UpdateActivity;
import com.ccee.videotool.view.fragment.MineFragment;
import com.ccee.videotool.view.fragment.VideoLibFragment;
import com.sunsh.baselibrary.base.activity.BaseActivity;
import com.sunsh.baselibrary.http.ok3.entity.HttpCallBack;
import com.sunsh.baselibrary.http.ok3.entity.HttpResponse;
import com.sunsh.baselibrary.utils.AppUtils;
import com.sunsh.baselibrary.utils.BitmapUtils;
import com.sunsh.baselibrary.utils.NotificationsUtils;
import com.sunsh.baselibrary.utils.ToastUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

@Route(path = RoutePath.MAIN)
public class MainActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager vp;
    private LinearLayout mLinearVideo;
    private LinearLayout mLinearMine;
    private VideoLibFragment videoLibFragment;
    private MineFragment mineFragment;
    private long exitTime;
    private boolean isEnterAnimationComplete;
    private static final int REQUEST_RECORD = 100;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        UpdateActivity.checkUpdate(this);
        NotificationsUtils.openNotifyPermission(this);
    }

    public void goCamera(View view) {
        getApplication().registerActivityLifecycleCallbacks(new VideoRecorder());
        Intent var3 = new Intent(this, AliyunVideoRecorder.class);
        var3.putExtra("video_resolution", AliyunSnapVideoParam.RESOLUTION_720P);
        var3.putExtra("video_ratio", AliyunSnapVideoParam.RATIO_MODE_9_16);
        var3.putExtra("record_mode", AliyunSnapVideoParam.RECORD_MODE_AUTO);
        var3.putExtra("beauty_level", 80);
        var3.putExtra("beauty_status", true);
        var3.putExtra("camera_type", CameraType.FRONT);
        var3.putExtra("falsh_type", FlashType.ON);
        var3.putExtra("need_clip", true);
        var3.putExtra("max_duration", 120000);
        var3.putExtra("min_duration", 10000);
        var3.putExtra("video_quality", VideoQuality.SSD);
        var3.putExtra("video_gop", 5);
//        var3.putExtra("video_bitrate", var2.getVideoBitrate());
        var3.putExtra("sort_mode", AliyunSnapVideoParam.SORT_MODE_PHOTO);
//        var3.putExtra("video_framerate", 5);
        var3.putExtra("crop_mode", ScaleMode.PS);
        var3.putExtra("min_crop_duration", 10000);
        var3.putExtra("min_video_duration", 10000);
        var3.putExtra("max_video_duration", 120000);
        var3.putExtra("sort_mode", 0);
        startActivityForResult(var3, REQUEST_RECORD);
        overridePendingTransition(R.anim.slid_up_enter, R.anim.slid_down_exit);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_RECORD) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                int type = data.getIntExtra(AliyunVideoRecorder.RESULT_TYPE, 0);
                String path = null;
                long duration = 0;
                if (type == AliyunVideoRecorder.RESULT_TYPE_CROP) {
                    path = data.getStringExtra(CropKey.RESULT_KEY_CROP_PATH);
                    duration = data.getLongExtra(CropKey.RESULT_KEY_DURATION, 0);
                } else if (type == AliyunVideoRecorder.RESULT_TYPE_RECORD) {
                    path = data.getStringExtra(AliyunVideoRecorder.OUTPUT_PATH);
                    try {
                        MediaPlayer mediaPlayer = new MediaPlayer();
                        mediaPlayer.setDataSource(path);
                        mediaPlayer.prepare();
                        duration = mediaPlayer.getDuration();
                        mediaPlayer.release();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                DBVideo dbVideo = new DBVideo();
                dbVideo.setLocalPath(path);
                dbVideo.setAddTime(System.currentTimeMillis());
                dbVideo.setDuration(duration);
                dbVideo.setLocalImg(BitmapUtils.fetchVideoThum(CCEEConstants.PATH, path));
                ARouter.getInstance().build(RoutePath.VIDEO_UPLOAD).withSerializable("dbVideo", dbVideo).navigation(this);
            } else if (resultCode == Activity.RESULT_CANCELED) {
//                Toast.makeText(this, "用户取消录制", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

