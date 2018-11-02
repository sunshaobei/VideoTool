package com.ccee.videotool.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.ccee.videotool.R;

public class UpdateDialog extends VideoToolDialog {
    public UpdateDialog(@NonNull Context context) {
        super(context);
        setTitle("发现新版本");
        ((TextView) findViewById(R.id.content)).setGravity(Gravity.LEFT);
    }

    public void setForceUpdate(boolean b) {
        findViewById(R.id.iv_dismiss).setVisibility(b ? View.GONE : View.VISIBLE);
        setCanceledOnTouchOutside(!b);
        setCancelable(!b);
    }

}
