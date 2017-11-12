package org.androidtown.homecare.Firebase;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.androidtown.homecare.Models.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanhb on 2017-11-12.
 */

public class FirebaseMessenger {

    /*
        FirebaseMessenger : 메시지 보내는 기능

        /chat/messages/ 에 메시지 저장

        writeChat() : CREATE Chat 홈케어가 진행될 때 채팅 생성
        destroyChat() : DESTROY Chat 홈케어가 중단될 때 채팅 제거

        sendMessage() : CREATE Message
        refreshMessage() : READ Message
     */

    private final static List<Message> messageList = new ArrayList<>();
    private final static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final static DatabaseReference chatRef = database.getReference().child("chat");

    private Context context;



    public FirebaseMessenger(Context context) {
        this.context = context;
    }

    public static void writeChat(String key){
        //홈케어와 같은 key로 채팅을 생성한다.



    }

    public static void destroyChat(String key){
        // key에 해당되는 채팅을 제거한다.


    }

    public void sendMessage(String uid, Message message){




    }



}
