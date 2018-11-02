package com.ccee.videotool.model.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

@Entity
public class DBVideo implements Serializable {
    private static final long serialVersionUID = 0x994553;

    @Id(autoincrement = true)
    private Long id;
    private String localPath;
    private String aliVideoId;
    private String title;
    private String cover;
    private String description;
    private int categoryId;
    private String categoryTitle;
    private int productId;
    private String productTitle;
    private int scale;
    private long duration;
    private int size;
    private Integer VideoId;
    private int auditStatus;
    private int auditId;
    private boolean isDraft;
    private String localImg;
    private long addTime;
    @Generated(hash = 630811326)
    public DBVideo(Long id, String localPath, String aliVideoId, String title,
            String cover, String description, int categoryId, String categoryTitle,
            int productId, String productTitle, int scale, long duration, int size,
            Integer VideoId, int auditStatus, int auditId, boolean isDraft,
            String localImg, long addTime) {
        this.id = id;
        this.localPath = localPath;
        this.aliVideoId = aliVideoId;
        this.title = title;
        this.cover = cover;
        this.description = description;
        this.categoryId = categoryId;
        this.categoryTitle = categoryTitle;
        this.productId = productId;
        this.productTitle = productTitle;
        this.scale = scale;
        this.duration = duration;
        this.size = size;
        this.VideoId = VideoId;
        this.auditStatus = auditStatus;
        this.auditId = auditId;
        this.isDraft = isDraft;
        this.localImg = localImg;
        this.addTime = addTime;
    }
    @Generated(hash = 694498395)
    public DBVideo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getLocalPath() {
        return this.localPath;
    }
    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }
    public String getAliVideoId() {
        return this.aliVideoId;
    }
    public void setAliVideoId(String aliVideoId) {
        this.aliVideoId = aliVideoId;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getCover() {
        return this.cover;
    }
    public void setCover(String cover) {
        this.cover = cover;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getCategoryId() {
        return this.categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    public String getCategoryTitle() {
        return this.categoryTitle;
    }
    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }
    public int getProductId() {
        return this.productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public String getProductTitle() {
        return this.productTitle;
    }
    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }
    public int getScale() {
        return this.scale;
    }
    public void setScale(int scale) {
        this.scale = scale;
    }
    public long getDuration() {
        return this.duration;
    }
    public void setDuration(long duration) {
        this.duration = duration;
    }
    public int getSize() {
        return this.size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public Integer getVideoId() {
        return this.VideoId;
    }
    public void setVideoId(Integer VideoId) {
        this.VideoId = VideoId;
    }
    public int getAuditStatus() {
        return this.auditStatus;
    }
    public void setAuditStatus(int auditStatus) {
        this.auditStatus = auditStatus;
    }
    public int getAuditId() {
        return this.auditId;
    }
    public void setAuditId(int auditId) {
        this.auditId = auditId;
    }
    public boolean getIsDraft() {
        return this.isDraft;
    }
    public void setIsDraft(boolean isDraft) {
        this.isDraft = isDraft;
    }
    public String getLocalImg() {
        return this.localImg;
    }
    public void setLocalImg(String localImg) {
        this.localImg = localImg;
    }
    public long getAddTime() {
        return this.addTime;
    }
    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

}
