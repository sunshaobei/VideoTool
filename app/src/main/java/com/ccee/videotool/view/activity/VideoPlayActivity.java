package com.ccee.videotool.view.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.SeekBar;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.aliyun.vodplayer.media.AliyunLocalSource;
import com.aliyun.vodplayer.media.AliyunVidSts;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.aliyun.vodplayerview.view.choice.AlivcShowMoreDialog;
import com.aliyun.vodplayerview.view.control.ControlView;
import com.aliyun.vodplayerview.view.more.AliyunShowMoreValue;
import com.aliyun.vodplayerview.view.more.ShowMoreView;
import com.aliyun.vodplayerview.view.more.SpeedValue;
import com.aliyun.vodplayerview.widget.AliyunVodPlayerView;
import com.ccee.videotool.R;
import com.ccee.videotool.arouter.RoutePath;
import com.ccee.videotool.model.entities.response.VideoSTSBean;
import com.ccee.videotool.model.http.HttpManager;
import com.sunsh.baselibrary.http.ok3.OkHttpUtils;
import com.sunsh.baselibrary.http.ok3.entity.HttpCallBack;
import com.sunsh.baselibrary.utils.DeviceUtils;

import java.io.File;
import java.lang.annotation.ElementType;
import java.util.HashMap;
import java.util.Map;

