package com.ccee.videotool.update;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v4.app.NotificationCompat;

import com.ccee.videotool.R;
import com.sunsh.baselibrary.utils.AppContextUtil;


public class NotificationFactory {
    public static final String CHANNEL_ID = "com.ccee.www";
    public static final String CHANNEL_NAME = "CCEE视频工具";
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

    public static void upDateNotify(Context context, String content,boolean needSound) {
        NotificationCompat.Builder builder = NotificationFactory.createNotificationBuilder(needSound);
        builder.setOngoing(true);
//        builder.setContentIntent(pendingIntentClick);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);

        Notification notification = builder
                .setContentTitle("CCEE视频工具版本更新")
                .setContentText(content)
                .setSmallIcon(R.mipmap.ccee)
                .build();

        notification.flags = Notification.FLAG_AUTO_CANCEL;
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(UPDATE_NOTIFY_ID, notification);
    }
}
