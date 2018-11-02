package com.ccee.videotool.share;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.ccee.videotool.AppIdConstants;
import com.ccee.videotool.share.activity.WBShareActivity;
import com.ccee.videotool.share.bean.QQShareBean;
import com.ccee.videotool.share.bean.ShareBean;
import com.ccee.videotool.share.bean.WBShareBean;
import com.ccee.videotool.share.bean.WXMiniProgramShareBean;
import com.ccee.videotool.share.bean.WXShareBean;
import com.sunsh.baselibrary.utils.ToastUtils;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

public class ShareManager {
    public static void share(Activity context, ShareBean shareMsg) {
//        String type = "";
        if (TextUtils.isEmpty(shareMsg.imgUrl))   shareMsg.imgUrl = "http://supperman.qiniudn.com/ic_default_post.png";
        if (shareMsg instanceof WBShareBean) {
            Intent intent = new Intent(context, WBShareActivity.class);
            intent.putExtra("title", ((WBShareBean) shareMsg).getTitle());
            intent.putExtra("detialurl", ((WBShareBean) shareMsg).getLinkUrl());
            intent.putExtra("imageurl", ((WBShareBean) shareMsg).getImageUrl());
            intent.putExtra("content",((WBShareBean) shareMsg).getContent());
            intent.putExtra("playUrl",((WBShareBean) shareMsg).getPlayUrl());
            context.startActivity(intent);
//            type = "weibo";
        } else if (shareMsg instanceof QQShareBean) {
            Tencent.createInstance(AppIdConstants.QQAPP_ID, context).shareToQQ(context, ShareMsgFactory.createMsg(shareMsg), new QQShareListener(context, ((QQShareBean) shareMsg).getLinkUrl()));
//            type = "qq";
        } else if (shareMsg instanceof WXShareBean || shareMsg instanceof WXMiniProgramShareBean) {
            IWXAPI api = WXAPIFactory.createWXAPI(context, AppIdConstants.WX_APPID);
            if (!api.isWXAppInstalled()) {
                ToastUtils.showShortToast("您还未安装微信客户端");
                return;
            }
            api.sendReq(ShareMsgFactory.createMsg(shareMsg));
            if (shareMsg instanceof WXShareBean){
                if (((WXShareBean) shareMsg).getShareType().equals(ShareType.WEICHAT)){
//                    type = "weichat";
                }else {
//                    type = "friend";
                }
            }else {
//                type = "weichat";
            }
        }
    }

}
