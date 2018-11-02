package com.ccee.videotool.model.entities.response;

import java.util.List;

public class VideoListBean  {
    private int count ;
    private List<VideoBean> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<VideoBean> getData() {
        return data;
    }

    public void setData(List<VideoBean> data) {
        this.data = data;
    }

    public static class VideoBean{

        private int AuditId;
        private int VideoId;
        private int AuditStatus;
        private String Title;
        private String AuditStatusText;
        private String CoverImgUrl;
        private String Description;
        private String Duration;
        private long AddTime;
        private String AddTimeText;

        public int getAuditId() {
            return AuditId;
        }

        public void setAuditId(int auditId) {
            AuditId = auditId;
        }

        public int getVideoId() {
            return VideoId;
        }

        public void setVideoId(int videoId) {
            VideoId = videoId;
        }

        public int getAuditStatus() {
            return AuditStatus;
        }

        public void setAuditStatus(int auditStatus) {
            AuditStatus = auditStatus;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getAuditStatusText() {
            return AuditStatusText;
        }

        public void setAuditStatusText(String auditStatusText) {
            AuditStatusText = auditStatusText;
        }

        public String getCoverImgUrl() {
            return CoverImgUrl;
        }

        public void setCoverImgUrl(String coverImgUrl) {
            CoverImgUrl = coverImgUrl;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public String getDuration() {
            return Duration;
        }

        public void setDuration(String duration) {
            Duration = duration;
        }

        public long getAddTime() {
            return AddTime;
        }

        public void setAddTime(long addTime) {
            AddTime = addTime;
        }

        public String getAddTimeText() {
            return AddTimeText;
        }

        public void setAddTimeText(String addTimeText) {
            AddTimeText = addTimeText;
        }
    }
}
