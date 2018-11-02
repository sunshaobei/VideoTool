package com.ccee.videotool.share.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.ccee.videotool.R;
import com.ccee.videotool.share.ShareError;
import com.ccee.videotool.share.ShareMsgFactory;
import com.ccee.videotool.share.bean.WBShareBean;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.sunsh.baselibrary.http.ok3.OkHttpUtils;
import com.sunsh.baselibrary.utils.BitmapUtils;
import com.sunsh.baselibrary.utils.StatusBarUtil;
import com.sunsh.baselibrary.utils.ToastUtils;
import com.sunsh.baselibrary.utils.sp.SpUtil;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WBShareActivity extends Activity implements WbShareCallback {

    //    private IWeiboShareAPI mWeiboShareAPI;
    private String title;
    private String detialurl;
    private String imageurl;
    private Bitmap bigBitmap;
    //    private Bitmap shareBitmap;
    SsoHandler mSsoHandler;
    //    AuthInfo mAuthInfo;
    private WbShareHandler shareHandler;
    private boolean canShare = true;//是否可以分享，防止返回继续分享
    private long startTime;//进入页面的时间
    private String content;
    private String playUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.darkMode(this, Color.TRANSPARENT, 0.2f, true);
        initShareUI();
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        detialurl = intent.getStringExtra("detialurl");
        imageurl = intent.getStringExtra("imageurl");
        content = intent.getStringExtra("content");
        playUrl = intent.getStringExtra("playUrl");
        Log.e("微博分享", "playUrl------------" + playUrl);
//        Log.e("微博分享", "detialurl------------" + detialurl);
//        Log.e("微博分享", "imageurl------------" + imageurl);
        if (null == title || null == detialurl || null == imageurl) {
            finish();
            return;
        }
        if (null == imageurl || imageurl.isEmpty()) {
            bigBitmap = BitmapFactory.decodeResource(getResources(),
                    R.mipmap.icon);
        } else {
            Log.e("微博分享", "下载------------" + imageurl);
            downBitmap(imageurl);
        }
    }

    private void initShareUI() {
//        mSsoHandler = new SsoHandler(this);
//        mSsoHandler.authorize(this);

        shareHandler = new WbShareHandler(this);
        shareHandler.registerApp();
    }

    /**
     * 下载分享图片
     */
    private void downBitmap(String url) {
        //请求取消收藏
        OkHttpClient mOkHttpClient = OkHttpUtils.getInstance().getOkHttpClient();
        Request request = new Request.Builder().url(url).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("微博下载失败", "------------" + imageurl);
                ToastUtils.showShortToastSafe("分享失败");
                finish();
            }

            @Override
            public void onResponse(Call call, final Response response) {
                InputStream inputStream = response.body().byteStream();
                Bitmap mBitmap = BitmapFactory.decodeStream(inputStream);
                if (null != mBitmap) {
                    bigBitmap = BitmapUtils.compressBitmap(mBitmap, 240, 400);
                    share();
                    Log.e("downBitmap微博分享", "图片下载成功---" + bigBitmap);
                } else {
                    bigBitmap = BitmapFactory.decodeResource(getResources(),
                            R.mipmap.icon);
                    share();
                }
            }
        });
    }

    /**
     * 分享
     */
    private void share() {
        runOnUiThread(() -> {
            shareHandler.shareMessage(ShareMsgFactory.createMsg(new WBShareBean(title, content, detialurl, imageurl, playUrl, bigBitmap)), false);
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
        Log.e("onNewIntent", "--------------------------");
        shareHandler.doResultIntent(intent, this);
    }

    @Override
    public void onWbShareSuccess() {
        ToastUtils.showShortToast("分享成功");
        finish();
    }

    @Override
    public void onWbShareCancel() {
        ToastUtils.showShortToast("分享取消");
        finish();
    }

    @Override
    public void onWbShareFail() {
        ToastUtils.showShortToast("分享失败");
        finish();
    }


    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTime = System.currentTimeMillis() / 1000;
    }
}
