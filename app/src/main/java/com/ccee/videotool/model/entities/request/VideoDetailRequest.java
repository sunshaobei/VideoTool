package com.ccee.videotool.model.entities.request;

import com.ccee.videotool.model.http.Api;
import com.sunsh.baselibrary.http.ok3.entity.BasicRequest;

public class VideoDetailRequest extends BasicRequest {
    private int auditId;

    public void setAuditId(int auditId) {
        this.auditId = auditId;
    }

    @Override
    public String $getHttpRequestPath() {
        return Api.VIDEO_DETAIL;
    }
}
