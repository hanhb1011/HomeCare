package org.androidtown.homecare.Utils;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by hanhb on 2017-11-09.
 */


/*
    BackButtonHandler
    뒤로가기를 두 번 눌러야 앱이 종료되게 함.
    Activity를 받아서 처리.
    사용하는 Activity의 onBackPressed를 오버라이딩하여 적용
 */

public class BackButtonHandler {

    private long backKeyPressedTime = 0;
    private Toast toast;
    private Activity activity;

    public BackButtonHandler(Activity activity) {
        this.activity = activity;
        toast = Toast.makeText(activity, "뒤로가기 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_LONG);
    }

    public void onBackPressed(){
        if(System.currentTimeMillis() > backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis();
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000){
            activity.finishAffinity();
            toast.cancel();
        }
    }

}
