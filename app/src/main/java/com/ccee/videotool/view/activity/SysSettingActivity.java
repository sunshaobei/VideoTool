package com.ccee.videotool.view.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ccee.videotool.R;
import com.ccee.videotool.arouter.RoutePath;
import com.sunsh.baselibrary.base.activity.BaseBarActivity;

@Route(path = RoutePath.SYS_SETTING)
public class SysSettingActivity extends BaseBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sys_setting);
        initView();
    }

    private void initView() {
        setTitle(getResources().getString(R.string.sys_setting));
    }
}
