package org.androidtown.homecare.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.androidtown.homecare.Firebase.FirebaseMessenger;
import org.androidtown.homecare.Models.Message;
import org.androidtown.homecare.R;

public class MessageActivity extends AppCompatActivity {

    private static RecyclerView messageRecyclerView;
    private Button messageSendButton, backButton;
    private EditText messageEditText;
    private FirebaseMessenger firebaseMessenger;
    private TextView nameText, statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        if(MainActivity.getHomeCareOfCurrentUser() == null || MainActivity.getUidOfCurrentUser() == null){
            Toast.makeText(this, "잠시 뒤에 시도해주세요.", Toast.LENGTH_SHORT).show();
            finish();
        }
        initView();
        initFirebase();
        initActionBar(); //상대방 이름, 활동 상태 표시

    }

    private void initActionBar() {
        String content = MainActivity.getOpponentUser().getName()+" 님과의 대화";
        nameText.setText(content);

        //현재 유저의 활동 상태에 따라 텍스트뷰에 표시
        FirebaseMessenger.getUserRef().child(MainActivity.getUidOfOpponentUser()).child("isOnline").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot== null) {
                    statusText.setText("활동 중이지 않음");
                    return;
                }
                if(dataSnapshot.getValue(Boolean.class)){
                    statusText.setText("현재 활동 중");
                } else {
                    statusText.setText("활동 중이지 않음");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // 유저 상태를 온라인으로 바꾸고 메시지를 "읽음"으로 표시
        FirebaseDatabase.getInstance().getReference().child("user").child( MainActivity.getUidOfCurrentUser()).child("isOnline").setValue(true);
        FirebaseDatabase.getInstance().getReference().child("user").child(MainActivity.getUidOfCurrentUser()).child("newMessages").setValue(0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //유저 상태를 오프라인으로 바꾸고 메시지를 "읽음"으로 표시
        FirebaseDatabase.getInstance().getReference().child("user").child( MainActivity.getUidOfCurrentUser()).child("isOnline").setValue(false);
        FirebaseDatabase.getInstance().getReference().child("user").child(MainActivity.getUidOfCurrentUser()).child("newMessages").setValue(0);
    }

    private void initFirebase() {
        firebaseMessenger = new FirebaseMessenger(MessageActivity.this, MainActivity.getUidOfOpponentUser(), MainActivity.getHomeCareOfCurrentUser().getKey(), true);
        firebaseMessenger.setRecyclerView(messageRecyclerView);
        firebaseMessenger.readMessages();
    }

    private void initView() {
        nameText = findViewById(R.id.name_text_view_in_message);
        statusText = findViewById(R.id.status_text_view_in_message);
        messageEditText = findViewById(R.id.message_edit_text);
        messageSendButton = findViewById(R.id.message_send_button);
        messageRecyclerView = findViewById(R.id.message_recycler_view);
        backButton = findViewById(R.id.back_button_in_activity_message);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        messageSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = messageEditText.getText().toString().trim();
                messageEditText.setText("");
                if(message.length()== 0 || MainActivity.getUidOfOpponentUser()==null) {
                    return;
                }

                if(firebaseMessenger == null || MainActivity.getHomeCareOfCurrentUser() == null){
                    Toast.makeText(MessageActivity.this, "잠시 뒤에 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                //보내기
                firebaseMessenger.sendMessage(MainActivity.getHomeCareOfCurrentUser().getKey(),new Message(MainActivity.getUidOfCurrentUser(), message));

            }
        });

    }
}
