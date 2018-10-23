package com.ccee.videotool.dialog;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ccee.videotool.R;
import com.sunsh.baselibrary.utils.PhoneUtils;

public class ContactServiceDialog extends VideoToolDialog {
    public ContactServiceDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle(getContext().getResources().getString(R.string.contact_service));
        setContent(getContext().getResources().getString(R.string.contact_service_des));
        setPositive(getContext().getResources().getString(R.string.contact_now), v -> {
            PhoneUtils.callPhoneDirectly(getContext(), "1730505920112");
            dismiss();
        }).setNegative(getContext().getResources().getString(R.string.contact_later), v -> dismiss());
    }
}
