package org.androidtown.homecare.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.androidtown.homecare.Utils.HomeCareNotification;

/*

    홈케어 서비스(구현 예정)
    1. 메시지 수신 알림
    TODO 기타 등등 사용자에게 적절한 메시지를 알림
 */

public class HomeCareService extends Service {

    private String uid;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userRef = database.getReference().child("user");
    private int previousNumOfMessages = 0;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class BackGroundThread extends Thread{

        @Override
        public void run() {
            super.run();

            while (true){
                try {
                    Thread.sleep(1000); //1초마다 진행

                    userRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //애플리케이션을 사용중일 때만
                            if(!dataSnapshot.child("isOnline").getValue(Boolean.class)){

                                final Integer newMessages = dataSnapshot.child("newMessages").getValue(Integer.class);
                                if(newMessages > 0 && previousNumOfMessages!=newMessages){
                                    previousNumOfMessages = newMessages; //계속해서 띄우는 현상을 방지
                                    HomeCareNotification.notifyNewMessage(HomeCareService.this, "새로운 메시지가 "+ newMessages + "건 도착했습니다!");
                                }

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent!=null)
            uid = intent.getStringExtra("uid");

        if(uid!=null) {

            

            BackGroundThread thread = new BackGroundThread();
            thread.start();

        }

        return super.onStartCommand(intent, flags, startId);
    }
}
