package com.ccee.videotool.share.bean;

import android.graphics.Bitmap;

public class WXMiniProgramShareBean extends ShareBean {
    private Bitmap bitmap;
    private String miniProgramUrl;


    public WXMiniProgramShareBean(String miniProgramUrl, String title, String linkUrl, String imgUrl, String content, Bitmap bitmap) {
        this.miniProgramUrl = miniProgramUrl;
        this.title = title;
        this.linkUrl = linkUrl;
        this.imgUrl = imgUrl;
        this.content = content;
        this.bitmap = bitmap;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setMiniProgramUrl(String miniProgramUrl) {
        this.miniProgramUrl = miniProgramUrl;
    }

    public String getMiniProgramUrl() {
        return miniProgramUrl;
    }
}
