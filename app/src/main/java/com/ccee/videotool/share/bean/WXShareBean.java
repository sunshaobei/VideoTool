package com.ccee.videotool.share.bean;

import android.graphics.Bitmap;

import com.ccee.videotool.share.ShareMsgType;
import com.ccee.videotool.share.ShareType;


public class WXShareBean extends ShareBean {
    private Bitmap bitmap;
    private ShareMsgType shareMsgType;
    private ShareType shareType;

    public WXShareBean(String title, String linkUrl, String imgUrl, String content, String playUrl, Bitmap bitmap, ShareMsgType shareMsgType, ShareType shareType) {
        this.title = title;
        this.linkUrl = linkUrl;
        this.imgUrl = imgUrl;
        this.content = content;
        this.playUrl = playUrl;
        this.bitmap = bitmap;
        this.shareMsgType = shareMsgType;
        this.shareType = shareType;
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

    public ShareMsgType getShareMsgType() {
        return shareMsgType;
    }

    public void setShareMsgType(ShareMsgType shareMsgType) {
        this.shareMsgType = shareMsgType;
    }

    public ShareType getShareType() {
        return shareType;
    }

    public void setShareType(ShareType shareType) {
        this.shareType = shareType;
    }
}
