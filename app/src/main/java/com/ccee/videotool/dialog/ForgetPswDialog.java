package com.ccee.videotool.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.ccee.videotool.R;

public class ForgetPswDialog extends VideoToolDialog {
    public ForgetPswDialog(@NonNull Context context) {
        super(context);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle(getContext().getResources().getString(R.string.forget_psw));
        setContent(getContext().getResources().getString(R.string.forget_psw_des));
        setPositive(getContext().getResources().getString(R.string.iknow), v -> dismiss());
    }
}
