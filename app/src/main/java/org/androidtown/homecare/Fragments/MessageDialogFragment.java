package org.androidtown.homecare.Fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.androidtown.homecare.R;

/**
 * Created by hanhb on 2017-11-09.
 */

/*
    <싱글톤 클래스>
    code에 따라 원하는 내용을 띄우는 다이얼로그 프래그먼트

    (static method) showDialog(int code, Context context)
    호출 시 다이얼로그 띄움

 */
public class MessageDialogFragment extends DialogFragment {

    //다이얼로그 타입
    public final static int INVALID_EMAIL_OR_PASSWORD = 0;
    public final static int SIGN_UP_FAILED = 1;
    public final static int SIGN_IN_FAILED = 2;
    public final static int GOOGLE_PLAY_SERVICE_NOT_FOUND = 3;
    public final static int SIGN_UP_SUCCESS = 4;

    private static int code; //띄울 다이얼로그 타입 구분

    //뷰
    Button leftButton, rightButton;
    TextView titleText, contentText;

    //싱글톤
    static MessageDialogFragment md;

    private MessageDialogFragment(){}

    public static void showDialog(int code, Context context){
        MessageDialogFragment.code = code;
        md = new MessageDialogFragment();
        md.show(((Activity)context).getFragmentManager(), "");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messagedialog, container);

        initView(view); //뷰 인스턴스 연결

        switch (code){

            case INVALID_EMAIL_OR_PASSWORD :
                titleText.setText("잘못된 입력");
                contentText.setText("이메일 형식으로 입력해 주세요.\n패스워드는 6자 이상입니다.");
                break;
            case SIGN_UP_FAILED :
                titleText.setText("회원가입 실패");
                contentText.setText("사용 중인 이메일입니다.");
                break;
            case SIGN_IN_FAILED :
                titleText.setText("로그인 실패");
                contentText.setText("아이디가 존재하지 않거나,\n잘못된 패스워드입니다.");
                break;
            case GOOGLE_PLAY_SERVICE_NOT_FOUND :
                titleText.setText("Error");
                contentText.setText("구글 플레이 서비스를 설치해주십시오.");
                break;
            case SIGN_UP_SUCCESS :
                titleText.setText("회원가입");
                contentText.setText("회원가입에 성공하였습니다!");
                break;
            default:
                titleText.setText("에러");
                break;
        }

        return view;
    }

    private void initView(View view) {
        leftButton = view.findViewById(R.id.left_button_message_dialog);
        rightButton = view.findViewById(R.id.right_button_message_dialog);
        titleText = view.findViewById(R.id.title_text_in_message_dialog);
        contentText = view.findViewById(R.id.content_text_in_message_dialog);

        //default initialize
        leftButton.setText("확인");
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}