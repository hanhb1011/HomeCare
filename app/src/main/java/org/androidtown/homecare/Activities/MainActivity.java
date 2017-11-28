package org.androidtown.homecare.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import org.androidtown.homecare.Adapters.ViewPagerAdapter;
import org.androidtown.homecare.Firebase.FirebaseAccount;
import org.androidtown.homecare.Firebase.FirebaseHomeCare;
import org.androidtown.homecare.Firebase.FirebasePicture;
import org.androidtown.homecare.Firebase.FirebaseProfile;
import org.androidtown.homecare.Fragments.FilterFragment;
import org.androidtown.homecare.Fragments.HomeCareCreationFragment;
import org.androidtown.homecare.Fragments.MessageDialogFragment;
import org.androidtown.homecare.Models.HomeCare;
import org.androidtown.homecare.Models.User;
import org.androidtown.homecare.R;
import org.androidtown.homecare.Services.HomeCareService;
import org.androidtown.homecare.Utils.BackButtonHandler;
import org.androidtown.homecare.Utils.PageChangeListener;

/*

    할 것들
    TODO 평가 완료됐을 때 현재 진행중인 홈케어 비우기
    TODO 테스팅 뒤 ->홈케어 기간이 끝났을 때 평가되게 변경
    TODO 채팅 상대방 프로필사진 공유 자원으로 변경
    TODO 가상화폐 관련 기능 추가

*/

public class MainActivity extends AppCompatActivity {

    //result & request codes
    public static final int RESULT_REFRESH = 1000;
    public static final int REQUEST_HOME_CARE_ACTIVITY = 1001;
    public static final int RESULT_REFRESH_IN_HOME_CARE_ACTIVITY = 1002;
    public static final int REQUEST_IN_HOME_CARE_ACTIVITY = 1003;
    public static final int REQUEST_GALLERY = 1004;

    private Button hiringButton, messageButton, myPageButton, addOrCheckHomeCareButton, filterButton ,logOutButton;
    private ViewPager mainViewPager;
    private BackButtonHandler backButtonHandler;
    private static ImageView profileImageView;
    private TextView profileNameText, titleText;
    private static LinearLayout progressBarLayout;
    private Button testButton;

    private static FirebaseAccount firebaseAccount;
    private static FirebaseHomeCare firebaseHomeCare;
    private static FirebaseProfile firebaseProfile;
    private static FirebasePicture firebasePicture;

    private static User currentUser, opponentUser;
    private static HomeCare homeCareOfCurrentUser;
    private static String uidOfCurrentUser, uidOfOpponentUser; //uidOfOpponentUser : 홈케어가 진행중일 때, 상대방의 uid

    private static Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        setContentView(R.layout.activity_main);

