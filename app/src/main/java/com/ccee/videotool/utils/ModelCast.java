package com.ccee.videotool.utils;

import com.ccee.videotool.greendao.GreenDaoManager;
import com.ccee.videotool.model.db.DBVideo;
import com.ccee.videotool.model.entities.response.VideoListBean;

import java.util.ArrayList;
import java.util.List;

public class ModelCast {
    public static List<VideoListBean.VideoBean> fetchDraft(){
        List<DBVideo> dbVideos = GreenDaoManager.getInstance().getDaoSession().getDBVideoDao().loadAll();
        List<VideoListBean.VideoBean> list = new ArrayList<>();
        for (DBVideo dbVideo : dbVideos) {
            VideoListBean.VideoBean videoBean = new VideoListBean.VideoBean();
//            videoBean.setAddTime();
            videoBean.setAuditStatusText("草稿");
            videoBean.setCoverImgUrl(dbVideo.getLocalImg());
            videoBean.setDescription(dbVideo.getDescription());
            videoBean.setTitle(dbVideo.getTitle());
            list.add(videoBean);
        }
        return list;
    }
}
