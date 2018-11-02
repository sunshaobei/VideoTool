package com.ccee.videotool.share;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;

import com.ccee.videotool.AppIdConstants;
import com.ccee.videotool.share.bean.QQShareBean;
import com.ccee.videotool.share.bean.ShareBean;
import com.ccee.videotool.share.bean.WBShareBean;
import com.ccee.videotool.share.bean.WXMiniProgramShareBean;
import com.ccee.videotool.share.bean.WXShareBean;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.utils.Utility;
import com.sunsh.baselibrary.http.retrofit.utils.BitmapUtil;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXMusicObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

import java.util.HashMap;

public class ShareMsgFactory {
    public static <T> T createMsg(ShareBean t) {
        Object msg = null;
        if (t instanceof QQShareBean) {
            QQShareBean shareMsg = (QQShareBean) t;
            final Bundle params = new Bundle();
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,
                    QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putString(QQShare.SHARE_TO_QQ_TITLE, shareMsg.getTitle());// 分享标题
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareMsg.getContent());// 分享内容
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareMsg.getLinkUrl());// 分享的链接地址
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareMsg.getImgUrl());// 分享内容图片
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "雨果网");// 来自雨果网应用
            msg = params;
        } else if (t instanceof WBShareBean) {
            // 1. 初始化微博的分享消息
            WBShareBean shareMsg = (WBShareBean) t;
            WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
            weiboMessage.textObject = getTextObj(shareMsg.getTitle() + "(来自@雨果网跨境电商)");
            if (null != shareMsg.getBitmap()) {
                weiboMessage.imageObject = getImageObj(shareMsg.getBitmap());
            }
            weiboMessage.mediaObject = getWebpageObj(shareMsg.getBitmap(), shareMsg.getTitle(), shareMsg.getContent(),shareMsg.getLinkUrl(), shareMsg.getPlayUrl());
            msg = weiboMessage;
        } else if (t instanceof WXShareBean) {
            WXShareBean shareBean = (WXShareBean) t;
            msg = share2weixin(shareBean);
        } else if (t instanceof WXMiniProgramShareBean) {
            WXMiniProgramShareBean shareMsg = (WXMiniProgramShareBean) t;
            WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
            miniProgramObj.webpageUrl = shareMsg.getLinkUrl(); // 兼容低版本的网页链接
            miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;// 正式版:0，测试版:1，体验版:2
            miniProgramObj.userName = shareMsg.getMiniProgramUrl().contains("yukeDetails")?AppIdConstants.WX_MINIPROGRAM_ID:AppIdConstants.WX_MESSAGE_MINIPROGRAM_ID;     // 小程序原始id
            miniProgramObj.path = shareMsg.getMiniProgramUrl();            //小程序页面路径
            WXMediaMessage wxMediaMessage = new WXMediaMessage(miniProgramObj);
            wxMediaMessage.title = shareMsg.getTitle();                    // 小程序消息title
            wxMediaMessage.description = shareMsg.getContent();               // 小程序消息desc
            wxMediaMessage.thumbData = BitmapUtil.Bitmap2Bytes(shareMsg.getBitmap());                      // 小程序消息封面图片，小于128k
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("mini_program");
            req.message = wxMediaMessage;
            req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前支持会话
            msg = req;
        }
        return (T) msg;
    }


    private static SendMessageToWX.Req share2weixin(WXShareBean shareMsg) {
        if (shareMsg.getShareMsgType().equals(ShareMsgType.PIC)) {
            //图片分享
            WXImageObject imgObj = new WXImageObject(shareMsg.getBitmap());
            WXMediaMessage msg = new WXMediaMessage();
            msg.mediaObject = imgObj;
            SendMessageToWX.Req req = new SendMessageToWX.Req();
//            Bitmap thumbBmp = Bitmap.createScaledBitmap(shareBitmap, THUMB_SIZE, THUMB_SIZE, true);
//            shareBitmap.recycle();
//            msg.thumbData = Util.bmpToByteArray(thumbBmp, true);  // 设置缩略图
            req.transaction = buildTransaction("imgshare");
            req.message = msg;
            if (shareMsg.getShareType().equals(ShareType.WECHATFRIEND)) {
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
            } else {
                req.scene = SendMessageToWX.Req.WXSceneSession;
            }
            return req;
        } else if (shareMsg.getShareMsgType().equals(ShareMsgType.VIDEO)) {
            //音频分享
            WXMusicObject wxMusicObject = new WXMusicObject();
            wxMusicObject.musicUrl = shareMsg.getLinkUrl();
            wxMusicObject.musicDataUrl = shareMsg.getPlayUrl();
            WXMediaMessage msg = new WXMediaMessage();
            msg.mediaObject = wxMusicObject;
            msg.title = shareMsg.getTitle();
            msg.description = shareMsg.getContent();
            msg.setThumbImage(shareMsg.getBitmap());
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("music");
            req.message = msg;
            if (shareMsg.getShareType().equals(ShareType.WECHATFRIEND)) {
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
            } else {
                req.scene = SendMessageToWX.Req.WXSceneSession;
            }
            return req;

        } else {
            //图文分享
            if (null != shareMsg.getContent() && shareMsg.getContent().length() > 100) {
                shareMsg.setContent(shareMsg.getContent().replace(" ", ""));
                shareMsg.setContent(shareMsg.getContent().substring(0, 101));
            }
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = shareMsg.getLinkUrl();
            WXMediaMessage msg = new WXMediaMessage(webpage);
            msg.title = shareMsg.getTitle();
            msg.description = shareMsg.getContent();
            msg.setThumbImage(shareMsg.getBitmap());
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("webpage");
            req.message = msg;
            HashMap<String, String> map = new HashMap<String, String>();
            if (shareMsg.getShareType().equals(ShareType.WECHATFRIEND)) {
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
            } else {
                req.scene = SendMessageToWX.Req.WXSceneSession;
            }
            return req;
        }

    }

    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();
    }


    /**
     * 创建图片消息对象。
     *
     * @param bitmap
     * @return 图片消息对象。
     */
    private static ImageObject getImageObj(Bitmap bitmap) {
        ImageObject imageObject = new ImageObject();
//        BitmapDrawable bitmapDrawable = (BitmapDrawable) mImageView.getDrawable();
        //设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
//        Bitmap  bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo);
        imageObject.setImageObject(bitmap);
        return imageObject;
    }

    /**
     * 创建文本消息对象。
     *
     * @param title
     * @return 文本消息对象。
     */
    private static TextObject getTextObj(String title) {
        TextObject textObject = new TextObject();
        textObject.text = title;
        return textObject;
    }

    /**
     * 创建多媒体（网页）消息对象。
     *
     * @param image
     * @param url
     * @param playUrl
     * @return 多媒体（网页）消息对象。
     */
    private static WebpageObject getWebpageObj(Bitmap image,
                                               String msg, String content,String url, String playUrl) {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = msg;
        mediaObject.description = content;
        // 设置 Bitmap 类型的图片到视频对象里 设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        if (null != image) {
            mediaObject.setThumbImage(image);
        }
        if (TextUtils.isEmpty(playUrl)) {
            mediaObject.actionUrl = url;//链接地址
        } else {
            mediaObject.actionUrl = playUrl;//链接地址
        }
        mediaObject.defaultText = "雨果网";
        return mediaObject;
    }


}
