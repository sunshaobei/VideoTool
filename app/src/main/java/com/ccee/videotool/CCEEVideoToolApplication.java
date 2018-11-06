package com.ccee.videotool;

import com.alivc.player.AliVcMediaPlayer;
import com.aliyun.common.httpfinal.QupaiHttpFinal;
import com.ccee.videotool.greendao.GreenDaoManager;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sunsh.baselibrary.BaseApplication;
import com.sunsh.baselibrary.utils.sp.SpKey;
import com.sunsh.baselibrary.utils.sp.SpUtil;

public class CCEEVideoToolApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        initRecorder();
//        AuthInfo mAuthInfo = new AuthInfo(this, AppIdConstants.WB_APP_KEY,
//                AppIdConstants.REDIRECT_URL, AppIdConstants.SCOPE);
//        WbSdk.install(this, mAuthInfo);
        AliVcMediaPlayer.init(getApplicationContext());
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
