package com.ccee.videotool.model.entities.request;

import com.ccee.videotool.model.http.Api;
import com.sunsh.baselibrary.http.ok3.entity.BasicRequest;

public class LoginRequest extends BasicRequest {

    private String account;
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String $getHttpRequestPath() {
        return Api.LOGIN;
    }
}
