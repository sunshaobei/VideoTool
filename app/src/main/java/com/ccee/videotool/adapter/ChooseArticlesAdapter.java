package com.ccee.videotool.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ccee.videotool.R;
import com.ccee.videotool.model.entities.response.ArticleBean;
import com.sunsh.baselibrary.adapter.recyclerview.CommonAdapter;
import com.sunsh.baselibrary.adapter.recyclerview.base.ViewHolder;
import com.sunsh.baselibrary.utils.StringUtils;

import java.util.List;

public class ChooseArticlesAdapter extends CommonAdapter<ArticleBean> {
    private List<Integer> selectIds;

    private String searchStr = "";

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }

    public ChooseArticlesAdapter(Context context, List<ArticleBean> datas, List<Integer> selectIds) {
        super(context, R.layout.item_articles_choose, datas);
        this.selectIds = selectIds;
        setEmptyView("暂无文章", R.mipmap.icon_empty);
    }

    @Override
    protected void convert(ViewHolder holder, ArticleBean o, int position) {
        ImageView iv_select = holder.getView(R.id.iv_select);
        ImageView icon = holder.getView(R.id.icon);
        TextView title = holder.getView(R.id.title);
        TextView content = holder.getView(R.id.content);
        Glide.with(mContext).load(o.getCoverImgUrl()).placeholder(R.mipmap.ic_default_square).error(R.mipmap.ic_default_square).into(icon);
        title.setText(o.getTitle());
        StringUtils.hightLight(title, o.getTitle(), searchStr, ContextCompat.getColor(mContext, R.color.c1color));
        StringUtils.hightLight(content, o.getDescription(), searchStr, ContextCompat.getColor(mContext, R.color.c1color));
        iv_select.setSelected(selectIds.contains(o.getId()));
        iv_select.setOnClickListener(v -> {
//            if (selectIds.contains(id)) {
//                selectIds.remove(id);
//                iv_select.setSelected(false);
//            } else {
//                selectIds.add(id);
//                iv_select.setSelected(true);
//            }
            if (selectedListener != null) selectedListener.onSelected(o.getId(),o.getTitle());
        });
    }

    public interface SelectedListener {
        void onSelected(int id,String title);
    }

    private SelectedListener selectedListener;

    public void setSelectedListener(SelectedListener selectedListener) {
        this.selectedListener = selectedListener;
    }
}