@Route(path = RoutePath.VIDEO_PLAY)
public class VideoPlayActivity extends AppCompatActivity implements IAliyunVodPlayer.OnPreparedListener,
        AliyunVodPlayerView.OnPlayStateBtnClickListener,
        AliyunVodPlayerView.NetConnectedListener,
        ControlView.OnShowMoreClickListener {

    @Autowired
    String aliVideoId;
    @Autowired
    String localPath;
    @Autowired
    String coverImg;
    @Autowired
    String title;

    private AliyunVodPlayerView aliyunVodPlayerView;
    private AlivcShowMoreDialog showMoreDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        ARouter.getInstance().inject(this);
        initView();
    }


    private void initView() {
        aliyunVodPlayerView = (AliyunVodPlayerView) findViewById(R.id.video_view);
        aliyunVodPlayerView.setKeepScreenOn(true);
        String sdDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CCEEVideo";
        aliyunVodPlayerView.setPlayingCache(false, sdDir, 60 * 60 /*时长, s */, 300 /*大小，MB*/);
        aliyunVodPlayerView.setTheme(AliyunVodPlayerView.Theme.Blue);
        aliyunVodPlayerView.setCirclePlay(true);
        aliyunVodPlayerView.setAutoPlay(true);
        aliyunVodPlayerView.setOnPreparedListener(this);
        aliyunVodPlayerView.setNetConnectedListener(this);
        aliyunVodPlayerView.setOnPlayStateBtnClickListener(this);
//        aliyunVodPlayerView.setOnCompletionListener();
//        aliyunVodPlayerView.setOnFirstFrameStartListener(this);
//        aliyunVodPlayerView.setOnChangeQualityListener();
//        aliyunVodPlayerView.setOnStoppedListener();
//        aliyunVodPlayerView.setOrientationChangeListener();
//        aliyunVodPlayerView.setOnUrlTimeExpiredListener();
        aliyunVodPlayerView.setOnShowMoreClickListener(this);
//        aliyunVodPlayerView.setOnPlayStateBtnClickListener();
//        aliyunVodPlayerView.setOnSeekCompleteListener();
//        aliyunVodPlayerView.setOnSeekStartListener();
        aliyunVodPlayerView.enableNativeLog();
        aliyunVodPlayerView.setCoverUri(coverImg);
        if (localPath != null) {
            AliyunLocalSource.AliyunLocalSourceBuilder builder = new AliyunLocalSource.AliyunLocalSourceBuilder();
            builder.setCoverPath(coverImg);
            builder.setSource(localPath);
            builder.setTitle(title);
            aliyunVodPlayerView.setLocalSource(builder.build());
        } else {
            loadData();
        }
    }

    private void loadData() {
        Map<String, String> map = new HashMap<>();
        map.put("videoid", aliVideoId);
        map.put("device", DeviceUtils.getDeviceId(this));
        map.put("unionid", "");
        OkHttpUtils.post().url("http://api.cifnews.com/video/PlayAuth").headers(HttpManager.getHeaders()).params(map).build().execute(new HttpCallBack<VideoSTSBean>() {
            @Override
            public void onResponse(VideoSTSBean response, int id) {
                AliyunVidSts vidSts = new AliyunVidSts();
                vidSts.setVid(aliVideoId);
                vidSts.setAcId(response.getData().getAccessKeyId());
                vidSts.setAkSceret(response.getData().getAccessKeySecret());
                vidSts.setSecurityToken(response.getData().getSecurityToken());
                aliyunVodPlayerView.setVidSts(vidSts);
            }
        });
    }


    @Override
    public void onPrepared() {

    }

    @Override
    public void onReNetConnected(boolean isReconnect) {

    }

    @Override
    public void onNetUnConnected() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (aliyunVodPlayerView != null) {
            boolean handler = aliyunVodPlayerView.onKeyDown(keyCode, event);
            if (!handler) {
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (aliyunVodPlayerView != null) {
            aliyunVodPlayerView.onResume();
        }
    }


    @Override
    protected void onDestroy() {
        if (aliyunVodPlayerView != null) {
            aliyunVodPlayerView.onDestroy();
            aliyunVodPlayerView = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (aliyunVodPlayerView != null) {
            aliyunVodPlayerView.onStop();
        }
    }

    @Override
    public void showMore() {
        showMoreDialog = new AlivcShowMoreDialog(this);
        AliyunShowMoreValue moreValue = new AliyunShowMoreValue();
        moreValue.setSpeed(aliyunVodPlayerView.getCurrentSpeed());
        moreValue.setVolume(aliyunVodPlayerView.getCurrentVolume());
        moreValue.setScreenBrightness(aliyunVodPlayerView.getCurrentScreenBrigtness());
        ShowMoreView showMoreView = new ShowMoreView(this, moreValue);
        showMoreDialog.setContentView(showMoreView);
        showMoreDialog.show();
        showMoreView.setOnSpeedCheckedChangedListener((group, checkedId) -> {
            // 点击速度切换
            if (checkedId == com.aliyun.vodplayer.R.id.rb_speed_normal) {
                aliyunVodPlayerView.changeSpeed(SpeedValue.One);
            } else if (checkedId == com.aliyun.vodplayer.R.id.rb_speed_onequartern) {
                aliyunVodPlayerView.changeSpeed(SpeedValue.OneQuartern);
            } else if (checkedId == com.aliyun.vodplayer.R.id.rb_speed_onehalf) {
                aliyunVodPlayerView.changeSpeed(SpeedValue.OneHalf);
            } else if (checkedId == com.aliyun.vodplayer.R.id.rb_speed_twice) {
                aliyunVodPlayerView.changeSpeed(SpeedValue.Twice);
            }

        });

        // 亮度seek
        showMoreView.setOnLightSeekChangeListener(new ShowMoreView.OnLightSeekChangeListener() {
            @Override
            public void onStart(SeekBar seekBar) {

            }

            @Override
            public void onProgress(SeekBar seekBar, int progress, boolean fromUser) {
                aliyunVodPlayerView.setCurrentScreenBrigtness(progress);
            }

            @Override
            public void onStop(SeekBar seekBar) {

            }
        });

        showMoreView.setOnVoiceSeekChangeListener(new ShowMoreView.OnVoiceSeekChangeListener() {
            @Override
            public void onStart(SeekBar seekBar) {

            }

            @Override
            public void onProgress(SeekBar seekBar, int progress, boolean fromUser) {
                aliyunVodPlayerView.setCurrentVolume(progress);
            }

            @Override
            public void onStop(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onPlayBtnClick(IAliyunVodPlayer.PlayerState playerState) {
        if (playerState == IAliyunVodPlayer.PlayerState.Started) {
            findViewById(R.id.iv_play).setVisibility(View.VISIBLE);
        } else if (playerState == IAliyunVodPlayer.PlayerState.Paused || playerState == IAliyunVodPlayer.PlayerState.Prepared) {
            findViewById(R.id.iv_play).setVisibility(View.GONE);
        }
    }

    public void videoPlay(View view) {
        aliyunVodPlayerView.start();
        view.setVisibility(View.GONE);
    }
}
