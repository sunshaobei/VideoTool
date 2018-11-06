package com.ccee.videotool.adapter;

import android.content.Context;
import android.support.v4.view.ViewCompat;

import com.ccee.videotool.R;
import com.ccee.videotool.constants.CCEEConstants;
import com.ccee.videotool.databinding.ItemVideoBinding;
import com.ccee.videotool.model.db.DBVideo;
import com.ccee.videotool.model.entities.response.VideoListBean;
import com.sunsh.baselibrary.adapter.recyclerview.CommonAdapter;
import com.sunsh.baselibrary.adapter.recyclerview.base.ViewHolder;
import com.sunsh.baselibrary.utils.BitmapUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VideoAdapter extends CommonAdapter {
    SimpleDateFormat dateFormat;
    SimpleDateFormat durationFormat;

    public VideoAdapter(Context context, List datas) {
        super(context, R.layout.item_video, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, Object item, int position) {
        ItemVideoBinding binding = viewHolder.getBinding();
        if (item instanceof VideoListBean.VideoBean) {
            ViewCompat.setTransitionName(binding.icon, ((VideoListBean.VideoBean) item).getAuditId() + "");
            binding.setData((VideoListBean.VideoBean) item);
        } else {
            if (dateFormat == null) dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (durationFormat == null) durationFormat = new SimpleDateFormat("mm:ss");
            DBVideo dbVideo = (DBVideo) item;
            VideoListBean.VideoBean videoBean = new VideoListBean.VideoBean();
            videoBean.setAddTimeText(dateFormat.format(new Date(dbVideo.getAddTime())));
            videoBean.setAddTime(dbVideo.getAddTime());
            videoBean.setDuration(durationFormat.format(dbVideo.getDuration()));
            videoBean.setAuditStatusText("草稿");
            String localImg = dbVideo.getLocalImg();
            File file = new File(localImg);
            if (!file.exists())
                BitmapUtils.fetchVideoThum(CCEEConstants.PATH, dbVideo.getLocalPath());
            videoBean.setCoverImgUrl(localImg);
            videoBean.setDescription(dbVideo.getDescription());
            videoBean.setTitle(dbVideo.getTitle());
            ViewCompat.setTransitionName(binding.icon, dbVideo.getId() + "");
            binding.setData(videoBean);
        }
        binding.executePendingBindings();
    }
}
