package com.ccee.videotool.adapter;

import android.content.Context;

import com.ccee.videotool.R;
import com.sunsh.baselibrary.adapter.recyclerview.CommonAdapter;
import com.sunsh.baselibrary.adapter.recyclerview.base.ViewHolder;

import java.util.List;

public class VideoAdapter extends CommonAdapter {
    public VideoAdapter(Context context, List datas) {
        super(context, R.layout.item_video, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, Object item, int position) {

    }
}
