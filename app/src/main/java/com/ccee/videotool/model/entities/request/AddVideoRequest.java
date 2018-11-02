package com.ccee.videotool.model.entities.request;

import com.ccee.videotool.model.http.Api;
import com.sunsh.baselibrary.http.ok3.entity.BasicRequest;

import java.util.List;

public class AddVideoRequest extends BasicRequest {
    private String title;
    private String CoverImgUrl;
    private String Description;
    private int categoryId;
    private String aliVideoId;
    private List<Integer> productIds;
    private int Scale;
    private long Duration;
    private long Size;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        CoverImgUrl = coverImgUrl;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setAliVideoId(String aliVideoId) {
        this.aliVideoId = aliVideoId;
    }

    public void setProductIds(List<Integer> productIds) {
        this.productIds = productIds;
    }

    public void setScale(int scale) {
        Scale = scale;
    }

    public void setDuration(long duration) {
        Duration = duration;
    }

    public void setSize(long size) {
        Size = size;
    }

    @Override
    public String $getHttpRequestPath() {
        return Api.ADD_VIDEO;
    }
}
