package com.ccee.videotool.model.entities.response;

public class LoginBean {
    private String access_token;
    private long expires_in;
    private long expires_at;
    private String refresh_token;
    private String supplier_title;
    private String supplier_logo;
    private String supplier_account;


    public long getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(long expires_at) {
        this.expires_at = expires_at;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getSupplier_title() {
        return supplier_title;
    }

    public void setSupplier_title(String supplier_title) {
        this.supplier_title = supplier_title;
    }

    public String getSupplier_logo() {
        return supplier_logo;
    }

    public void setSupplier_logo(String supplier_logo) {
        this.supplier_logo = supplier_logo;
    }

    public String getSupplier_account() {
        return supplier_account;
    }

    public void setSupplier_account(String supplier_account) {
        this.supplier_account = supplier_account;
    }
}
