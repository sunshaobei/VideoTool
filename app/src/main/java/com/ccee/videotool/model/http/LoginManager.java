package com.ccee.videotool.model.http;

import com.sunsh.baselibrary.base.BaseManager;

public class LoginManager extends BaseManager {

    private static LoginManager instance;

    private LoginManager(){
    }

    public static LoginManager getInstance() {
        if (instance == null) {
            synchronized (LoginManager.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }

}
