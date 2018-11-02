package com.ccee.videotool.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ccee.videotool.R;
import com.ccee.videotool.arouter.RoutePath;
import com.ccee.videotool.databinding.ActivityVideoBinding;
import com.ccee.videotool.dialog.CategoryDialog;
import com.ccee.videotool.dialog.VideoToolDialog;
import com.ccee.videotool.event.VideoSaveDraftListener;
import com.ccee.videotool.greendao.GreenDaoManager;
import com.ccee.videotool.model.data.VideoData;
import com.ccee.videotool.model.db.DBVideo;
import com.ccee.videotool.viewmodel.VideoViewModel;
import com.sunsh.baselibrary.base.activity.BaseBarActivity;
import com.sunsh.baselibrary.rxbus.RxBus;
import com.sunsh.baselibrary.utils.ToastUtils;

public abstract class VideoActivity extends BaseBarActivity implements CategoryDialog.SelectedListener {

    protected ActivityVideoBinding binding;
    protected VideoViewModel videoViewModel;
    protected VideoData videoData;
    protected DBVideo dbVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingContentView(R.layout.activity_video);
        initListener();
        initData();
    }

    private void initListener() {
        binding.editDescribe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dbVideo.setDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.editTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dbVideo.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    protected void initData() {
        dbVideo = (DBVideo) getIntent().getSerializableExtra("dbVideo");
        ViewCompat.setTransitionName(binding.ivCover, dbVideo.getAuditId() == 0 ? dbVideo.getId() + "" : dbVideo.getAuditId() + "");
        videoViewModel = ViewModelProviders.of(this).get(VideoViewModel.class);
        videoViewModel.getVideoDataLiveData().observe(this, videoData -> {
            this.videoData = videoData;
            binding.setData(videoData);
        });
    }

    public void save(View view) {
        dbVideo.setIsDraft(true);
        GreenDaoManager.getInstance().getDaoSession().getDBVideoDao().insertOrReplace(dbVideo);
        VideoToolDialog dialog = new VideoToolDialog(this);
        dialog.setTitle("提示")
                .setContent("保存成功，是否结束编辑？")
                .setNegative("取消", v -> dialog.dismiss())
                .setPositive("结束", v -> {
                    RxBus.getDefault().post(new VideoSaveDraftListener.Draft());
                    finish();
                }).show();
    }

    public void bottomLeft(View view) {

    }

    public void bottomRight(View view) {
    }

    public void chooseClassify(View view) {
        CategoryDialog categoryDialog = new CategoryDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("categoryId", dbVideo.getCategoryId());
        categoryDialog.setArguments(bundle);
        categoryDialog.setSelectedListener(this);
        categoryDialog.show(getSupportFragmentManager());
    }

    public void chooseArticles(View view) {
        ARouter.getInstance().build(RoutePath.ARTICLES_CHOOSE).withOptionsCompat(ActivityOptionsCompat.makeSceneTransitionAnimation(this)).navigation(this, 110);
    }


    @Override
    public void onSelected(int id, String title) {
        dbVideo.setCategoryId(id);
        dbVideo.setCategoryTitle(title);
        videoData.setCategory(title);
    }

    public void videoPlay(View view) {
        if (videoData.getAliVideoId() != null) {
            ARouter.getInstance()
                    .build(RoutePath.VIDEO_PLAY)
                    .withString("coverImg", videoData.getCover())
                    .withString("aliVideoId", videoData.getAliVideoId())
                    .withString("title", videoData.getTitle())
                    .navigation(this);
        } else if (videoData.getLocalPath() != null) {
            ARouter.getInstance()
                    .build(RoutePath.VIDEO_PLAY)
                    .withString("coverImg", videoData.getCover())
                    .withString("localPath", videoData.getLocalPath())
                    .withString("title", videoData.getTitle())
                    .navigation(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 110) {
            int productId = data.getIntExtra("productId", 0);
            String productTitle = data.getStringExtra("productTitle");
            if (productId != 0) {
                dbVideo.setProductId(productId);
                dbVideo.setProductTitle(productTitle);
                videoData.setArticle(productTitle);
            }
        }
    }
    protected boolean checkError() {
        if (dbVideo.getCategoryId() == 0) {
            ToastUtils.showShortToast("请选择分类！");
            return false;
        }
        if (TextUtils.isEmpty(binding.editTitle.getText())) {
            ToastUtils.showShortToast("请填写标题！");
            return false;
        }
        if (TextUtils.isEmpty(binding.editDescribe.getText())) {
            ToastUtils.showShortToast("请添加描述！");
            return false;
        }

        return true;
    }
}
