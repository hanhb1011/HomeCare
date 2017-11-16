package org.androidtown.homecare.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.androidtown.homecare.Utils.HomeCareNotification;

/*

    홈케어 서비스(구현 예정)
    TODO 사용자에게 수신 메시지 알림 (앱 사용 중이 아닐 때)
    TODO 기타 등등 사용자에게 적절한 메시지를 알림
 */

public class HomeCareService extends Service {
    public HomeCareService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        HomeCareNotification.notifyNewMessage(this, "새로운 메시지가 도착했습니다!");


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
