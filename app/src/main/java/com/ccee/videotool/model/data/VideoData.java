package com.ccee.videotool.model.data;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.ccee.videotool.BR;

import java.util.List;

public class VideoData extends BaseObservable {
    private String bottomLeft;
    private String bottomRight;
    private String category;
    private String title;
    private String des;
    private String article;
    private String cover;
    private String aliVideoId;
    private String localPath;

    @Bindable
    public String getAliVideoId() {
        return aliVideoId;
    }

    public void setAliVideoId(String aliVideoId) {
        this.aliVideoId = aliVideoId;
        notifyPropertyChanged(BR.aliVideoId);
    }

    @Bindable
    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
        notifyPropertyChanged(BR.localPath);
    }

    @Bindable
    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
        notifyPropertyChanged(BR.cover);
    }

    @Bindable
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
        notifyPropertyChanged(BR.category);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
        notifyPropertyChanged(BR.des);
    }

    @Bindable
    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
        notifyPropertyChanged(BR.article);
    }

    boolean show;

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }


    public String getBottomLeft() {
        return bottomLeft;
    }

    public void setBottomLeft(String bottomLeft) {
        this.bottomLeft = bottomLeft;
    }

    public String getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(String bottomRight) {
        this.bottomRight = bottomRight;
    }
}
