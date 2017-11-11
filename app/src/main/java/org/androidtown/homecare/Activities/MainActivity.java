package org.androidtown.homecare.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidtown.homecare.Adapters.ViewPagerAdapter;
import org.androidtown.homecare.Firebase.FirebaseAccount;
import org.androidtown.homecare.Firebase.FirebaseHomeCare;
import org.androidtown.homecare.Firebase.FirebaseProfile;
import org.androidtown.homecare.Fragments.FilterFragment;
import org.androidtown.homecare.Fragments.HomeCareCreationFragment;
import org.androidtown.homecare.Models.HomeCare;
import org.androidtown.homecare.Models.User;
import org.androidtown.homecare.R;
import org.androidtown.homecare.Utils.BackButtonHandler;


public class MainActivity extends AppCompatActivity {

    //result & request codes
    public static final int RESULT_REFRESH = 1000;
    public static final int REQUEST_HOME_CARE_ACTIVITY = 1001;
    public static final int RESULT_REFRESH_IN_HOME_CARE_ACTIVITY = 1002;
    public static final int REQUEST_IN_HOME_CARE_ACTIVITY = 1003;

    private Button hiringButton, messageButton, myPageButton, addOrCheckHomeCareButton, filterButton;
    private ViewPager mainViewPager;
    private BackButtonHandler backButtonHandler;
    private ImageView profileImageView;
    private TextView profileNameText;

    private FirebaseAccount firebaseAccount;
    private FirebaseHomeCare firebaseHomeCare;
    private FirebaseProfile firebaseProfile;


    private static User currentUser;
    private static HomeCare homeCareOfCurrentUser;
    private static Bitmap profileImageOfCurrentUser;
    private static String uidOfCurrentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        setContentView(R.layout.activity_main);

        initInstances(); //인스턴스 생성 및 초기화
        initAuth(); //파이어베이스 관련 객체 초기화
        initView(); //뷰 초기화
        initButtons(); //삭제, 필터 버튼 초기화
        getDataFromFirebase(); //파이어베이스로부터 유저 정보를 받고 ui를 업데이트한다.

        //TODO : 유저의 상태에 따라 적절한 메시지 띄움. (지원자가 있다던지, 새 메시지가 있다던지 등등)

    }

    private void getDataFromFirebase() {
        //TODO currenUser에 정보를 불러옴
        firebaseProfile.getCurrentUserAndHomecareInMainActivity(uidOfCurrentUser, profileNameText);
    }

    private void initAuth() {
        firebaseAccount = new FirebaseAccount(this);
        firebaseHomeCare = new FirebaseHomeCare(this);
        firebaseProfile = new FirebaseProfile(this);
    }

    private void initInstances() {
        uidOfCurrentUser = getIntent().getStringExtra("uid");
        backButtonHandler = new BackButtonHandler(this);
    }

    private void initButtons() {
        //카드 추가, 또는 체크 버튼
        addOrCheckHomeCareButton = findViewById(R.id.home_care_add_or_check_button);
        addOrCheckHomeCareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeCareCreationFragment homeCareCreationFragment = new HomeCareCreationFragment();
                homeCareCreationFragment.setCancelable(false);
                homeCareCreationFragment.show(getFragmentManager(), "");
            }
        });

        //필터링 버튼
        filterButton = findViewById(R.id.filter_button);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilterFragment filterFragment = new FilterFragment();
                filterFragment.setCancelable(false);
                filterFragment.show(getFragmentManager(), "");
            }
        });
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
        mainViewPager.setAdapter(new ViewPagerAdapter(this, getSupportFragmentManager()));
        mainViewPager.setCurrentItem(0);

        //프로필 관련
        profileImageView = findViewById(R.id.profile_image_view_in_main_activity);
        profileImageView.setBackground(new ShapeDrawable(new OvalShape()));
        profileImageView.setClipToOutline(true);
        profileImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        profileNameText = findViewById(R.id.name_text_view_in_main_activity);
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


    public FirebaseAccount getFirebaseAccount() {
        return firebaseAccount;
    }

    public FirebaseHomeCare getFirebaseHomeCare() {
        return firebaseHomeCare;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        MainActivity.currentUser = currentUser;
    }

    public static String getUidOfCurrentUser() {
        return uidOfCurrentUser;
    }

    public Button getAddOrCheckHomeCareButton() {
        return addOrCheckHomeCareButton;
    }

    public Button getFilterButton() {
        return filterButton;
    }

    public static HomeCare getHomeCareOfCurrentUser() {
        return homeCareOfCurrentUser;
    }

    public static void setHomeCareOfCurrentUser(HomeCare homeCareOfCurrentUser) {
        MainActivity.homeCareOfCurrentUser = homeCareOfCurrentUser;
    }
}
