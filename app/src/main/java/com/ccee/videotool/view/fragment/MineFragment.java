package com.ccee.videotool.view.fragment;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ccee.videotool.R;
import com.ccee.videotool.arouter.Arouter;
import com.ccee.videotool.arouter.RoutePath;
import com.ccee.videotool.dialog.ContactServiceDialog;
import com.ccee.videotool.dialog.LoginOutDialog;
import com.ccee.videotool.dialog.UpLoadDialog;
import com.ccee.videotool.utils.GlideCircleTransform;
import com.sunsh.baselibrary.utils.sp.SpUtil;

import org.w3c.dom.Text;

public class MineFragment extends VideoToolFragment implements View.OnClickListener {

    private TextView mTvContactService;
    private TextView mTvSysSetting;
    private TextView mTvExitLogin;
    private ImageView iv_header;
    private TextView tv_name;
    private TextView tv_category;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View rootView) {
        iv_header = rootView.findViewById(R.id.iv_header);
        tv_name = rootView.findViewById(R.id.tv_name);
        tv_category = rootView.findViewById(R.id.tv_category);
        Glide.with(getActivity()).load(SpUtil.getInstance().getSupplier_logo()).placeholder(R.mipmap.ic_default_square).error(R.mipmap.ic_default_square).transform(new GlideCircleTransform(getActivity())).into(iv_header);
        tv_category.setText(SpUtil.getInstance().getSupplier_title());
        tv_name.setText(SpUtil.getInstance().getSupplier_account());
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
                Arouter.navigation(RoutePath.SYS_SETTING);
                break;
            case R.id.tv_exit_login:
                new LoginOutDialog(getContext()).show();
                break;
        }
    }
}
