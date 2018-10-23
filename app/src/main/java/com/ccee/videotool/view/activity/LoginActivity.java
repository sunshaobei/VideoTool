package com.ccee.videotool.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ccee.videotool.R;
import com.ccee.videotool.arouter.Arouter;
import com.ccee.videotool.arouter.RoutePath;
import com.ccee.videotool.databinding.ActivityLoginBinding;
import com.ccee.videotool.dialog.ForgetPswDialog;
import com.sunsh.baselibrary.base.activity.BaseActivity;

@Route(path = RoutePath.LOGIN)
public class LoginActivity extends BaseActivity {
    ActivityLoginBinding binding;
    private boolean isEnterAnimationComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {

    }

    public void showPsw(View view) {
        view.setSelected(!view.isSelected());
        if (view.isSelected())
            binding.textInputPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        else
            binding.textInputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        Editable text = binding.textInputPassword.getText();
        if (!TextUtils.isEmpty(text)) {
            binding.textInputPassword.setSelection(text.length());
        }
    }

    public void forgetPsw(View view) {
        new ForgetPswDialog(this).show();
    }

    public void userAgreement(View view) {

    }


    @Override
    public void onBackPressed() {
        if (isEnterAnimationComplete) {
            super.onBackPressed();
        }
    }

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        isEnterAnimationComplete = true;
    }

    public void login(View view) {
        Arouter.navigationWhitOption(RoutePath.MAIN);
        new Handler().postDelayed(this::finish, 1000);
    }
}
