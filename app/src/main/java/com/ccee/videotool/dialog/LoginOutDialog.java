package com.ccee.videotool.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.ccee.videotool.R;
import com.ccee.videotool.arouter.Arouter;
import com.ccee.videotool.arouter.RoutePath;
import com.ccee.videotool.greendao.GreenDaoManager;
import com.sunsh.baselibrary.utils.sp.SpUtil;
import com.sunsh.baselibrary.widgets.swipeback.StackManager;

public class LoginOutDialog extends VideoToolDialog {
    public LoginOutDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        super.initView();
        setPositive(getContext().getResources().getString(R.string.confirm), v -> {
            SpUtil.getInstance().clearSp4LoginOut();
            Arouter.greenNavigationWithOption(RoutePath.LOGIN, new NavigationCallback() {

                @Override
                public void onFound(Postcard postcard) {

                }

                @Override
                public void onLost(Postcard postcard) {

                }

                @Override
                public void onArrival(Postcard postcard) {
                    new Handler().postDelayed(() -> {
                        int size = StackManager.getInstance().getStack().size();
                        if (size > 1) {
                            for (int i = 0; i < size - 1; i++) {
                                Activity indexActivity = StackManager.getInstance().getIndexActivity(i);
                                indexActivity.finish();
                                indexActivity.overridePendingTransition(R.anim.no_anim, R.anim.no_anim);
                            }
                        }
                    }, 1000);
                }

                @Override
                public void onInterrupt(Postcard postcard) {

                }
            });
            dismiss();
        }).setNegative(getContext().getResources().getString(R.string.cancle), v -> dismiss())
                .setTitle(getContext().getResources().getString(R.string.tip))
                .setContent(getContext().getResources().getString(R.string.login_out_des));
    }
}
