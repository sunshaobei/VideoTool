package com.ccee.videotool.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.ccee.videotool.R;
import com.sunsh.baselibrary.base.dialog.BaseDialog;

public class VideoToolDialog extends BaseDialog {

    public VideoToolDialog(@NonNull Context context) {
        super(context);
    }

    public VideoToolDialog setPositive(String text, View.OnClickListener positiveListener) {
        findViewById(R.id.relative_positive).setVisibility(View.VISIBLE);
        TextView btn_positive = findViewById(R.id.btn_positive);
        btn_positive.setOnClickListener(positiveListener);
        btn_positive.setText(text);
        return this;
    }
    public VideoToolDialog setNegative(String text, View.OnClickListener negativeListener) {
        findViewById(R.id.relative_negative).setVisibility(View.VISIBLE);
        TextView btn_negative = findViewById(R.id.btn_negative);
        btn_negative.setOnClickListener(negativeListener);
        btn_negative.setText(text);
        return this;
    }

    public VideoToolDialog setTitle(String title) {
        ((TextView) findViewById(R.id.title)).setText(title);
        return this;
    }
    public VideoToolDialog setContent(String title) {
        ((TextView) findViewById(R.id.content)).setText(title);
        return this;
    }


    @Override
    protected void initView() {
        findViewById(R.id.iv_dismiss).setOnClickListener(v -> dismiss());
    }


    @Override
    public int getWindowAnimation() {
        return R.style.dialog_custom;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_video_tool;
    }
}
