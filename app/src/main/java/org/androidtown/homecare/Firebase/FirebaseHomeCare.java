package org.androidtown.homecare.Firebase;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.androidtown.homecare.Models.HomeCare;

/**
 * Created by hanhb on 2017-11-09.
 */

public class FirebaseHomeCare {

    private FirebaseDatabase database;
    private DatabaseReference homeCareRef;
    private DatabaseReference userRef;
    private Context context;

    FirebaseHomeCare(Context context){

        database = FirebaseDatabase.getInstance();
        homeCareRef = database.getReference().child("homecare");
        userRef = database.getReference().child("user");
        this.context = context;

        //리스너 추가
    }

    public void writeHomeCare(String uid, HomeCare homeCare){

        /*
            0. 프로그레스 다이얼로그 띄움
            1. root/user/uid/current_homcare이 null인지 아닌지 확인 (이미 올린 홈케어가 있는지 없는지 확인)
            2. null이면 생성, 이미 존재할 경우 생성하지 않음
            3. (생성하였으면 리프레쉬)
            4. 결과를 다이얼로그로 띄움
            5. 프로그레스 다이얼로그 dismiss
         */

        userRef.child(uid).child("current_homecare").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()==null)
                    Toast.makeText(context, "tlqkf!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void refresh(){


    }

}
