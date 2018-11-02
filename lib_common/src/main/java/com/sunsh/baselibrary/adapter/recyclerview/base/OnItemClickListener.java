package com.sunsh.baselibrary.adapter.recyclerview.base;

public interface OnItemClickListener<T> {
    void onItemClick(T t, ViewHolder holder, int position);
}
