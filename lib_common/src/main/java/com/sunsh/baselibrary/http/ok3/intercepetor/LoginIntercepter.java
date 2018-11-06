package com.sunsh.baselibrary.http.ok3.intercepetor;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sunsh.baselibrary.R;
import com.sunsh.baselibrary.utils.ToastUtils;
import com.sunsh.baselibrary.widgets.swipeback.StackManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LoginIntercepter implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        int code = response.code();
        if (code == 401) {
            ToastUtils.showShortToastSafe("Token过期,请重新登录");
            Postcard build = ARouter.getInstance()
                    .build("/app/login")
                    .greenChannel();
            Activity currentActivity = StackManager.getInstance().getCurrentActivity();
            if (currentActivity != null) {
                currentActivity.runOnUiThread(() -> {
                    build.withOptionsCompat(ActivityOptionsCompat.makeSceneTransitionAnimation(currentActivity)).navigation(currentActivity, new NavigationCallback() {
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
                });
            } else {
                build.navigation();
            }
        }
        return response;
    }
}
