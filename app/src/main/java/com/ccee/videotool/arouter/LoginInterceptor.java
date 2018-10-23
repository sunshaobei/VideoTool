package com.ccee.videotool.arouter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActivityOptionsCompat;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sunsh.baselibrary.utils.LogUtil;
import com.sunsh.baselibrary.utils.sp.SpUtil;
import com.sunsh.baselibrary.widgets.swipeback.StackManager;

import java.util.List;

@Interceptor(priority = 1)
public class LoginInterceptor implements IInterceptor {

    private List<String> needLoginPaths = ARouterPathFilter.needLogingPaths();

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        if (needLoginPaths.contains(postcard.getPath())) {
            if (!SpUtil.getInstance().isLogin()) {
                LogUtil.e("Arouter", "登陆拦截");
                callback.onInterrupt(null);
                Postcard build = ARouter.getInstance()
                        .build(RoutePath.LOGIN)
                        .greenChannel();
                Activity currentActivity = StackManager.getInstance().getCurrentActivity();
                if (currentActivity != null) {
                    currentActivity.runOnUiThread(() -> {
                        build.withOptionsCompat(ActivityOptionsCompat.makeSceneTransitionAnimation(currentActivity)).navigation(currentActivity);
                    });
                } else {
                    build.navigation();
                }
            } else {
                callback.onContinue(postcard);
            }
        } else {
            callback.onContinue(postcard);
        }
    }

    @Override
    public void init(Context context) {
    }
}
