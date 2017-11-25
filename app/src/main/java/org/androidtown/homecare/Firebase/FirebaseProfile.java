package org.androidtown.homecare.Firebase;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.androidtown.homecare.Activities.MainActivity;
import org.androidtown.homecare.Activities.UserProfileActivity;
import org.androidtown.homecare.Fragments.HomeCareFragment;
import org.androidtown.homecare.Fragments.MessageDialogFragment;
import org.androidtown.homecare.Models.Estimation;
import org.androidtown.homecare.Models.HomeCare;
import org.androidtown.homecare.Models.User;
import org.androidtown.homecare.Utils.ProgressDialogHelper;

/**
 * Created by hanhb on 2017-11-11.
 *
 */

// 유저 프로필 관련 (점수 매기기 등)

public class FirebaseProfile {

    private final static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final static DatabaseReference userRef = database.getReference().child("user");
    private final static DatabaseReference homeCareRef = database.getReference().child("homecare");

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
                if(((MainActivity)context).getProfileImageView()!=null){
                    ((MainActivity)context).getProfileImageView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, UserProfileActivity.class);
                            intent.putExtra("uid", uidOfCurrentUser);
                            intent.putExtra("name", user.getName());
                            intent.putExtra("star",  "★ "+ String.format("%.2f", user.getStar())+" ("+user.getHomecareCount().toString()+")");
                            context.startActivity(intent);
                        }
                    });
                }

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
                                userRef.child(MainActivity.getUidOfOpponentUser()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        User opponentUser = dataSnapshot.getValue(User.class);
                                        MainActivity.setOpponentUser(opponentUser);

                                        HomeCareFragment.setViews();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });


                                if(HomeCareFragment.getHiddenLayout() != null) {
                                    HomeCareFragment.getHiddenLayout().setVisibility(View.VISIBLE); //데이터를 받아왔을 때 화면을 띄움
                                }
                                if(HomeCareFragment.getNoneCareLayout() != null){
                                    HomeCareFragment.getNoneCareLayout().setVisibility(View.GONE);
                                }
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
                    if(HomeCareFragment.getNoneCareLayout() != null){
                        HomeCareFragment.getNoneCareLayout().setVisibility(View.VISIBLE);
                    }
                    if(HomeCareFragment.getHiddenLayout() != null) {
                        HomeCareFragment.getHiddenLayout().setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    //해당 홈케어에 대한 평가를 내림
    public void evaluate(String uidOfOpponentUser, final Estimation estimation){
        if(uidOfOpponentUser == null || estimation == null || MainActivity.getCurrentUser() == null)
            return;

        ProgressDialogHelper.show(context);

        /*
            1. user의 "homecareRecords"의 values 수를 구함 (평균 평점을 내리기 위함)
            2. user의 "homecareRecords"에 homecare의 key로 estimation을 push한다.
            3. (OnCompleteListner) user의 평균 평점을 갱신한다.
         */
        final DatabaseReference opponentRef = userRef.child(uidOfOpponentUser);

        opponentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ProgressDialogHelper.dismiss();
                int count = dataSnapshot.child("homecareCount").getValue(Integer.class);
                double averageScore = dataSnapshot.child("star").getValue(Double.class); //평균 평점을 구한 뒤
                averageScore = (averageScore*count + (estimation.getFaithfulness()+estimation.getKindness()+estimation.getWellness())/3)/(count+1);

                opponentRef.child("homecareCount").setValue(count+1);
                opponentRef.child("star").setValue(averageScore);
                opponentRef.child("homecareRecords").push().setValue(estimation);

                //TODO 테스팅 완료되면 현재 진행중인 홈케어를 지워야 함.

                MessageDialogFragment.setContext(context);
                MessageDialogFragment.setEstimation(estimation);
                MessageDialogFragment.showDialog(MessageDialogFragment.ESTIMATION_SUCCESS, context);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                ProgressDialogHelper.dismiss();
            }
        });

    }

    public static DatabaseReference getUserRef() {
        return userRef;
    }
}
