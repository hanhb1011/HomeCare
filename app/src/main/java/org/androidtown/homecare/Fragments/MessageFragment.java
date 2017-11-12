package org.androidtown.homecare.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.androidtown.homecare.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {

    //TODO 뷰 연결, 메시지 테스트

    private RecyclerView messageRecyclerView;
    private Button messageSendButton;
    private EditText messageEditText;



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

        messageSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = messageEditText.getText().toString().trim();
                messageEditText.setText("");
                if(message.length()==0)
                    return;

                //TODO 메시지 보내기


            }
        });

        return view;
    }

}
