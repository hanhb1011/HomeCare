package org.androidtown.homecare.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.androidtown.homecare.Firebase.FirebaseMessenger;
import org.androidtown.homecare.Models.Message;
import org.androidtown.homecare.R;

public class MessageActivity extends AppCompatActivity {

    private static RecyclerView messageRecyclerView;
    private Button messageSendButton, backButton;
    private EditText messageEditText;
    private FirebaseMessenger firebaseMessenger;

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

    }

    private void initFirebase() {
        firebaseMessenger = new FirebaseMessenger(MessageActivity.this, MainActivity.getUidOfOpponentUser());
        firebaseMessenger.setRecyclerView(messageRecyclerView);
        firebaseMessenger.readMessages(MainActivity.getHomeCareOfCurrentUser().getKey());
    }

    private void initView() {
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
