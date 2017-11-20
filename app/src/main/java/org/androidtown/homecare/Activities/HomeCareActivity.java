package org.androidtown.homecare.Activities;

import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.androidtown.homecare.Firebase.FirebaseHomeCare;
import org.androidtown.homecare.Firebase.FirebasePicture;
import org.androidtown.homecare.Fragments.MessageDialogFragment;
import org.androidtown.homecare.Models.HomeCare;
import org.androidtown.homecare.Models.User;
import org.androidtown.homecare.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;


//게시글을 자세히 보여주는 액티비티
public class HomeCareActivity extends AppCompatActivity {

    private ImageView profileImageView;
    private TextView titleText, dateText, payText, periodText, careTypeText, locationText, commentText
            , nameText, starText;
    private HomeCare homeCare;
    private Button contactButton, editButton, deleteButton;
    private FirebaseHomeCare firebaseHomeCare;
    private FirebasePicture firebasePicture;
    private String key;
    private User user;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_care);

        key = getIntent().getStringExtra("key");
        initView();
        initViewFromHomeCareInstance();
        getDataAndUpdateUI(); //서버로부터 데이터를 받아서 뷰 처리.

    }

    private void getDataAndUpdateUI() {

        //자신이 작성한 글일 경우 수정 삭제 버튼 띄움.
        if(MainActivity.getUidOfCurrentUser().equals(homeCare.getUid())){
            //editButton.setVisibility(View.VISIBLE); //TODO 메시지 구현 후 구현 예정.
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MessageDialogFragment.setContext(HomeCareActivity.this);
                    MessageDialogFragment.setKeyAndUid(key, MainActivity.getUidOfCurrentUser());
                    MessageDialogFragment.showDialog(MessageDialogFragment.HOMECARE_DELETION, HomeCareActivity.this);
                }
            });

            contactButton.setText("신청자 보기");
            contactButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HomeCareActivity.this, CandidateListActivity.class);
                    intent.putExtra("key", key);
                    startActivityForResult(intent, MainActivity.REQUEST_IN_HOME_CARE_ACTIVITY);
                }
            });
        } else {
            //자신이 작성하지 않았으면, 신청 상태에 따라 "신청취소" 또는 "신청하기"로 텍스트 변경
            firebaseHomeCare.initTextOfRequestButton(key, MainActivity.getUidOfCurrentUser(), contactButton);
            contactButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    firebaseHomeCare.requestHomeCare(key, MainActivity.getUidOfCurrentUser(), contactButton);
                }
            });

        }



    }

    private void initViewFromHomeCareInstance() {
        firebaseHomeCare = new FirebaseHomeCare(this);
        firebasePicture = new FirebasePicture(this);
        homeCare = firebaseHomeCare.searchHomeCare(key); //메인에서 서버로부터 받은 홈케어 리스트에서 해당 key에 맞는 홈케어를 탐색.
        user = firebaseHomeCare.searchUser(key); //작성자 정보를 불러온다.

        if(homeCare == null || user == null){
            Toast.makeText(this, "존재하지 않는 홈케어입니다.", Toast.LENGTH_SHORT).show();
            finish();
        }

        //사진을 띄움
        firebasePicture.downloadImage(homeCare.getUid(), profileImageView);

        //마감 상태에 따라 버튼과 타이틀의 상태를 바꿈
        if(homeCare.getUidOfCareTaker() !=null){
            //마감된 경우 타이틀 변경
            titleText.setTextColor(getResources().getColor(R.color.colorAccent));
            titleText.setText("( 마감된 홈케어입니다. )");

        } else {
            //마감되지 않은 경우 버튼 신청 버튼 띄움
            contactButton.setVisibility(View.VISIBLE);
            titleText.setText(homeCare.getTitle());

        }

        //시간 관련 텍스트뷰 (Period, Date)
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat fmt2 = new SimpleDateFormat("MM/dd");
        SimpleDateFormat fmt3 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(homeCare.getStartPeriod());
        periodText.setText(fmt.format(cal.getTime()));
        cal.setTimeInMillis(homeCare.getEndPeriod());
        periodText.append(" - "+ fmt2.format(cal.getTime()));
        cal.setTimeInMillis((long)homeCare.getTimestamp());
        dateText.setText(fmt3.format(cal.getTime()));

        //나머지
        payText.setText(String.valueOf(homeCare.getPay()));
        careTypeText.setText(homeCare.getCareType());
        locationText.setText(homeCare.getLocation());
        commentText.setText(homeCare.getComment());
        //유저 정보를 띄움
        starText.setText("★ " + String.format("%.2f",user.getStar()) + " (" + user.getHomecareCount() + ")");
        nameText.setText(user.getName());


    }


    private void initView() {

        profileImageView = findViewById(R.id.profile_image_view_in_activity_home_care);
        profileImageView.setBackground(new ShapeDrawable(new OvalShape()));
        profileImageView.setClipToOutline(true);
        profileImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        titleText = findViewById(R.id.title_text_view_in_activity_home_care);
        dateText = findViewById(R.id.upload_date_text_view_in_activity_home_care);
        payText = findViewById(R.id.home_care_pay_text_view_in_activity_home_care);
        periodText = findViewById(R.id.home_care_period_text_view_in_activity_home_care);
        careTypeText = findViewById(R.id.home_care_care_type_text_view_in_activity_home_care);
        locationText = findViewById(R.id.home_care_location_text_view_in_activity_home_care);
        commentText = findViewById(R.id.comment_text_view_in_activity_home_care);
        nameText = findViewById(R.id.name_text_view_in_activity_home_care);
        starText = findViewById(R.id.star_text_view_in_activity_home_care);

        contactButton = findViewById(R.id.contact_button_in_activity_home_care);
        editButton = findViewById(R.id.edit_button_in_activity_home_care);
        deleteButton = findViewById(R.id.delete_button_in_activity_home_care);

        findViewById(R.id.back_button_in_activity_home_care).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == MainActivity.RESULT_REFRESH_IN_HOME_CARE_ACTIVITY){
            titleText.setTextColor(getResources().getColor(R.color.colorAccent));
            titleText.setText("( 마감된 홈케어입니다. )");
            setResult(MainActivity.RESULT_REFRESH);
            contactButton.setVisibility(View.GONE);
        }

    }

    public FirebaseHomeCare getFirebaseHomeCare() {
        return firebaseHomeCare;
    }
}
