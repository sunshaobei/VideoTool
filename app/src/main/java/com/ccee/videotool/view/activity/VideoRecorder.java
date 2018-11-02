package com.ccee.videotool.view.activity;

import android.app.Activity;
import android.app.Application;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.aliyun.demo.recorder.AliyunVideoRecorder;
import com.ccee.videotool.R;
import com.sunsh.baselibrary.utils.SizeUtils;

public class VideoRecorder implements Application.ActivityLifecycleCallbacks {

    private int beauty_level = 80;

    public VideoRecorder() {
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (activity instanceof AliyunVideoRecorder) {
            AliyunVideoRecorder var = (AliyunVideoRecorder) activity;
            View beauty = var.findViewById(R.id.aliyun_switch_beauty);
            beauty.setOnClickListener(v -> {
                View view = var.getLayoutInflater().inflate(R.layout.popup_beauty, null);
                PopupWindow popupWindow = new PopupWindow(SizeUtils.dp2px(var,200), ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setContentView(view);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.setOutsideTouchable(true);
                popupWindow.setTouchable(true);
                SeekBar seekBar = view.findViewById(R.id.seekBar);
                TextView tv_progress = view.findViewById(R.id.tv_progress);
                seekBar.setProgress(beauty_level);
                tv_progress.setText(beauty_level + "%");
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        popupWindow.dismiss();
                    }
                };
                seekBar.postDelayed(runnable, 3000);
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        beauty_level = progress;
                        tv_progress.setText(progress + "%");
                        var.setBeautyLevel(progress);
                        beauty.setActivated(progress != 0);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        seekBar.removeCallbacks(runnable);
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        seekBar.postDelayed(() -> {
                            popupWindow.dismiss();
                        }, 300);
                    }
                });
                popupWindow.showAsDropDown(beauty);
            });
        }
        activity.getApplication().unregisterActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
