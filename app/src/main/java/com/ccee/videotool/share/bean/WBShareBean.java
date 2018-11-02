package com.ccee.videotool.share.bean;

import android.graphics.Bitmap;

public class WBShareBean extends ShareBean{
    private Bitmap bitmap;
    private String content;

    public WBShareBean(String title,String content,String linkUrl, String imageUrl, String playUrl,Bitmap bitmap) {
        this.content = content;
        this.title = title;
        this.linkUrl = linkUrl;
        this.imgUrl = imageUrl;
        this.playUrl = playUrl;
        this.bitmap = bitmap;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    public String getImageUrl() {
        return imgUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imgUrl = imageUrl;
    }
}
