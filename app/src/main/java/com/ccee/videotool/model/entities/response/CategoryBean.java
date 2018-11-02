package com.ccee.videotool.model.entities.response;

import java.util.List;

public class CategoryBean {

    private int CategoryId;
    private String Title;
    private List<CategoryBean> SubCategorys;

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public List<CategoryBean> getSubCategorys() {
        return SubCategorys;
    }

    public void setSubCategorys(List<CategoryBean> subCategorys) {
        SubCategorys = subCategorys;
    }
}
