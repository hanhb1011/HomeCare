package org.androidtown.homecare.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.androidtown.homecare.Firebase.FirebaseAccount;
import org.androidtown.homecare.Fragments.MessageDialogFragment;
import org.androidtown.homecare.Models.User;
import org.androidtown.homecare.R;
import org.androidtown.homecare.Utils.BackButtonHandler;
import org.androidtown.homecare.Utils.SharedPreferenceHelper;


public class SigninActivity extends AppCompatActivity {

    private FirebaseAccount firebaseAccount;
    private EditText emailEdit, passwordEdit;
    private BackButtonHandler backButtonHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        initInstances(); //인스턴스 생성 및 초기화
        initView(); //뷰 초기화
        checkGooglePlayService(this); //구글 플레이 서비스가 존재하지 않을 경우 다이얼로그 띄우기
        /* temp */
        findViewById(R.id.data_generate_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SigninActivity.this, DataGeneratorActivity.class));
            }
        });
        /* temp 끝 */
    }

    private void initInstances() {
        firebaseAccount = new FirebaseAccount(this);
        backButtonHandler = new BackButtonHandler(this);
    }


    private void initView() {
        emailEdit = findViewById(R.id.email_text_view_in_activity_signin);
        passwordEdit = findViewById(R.id.password_text_view_in_activity_signin);

        //로그인 기록이 있으면 이메일, 패스워드 자동 기입
        emailEdit.setText(SharedPreferenceHelper.getString(this, "email"));
        passwordEdit.setText(SharedPreferenceHelper.getString(this, "password"));

        //로그인 버튼
        findViewById(R.id.signin_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String email = emailEdit.getText().toString().trim();
            String password = passwordEdit.getText().toString();
            firebaseAccount.attemptSignin(email, password);
            }
        });

        //회원가입 버튼
        findViewById(R.id.signup_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEdit.getText().toString().trim();
                String password = passwordEdit.getText().toString();
                //TODO User 정보 입력해야함
                firebaseAccount.attemptSignup(email, password, new User());
            }
        });

    }

    //구글 플레이 서비스가 존재하지 않거나, 낮은 버전일 경우 설치 페이지 띄움
    static public void checkGooglePlayService(Activity activity) {
        Integer resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if (resultCode == ConnectionResult.SUCCESS) {
            return;
        }
        MessageDialogFragment.showDialog(MessageDialogFragment.GOOGLE_PLAY_SERVICE_NOT_FOUND, activity);
        Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, activity, 0);
        if (dialog != null) {
            dialog.show();
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
        backButtonHandler.onBackPressed();
    }
}
