package org.androidtown.homecare.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.androidtown.homecare.Activities.CandidateListActivity;
import org.androidtown.homecare.Activities.HomeCareActivity;
import org.androidtown.homecare.Activities.MainActivity;
import org.androidtown.homecare.Activities.SigninActivity;
import org.androidtown.homecare.Models.Estimation;
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
    public final static int CANCEL_ASKING = 5;
    public final static int PAY_INVALID = 6;
    public final static int COMMENT_INVALID = 7;
    public final static int DATE_INVALID = 8;
    public final static int TITLE_INVALID = 9;
    public final static int HOME_CARE_ALREADY_EXISTS = 10;
    public final static int HOMECARE_CREATION_SUCCESS = 11;
    public final static int CANDIDATE_PICK = 12;
    public final static int CANDIDATE_PICK_SUCCESS = 13;
    public final static int HOMECARE_DELETION = 14;
    public final static int DELETION_WAITING = 15;
    public final static int DELETION_CHECK = 16;
    public final static int ESTIMATION_SUCCESS = 17;
    public final static int HOMECARE_CANCELLATION = 18;
    public final static int LOG_OUT = 19;
    public final static int SIGN_UP_INVALID = 20;
    public final static int HOMECARE_DELETION_IN_MAIN = 21;

    private static int code; //띄울 다이얼로그 타입 구분

    //뷰
    Button leftButton, rightButton;
    TextView titleText, contentText;

    //싱글톤
    static MessageDialogFragment md;

    private static HomeCareCreationFragment homeCareCreationFragment;
    private static Context context;
    private static String key, uid; //home care의 key와, candidate의 uid
    private static String contentOfDialog;
    private static Estimation estimation;

    @SuppressLint("ValidFragment")
    private MessageDialogFragment(){}

    public static void showDialog(int code, Context context){
        MessageDialogFragment.code = code;
        md = new MessageDialogFragment();
        md.setCancelable(false);
        md.show(((Activity)context).getFragmentManager(), "");
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
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
                rightButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((Activity)context).finish();
                        dismiss();
                    }
                });
                break;
            case CANCEL_ASKING :
                titleText.setText("알림");
                contentText.setText("홈케어 등록을 취소하시겠습니까?");
                leftButton.setText("네");
                leftButton.setVisibility(View.VISIBLE);
                leftButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        homeCareCreationFragment.dismiss();
                        dismiss();
                    }
                });
                rightButton.setText("아니오");

                break;
            case PAY_INVALID :
                titleText.setText("등록 실패!");
                contentText.setText("급여를 입력해 주십시오.");
                break;
            case COMMENT_INVALID :
                titleText.setText("등록 실패!");
                contentText.setText("상세내용을 입력해 주십시오.");
                break;
            case DATE_INVALID :
                titleText.setText("등록 실패!");
                contentText.setText("정확한 날짜를 입력해 주십시오.");
                break;
            case TITLE_INVALID :
                titleText.setText("등록 실패!");
                contentText.setText("제목을 입력해 주십시오.");
                break;
            case HOME_CARE_ALREADY_EXISTS :
                titleText.setText("등록 실패!");
                contentText.setText("이미 등록된 홈케어가 존재합니다.");
                break;
            case HOMECARE_CREATION_SUCCESS :
                titleText.setText("완료");
                contentText.setText("홈케어 등록에 성공했습니다!");
                rightButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        homeCareCreationFragment.dismiss();
                        dismiss();
                    }
                });
                break;
            case CANDIDATE_PICK :
                titleText.setText("알림");
                contentText.setText("홈케어 요청을 수락하시겠습니까?");
                leftButton.setText("네");
                leftButton.setVisibility(View.VISIBLE);
                leftButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //key와 uidOfCandidate를 받은 상태.
                        ((CandidateListActivity)context).getFirebaseHomeCare().pickCandidate(key, uid);
                        dismiss();
                    }
                });
                rightButton.setText("아니오");
                break;
            case CANDIDATE_PICK_SUCCESS :
                titleText.setText("등록 성공!");
                contentText.setText("수락되었습니다.\n메시지 탭에서 메시지를 보내보세요!");
                rightButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((Activity)context).setResult(MainActivity.RESULT_REFRESH_IN_HOME_CARE_ACTIVITY);
                        ((Activity) context).finish();
                        dismiss();
                    }
                });
                break;
            case HOMECARE_DELETION :
                titleText.setText("홈케어 삭제");
                contentText.setText("삭제하시겠습니까?\n\n홈 케어가 이미 진행중이라면 \n상대방도 수락해야 삭제됩니다.\n\n하지만 진행중인 홈케어가 아닐 경우\n바로 삭제됩니다.");
                leftButton.setText("네");
                leftButton.setVisibility(View.VISIBLE);
                leftButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //key와 uid를 받은 상태.
                        ((HomeCareActivity)context).getFirebaseHomeCare().destroyHomeCare(key, uid, false);
                        dismiss();
                    }
                });
                rightButton.setText("아니오");

                break;
            case DELETION_WAITING :
                titleText.setText("알림");
                contentText.setText("상대방에게 삭제 요청을 보냈습니다.");
                break;
            case DELETION_CHECK :
                titleText.setText("홈케어 중단 요청");
                contentText.setText("상대방이 홈케어 중단 요청을 하셨습니다.\n수락하시겠습니까?");
                leftButton.setText("네");
                leftButton.setVisibility(View.VISIBLE);
                leftButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //key와 uid를 받은 상태.
                        ((MainActivity)context).getFirebaseHomeCare().destroyHomeCare(key, uid, false);
                        dismiss();
                    }
                });
                rightButton.setText("보류");
                rightButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "다음 로그인 시 다시 알립니다.", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                });

                break;
            case ESTIMATION_SUCCESS :
                titleText.setText("평가 완료");
                String content = "평균 평점 ★" + String.format("%.2f",(estimation.getWellness()+estimation.getKindness()+estimation.getFaithfulness())/3)
                        + "\n평가가 완료되었습니다!\n상대방의 평점에 반영됩니다.";
                contentText.setText(content);
                rightButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((Activity)context).setResult(MainActivity.RESULT_REFRESH_IN_HOME_CARE_ACTIVITY);
                        ((Activity) context).finish();
                        dismiss();
                    }
                });
                break;
            case HOMECARE_CANCELLATION :
                titleText.setText("홈케어 중단");
                contentText.setText("상대방에게 홈케어 중단 요청을 하시겠습니까?\n\n상대방이 요청을 수락해야 중단됩니다.");
                leftButton.setText("네");
                leftButton.setVisibility(View.VISIBLE);
                leftButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //key와 uid를 받은 상태.
                        ((MainActivity)context).getFirebaseHomeCare().destroyHomeCare(key, uid, false);
                        dismiss();
                    }
                });
                rightButton.setText("아니오");

                break;
            case LOG_OUT :
                titleText.setText("로그아웃");
                contentText.setText("로그아웃 하시겠습니까?");
                leftButton.setText("네");
                leftButton.setVisibility(View.VISIBLE);
                leftButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(context, SigninActivity.class));
                        FirebaseAuth.getInstance().signOut();
                        ((Activity)context).finish();
                    }
                });
                rightButton.setText("아니오");
                break;
            case SIGN_UP_INVALID :
                titleText.setText("회원가입 실패");
                contentText.setText(contentOfDialog);
                break;
            case HOMECARE_DELETION_IN_MAIN :
                titleText.setText("홈케어 삭제");
                contentText.setText("삭제하시겠습니까?\n홈케어가 진행중이 아니기 때문에\n바로 삭제됩니다.");
                leftButton.setText("네");
                leftButton.setVisibility(View.VISIBLE);
                leftButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //key와 uid를 받은 상태.
                        ((MainActivity)context).getFirebaseHomeCare().destroyHomeCare(key, uid, true);
                        dismiss();
                    }
                });
                rightButton.setText("아니오");
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
        rightButton.setText("확인");
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public static void setHomeCareCreationFragment(HomeCareCreationFragment fragment) {
        homeCareCreationFragment = fragment;
    }

    public static void setContext(Context context) {
        MessageDialogFragment.context = context;
    }

    public static void setKeyAndUid(String key, String uid){

        MessageDialogFragment.key = key;
        MessageDialogFragment.uid = uid;

    }

    public static void setEstimation(Estimation estimation) {
        MessageDialogFragment.estimation = estimation;
    }

    public static void setContentOfDialog(String contentOfDialog) {
        MessageDialogFragment.contentOfDialog = contentOfDialog;
    }
}
