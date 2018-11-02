package com.sunsh.baselibrary.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunsh.baselibrary.R;
import com.sunsh.baselibrary.adapter.recyclerview.base.ItemViewDelegate;
import com.sunsh.baselibrary.adapter.recyclerview.base.ItemViewDelegateManager;
import com.sunsh.baselibrary.adapter.recyclerview.base.OnItemClickListener;
import com.sunsh.baselibrary.adapter.recyclerview.base.OnItemLongClickListener;
import com.sunsh.baselibrary.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by sunsh on 18/5/30.
 */
public class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected Context mContext;
    protected List<T> mDatas;

    protected ItemViewDelegateManager mItemViewDelegateManager;
    protected OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private final EmptyItemViewDelegate emptyItemViewDelegate;
    private boolean enableShowEmpty;


    public MultiItemTypeAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas;
        mItemViewDelegateManager = new ItemViewDelegateManager();
        emptyItemViewDelegate = new EmptyItemViewDelegate(mDatas);
        addItemViewDelegate(emptyItemViewDelegate);
    }

    @Override
    public int getItemViewType(int position) {
        if (!useItemViewDelegateManager()) return super.getItemViewType(position);
        if (position < mDatas.size())
            return mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
        else return mItemViewDelegateManager.getItemViewType(null, position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(viewType);
        int layoutId = itemViewDelegate.getItemViewLayoutId();
        ViewHolder holder = ViewHolder.createViewHolder(mContext, parent, layoutId);
        onViewHolderCreated(holder, holder.getConvertView());
        setListener(parent, holder, viewType);
        return holder;
    }

    public void onViewHolderCreated(ViewHolder holder, View itemView) {

    }

    public void convert(ViewHolder holder, T t) {
        mItemViewDelegateManager.convert(holder, t, holder.getAdapterPosition());
    }


    protected boolean isEnabled(int viewType) {
        return true;
    }


    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener(v -> {
            if (mOnItemClickListener != null) {
                int position = viewHolder.getAdapterPosition();
                if (position < mDatas.size())
                    mOnItemClickListener.onItemClick(mDatas.get(position), viewHolder, position);
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(v -> {
            if (onItemLongClickListener != null) {
                int position = viewHolder.getAdapterPosition();
                if (position < mDatas.size())
                    return onItemLongClickListener.onItemLongClick(mDatas.get(position), viewHolder, position);
            }
            return false;
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder, position < mDatas.size() ? mDatas.get(position) : null);
    }

    @Override
    public int getItemCount() {
        int itemCount = mDatas.size();
        return itemCount == 0 ? (enableShowEmpty ? 1 : 0) : itemCount;
    }


    public List<T> getDatas() {
        return mDatas;
    }

    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    public MultiItemTypeAdapter addItemViewDelegate(int viewType, ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    protected boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setEmptyView(String text, int resid) {
        enableShowEmpty = true;
        emptyItemViewDelegate.setText(text);
        emptyItemViewDelegate.setImage(resid);
    }
}
