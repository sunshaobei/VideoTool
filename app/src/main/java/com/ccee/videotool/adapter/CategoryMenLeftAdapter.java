package com.ccee.videotool.adapter;

import android.content.Context;
import android.widget.TextView;

import com.ccee.videotool.R;
import com.ccee.videotool.model.entities.response.CategoryBean;
import com.sunsh.baselibrary.adapter.recyclerview.CommonAdapter;
import com.sunsh.baselibrary.adapter.recyclerview.base.ViewHolder;

import java.util.List;

public class CategoryMenLeftAdapter extends CommonAdapter<CategoryBean> {
    private List<Integer> ids;

    public CategoryMenLeftAdapter(Context context, List<CategoryBean> datas, List<Integer> ids) {
        super(context, R.layout.item_category_menu_left, datas);
        this.ids = ids;
    }

    @Override
    protected void convert(ViewHolder holder, CategoryBean o, int position) {
        TextView textView = (TextView) holder.itemView;
        textView.setText(o.getTitle());
        textView.setSelected(ids.get(0) == o.getCategoryId());
    }

}
