package com.ccee.videotool.upload;


import android.util.Log;

import com.alibaba.sdk.android.vod.upload.VODSVideoUploadCallback;
import com.alibaba.sdk.android.vod.upload.VODSVideoUploadClientImpl;
import com.alibaba.sdk.android.vod.upload.model.SvideoInfo;
import com.alibaba.sdk.android.vod.upload.session.VodHttpClientConfig;
import com.alibaba.sdk.android.vod.upload.session.VodSessionCreateInfo;
import com.ccee.videotool.dialog.UpLoadDialog;
import com.ccee.videotool.model.entities.response.UpLoadCoverBean;
import com.ccee.videotool.model.entities.response.VideoSTSBean;
import com.ccee.videotool.model.http.Api;
import com.ccee.videotool.model.http.HttpManager;
import com.sunsh.baselibrary.http.ok3.OkHttpUtils;
import com.sunsh.baselibrary.http.ok3.entity.HttpCallBack;
import com.sunsh.baselibrary.http.ok3.entity.HttpResponse;
import com.sunsh.baselibrary.widgets.swipeback.StackManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class UpLoadManager {
    private static UpLoadManager instance;
    private VODSVideoUploadClientImpl vodsVideoUploadClient;
    private final UpLoadDialog upLoadDialog;

    public interface OnUpLoadSuccessCallback {
        void OnUpLoadSuccess(String videoId, long size, String image);
    }

    private List<OnUpLoadSuccessCallback> callbacks;

    public void addCallback(OnUpLoadSuccessCallback callback) {
        if (callbacks == null) callbacks = new ArrayList<>();
        this.callbacks.add(callback);
    }

    public void reMoveCallback(OnUpLoadSuccessCallback callback) {
        this.callbacks.remove(callback);
    }
    //双重检查锁


    private UpLoadManager() {
        upLoadDialog = new UpLoadDialog();
        vodsVideoUploadClient = new VODSVideoUploadClientImpl(StackManager.getInstance().getCurrentActivity().getApplicationContext());
        vodsVideoUploadClient.init();
    }

    public static UpLoadManager getInstance() {
        if (instance == null) {
            synchronized (UpLoadManager.class) {
                if (instance == null) {
                    instance = new UpLoadManager();
                }
            }
        }
        return instance;
    }


    private void getSts(HttpCallBack<VideoSTSBean> callBack) {
        OkHttpUtils.post().url(Api.UPLOAD_VIDEO_STS).headers(HttpManager.getHeaders()).build().execute(callBack);
    }


    public void upLoad(String videoPahth, String imgPath) {
        upLoadDialog.setText("封面上传中\n请耐心等待");
        upLoadDialog.show();
        upLoadImg(imgPath, new HttpCallBack<HttpResponse<UpLoadCoverBean>>() {
            @Override
            public void onResponse(HttpResponse<UpLoadCoverBean> response, int id) {
                if (response.isResult()) {
                    upLoadVideo(videoPahth, response.getData().getUrl());
                } else {
                    upLoadDialog.setFailed("封面上传失败");
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                upLoadDialog.setFailed("封面上传失败");
            }
        });
    }

    private void upLoadImg(String imgPath, HttpCallBack<HttpResponse<UpLoadCoverBean>> callBack) {
        File file = new File(imgPath);
        HttpManager.postFile(Api.UPLOAD_COVER, file, callBack);
    }

    private void upLoadVideo(String videoPath, String imgUrl) {
        upLoadDialog.setProgress(0);
        upLoadDialog.show();
        getSts(new HttpCallBack<VideoSTSBean>() {
            @Override
            public void onResponse(VideoSTSBean response, int id) {
                if (response.isResult()) {
                    upLoadVideo(videoPath, imgUrl, response.getData());
                } else {
                    upLoadDialog.setFailed(response.getMessage());
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                upLoadDialog.setFailed(e.getMessage());
            }
        });
    }

    private void refreshSTSToken() {
        getSts(new HttpCallBack<VideoSTSBean>() {
            @Override
            public void onResponse(VideoSTSBean response, int id) {
                VideoSTSBean.DataBean data = response.getData();
                vodsVideoUploadClient.refreshSTSToken(data.getAccessKeyId(), data.getAccessKeySecret(), data.getSecurityToken(), data.getExpiration());
            }
        });
    }

    private void upLoadVideo(String videoPath, String imgUrl, VideoSTSBean.DataBean dataBean) {
        //参数请确保存在，如不存在SDK内部将会直接将错误throw Exception
        // 文件路径保证存在之外因为Android 6.0之后需要动态获取权限，请开发者自行实现获取"文件读写权限".
        VodHttpClientConfig vodHttpClientConfig = new VodHttpClientConfig.Builder()
                .setMaxRetryCount(2)
                .setConnectionTimeout(15 * 1000)
                .setSocketTimeout(15 * 1000)
                .build();
        SvideoInfo svideoInfo = new SvideoInfo();
        svideoInfo.setTitle(new File(videoPath).getName());
        svideoInfo.setDesc("");
        svideoInfo.setCateId(438589781);

        VodSessionCreateInfo vodSessionCreateInfo = new VodSessionCreateInfo.Builder()
                .setVideoPath(videoPath)
                .setAccessKeyId(dataBean.getAccessKeyId())
                .setAccessKeySecret(dataBean.getAccessKeySecret())
                .setSecurityToken(dataBean.getSecurityToken())
                .setRequestID(null)
                .setExpriedTime(dataBean.getExpiration())
                .setIsTranscode(true)
                .setSvideoInfo(svideoInfo)
//                        .setPartSize(500 * 1024)
                .setVodHttpClientConfig(vodHttpClientConfig)
                .build();
        vodsVideoUploadClient.uploadWithVideoAndImg(vodSessionCreateInfo, new VODSVideoUploadCallback() {
            private long totalSize;

            @Override
            public void onUploadSucceed(String videoId, String imageUrl) {
                Log.e("UpLoad", "onUploadSucceed" + "videoId:" + videoId + "imageUrl" + imageUrl);
                if (callbacks != null && callbacks.size() > 0) {
                    for (OnUpLoadSuccessCallback callback : callbacks) {
                        callback.OnUpLoadSuccess(videoId, totalSize, imgUrl);
                    }
                }
                upLoadDialog.setSuccess();
            }

            @Override
            public void onUploadFailed(String code, String message) {
                Log.e("UpLoad", "onUploadFailed" + "code" + code + "message" + message);
                upLoadDialog.setFailed(code + message);
            }

            @Override
            public void onUploadProgress(long uploadedSize, long totalSize) {
                float progress = (float) uploadedSize * 100 / (float) totalSize;
                this.totalSize = totalSize;
                Log.e("UpLoad", "onUploadProgress" + progress);
                upLoadDialog.setProgress(progress);
            }

            @Override
            public void onSTSTokenExpried() {
                Log.e("UpLoad", "onSTSTokenExpried");
//                        vodsVideoUploadClient.refreshSTSToken(accessKeyId,accessKeySecret,securityToken,expriedTime);
                refreshSTSToken();
            }

            @Override
            public void onUploadRetry(String code, String message) {
                //上传重试的提醒
                Log.e("UpLoad", "onUploadRetry" + "code" + code + "message" + message);
            }

            @Override
            public void onUploadRetryResume() {
                //上传重试成功的回调.告知用户重试成功
                Log.e("UpLoad", "onUploadRetryResume");
            }
        });
    }
}
