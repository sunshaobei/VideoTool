package com.sunsh.baselibrary.adapter.recyclerview.base;

public interface OnItemLongClickListener<T> {
    boolean onItemLongClick(T t, ViewHolder holder, int position);
}
