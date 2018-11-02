package com.ccee.videotool.share;

import android.app.Activity;

import com.sunsh.baselibrary.utils.LogUtil;
import com.sunsh.baselibrary.utils.ToastUtils;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

public class QQShareListener implements IUiListener {
    String toURL;
    private Activity context;

    public QQShareListener(Activity context, String toURL) {
        this.context = context;
        this.toURL = toURL;
    }

    @Override
    public void onCancel() {
        LogUtil.e("QQ分享", "----------------------onCancel");
    }

    @Override
    public void onComplete(Object arg0) {
        // TODO Auto-generated method stub
        LogUtil.e("QQ分享", "----------------------onComplete");
    }

    @Override
    public void onError(UiError arg0) {
        // TODO Auto-generated method stub
        LogUtil.e("QQ分享失败", "onError----------------------" + arg0.errorMessage);
        ToastUtils.showShortToast("分享失败");
    }

}
