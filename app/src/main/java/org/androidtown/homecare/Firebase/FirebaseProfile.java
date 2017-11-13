package org.androidtown.homecare.Firebase;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.androidtown.homecare.Activities.MainActivity;
import org.androidtown.homecare.Fragments.MessageDialogFragment;
import org.androidtown.homecare.Fragments.MessageFragment;
import org.androidtown.homecare.Models.HomeCare;
import org.androidtown.homecare.Models.User;

/**
 * Created by hanhb on 2017-11-11.
 *
 */

public class FirebaseProfile {

    private final static FirebaseDatabase database = FirebaseDatabase.getInstance();;
    private final static DatabaseReference userRef = database.getReference().child("user");;
    private final DatabaseReference homeCareRef = database.getReference().child("homecare");

    private Context context;


    public FirebaseProfile(Context context) {
        this.context = context;
    }



    //서버에서 필요한 데이터를 가져옴 (현재 진행중인 Home care, 상대방 정보(uid) 등
    public void getCurrentUserAndHomecareInMainActivity(final String uidOfCurrentUser, final TextView nameText){

        userRef.child(uidOfCurrentUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final User user = dataSnapshot.getValue(User.class);
                MainActivity.setCurrentUser(user);
                nameText.setText(user.getName());

                if(user.getCurrent_homecare() != null){
                    homeCareRef.child(user.getCurrent_homecare()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            HomeCare homeCare = dataSnapshot.getValue(HomeCare.class);
                            MainActivity.setHomeCareOfCurrentUser(homeCare);

                            if(homeCare.getUidOfCareTaker()!=null){
                                //케어가 진행 중일 때
                                if(homeCare.getUidOfCareTaker().equals(uidOfCurrentUser)){
                                    MainActivity.setUidOfOpponentUser(homeCare.getUid()); //내가(current user가) 케어테이커일 경우 상대방은 uid
                                } else {
                                    MainActivity.setUidOfOpponentUser(homeCare.getUidOfCareTaker()); //내가 작성자인 경우
                                }
                                if(MessageFragment.getHiddenLayout() != null)
                                    MessageFragment.getHiddenLayout().setVisibility(View.VISIBLE); //데이터를 받아왔을 때 화면을 띄움



                                FirebaseMessenger firebaseMessenger = new FirebaseMessenger(context, MainActivity.getUidOfOpponentUser());
                                firebaseMessenger.setRecyclerView(MessageFragment.getMessageRecyclerView());
                                firebaseMessenger.readMessages(homeCare.getKey());
                                MainActivity.setFirebaseMessenger(firebaseMessenger);

                            }

                            if(homeCare.getWaitingForDeletion() != null && !homeCare.getWaitingForDeletion().equals(uidOfCurrentUser)){
                                //상대방이 삭제 요청을 보낸 경우
                                MessageDialogFragment.setContext(context);
                                MessageDialogFragment.setKeyAndUid(homeCare.getKey(), uidOfCurrentUser);
                                MessageDialogFragment.showDialog(MessageDialogFragment.DELETION_CHECK, context);

                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                } else {
                    if(MessageFragment.getNoneCareLayout() != null){
                        MessageFragment.getNoneCareLayout().setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
