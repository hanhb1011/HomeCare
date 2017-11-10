package org.androidtown.homecare.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import org.androidtown.homecare.Adapters.ViewPagerAdapter;
import org.androidtown.homecare.Firebase.FirebaseAccount;
import org.androidtown.homecare.Firebase.FirebaseHomeCare;
import org.androidtown.homecare.Models.User;
import org.androidtown.homecare.R;
import org.androidtown.homecare.Utils.BackButtonHandler;


public class MainActivity extends AppCompatActivity {

    //result Codes
    public static final int RESULT_REFRESH = 1000;
    public static final int REQUEST_HOME_CARE_ACTIVITY = 1001;
    public static final int RESULT_REFRESH_IN_HOME_CARE_ACTIVITY = 1002;
    public static final int REQUEST_IN_HOME_CARE_ACTIVITY = 1003;


    Button hiringButton, messageButton, myPageButton;
    ViewPager mainViewPager;
    BackButtonHandler backButtonHandler;

    public FirebaseAccount firebaseAccount;
    public FirebaseHomeCare firebaseHomeCare;


    public static User user;
    public static String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        setContentView(R.layout.activity_main);

        initInstances(); //인스턴스 생성 및 초기화
        initAuth(); //파이어베이스 관련 객체 초기화
        initView(); //뷰 초기화
        getDataFromFirebase(); //파이어베이스로부터 유저 정보와 구인 정보를 받는다.

        //TODO : 유저의 상태에 따라 적절한 메시지 띄움. (지원자가 있다던지, 새 메시지가 있다던지 등등)

    }

    private void getDataFromFirebase() {



    }

    private void initAuth() {
        firebaseAccount = new FirebaseAccount(this);
        firebaseHomeCare = new FirebaseHomeCare(this);
    }

    private void initInstances() {
        uid = getIntent().getStringExtra("uid");
        backButtonHandler = new BackButtonHandler(this);
    }



    private void initView() {

        //버튼
        hiringButton = findViewById(R.id.hiring_fragment_button);
        hiringButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {mainViewPager.setCurrentItem(0);
            }
        });
        messageButton = findViewById(R.id.message_fragment_button);
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {mainViewPager.setCurrentItem(1);
            }
        });
        myPageButton = findViewById(R.id.my_page_fragment_button);
        myPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {mainViewPager.setCurrentItem(2) ;
            }
        });

        //뷰페이저
        mainViewPager = findViewById(R.id.main_view_pager);
        mainViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        mainViewPager.setCurrentItem(0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_REFRESH){ //리프레쉬가 필요한 경우
            if(firebaseHomeCare != null && firebaseHomeCare.getHomeCareRecyclerView()!=null){

                firebaseHomeCare.refreshHomeCare();

            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        firebaseAccount.mAuth.addAuthStateListener(firebaseAccount.mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAccount.mAuthListener != null) {
            firebaseAccount.mAuth.removeAuthStateListener(firebaseAccount.mAuthListener);
        }
    }

    @Override
    public void onBackPressed() {
        backButtonHandler.onBackPressed(); //두 번 눌렀을 때 종료되도록
    }

}
