package org.androidtown.homecare.Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import org.androidtown.homecare.Activities.MainActivity;
import org.androidtown.homecare.Activities.SigninActivity;
import org.androidtown.homecare.R;
import org.androidtown.homecare.Services.HomeCareService;

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
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("uid", HomeCareService.getUid());
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);

        Notification notification = notificationBuilder
                .setContentInfo("홈케어")
                .setContentText(content)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.homecare1)
                .setContentIntent(pendingIntent).build();

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, SigninActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(contentIntent);

        notificationManager.notify(MESSAGE_TAG, notification);

    }


}
