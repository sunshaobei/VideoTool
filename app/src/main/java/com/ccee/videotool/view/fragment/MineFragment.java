package com.ccee.videotool.view.fragment;


import android.view.View;
import android.widget.TextView;

import com.ccee.videotool.R;
import com.ccee.videotool.arouter.Arouter;
import com.ccee.videotool.arouter.RoutePath;
import com.ccee.videotool.dialog.ContactServiceDialog;
import com.ccee.videotool.dialog.LoginOutDialog;

public class MineFragment extends VideoToolFragment implements View.OnClickListener {

    private TextView mTvContactService;
    private TextView mTvSysSetting;
    private TextView mTvExitLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View rootView) {
        mTvContactService = (TextView) rootView.findViewById(R.id.tv_contact_service);
        mTvContactService.setOnClickListener(this);
        mTvSysSetting = (TextView) rootView.findViewById(R.id.tv_sys_setting);
        mTvSysSetting.setOnClickListener(this);
        mTvExitLogin = (TextView) rootView.findViewById(R.id.tv_exit_login);
        mTvExitLogin.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    public void refersh() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_contact_service:
                ContactServiceDialog contactServiceDialog = new ContactServiceDialog(getContext());
                contactServiceDialog.show();
                break;
            case R.id.tv_sys_setting:
                Arouter.greenNavigationWithOption(RoutePath.SYS_SETTING);
                break;
            case R.id.tv_exit_login:
                new LoginOutDialog(getContext()).show();
                break;
        }
    }
}
