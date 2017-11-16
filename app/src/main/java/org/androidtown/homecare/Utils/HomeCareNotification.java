package org.androidtown.homecare.Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import org.androidtown.homecare.R;

/**
 * Created by hanhb on 2017-11-17.
 */

public class HomeCareNotification extends Notification {

    //싱글톤
    private static NotificationManager notificationManager;
    private static int MESSAGE_TAG = 1000;

    private HomeCareNotification(){}

    public static void notifyNewMessage(Context context, String content){
        if(notificationManager == null)
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);

        Notification notification = notificationBuilder.setContentTitle("홈케어")
                .setContentInfo("홈케어")
                .setContentText(content)
                .setSmallIcon(R.mipmap.ic_launcher).build();
        notificationManager.notify(MESSAGE_TAG, notification);
    }


}
