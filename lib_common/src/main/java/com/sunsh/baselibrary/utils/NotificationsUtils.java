package com.sunsh.baselibrary.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.sunsh.baselibrary.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class NotificationsUtils {

    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";


    public static final String CHANNEL_ID = "xx";
    public static final String CHANNEL_NAME = "xx";
    public static final int UPDATE_NOTIFY_ID = 0x86;

    public static NotificationCompat.Builder createNotificationBuilder(boolean needSound) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) AppContextUtil.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            if (!needSound)
                channel.setSound(null, null);
            notificationManager.createNotificationChannel(channel);
        }
        return new NotificationCompat.Builder(AppContextUtil.getContext(), CHANNEL_ID);
    }


    @SuppressLint("NewApi")
    public static boolean isNotificationEnabled(Context context) {

        AppOpsManager mAppOps =
                (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);

        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;
        Class appOpsClass = null;

        /* Context.APP_OPS_MANAGER */
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());

            Method checkOpNoThrowMethod =
                    appOpsClass.getMethod(CHECK_OP_NO_THROW,
                            Integer.TYPE, Integer.TYPE, String.class);

            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
            int value = (Integer) opPostNotificationValue.get(Integer.class);

            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) ==
                    AppOpsManager.MODE_ALLOWED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void openNotifyPermission(Activity context) {
        if (!NotificationsUtils.isNotificationEnabled(context)) {
            AlertDialog alertDialog = new AlertDialog.Builder(context)
                    .setTitle("提示")
                    .setMessage("检测到您没有打开通知权限，是否去打开")
                    .setPositiveButton("确定", (dialog, which) -> {
                        dialog.cancel();
                        Intent localIntent = new Intent();
                        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        if (Build.VERSION.SDK_INT >= 9) {
                            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
                        } else if (Build.VERSION.SDK_INT <= 8) {
                            localIntent.setAction(Intent.ACTION_VIEW);

                            localIntent.setClassName("com.android.settings",
                                    "com.android.settings.InstalledAppDetails");

                            localIntent.putExtra("com.android.settings.ApplicationPkgName",
                                    context.getPackageName());
                        }
                        context.startActivity(localIntent);
                    })
                    .setNegativeButton("取消", (dialog, which) -> dialog.cancel()).create();
            alertDialog.show();
        }
    }
}
