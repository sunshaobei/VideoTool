package com.ccee.videotool.dialog;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.ccee.videotool.R;
import com.ccee.videotool.adapter.CategoryMenLeftAdapter;
import com.ccee.videotool.adapter.CategoryMenRightAdapter;
import com.ccee.videotool.model.entities.request.AllCategoryRequest;
import com.ccee.videotool.model.entities.response.AllCategoryBean;
import com.ccee.videotool.model.entities.response.CategoryBean;
import com.ccee.videotool.model.http.HttpManager;
import com.sunsh.baselibrary.adapter.recyclerview.base.OnItemClickListener;
import com.sunsh.baselibrary.adapter.recyclerview.base.ViewHolder;
import com.sunsh.baselibrary.base.dialog.BaseDialogFragment;
import com.sunsh.baselibrary.http.ok3.entity.HttpCallBack;
import com.sunsh.baselibrary.http.ok3.entity.HttpResponse;
import com.sunsh.baselibrary.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class CategoryDialog extends BaseDialogFragment {


    private List<CategoryBean> menuLeftDatas = new ArrayList<>();
    private List<CategoryBean> menuRightDatas = new ArrayList<>();
    private List<Integer> ids = new ArrayList<>();


    public interface SelectedListener {
        void onSelected(int id, String title);
    }

    private SelectedListener selectedListener;

    public void setSelectedListener(SelectedListener selectedListener) {
        this.selectedListener = selectedListener;
    }

    @Override
    public int getWindowAnimation() {
        return R.style.pickView;
    }

    @Override
    protected void initViews() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_category;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        int categoryId = getArguments().getInt("categoryId");
        RecyclerView menuLeft = findViewById(R.id.rv_left);
        RecyclerView menuRight = findViewById(R.id.rv_right);
        menuLeft.setLayoutManager(new LinearLayoutManager(getContext()));
        menuRight.setLayoutManager(new LinearLayoutManager(getContext()));
        CategoryMenLeftAdapter categoryMenLeftAdapter = new CategoryMenLeftAdapter(getContext(), menuLeftDatas, ids);
        menuLeft.setAdapter(categoryMenLeftAdapter);
        CategoryMenRightAdapter categoryMenRightAdapter = new CategoryMenRightAdapter(getContext(), menuRightDatas, ids);
        menuRight.setAdapter(categoryMenRightAdapter);
        categoryMenLeftAdapter.setOnItemClickListener((OnItemClickListener<CategoryBean>) (o, holder, position) -> {
            if (o.getCategoryId() == ids.get(0)) return;
            ids.clear();
            ids.add(o.getCategoryId());
            categoryMenLeftAdapter.notifyDataSetChanged();
            categoryMenRightAdapter.notifyDataSetChanged();
        });

        categoryMenRightAdapter.setOnItemClickListener((OnItemClickListener<CategoryBean>) (o, holder, position) -> {
            if (ids.size() > 1) {
                ids.remove(1);
            }
            ids.add(o.getCategoryId());
            categoryMenRightAdapter.notifyDataSetChanged();
            List<String> list = null;
            for (CategoryBean menuLeftData : menuLeftDatas) {
                list = new ArrayList<>();
                boolean[] find = {false};
                list.add(menuLeftData.getTitle());
                fetchCategoryStr(o.getCategoryId(), list, menuLeftData, find);
                if (find[0]) break;
                list.clear();

            }
            if (selectedListener != null) {
                StringBuffer sb = new StringBuffer("");
                for (String s : list) {
                    if (TextUtils.isEmpty(sb.toString())) {
                        sb.append(s);
                    } else {
                        sb.append(">").append(s);
                    }
                }
                selectedListener.onSelected(o.getCategoryId(), sb.toString());
            }
            dismiss();
        });

        HttpManager.get(new AllCategoryRequest(), new HttpCallBack<HttpResponse<AllCategoryBean>>() {
            @Override
            public void onResponse(HttpResponse<AllCategoryBean> response, int id) {
                if (response.isResult()) {
                    List<CategoryBean> data = response.getData().getData();
                    for (CategoryBean datum : data) {
                        ids.clear();
                        ids.add(datum.getCategoryId());
                        boolean[] find = {false};
                        fetchCategoryIds(categoryId, ids, datum, find);
                        if (find[0]) break;
                        else ids.clear();
                    }
                    if (ids.size() == 0) {
                        ids.add(data.get(0).getCategoryId());
                    }
                    menuLeftDatas.clear();
                    menuLeftDatas.addAll(data);
                    categoryMenLeftAdapter.notifyDataSetChanged();
                    if (data.size() > 0) {
                        menuRightDatas.clear();
                        menuRightDatas.addAll(data.get(0).getSubCategorys());
                        categoryMenRightAdapter.notifyDataSetChanged();
                    }
                } else {
                    ToastUtils.showShortToast(response.getMessage());
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                ToastUtils.showShortToast(e.getMessage());
            }
        });
        findViewById(R.id.tv_cancel).setOnClickListener(v -> dismiss());
        setCanceledOnTouchOutside(true);
    }

    private void fetchCategoryStr(int categoryId, List<String> list, CategoryBean data, boolean[] find) {
        if (data.getCategoryId() == categoryId) {
            find[0] = true;
        } else if (data.getSubCategorys().size() > 0) {
            for (CategoryBean categoryBean : data.getSubCategorys()) {
                List<String> objects = new ArrayList<>();
                objects.addAll(list);
                list.add(categoryBean.getTitle());
                fetchCategoryStr(categoryId, list, categoryBean, find);
                if (find[0]) break;
                list.clear();
                list.addAll(objects);
            }
        }
    }

    private void fetchCategoryIds(int categoryId, List<Integer> ids, CategoryBean data, boolean[] find) {
        if (data.getCategoryId() == categoryId) {
            find[0] = true;
        } else if (data.getSubCategorys().size() > 0) {
            for (CategoryBean categoryBean : data.getSubCategorys()) {
                List<Integer> objects = new ArrayList<>();
                objects.addAll(ids);
                ids.add(categoryBean.getCategoryId());
                fetchCategoryIds(categoryId, ids, categoryBean, find);
                if (find[0]) break;
                ids.clear();
                ids.addAll(objects);
            }
        }
    }
}
