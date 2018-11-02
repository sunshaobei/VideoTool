package com.aliyun.vodplayerview.view.more;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.aliyun.vodplayer.R;
import com.aliyun.vodplayerview.activity.AliyunPlayerSkinActivity;
import com.aliyun.vodplayerview.widget.AliyunScreenMode;

public class ShowMoreView extends LinearLayout implements RadioGroup.OnCheckedChangeListener {

    private  Context context;
    private SeekBar seekLight;
    private SeekBar seekVoice;
    private RadioGroup rgSpeed;
    private  AliyunShowMoreValue moreValue;
    private OnSpeedCheckedChangedListener mOnSpeedCheckedChangedListener;
    private OnLightSeekChangeListener mOnLightSeekChangeListener;
    private OnVoiceSeekChangeListener mOnVoiceSeekChangeListener;

    public ShowMoreView(Activity context, AliyunShowMoreValue moreValue) {
        super(context);
        this.context = context;
        this.moreValue = moreValue;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.alivc_dialog_more, this, true);
        findAllViews(view);
    }

    private void findAllViews(View view) {
        seekLight = view.findViewById(R.id.seek_light);
        seekVoice = view.findViewById(R.id.seek_voice);
//        tvDonwload = view.findViewById(R.id.tv_download);
//        tvCastScreen = view.findViewById(R.id.tv_cast_screen);
//        tvBarrage = view.findViewById(R.id.tv_barrage);
        rgSpeed = findViewById(R.id.alivc_rg_speed);
        configViews();
        addListener();

    }

    private void configViews() {
        if (moreValue == null){
            return;
        }
        seekLight.setProgress(moreValue.getScreenBrightness());
        seekVoice.setProgress(moreValue.getVolume());

        int currentRbIndex = 0;
        float curentSpeed = moreValue.getSpeed();
        if (curentSpeed == 1.0f) {
            currentRbIndex = 0;
        } else if (curentSpeed == 1.25f) {
            currentRbIndex = 1;
        } else if (curentSpeed == 1.5f) {
            currentRbIndex = 2;
        } else if (curentSpeed == 2.0f) {
            currentRbIndex = 3;
        }
        rgSpeed.check(rgSpeed.getChildAt(currentRbIndex).getId());
    }




    private void addListener() {
//        tvDonwload.setOnClickListener(this);
//        tvCastScreen.setOnClickListener(this);
//        tvBarrage.setOnClickListener(this);

        rgSpeed.setOnCheckedChangeListener(this);

        seekLight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (mOnLightSeekChangeListener != null) {
                    mOnLightSeekChangeListener.onStart(seekBar);
                }
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mOnLightSeekChangeListener != null) {
                    mOnLightSeekChangeListener.onProgress(seekBar, progress, fromUser);
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mOnLightSeekChangeListener != null) {
                    mOnLightSeekChangeListener.onStop(seekBar);
                }
            }
        });

        seekVoice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (mOnVoiceSeekChangeListener != null) {
                    mOnVoiceSeekChangeListener.onStart(seekBar);
                }
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mOnVoiceSeekChangeListener != null) {
                    mOnVoiceSeekChangeListener.onProgress(seekBar, progress, fromUser);
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mOnVoiceSeekChangeListener != null) {
                    mOnVoiceSeekChangeListener.onStop(seekBar);
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (mOnSpeedCheckedChangedListener != null) {
            mOnSpeedCheckedChangedListener.onSpeedChanged(group, checkedId);
        }
    }

    public interface OnSpeedCheckedChangedListener {
        /**
         * 速度切换
         * @param group
         * @param checkedId
         */
        void onSpeedChanged(RadioGroup group, int checkedId);
    }

    public void setOnSpeedCheckedChangedListener(OnSpeedCheckedChangedListener listener) {
        this.mOnSpeedCheckedChangedListener = listener;
    }

    /**
     * 亮度调节
     */
    public interface OnLightSeekChangeListener {
        void onStart(SeekBar seekBar);
        void onProgress(SeekBar seekBar, int progress, boolean fromUser);
        void onStop(SeekBar seekBar);
    }

    public void setOnLightSeekChangeListener(OnLightSeekChangeListener listener) {
        this.mOnLightSeekChangeListener = listener;
    }

    /**
     * 音量调节
     */
    public interface OnVoiceSeekChangeListener {
        void onStart(SeekBar seekBar);
        void onProgress(SeekBar seekBar, int progress, boolean fromUser);
        void onStop(SeekBar seekBar);
    }

    public void setOnVoiceSeekChangeListener(OnVoiceSeekChangeListener listener) {
        this.mOnVoiceSeekChangeListener = listener;
    }
}

