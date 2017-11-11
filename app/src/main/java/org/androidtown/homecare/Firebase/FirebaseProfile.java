package org.androidtown.homecare.Firebase;

import android.content.Context;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.androidtown.homecare.Activities.MainActivity;
import org.androidtown.homecare.Models.User;

/**
 * Created by hanhb on 2017-11-11.
 *
 */

public class FirebaseProfile {

    private final static FirebaseDatabase database = FirebaseDatabase.getInstance();;
    private final static DatabaseReference userRef = database.getReference().child("user");;
    private Context context;


    public FirebaseProfile(Context context) {
        this.context = context;
    }


    public void getUserInMainActivity(String uid, final TextView nameText){

        userRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MainActivity.setCurrentUser(dataSnapshot.getValue(User.class));
                nameText.setText(dataSnapshot.child("name").getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
