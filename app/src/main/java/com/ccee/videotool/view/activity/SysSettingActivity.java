package com.ccee.videotool.view.activity;

import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ccee.videotool.R;
import com.ccee.videotool.arouter.RoutePath;
import com.ccee.videotool.constants.CCEEConstants;
import com.ccee.videotool.update.UpdateActivity;
import com.ccee.videotool.utils.FileUtil;
import com.sunsh.baselibrary.base.activity.BaseBarActivity;
import com.sunsh.baselibrary.utils.AppUtils;
import com.sunsh.baselibrary.utils.ToastUtils;

import java.io.File;

@Route(path = RoutePath.SYS_SETTING)
public class SysSettingActivity extends BaseBarActivity {

    private TextView tv_cache;
    private TextView tv_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sys_setting);
        initView();
        initListener();
    }


    private void initView() {
        setTitle(getResources().getString(R.string.sys_setting));
        tv_cache = (TextView) findViewById(R.id.tv_cache);
        tv_cache.setText(FileUtil.getFileOrFilesSize(CCEEConstants.PATH, FileUtil.SIZETYPE_MB) + " M");
        tv_version = (TextView) findViewById(R.id.tv_version);
        tv_version.setText(AppUtils.getVersionName(this));
    }

    private void initListener() {
        findViewById(R.id.relative_clean).setOnClickListener(v -> {
            FileUtil.deleteFile(new File(CCEEConstants.PATH));
            ToastUtils.showShortToast("已清除缓存");
            TransitionManager.beginDelayedTransition((ViewGroup) findViewById(R.id.relative_clean));
            tv_cache.setText(FileUtil.getFileOrFilesSize(CCEEConstants.PATH, FileUtil.SIZETYPE_MB) + " M");
        });

        findViewById(R.id.relative_version).setOnClickListener(v -> {
            checkUpdate();
        });
    }

    private void checkUpdate() {
        UpdateActivity.checkUpdate(this);
    }

    public void userAgreement(View view) {
        ARouter.getInstance()
                .build(RoutePath.USER_AGREEMENT)
                .navigation(this);
    }

}
