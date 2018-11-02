package com.ccee.videotool.model.entities.request;

import com.ccee.videotool.model.http.Api;
import com.sunsh.baselibrary.http.ok3.entity.BasicRequest;

public class AllCategoryRequest extends BasicRequest {
    @Override
    public String $getHttpRequestPath() {
        return Api.ALL_CATEGORY;
    }
}
