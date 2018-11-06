package com.ccee.videotool.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ccee.videotool.R;
import com.ccee.videotool.arouter.RoutePath;
import com.ccee.videotool.constants.CCEEConstants;
import com.ccee.videotool.dialog.VideoToolDialog;
import com.ccee.videotool.event.VideoDeleteListener;
import com.ccee.videotool.event.VideoSaveDraftListener;
import com.ccee.videotool.event.VideoUpLoadSuccessListener;
import com.ccee.videotool.event.VideoUpdateListener;
import com.ccee.videotool.greendao.GreenDaoManager;
import com.ccee.videotool.model.entities.request.AddVideoRequest;
import com.ccee.videotool.model.http.HttpManager;
import com.ccee.videotool.upload.UpLoadManager;
import com.ccee.videotool.utils.FileUtil;
import com.sunsh.baselibrary.http.ok3.entity.HttpCallBack;
import com.sunsh.baselibrary.http.ok3.entity.HttpResponse;
import com.sunsh.baselibrary.rxbus.RxBus;
import com.sunsh.baselibrary.utils.BitmapUtils;
import com.sunsh.baselibrary.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;

@Route(path = RoutePath.VIDEO_UPLOAD)
public class UpLoadVideoActivity extends VideoActivity implements UpLoadManager.OnUpLoadSuccessCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        setTitle(getResources().getString(R.string.upload_video));
        videoViewModel.load4EditDraft(dbVideo);
        UpLoadManager.getInstance().addCallback(this);
    }

    @Override
    public void bottomLeft(View view) {
        GreenDaoManager.getInstance().getDaoSession().getDBVideoDao().deleteByKey(dbVideo.getId());
        FileUtil.deleteFile(new File(dbVideo.getLocalPath()));
        FileUtil.deleteFile(new File(dbVideo.getLocalImg()));
        RxBus.getDefault().post(new VideoDeleteListener.DeleteVideo(0));
        ToastUtils.showShortToast("删除成功");
        finish();
    }


    @Override
    public void bottomRight(View view) {
        if (!checkError()) return;
        String localImg = dbVideo.getLocalImg();
        File file = new File(localImg);
        if (!file.exists())
            localImg = BitmapUtils.fetchVideoThum(CCEEConstants.PATH, dbVideo.getLocalPath());
        UpLoadManager.getInstance().upLoad(dbVideo.getLocalPath(), localImg);
    }

    @Override
    public void OnUpLoadSuccess(String videoId, long size, String image) {
        showLoadingDialog("正在提交...");
        AddVideoRequest request = new AddVideoRequest();
        request.setCategoryId(dbVideo.getCategoryId());
        request.setCoverImgUrl(image);
        request.setDescription(dbVideo.getDescription());
        request.setScale(1);
        request.setDuration(dbVideo.getDuration() / 1000);
        request.setSize(size);
        request.setAliVideoId(videoId);
        List<Integer> list = new ArrayList<>();
        list.add(dbVideo.getProductId());
        request.setProductIds(list);
        request.setTitle(dbVideo.getTitle());
        HttpManager.post(request, new HttpCallBack<HttpResponse>() {
            @Override
            public void onResponse(HttpResponse response, int id) {
                if (response.isResult()) {
                    successDialog("提交成功");
                    new Handler().postDelayed(() -> {
                        dismissLoadingDialog();
                        RxBus.getDefault().post(new VideoUpLoadSuccessListener.UpLoadSuccess());
                        GreenDaoManager.getInstance().getDaoSession().getDBVideoDao().deleteByKey(dbVideo.getId());
                        finish();
                    }, 1000);
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UpLoadManager.getInstance().reMoveCallback(this);
    }
}
