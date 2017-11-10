package org.androidtown.homecare.Activities;

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
import org.androidtown.homecare.Models.HomeCare;
import org.androidtown.homecare.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;


//게시글을 자세히 보여주는 액티비티
public class HomeCareActivity extends AppCompatActivity {

    ImageView profileImageView;
    TextView titleText, dateText, payText, periodText, careTypeText, locationText, commentText;
    HomeCare homeCare;
    Button editButton, deleteButton;
    FirebaseHomeCare firebaseHomeCare;
    String key;
    String uid;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_care);

        key = getIntent().getStringExtra("key");
        uid = getIntent().getStringExtra("uid");
        initView();
        getData();

    }

    private void getData() {
        firebaseHomeCare = new FirebaseHomeCare(this);
        homeCare = firebaseHomeCare.searchHomeCare(key);

        if(homeCare == null){
            Toast.makeText(this, "존재하지 않는 홈케어입니다.", Toast.LENGTH_SHORT).show();
            finish();
        }

        //자신이 작성한 글일 경우
        if(uid.equals(homeCare.getUid())){
            editButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
        }

        titleText.setText(homeCare.getTitle());

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

        payText.setText(String.valueOf(homeCare.getPay()));
        careTypeText.setText(homeCare.getCareType());
        locationText.setText(homeCare.getLocation());
        commentText.setText(homeCare.getComment());
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

        editButton = findViewById(R.id.edit_button_in_activity_home_care);
        deleteButton = findViewById(R.id.delete_button_in_activity_home_care);

        findViewById(R.id.back_button_in_activity_home_care).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

}
