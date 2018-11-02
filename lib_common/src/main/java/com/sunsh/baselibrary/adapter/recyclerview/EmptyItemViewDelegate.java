package com.sunsh.baselibrary.adapter.recyclerview;

import android.text.TextUtils;

import com.sunsh.baselibrary.R;
import com.sunsh.baselibrary.adapter.recyclerview.base.ItemViewDelegate;
import com.sunsh.baselibrary.adapter.recyclerview.base.ViewHolder;

import java.util.List;

public class EmptyItemViewDelegate implements ItemViewDelegate {

    private List datas;
    private String text;
    private int resid;

    public EmptyItemViewDelegate(List datas) {
        this.datas = datas;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.rv_empty;
    }

    @Override
    public boolean isForViewType(Object item, int position) {
        return datas.size() == 0;
    }

    @Override
    public void convert(ViewHolder holder, Object o, int position) {
        if (!TextUtils.isEmpty(text)) holder.setText(R.id.tv_empty, text);
        if (resid != 0) holder.setImageResource(R.id.iv_empty, resid);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setImage(int resid) {
        this.resid = resid;
    }
}
