package org.androidtown.homecare.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.androidtown.homecare.Activities.MainActivity;
import org.androidtown.homecare.Models.Message;
import org.androidtown.homecare.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {

    //TODO 뷰 연결, 메시지 테스트

    private static RecyclerView messageRecyclerView;
    private Button messageSendButton;
    private EditText messageEditText;
    private static LinearLayout hiddenLayout, noneCareLayout; //진행중인 홈케어의 존재 여부에 따라 레이아웃을 띄운다.



    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        messageEditText = view.findViewById(R.id.message_edit_text);
        messageSendButton = view.findViewById(R.id.message_send_button);
        messageRecyclerView = view.findViewById(R.id.message_recycler_view);
        hiddenLayout = view.findViewById(R.id.hidden_view_in_message_fragment);
        noneCareLayout = view.findViewById(R.id.none_care_view_in_message_fragment);
        messageSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = messageEditText.getText().toString().trim();
                messageEditText.setText("");
                if(message.length()== 0 || MainActivity.getUidOfOpponentUser()==null) {
                    return;
                }

                if(MainActivity.getFirebaseMessenger() == null || MainActivity.getHomeCareOfCurrentUser() == null){
                    Toast.makeText(getActivity(), "잠시 뒤에 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                //보내기
                MainActivity.getFirebaseMessenger().sendMessage(MainActivity.getHomeCareOfCurrentUser().getKey(),new Message(MainActivity.getUidOfCurrentUser(), message));

            }
        });

        return view;
    }

    public static LinearLayout getHiddenLayout() {
        return hiddenLayout;
    }

    public static LinearLayout getNoneCareLayout() {
        return noneCareLayout;
    }

    public static RecyclerView getMessageRecyclerView() {
        return messageRecyclerView;
    }
}
