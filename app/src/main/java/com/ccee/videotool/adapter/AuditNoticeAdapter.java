package com.ccee.videotool.adapter;

import android.content.Context;

import com.ccee.videotool.R;
import com.sunsh.baselibrary.adapter.recyclerview.CommonAdapter;
import com.sunsh.baselibrary.adapter.recyclerview.base.ViewHolder;

import java.util.List;

public class AuditNoticeAdapter extends CommonAdapter {
    public AuditNoticeAdapter(Context context, List datas) {
        super(context, R.layout.recycler_view_item, datas);
    }

    @Override
    protected void convert(ViewHolder holder, Object o, int position) {

    }
}
