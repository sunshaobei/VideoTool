package com.ccee.videotool.model.entities.response;

import java.util.List;

public class ArticleListBean {
    private int count;
    private List<ArticleBean> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ArticleBean> getData() {
        return data;
    }

    public void setData(List<ArticleBean> data) {
        this.data = data;
    }
}
