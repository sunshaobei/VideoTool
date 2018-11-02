package com.ccee.videotool.share;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.ccee.videotool.R;
import com.ccee.videotool.share.bean.QQShareBean;
import com.ccee.videotool.share.bean.WBShareBean;
import com.ccee.videotool.share.bean.WXMiniProgramShareBean;
import com.ccee.videotool.share.bean.WXShareBean;
import com.sunsh.baselibrary.base.dialog.BaseDialogFragment;
import com.sunsh.baselibrary.utils.BitmapUtils;

import java.util.Arrays;
import java.util.List;


@SuppressLint("ValidFragment")
public class ShareDialog extends BaseDialogFragment {
    private ShareType[] shareTypes;
    private ShareClickListener shareClickListener;
    private Context context;
    private Bitmap shareBitmap;
    String shareString = "";

    public ShareDialog(@NonNull Context context) {
        this.context = context;
        shareTypes = ShareType.values();
    }

    public ShareDialog(@NonNull Context context, ShareType... shareTypes) {
        this.shareTypes = shareTypes;
        this.context = context;
    }


    @Override
    public int getWindowAnimation() {
        return R.style.pickView;
    }

    @Override
    protected void initViews() {
        findViewById(R.id.cancelview).setOnClickListener(v -> dismiss());
        if (shareTypes != null) {
            List<ShareType> shareTypeList = Arrays.asList(shareTypes);
            findViewById(R.id.wxsharelayout).setVisibility(shareTypeList.contains(ShareType.WEICHAT) ? View.VISIBLE : View.GONE);
            findViewById(R.id.wxfrendsharelayout).setVisibility(shareTypeList.contains(ShareType.WECHATFRIEND) ? View.VISIBLE : View.GONE);
            findViewById(R.id.qqsharelayout).setVisibility(shareTypeList.contains(ShareType.QQ) ? View.VISIBLE : View.GONE);
            findViewById(R.id.weibosharelayout).setVisibility(shareTypeList.contains(ShareType.WEIBO) ? View.VISIBLE : View.GONE);
        }
        findViewById(R.id.wxsharelayout).setOnClickListener(v -> {
            onClick(ShareType.WEICHAT);
        });
        findViewById(R.id.wxfrendsharelayout).setOnClickListener(v -> {
            onClick(ShareType.WECHATFRIEND);
        });
        findViewById(R.id.qqsharelayout).setOnClickListener(v -> {
            onClick(ShareType.QQ);
        });
        findViewById(R.id.weibosharelayout).setOnClickListener(v -> {
            onClick(ShareType.WEIBO);
        });
        conentView.setOnTouchListener(this);
        setCanceledOnTouchOutside(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.sharelayout;
    }

    public void shareClick(ShareMsgType shareMsgType, String title, String summary, String picture, String linkUrl, String playUrl, String miniUrl) {
        if (null == shareBitmap) {
            downLoadBitamp(picture);
        }
        setOnShareClickListener(new ShareClickListener() {
            @Override
            public void onShareClick(ShareType shareType) {
                if (null == shareBitmap) {
                    shareBitmap = BitmapFactory.decodeResource(context.getResources(),
                            R.mipmap.icon);
                }
                if (shareType == ShareType.WECHATFRIEND) {
                    WXShareBean wxShareBean = new WXShareBean(title, linkUrl, picture, summary, playUrl, shareBitmap, shareMsgType, ShareType.WECHATFRIEND);
                    ShareManager.share((Activity) context, wxShareBean);
                    shareString = "WeChatFriend";
                } else if (shareType == ShareType.QQ) {
                    QQShareBean qqShareBean = new QQShareBean(title, linkUrl, picture, summary);
                    ShareManager.share((Activity) context, qqShareBean);
                    shareString = "QQ";
                } else if (shareType == ShareType.WEIBO) {
                    WBShareBean wbShareBean = new WBShareBean(title, summary, linkUrl, picture, playUrl, shareBitmap);
                    ShareManager.share((Activity) context, wbShareBean);
                    shareString = "Weibo";
                } else {
                    if (!TextUtils.isEmpty(miniUrl)) {
                        if (null != shareBitmap) {
                            WXMiniProgramShareBean wxShareBean = new WXMiniProgramShareBean(miniUrl, title, linkUrl, picture, summary, shareBitmap);
                            ShareManager.share((Activity) context, wxShareBean);
                        }
                    } else {
                        WXShareBean wxShareBean = new WXShareBean(title, linkUrl, picture, summary, playUrl, shareBitmap, shareMsgType, ShareType.WEICHAT);
                        ShareManager.share((Activity) context, wxShareBean);
                    }
                    shareString = "WeChat";
                }
                dismiss();
            }
        });
    }


    public void setOnShareClickListener(ShareClickListener s) {
        this.shareClickListener = s;
    }

    private void onClick(ShareType shareType) {
        if (shareClickListener != null) shareClickListener.onShareClick(shareType);
    }

    public void downLoadBitamp(String url) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .dontAnimate()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Bitmap bm = resource;
                        if (null != bm) {
                            shareBitmap = BitmapUtils.compressBitmap(bm, 240, 400);
                            Log.e("downBitmap", "图片下载成功" + shareBitmap);
                        } else {
                            shareBitmap = BitmapFactory.decodeResource(context.getResources(),
                                    R.mipmap.icon);
                            Log.e("downBitmap", "图片下载为空" + shareBitmap);
                        }
                    }
                });
    }
}
