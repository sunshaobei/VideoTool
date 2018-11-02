package com.ccee.videotool.share;

public enum ShareType {
    WEICHAT("WeChat"),
    WECHATFRIEND("WeChatFriend"),
    QQ("QQ"),
    WEIBO("Weibo");

    private String type;

    ShareType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
