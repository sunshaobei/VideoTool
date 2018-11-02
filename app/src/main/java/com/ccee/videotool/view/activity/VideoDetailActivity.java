package com.ccee.videotool.view.activity;

import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.aliyun.vodplayerview.widget.AliyunScreenMode;
import com.aliyun.vodplayerview.widget.AliyunVodPlayerView;
import com.ccee.videotool.R;
import com.ccee.videotool.arouter.RoutePath;
import com.ccee.videotool.databinding.ActivityVideoDetailBinding;
import com.ccee.videotool.dialog.VideoToolDialog;
import com.ccee.videotool.event.VideoDeleteListener;
import com.ccee.videotool.event.VideoUpdateListener;
import com.ccee.videotool.model.db.DBVideo;
import com.ccee.videotool.model.entities.request.VideoDetailRequest;
import com.ccee.videotool.model.entities.response.VideoDetailBean;
import com.ccee.videotool.model.http.Api;
import com.ccee.videotool.model.http.HttpManager;
import com.ccee.videotool.share.ShareDialog;
import com.ccee.videotool.share.ShareMsgType;
import com.sunsh.baselibrary.base.activity.BaseBarActivity;
import com.sunsh.baselibrary.http.ok3.OkHttpUtils;
import com.sunsh.baselibrary.http.ok3.entity.HttpCallBack;
import com.sunsh.baselibrary.http.ok3.entity.HttpResponse;
import com.sunsh.baselibrary.rxbus.RxBus;
import com.sunsh.baselibrary.rxbus.Subscribe;
import com.sunsh.baselibrary.utils.ToastUtils;

import okhttp3.Call;

@Route(path = RoutePath.VIDEO_DETAIL)
public class VideoDetailActivity extends BaseBarActivity implements AliyunVodPlayerView.OnScreenModeChangeListener, VideoUpdateListener {


    ActivityVideoDetailBinding binding;

    @Autowired
    int auditId;
    private String aliVideoId;
    private String coverImgUrl;
    private String remark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingContentView(R.layout.activity_video_detail);
        ARouter.getInstance().inject(this);
        setTitle(getResources().getString(R.string.video_detail));
        initView();
        initListener();
        initData();
        RxBus.getDefault().register(this);
    }


    private void initView() {
        ViewCompat.setTransitionName(binding.ivCover, auditId + "");
    }

    private void initListener() {

    }

    private void initData() {
        VideoDetailRequest request = new VideoDetailRequest();
        request.setAuditId(auditId);
        showLoadingView();
        HttpManager.get(request, new HttpCallBack<HttpResponse<VideoDetailBean>>() {
            @Override
            public void onResponse(HttpResponse<VideoDetailBean> response, int id) {
                dismissLoadingView();
                if (response.isResult()) {
                    binding.setData(response.getData());
                    aliVideoId = response.getData().getAliVideoId();
                    coverImgUrl = response.getData().getCoverImgUrl();
                    remark = response.getData().getRemark();
                    addMenu(response.getData());
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

    private void addMenu(VideoDetailBean data) {
        TextView edit = new TextView(this);
        edit.setTextColor(ContextCompat.getColor(this, R.color.c1color));
        edit.setTextSize(15);
        edit.setText(getResources().getString(R.string.edit1));
        edit.setBackground(ContextCompat.getDrawable(this, R.drawable.selector_home_tab));
        DBVideo dbVideo = new DBVideo();
        dbVideo.setCategoryId(data.getCategoryId());
        dbVideo.setCover(data.getCoverImgUrl());
        dbVideo.setDescription(data.getDescription());
        dbVideo.setScale(1);
        dbVideo.setAuditId(auditId);
        dbVideo.setAliVideoId(data.getAliVideoId());
        dbVideo.setTitle(data.getTitle());
        dbVideo.setVideoId(data.getVideoId());
        dbVideo.setCategoryTitle(data.getCategoryTitle());
        dbVideo.setAuditStatus(data.getAuditStatus());
        VideoDetailBean.ProductBean productInfo = data.getProductInfo();
        if (productInfo != null) {
            dbVideo.setProductId(productInfo.getProductId());
            dbVideo.setProductTitle(productInfo.getTitle());
        }
        edit.setOnClickListener(v -> {
            ARouter.getInstance()
                    .build(RoutePath.VIDEO_EDIT)
                    .withOptionsCompat(ActivityOptionsCompat.makeSceneTransitionAnimation(this, Pair.create(binding.ivCover, auditId+"")))
                    .withSerializable("dbVideo", dbVideo)
                    .navigation(this);
        });
        addRightItemView(edit);
    }


    @Override
    public void onScreenModeChange(AliyunScreenMode currentMode) {
        setToolBarVisible(!currentMode.equals(AliyunScreenMode.Full));
    }

    public void share(View view) {
        ShareDialog shareDialog = new ShareDialog(this);
        shareDialog.shareClick(ShareMsgType.PIC_TEXT, "", "", "", "", "", "");
        shareDialog.show(getSupportFragmentManager());
    }

    public void reason(View view) {
        VideoToolDialog dialog = new VideoToolDialog(this);
        dialog.setTitle("提示")
                .setContent(remark==null?"未知原因":remark)
                .setPositive("确定", v -> dialog.dismiss())
                .show();
    }

    public void delete(View view) {
        VideoToolDialog dialog = new VideoToolDialog(this);
        dialog.setTitle("提示")
                .setContent("确定删除该视频？")
                .setPositive("确定", v -> {
                    showLoadingDialog("删除中...");
                    OkHttpUtils.delete().url(Api.VIDEO_DELETE + "?auditId=" + 0).headers(HttpManager.getHeaders()).build().execute(new HttpCallBack<HttpResponse>() {
                        @Override
                        public void onResponse(HttpResponse response, int id) {
                            if (response.isResult()) {
                                RxBus.getDefault().post(new VideoDeleteListener.DeleteVideo(auditId));
                                successDialog("删除成功");
                                view.postDelayed(()->onBackPressed(),1000);
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

    public void videoPlay(View view) {
        if (aliVideoId != null)
            ARouter.getInstance()
                    .build(RoutePath.VIDEO_PLAY)
                    .withString("coverImg", coverImgUrl)
                    .withString("aliVideoId", aliVideoId)
                    .navigation(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unregister(this);
    }

    @Subscribe
    @Override
    public void onUpdate(UpdateVideo o) {
        finish();
    }
}
