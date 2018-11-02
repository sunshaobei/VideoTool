package com.ccee.videotool.model.entities.response;

import java.io.Serializable;

public class VideoDetailBean implements Serializable {
    private String Title;
    private Integer VideoId;
    private String Description;
    private String CoverImgUrl;
    private String AliVideoId;
    private int AuditStatus;
    private String AuditStatusText;
    private int categoryId;
    private String AddTime;
    private String categoryTitle;
    private ProductBean ProductInfo;
    private String Remark;

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getAddTime() {
        return AddTime;
    }

    public void setAddTime(String addTime) {
        AddTime = addTime;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public Integer getVideoId() {
        return VideoId;
    }

    public void setVideoId(Integer videoId) {
        VideoId = videoId;
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

    public String getCoverImgUrl() {
        return CoverImgUrl;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        CoverImgUrl = coverImgUrl;
    }

    public String getAliVideoId() {
        return AliVideoId;
    }

    public void setAliVideoId(String aliVideoId) {
        AliVideoId = aliVideoId;
    }

    public int getAuditStatus() {
        return AuditStatus;
    }

    public void setAuditStatus(int auditStatus) {
        AuditStatus = auditStatus;
    }

    public String getAuditStatusText() {
        return AuditStatusText;
    }

    public void setAuditStatusText(String auditStatusText) {
        AuditStatusText = auditStatusText;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public ProductBean getProductInfo() {
        return ProductInfo;
    }

    public void setProductInfo(ProductBean productInfo) {
        ProductInfo = productInfo;
    }

    public static class ProductBean implements Serializable{
        private String Title;
        private int productId;

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }
    }
}
