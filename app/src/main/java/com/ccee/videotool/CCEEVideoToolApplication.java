package com.ccee.videotool;

import com.aliyun.common.crash.CrashHandler;
import com.aliyun.common.httpfinal.QupaiHttpFinal;
import com.sunsh.baselibrary.BaseApplication;

public class CCEEVideoToolApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        initRecorder();
    }

    /**
     * 视频录制
     */
    private void initRecorder() {
        System.loadLibrary("live-openh264");
        System.loadLibrary("QuCore-ThirdParty");
        System.loadLibrary("QuCore");
        QupaiHttpFinal.getInstance().initOkHttpFinal();
        localCrashHandler();
    }

    private void localCrashHandler() {
//        CrashHandler catchHandler = CrashHandler.getInstance();
//        catchHandler.init(getApplicationContext());
    }
}
