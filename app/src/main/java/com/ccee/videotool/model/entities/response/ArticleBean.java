package com.ccee.videotool.model.entities.response;

public class ArticleBean {
    private int id;
    private String CoverImgUrl;
    private String Title;
    private String Description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCoverImgUrl() {
        return CoverImgUrl;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        CoverImgUrl = coverImgUrl;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
