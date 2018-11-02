package com.ccee.videotool.arouter;

import android.support.v4.app.ActivityOptionsCompat;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sunsh.baselibrary.widgets.swipeback.StackManager;

import java.io.Serializable;

public class Arouter {
    public static void greenNavigation(String path) {
        ARouter.getInstance()
                .build(path)
                .greenChannel()
                .navigation(StackManager.getInstance().getCurrentActivity());
    }
    public static void greenNavigationWithOption(String path) {
        ARouter.getInstance()
                .build(path)
                .greenChannel()
                .withOptionsCompat(ActivityOptionsCompat.makeSceneTransitionAnimation(StackManager.getInstance().getCurrentActivity()))
                .navigation(StackManager.getInstance().getCurrentActivity());
    }
    public static void greenNavigationWithOption(String path,NavigationCallback callback) {
        ARouter.getInstance()
                .build(path)
                .greenChannel()
                .withOptionsCompat(ActivityOptionsCompat.makeSceneTransitionAnimation(StackManager.getInstance().getCurrentActivity()))
                .navigation(StackManager.getInstance().getCurrentActivity(),callback);
    }
    public static void greenNavigation(String path, NavigationCallback callback) {
        ARouter.getInstance()
                .build(path)
                .greenChannel()
                .navigation(StackManager.getInstance().getCurrentActivity(),callback);
    }

    public static void navigation(String path) {
        ARouter.getInstance().build(path).navigation(StackManager.getInstance().getCurrentActivity());
    }
    public static void navigationWhitOption(String path) {
        ARouter.getInstance().build(path)  .withOptionsCompat(ActivityOptionsCompat.makeSceneTransitionAnimation(StackManager.getInstance().getCurrentActivity())).navigation(StackManager.getInstance().getCurrentActivity());
    }

    public static void greenNavigation(String path, String key, Object value) {
        Postcard postcard = ARouter.getInstance().build(path).greenChannel();
        putValue(key, value, postcard);
        postcard.navigation(StackManager.getInstance().getCurrentActivity());
    }

    public static void navigation(String path, String key, Object value) {
        Postcard postcard = ARouter.getInstance().build(path);
        putValue(key, value, postcard);
        postcard.navigation(StackManager.getInstance().getCurrentActivity());
    }

    private static void putValue(String key, Object value, Postcard postcard) {
        if (value instanceof String) {
            postcard.withString(key, (String) value);
        } else if (value instanceof Integer) {
            postcard.withInt(key, (Integer) value);
        } else if (value instanceof Serializable) {
            postcard.withSerializable(key, (Serializable) value);
        } else if (value instanceof Long) {
            postcard.withLong(key, (Long) value);
        } else if (value instanceof Boolean) {
            postcard.withLong(key, (Long) value);
        }
    }
}
