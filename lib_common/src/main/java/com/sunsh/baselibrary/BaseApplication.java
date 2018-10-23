package com.sunsh.baselibrary;

import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sunsh.baselibrary.utils.AppContextUtil;
import com.sunsh.baselibrary.widgets.swipeback.SwipeBackHelper;
import com.zzhoujay.richtext.RichText;

public class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        //全局上下文
        AppContextUtil.init(this);

        //路由
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);

        //侧滑返回 内部包含taskmanager
        SwipeBackHelper.init(this, null);

        //富文本
        RichText.initCacheDir(this);
    }
}
