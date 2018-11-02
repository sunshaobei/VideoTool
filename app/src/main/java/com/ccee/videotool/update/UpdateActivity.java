package com.ccee.videotool.update;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.ccee.videotool.R;
import com.ccee.videotool.constants.CCEEConstants;
import com.ccee.videotool.dialog.UpdateDialog;
import com.ccee.videotool.model.entities.request.BaseConfigRequest;
import com.ccee.videotool.model.entities.response.ConfigBean;
import com.ccee.videotool.model.http.HttpManager;
import com.ccee.videotool.view.activity.MainActivity;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.sunsh.baselibrary.base.activity.BaseActivity;
import com.sunsh.baselibrary.http.ok3.entity.HttpCallBack;
import com.sunsh.baselibrary.http.ok3.entity.HttpResponse;
import com.sunsh.baselibrary.utils.AppContextUtil;
import com.sunsh.baselibrary.utils.AppUtils;
import com.sunsh.baselibrary.utils.DeviceUtils;
import com.sunsh.baselibrary.utils.PermissionUtils;
import com.sunsh.baselibrary.utils.ToastUtils;
import com.sunsh.baselibrary.utils.sp.SpUtil;

import java.io.File;
import java.util.List;

import okhttp3.Call;


public class UpdateActivity extends BaseActivity {

    public static final String DATA = "data";

    public static void checkUpdate(Context context) {
        checkUpdate(context, false);
    }

    public static void checkUpdate(Context context, boolean showNoFind) {
        BaseConfigRequest request = new BaseConfigRequest();
        HttpManager.get(request, new HttpCallBack<HttpResponse<ConfigBean>>() {
            @Override
            public void onResponse(HttpResponse<ConfigBean> response, int id) {
                //获取当前版本号
                if (response.isResult()) {
                    int versionCode = AppUtils.getVersionCode(context);
                    if (response.getData().getVersion() > versionCode) {
                        UpdateActivity.showUpdateDialog(response.getData());
                    } else if (showNoFind) {
                        ToastUtils.showShortToast("已是最新版本");
                    }
                } else {
                    ToastUtils.showShortToast(response.getMessage());
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                ToastUtils.showShortToast("检查版本更新失败");
            }
        });
    }

    public static void showUpdateDialog(ConfigBean response) {
        Intent intent = new Intent(AppContextUtil.getContext(), UpdateActivity.class);
        intent.putExtra(UpdateActivity.DATA, response);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppContextUtil.getContext().startActivity(intent);
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConfigBean bean = (ConfigBean) getIntent().getSerializableExtra(DATA);
        update(bean);
    }

    private void update(ConfigBean bean) {
        UpdateDialog updateDialog = new UpdateDialog(this);
        updateDialog.setPositive("立即更新", v -> {
            checkHasSelfPermissions(new OnPermissionResult() {
                @Override
                public void permissionAllow() {
                    downloadFile(bean.getDownloadUrl());
                    updateDialog.dismiss();
                    ToastUtils.showShortToast("正在后台下载更新...");
                }

                @Override
                public void permissionForbid() {

                }
            }, PermissionUtils.PermissionEnum.EXTERNAL_STORAGE.permission());
        });
        String des = "";
        List<String> description = bean.getDescription();
        for (int i = 0; i < description.size(); i++) {
            if (i == 0)
                des = (i + 1) + "、" + description.get(i);
            else
                des = des + "\n" + (i + 1) + "、" + description.get(i);
        }
        updateDialog.setContent(des);
        updateDialog.setForceUpdate(bean.isUpgrade());
        updateDialog.setOnDismissListener(dialog -> {
            finish();
            overridePendingTransition(R.anim.no_anim, R.anim.no_anim);
        });

        updateDialog.show();
    }

    private void downloadFile(String downloadUrl) {
        long time = System.currentTimeMillis();
        if (!DeviceUtils.existSDCard()) {
            ToastUtils.showShortToast("SD卡不存在");
            return;
        }
        final String filePath = CCEEConstants.PATH + "/" + "cifnews" + time + ".apk";
        FileDownloader.getImpl().create(downloadUrl)
                .setPath(filePath)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        NotificationFactory.upDateNotify(UpdateActivity.this, "下载中...", true);
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        NotificationFactory.upDateNotify(UpdateActivity.this, "下载中..." + (soFarBytes * 100 / totalBytes) + "%", false);
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        NotificationFactory.upDateNotify(UpdateActivity.this, "完成下载", true);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Uri uri;
                        File file = new File(filePath);
                        int sdkInt = Build.VERSION.SDK_INT;
                        if (sdkInt >= Build.VERSION_CODES.N) {
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            uri = FileProvider.getUriForFile(UpdateActivity.this, "com.example.cifnews.FileProvider", file);
                        } else {
                            uri = Uri.fromFile(file);
                        }
                        intent.setDataAndType(uri, "application/vnd.android.package-archive");
                        startActivity(intent);
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        Log.e("error", "--------------------" + e.getMessage());
                        ToastUtils.showShortToast("下载出错了！");
                        SpUtil.getInstance().putString("checkTime", "");
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        Log.e("warn", "--------------------");
                    }
                }).start();
    }
}
