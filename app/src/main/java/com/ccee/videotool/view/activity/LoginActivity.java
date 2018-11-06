package com.ccee.videotool.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.ArrayMap;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.aliyun.common.utils.MD5Util;
import com.ccee.videotool.R;
import com.ccee.videotool.arouter.Arouter;
import com.ccee.videotool.arouter.RoutePath;
import com.ccee.videotool.databinding.ActivityLoginBinding;
import com.ccee.videotool.dialog.ForgetPswDialog;
import com.ccee.videotool.greendao.GreenDaoManager;
import com.ccee.videotool.model.entities.request.LoginRequest;
import com.ccee.videotool.model.entities.response.LoginBean;
import com.ccee.videotool.model.http.Api;
import com.ccee.videotool.model.http.HttpManager;
import com.sunsh.baselibrary.base.activity.BaseActivity;
import com.sunsh.baselibrary.http.ok3.OkHttpUtils;
import com.sunsh.baselibrary.http.ok3.entity.HttpCallBack;
import com.sunsh.baselibrary.http.ok3.entity.HttpResponse;
import com.sunsh.baselibrary.json.JSONUtils;
import com.sunsh.baselibrary.utils.Md5;
import com.sunsh.baselibrary.utils.ToastUtils;
import com.sunsh.baselibrary.utils.sp.SpUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

@Route(path = RoutePath.LOGIN)
public class LoginActivity extends BaseActivity {
    ActivityLoginBinding binding;
    private boolean isEnterAnimationComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingContentView(R.layout.activity_login);
        initView();
        GreenDaoManager.clearInstance();
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
        Arouter.navigation(RoutePath.USER_AGREEMENT);
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
        if (!binding.checkbox.isChecked()) {
            ToastUtils.showShortToast("阅读并勾选\"用户协议\"");
            return;
        }
        if (!checkError()) return;
        LoginRequest request = new LoginRequest();
        request.setAccount(binding.textInputName.getText().toString());
        request.setPassword(MD5Util.getMD5(binding.textInputPassword.getText().toString()));
        showLoadingDialog("登录中...");
        HttpManager.post(request, new HttpCallBack<HttpResponse<LoginBean>>() {
            @Override
            public void onResponse(HttpResponse<LoginBean> response, int id) {
                if (response.isResult()) {
                    successDialog("登录成功");
                    SpUtil.getInstance().saveSupplier_title(response.getData().getSupplier_title());
                    SpUtil.getInstance().saveSupplier_logo(response.getData().getSupplier_logo());
                    SpUtil.getInstance().saveToken(response.getData().getAccess_token());
                    SpUtil.getInstance().saveSupplier_account(response.getData().getSupplier_account());
                    SpUtil.getInstance().saveExpiresAt(response.getData().getExpires_at());
                    SpUtil.getInstance().saveUserId(response.getData().getSupplier_id());
                    new Handler().postDelayed(() -> {
                        Arouter.navigation(RoutePath.MAIN);
                        finish();
                    }, 1000);
                } else {
                    errorDialog(response.getMessage());
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                errorDialog(e.getMessage());

            }
        });
    }

    private boolean checkError() {
        if (TextUtils.isEmpty(binding.textInputName.getText())) {
            ToastUtils.showShortToast(getResources().getString(R.string.please_input_user_name));
            return false;
        }
        if (TextUtils.isEmpty(binding.textInputPassword.getText())) {
            ToastUtils.showShortToast(getResources().getString(R.string.please_input_psw));
            return false;
        }
        return true;
    }
}
