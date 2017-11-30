package org.androidtown.homecare.Firebase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.androidtown.homecare.Adapters.MessageAdapter;
import org.androidtown.homecare.Models.Chat;
import org.androidtown.homecare.Models.Message;
import org.androidtown.homecare.Utils.MyLinearLayoutManager;

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
        readMessages() : READ Message
     */

    private final static List<Message> messageList = new ArrayList<>();
    private final static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final static DatabaseReference chatRef = database.getReference().child("chat");
    private final static DatabaseReference userRef = database.getReference().child("user");
    private RecyclerView recyclerView;

    private String key;
    private String oUid;
    private Context context;
    private Bitmap bitmap;
    private MessageValueEventListenter messageValueEventListenter;
    private MyLinearLayoutManager myLinearLayoutManager;



    public FirebaseMessenger(Context context, String oUid, final String key, boolean getImage) {
        this.context = context;
        this.oUid = oUid;
        this.key = key;

        messageValueEventListenter = new MessageValueEventListenter();

        if(getImage) {
            final long ONE_MEGABYTE = 1024 * 1024;
            FirebasePicture.storageRef.child(oUid).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    bitmap = BitmapFactory.decodeByteArray(bytes , 0, bytes.length);
                    chatRef.child(key).child("message").removeEventListener(messageValueEventListenter);
                    readMessages();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });


        }
    }

    /* Message 관련 */
    public void readMessages(){
        if(recyclerView == null || key == null || oUid == null) {
            Toast.makeText(context, "비정상적인 접근입니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        chatRef.child(key).child("message").addValueEventListener(messageValueEventListenter);

    }

    public void sendMessage(String key, Message message){
        chatRef.child(key).child("message").push().setValue(message);

        userRef.child(oUid).child("newMessages").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer newMessages = dataSnapshot.getValue(Integer.class) + 1;
                userRef.child(oUid).child("newMessages").setValue(newMessages);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
    class MessageValueEventListenter implements ValueEventListener {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            messageList.clear();

            for(DataSnapshot ds : dataSnapshot.getChildren()){
                messageList.add(ds.getValue(Message.class));
            }

            userRef.child(oUid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    MessageAdapter messageAdapter = new MessageAdapter(context, messageList, oUid, dataSnapshot.child("name").getValue(String.class));
                    if(bitmap!=null)
                        messageAdapter.setBitmap(bitmap);

                    if(myLinearLayoutManager== null) {
                        myLinearLayoutManager = new MyLinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(myLinearLayoutManager);
                    }
                    recyclerView.setAdapter(messageAdapter);
                    myLinearLayoutManager.scrollToPosition(messageList.size()-1);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }


    /* Chat 관련 */
    public static void writeChat(Chat chat){
        //홈케어와 같은 key로 채팅을 생성한다.
        chatRef.child(chat.getKey()).setValue(chat);
    }

    public static void destroyChat(String key){
        // key에 해당되는 채팅을 제거한다.
        chatRef.child(key).removeValue();

    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public static DatabaseReference getChatRef() {
        return chatRef;
    }

    public static DatabaseReference getUserRef() {
        return userRef;
    }
}
