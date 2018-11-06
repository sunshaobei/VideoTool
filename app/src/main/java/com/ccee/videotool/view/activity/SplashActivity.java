/*
 * Copyright (C) 2010-2017 Alibaba Group Holding Limited.
 */

package com.ccee.videotool.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ccee.videotool.R;
import com.ccee.videotool.arouter.RoutePath;
import com.ccee.videotool.databinding.ActivitySpalashBinding;
import com.ccee.videotool.model.data.SplashData;
import com.sunsh.baselibrary.base.activity.BaseActivity;
import com.sunsh.baselibrary.utils.PermissionUtils;
import com.sunsh.baselibrary.utils.sp.SpKey;
import com.sunsh.baselibrary.utils.sp.SpUtil;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Mulberry
 */
public class SplashActivity extends BaseActivity implements Handler.Callback, BaseActivity.OnPermissionResult {
    private final int JUMP_TO_MAIN = 1;
    private final int SPALASH_DELAY_TIME = 2000;
    private final int FINISH = 2;

    private Handler jumpHandler = new Handler(this);

    Timer timer = new Timer();
    int countDown = 3;
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            jumpHandler.obtainMessage(JUMP_TO_MAIN, --countDown).sendToTarget();
        }
    };
    private ActivitySpalashBinding binding;

    @Override
    protected boolean needCustomStatusbar() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int systemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
        systemUiVisibility |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
        binding = DataBindingContentView(R.layout.activity_spalash);
        checkHasSelfPermissions(this
                , PermissionUtils.PermissionEnum.EXTERNAL_STORAGE.permission()
                , PermissionUtils.PermissionEnum.READ_EXTERNAL_STORAGE.permission()
                , PermissionUtils.PermissionEnum.CAMERA.permission()
                , PermissionUtils.PermissionEnum.PHONE.permission()
                , PermissionUtils.PermissionEnum.LOCATION.permission()
        );
    }

    private void initData() {
        SplashData splashData = new SplashData();
        splashData.countDown.set(String.format("跳过 %s s", countDown));
        binding.setData(splashData);
    }

    private void countDown() {
        timer.schedule(timerTask, 1000, 1000);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case JUMP_TO_MAIN:
                if ((int) msg.obj == 0) {
                    binding.btnJump.setClickable(false);
                    jump2Main();
                } else {
                    binding.getData().countDown.set(String.format("跳过 %s s", msg.obj.toString()));
                }
                break;
            case FINISH:
                finish();
                break;
            default:
                break;
        }
        return false;
    }

    private void jump2Main() {
        timer.cancel();
        ARouter.getInstance()
                .build(RoutePath.MAIN)
                .navigation(this);
        Message message = new Message();
        message.what = FINISH;
        jumpHandler.sendMessageDelayed(message, 1000);
    }

    @Override
    public void onBackPressed() {
        //clean event
    }

    public void jump(View view) {
        jump2Main();
    }

    @Override
    public void permissionAllow() {
        initData();
        countDown();
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> list = locationManager.getProviders(true);
        if (list.contains(LocationManager.GPS_PROVIDER)) {
            //是否为GPS位置控制器
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                SpUtil.getInstance().putString(SpKey.LONGITUDE, String.valueOf(location.getLongitude()));
                SpUtil.getInstance().putString(SpKey.LATITUDE, String.valueOf(location.getLatitude()));
            }
        } else if (list.contains(LocationManager.NETWORK_PROVIDER)) {
            //是否为网络位置控制器
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                SpUtil.getInstance().putString(SpKey.LONGITUDE, String.valueOf(location.getLongitude()));
                SpUtil.getInstance().putString(SpKey.LATITUDE, String.valueOf(location.getLatitude()));
            }
        }
    }

    @Override
    public void permissionForbid() {

    }
}
