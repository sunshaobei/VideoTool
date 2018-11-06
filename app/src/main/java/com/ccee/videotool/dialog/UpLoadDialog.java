package com.ccee.videotool.dialog;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ccee.videotool.R;
import com.sunsh.baselibrary.base.dialog.BaseDialogFragment;
import com.sunsh.baselibrary.widgets.swipeback.StackManager;

import java.text.DecimalFormat;

public class UpLoadDialog extends BaseDialogFragment {

    private ImageView imageView;
    private TextView tv_progress;
    private ProgressBar progressBar;

    @Override
    public void setStyle(int style, int theme) {
        super.setStyle(style, R.style.dialog_upload);
    }

    @Override
    protected void initViews() {
        imageView = findViewById(R.id.iv);
        tv_progress = findViewById(R.id.tv_progress);
        progressBar = findViewById(R.id.progressBar);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    public void setProgress(float progress) {
        tv_progress.post(()->{
            DecimalFormat decimalFormat = new DecimalFormat("00.00");
            tv_progress.setText("上传中("+decimalFormat.format(progress)+"%)\n  请耐心等待");
        });
    }

    public void setText(String s) {
        tv_progress.setText(s);
    }

    public void setFailed(String error) {
        imageView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        imageView.setImageDrawable(ContextCompat.getDrawable(StackManager.getInstance().getCurrentActivity(), R.mipmap.error));
        tv_progress.setText(error);
        tv_progress.postDelayed(this::dismiss, 1000);
    }

    public void setSuccess() {
        imageView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        imageView.setImageDrawable(ContextCompat.getDrawable(StackManager.getInstance().getCurrentActivity(), R.mipmap.success));
        tv_progress.setText("上传成功");
        tv_progress.postDelayed(this::dismiss, 1000);
    }

    public void show() {
        progressBar.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        super.show(((AppCompatActivity) StackManager.getInstance().getCurrentActivity()).getSupportFragmentManager());
    }

    @Override
    public void dismiss() {
        progressBar.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);
        super.dismiss();
    }

    @Override
    public int getWindowAnimation() {
        return R.style.pickView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_upload;
    }

}
