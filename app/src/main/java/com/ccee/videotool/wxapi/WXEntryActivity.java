package com.ccee.videotool.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ccee.videotool.AppIdConstants;
import com.sunsh.baselibrary.utils.StatusBarUtil;
import com.sunsh.baselibrary.utils.ToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    // IWXAPI �ǵ�����app��΢��ͨ�ŵ�openapi�ӿ�
    private IWXAPI api;
    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.entry);
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        StatusBarUtil.darkMode(this, Color.TRANSPARENT, 0.2f, true);
        //注册到微信
        api = WXAPIFactory.createWXAPI(this, AppIdConstants.WX_APPID, false);
        api.registerApp(AppIdConstants.WX_APPID);
        api.handleIntent(getIntent(), this);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.tencent.mm.opensdk.openapi.IWXAPIEventHandler#onReq(com.tencent.mm.opensdk
     * .modelbase.BaseReq) //微信直接发送给app的消息处理回调
     */
    @Override
    public void onReq(BaseReq arg0) {
        // TODO Auto-generated method stub
        Log.e("onReq", "------------------------------------------" + arg0);
        finish();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.tencent.mm.opensdk.openapi.IWXAPIEventHandler#onResp(com.tencent.mm.opensdk
     * .modelbase.BaseResp) app发送消息给微信，处理返回消息的回调
     */
    @Override
    public void onResp(BaseResp arg0) {
        Log.e("BaseResp", "------------------------------------------" + "{" + arg0.errCode);
        switch (arg0.errCode) {
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Log.e("被拒绝", "---------------------------");
                finish();
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (RETURN_MSG_TYPE_SHARE == arg0.getType()) {
                } else {
                    Toast.makeText(this, "登录失败", Toast.LENGTH_LONG).show();
                }
                break;
            case BaseResp.ErrCode.ERR_OK:
                switch (arg0.getType()) {
                    case RETURN_MSG_TYPE_LOGIN:
                        // 拿到了微信返回的code,立马再去请求access_token
                        String code = ((SendAuth.Resp) arg0).code;
                        // Toast.makeText(this, "授权成功", Toast.LENGTH_LONG).show();
                        break;
                    case RETURN_MSG_TYPE_SHARE:
                        ToastUtils.showShortToast("分享成功");
                        finish();
                        break;
                    default:
                        ToastUtils.showShortToast("被返回");
                        break;
                }
                break;
            default:
                finish();
                break;

        }
    }


    /**
     * @param string 统计失败
     */
    private void getShreResult(final String string) {
        Log.e("getShreResult", "---------------" + string);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    int state = jsonObject.getInt("state");
                    String message = jsonObject.getString("message");
                    finish();
                } catch (JSONException e) {
                    finish();
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

}