        initInstances(); //인스턴스 생성 및 초기화
        initButtons(); //삭제, 필터 버튼 초기화
        initAuth(); //파이어베이스 관련 객체 초기화
        initView(); //뷰 초기화
        getDataFromFirebase(); //파이어베이스로부터 유저 정보를 받고 ui를 업데이트한다.
        initService(); //서비스 초기화



    }

    private void initService() {
        serviceIntent = new Intent(this, HomeCareService.class);
        serviceIntent.putExtra("uid", uidOfCurrentUser);
        startService(serviceIntent);
    }

    private void getDataFromFirebase() {
        firebaseProfile.getCurrentUserAndHomecareInMainActivity(uidOfCurrentUser, profileNameText);
        firebasePicture.downloadImage(uidOfCurrentUser, profileImageView);
    }

    private void initAuth() {
        firebaseAccount = new FirebaseAccount(this);
        firebaseHomeCare = new FirebaseHomeCare(this);
        firebaseProfile = new FirebaseProfile(this);
        firebasePicture = new FirebasePicture(this);
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

        //로그아웃 버튼
        logOutButton = findViewById(R.id.logout_button);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageDialogFragment.setContext(MainActivity.this);
                MessageDialogFragment.showDialog(MessageDialogFragment.LOG_OUT, MainActivity.this);
            }
        });
    }

    private void initView() {
        //타이틀
        titleText = findViewById(R.id.title_text_view_in_main);


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
        mainViewPager.setOffscreenPageLimit(3);
        mainViewPager.setAdapter(new ViewPagerAdapter(this, getSupportFragmentManager()));
        PageChangeListener pageChangeListener = new PageChangeListener(hiringButton, messageButton,
                myPageButton, addOrCheckHomeCareButton, filterButton ,logOutButton, titleText);
        pageChangeListener.onPageSelected(0);
        mainViewPager.setOnPageChangeListener(pageChangeListener);
        mainViewPager.setCurrentItem(0);

        //프로필 관련
        profileImageView = findViewById(R.id.profile_image_view_in_main_activity);
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.getPaint().setColor(Color.TRANSPARENT);
        profileImageView.setBackground(shapeDrawable);
        profileImageView.setClipToOutline(true);
        profileImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        profileNameText = findViewById(R.id.name_text_view_in_main_activity);

        //프로그래스바 레이아웃
        progressBarLayout = findViewById(R.id.progress_bar_layout);
    }

    public void refresh(boolean setProgressBarLayoutVisible, @Nullable SwipeRefreshLayout swipeRefreshLayout){
        if(firebaseHomeCare != null && firebaseHomeCare.getHomeCareRecyclerView()!=null){
            if(setProgressBarLayoutVisible)
                progressBarLayout.setVisibility(View.VISIBLE);
            firebaseProfile.getCurrentUserAndHomecareInMainActivity(uidOfCurrentUser, profileNameText);
            firebaseHomeCare.refreshHomeCare(swipeRefreshLayout);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_REFRESH || resultCode == RESULT_REFRESH_IN_HOME_CARE_ACTIVITY){ //리프레쉬가 필요한 경우
            refresh(true, null);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        firebaseAccount.mAuth.addAuthStateListener(firebaseAccount.mAuthListener);

        // 유저 상태를 온라인으로 바꾸고 메시지를 "읽음"으로 표시
        FirebaseDatabase.getInstance().getReference().child("user").child(uidOfCurrentUser).child("isOnline").setValue(true);
        FirebaseDatabase.getInstance().getReference().child("user").child(MainActivity.getUidOfCurrentUser()).child("newMessages").setValue(0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAccount.mAuthListener != null) {
            firebaseAccount.mAuth.removeAuthStateListener(firebaseAccount.mAuthListener);
        }

        //유저 상태를 오프라인으로 바꾸고 메시지를 "읽음"으로 표시
        FirebaseDatabase.getInstance().getReference().child("user").child(uidOfCurrentUser).child("isOnline").setValue(false);
        FirebaseDatabase.getInstance().getReference().child("user").child(MainActivity.getUidOfCurrentUser()).child("newMessages").setValue(0);
    }

    @Override
    public void onBackPressed() {
        backButtonHandler.onBackPressed(); //두 번 눌렀을 때 종료되도록
    }


    //getters & setters
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

    public static String getUidOfOpponentUser() {
        return uidOfOpponentUser;
    }

    public static void setUidOfOpponentUser(String uidOfOpponentUser) {
        MainActivity.uidOfOpponentUser = uidOfOpponentUser;
    }

    public static FirebasePicture getFirebasePicture() {
        return firebasePicture;
    }

    public static FirebaseProfile getFirebaseProfile() {
        return firebaseProfile;
    }

    public static User getOpponentUser() {
        return opponentUser;
    }

    public static void setOpponentUser(User opponentUser) {
        MainActivity.opponentUser = opponentUser;
    }

    public static LinearLayout getProgressBarLayout() {
        return progressBarLayout;
    }

    public static ImageView getProfileImageView() {
        return profileImageView;
    }
}
