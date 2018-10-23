package com.ccee.videotool.arouter;

import com.alibaba.android.arouter.facade.Postcard;

public class StartAlfterLoginHelper {
    private static StartAlfterLoginHelper instance;

    private Postcard postcard;

    private StartAlfterLoginHelper() {

    }

    public static StartAlfterLoginHelper getInstance() {
        //双重校验锁
        if (instance == null) {
            synchronized (StartAlfterLoginHelper.class) {
                if (instance == null) {
                    return instance = new StartAlfterLoginHelper();
                } else {
                    return instance;
                }
            }
        } else {
            return instance;
        }
    }

    public Postcard getPostcard() {
        return postcard;
    }

    public void setPostcard(Postcard postcard) {
        this.postcard = postcard;
    }

    public void startActivity() {
        if (postcard != null) {
            postcard.navigation();
            postcard = null;
        }
    }
}
