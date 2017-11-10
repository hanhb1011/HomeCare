package org.androidtown.homecare.Firebase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.androidtown.homecare.Activities.MainActivity;
import org.androidtown.homecare.Fragments.MessageDialogFragment;
import org.androidtown.homecare.Models.User;
import org.androidtown.homecare.Utils.ProgressDialogHelper;
import org.androidtown.homecare.Utils.SharedPreferenceHelper;

/**
 * Created by hanhb on 2017-11-09.
 */

public class FirebaseAccount {

    //파이어베이스 계정 생성
    /*
        method list

        createAccount(String email, String password) : 계정 생성
        checkEmailValid(String email)
        checkPasswordValid(String password)

     */
    //Firebase
    public FirebaseAuth mAuth;
    public FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference userRef;

    private static final String TAG = "Firebase Authentication"; //인증 로깅에 사용될 태그
    private Context context;


    public FirebaseAccount(Context context) {

        this.context = context;
        mAuth = FirebaseAuth.getInstance();

        setDefaultAuthListener(); //기본 리스너 생성 (uid 값만 할당시킴)

    }

    private void setDefaultAuthListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

    }



    //회원가입
    public void attemptSignup(String email, String password, final User user){
        //firebase instances initialize
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference().child("user"); // root/user

        if(context == null || user == null)
            return;

        if(!checkEmailValid(email) || !checkPasswordValid(password)){
            MessageDialogFragment.showDialog(MessageDialogFragment.INVALID_EMAIL_OR_PASSWORD, context);
            return;
        }

        ProgressDialogHelper.show(context); //프로그래스바 띄우기

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity)context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        ProgressDialogHelper.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            MessageDialogFragment.showDialog(MessageDialogFragment.SIGN_UP_SUCCESS, context);
                            DatabaseReference specificUser = userRef.child(mAuth.getCurrentUser().getUid());
                            specificUser.setValue(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            MessageDialogFragment.showDialog(MessageDialogFragment.SIGN_UP_FAILED, context);
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                        }

                    }
                });


    }

    //로그인
    public void attemptSignin(final String email, final String password){
        if(context == null)
            return;
        if(!checkEmailValid(email) || !checkPasswordValid(password)){
            MessageDialogFragment.showDialog(MessageDialogFragment.INVALID_EMAIL_OR_PASSWORD, context);
            return;
        }

        ProgressDialogHelper.show(context); //프로그래스바 띄우기

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity)context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        ProgressDialogHelper.dismiss();

                        if (task.isSuccessful()) {
                            //성공 시 입력값을 저장하고 다음 액티비티로 이동
                            SharedPreferenceHelper.putString(context, "email", email);
                            SharedPreferenceHelper.putString(context, "password", password);

                            Intent intent = new Intent(context, MainActivity.class);
                            intent.putExtra("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            context.startActivity(intent);
                            ((Activity) context).finish();

                        } else {
                            // If sign in fails, display a messa,ge to the user.
                            MessageDialogFragment.showDialog(MessageDialogFragment.SIGN_IN_FAILED, context);
                            Log.w(TAG, "signInWithEmail:failure", task.getException());


                        }

                        // ...
                    }
                });


    }

    //정규식으로 validation 체크
    public static boolean checkEmailValid(String email){
        final String emailPattern = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$";
        return email.matches(emailPattern);
    }

    public static boolean checkPasswordValid(String password){
        final String passwordPattern = "^[A-Za-z0-9_-]{6,100}$";
        return password.matches(passwordPattern);
    }

}
