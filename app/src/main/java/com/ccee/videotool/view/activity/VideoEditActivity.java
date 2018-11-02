package com.ccee.videotool.view.activity;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ccee.videotool.R;
import com.ccee.videotool.arouter.RoutePath;
import com.ccee.videotool.dialog.VideoToolDialog;
import com.ccee.videotool.event.VideoDeleteListener;
import com.ccee.videotool.event.VideoUpdateListener;
import com.ccee.videotool.model.entities.request.EditVideoRequest;
import com.ccee.videotool.model.http.Api;
import com.ccee.videotool.model.http.HttpManager;
import com.sunsh.baselibrary.http.ok3.OkHttpUtils;
import com.sunsh.baselibrary.http.ok3.entity.HttpCallBack;
import com.sunsh.baselibrary.http.ok3.entity.HttpResponse;
import com.sunsh.baselibrary.rxbus.RxBus;
import com.sunsh.baselibrary.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

@Route(path = RoutePath.VIDEO_EDIT)
public class VideoEditActivity extends VideoActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getResources().getString(R.string.video_edit));
        TextView cancle = new TextView(this);
        cancle.setTextColor(ContextCompat.getColor(this, R.color.c1color));
        cancle.setTextSize(15);
        cancle.setText(getResources().getString(R.string.cancle));
        cancle.setOnClickListener(v -> ActivityCompat.finishAfterTransition(this));
        addRightItemView(cancle);
        if (dbVideo.getIsDraft()) videoViewModel.load4EditDraft(dbVideo);
        else videoViewModel.load4EditData(dbVideo);
    }

    @Override
    public void bottomLeft(View view) {
        VideoToolDialog dialog = new VideoToolDialog(this);
        dialog.setTitle("提示")
                .setContent("确定删除该视频？")
                .setPositive("确定", v -> {
                    showLoadingDialog("删除中...");
                    OkHttpUtils.delete().url(Api.VIDEO_DELETE + "?auditId=" + 0).headers(HttpManager.getHeaders()).build().execute(new HttpCallBack<HttpResponse>() {
                        @Override
                        public void onResponse(HttpResponse response, int id) {
                            if (response.isResult()) {
                                RxBus.getDefault().post(new VideoDeleteListener.DeleteVideo(dbVideo.getAuditId()));
                                successDialog("删除成功");
                                view.postDelayed(() -> onBackPressed(), 1000);
                            } else {
                                errorDialog(response.getMessage());
                            }
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            super.onError(call, e, id);
                            errorDialog(e.getMessage());
                        }
                    });
                    dialog.dismiss();
                })
                .setNegative("取消", v -> dialog.dismiss())
                .show();
    }

    @Override
    public void bottomRight(View view) {
        if (!checkError()) return;
        showTranslucentLoadingView();
        EditVideoRequest request = new EditVideoRequest();
        request.setAuditId(dbVideo.getAuditId());
        request.setTitle(dbVideo.getTitle());
        request.setDescription(dbVideo.getDescription());
        request.setCategoryId(dbVideo.getCategoryId());
        List<Integer> list = new ArrayList<>();
        list.add(dbVideo.getProductId());
        request.setProductIds(list);
        HttpManager.post(request, new HttpCallBack<HttpResponse>() {
            @Override
            public void onResponse(HttpResponse response, int id) {
                dismissLoadingView();
                if (response.isResult()) {
                    ToastUtils.showShortToast("提交成功");
                    RxBus.getDefault().post(new VideoUpdateListener.UpdateVideo(2));
                    finish();
                } else {
                    ToastUtils.showShortToast(response.getMessage());
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                dismissLoadingView();
                ToastUtils.showShortToast(e.getMessage());
            }
        });
    }

}
