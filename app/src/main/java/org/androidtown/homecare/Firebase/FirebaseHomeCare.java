package org.androidtown.homecare.Firebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.androidtown.homecare.Adapters.HomeCareAdapter;
import org.androidtown.homecare.Fragments.HomeCareCreationFragment;
import org.androidtown.homecare.Fragments.MessageDialogFragment;
import org.androidtown.homecare.Models.HomeCare;
import org.androidtown.homecare.Utils.ProgressDialogHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by hanhb on 2017-11-09.
 */

//싱글톤 클래스
public class FirebaseHomeCare {

    private FirebaseDatabase database;
    private DatabaseReference homeCareRef;
    private DatabaseReference userRef;
    private Context context;
    private RecyclerView recyclerView;
    private final static List<HomeCare> homeCareList = new ArrayList<>();

    public FirebaseHomeCare(Context context) {

        database = FirebaseDatabase.getInstance();
        homeCareRef = database.getReference().child("homecare");
        userRef = database.getReference().child("user");
        this.context = context;

    }

    //홈케어를 키값으로 탐색
    public HomeCare searchHomeCare(String key){
        Iterator<HomeCare> it = homeCareList.iterator();

        while (it.hasNext()){
            HomeCare homeCare = it.next();
            if(key.equals(homeCare.getKey())){
                return homeCare;
            }
        }
        return null;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public void writeHomeCare(final String uid, final HomeCare homeCare, final HomeCareCreationFragment fragment){
        ProgressDialogHelper.show(context);

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

                if(dataSnapshot.getValue()==null) {

                    DatabaseReference specificHomeCareRef = homeCareRef.push();
                    homeCare.setKey(specificHomeCareRef.getKey());
                    specificHomeCareRef.setValue(homeCare).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            ProgressDialogHelper.dismiss();
                            MessageDialogFragment.setHomeCareCreationFragment(fragment);
                            MessageDialogFragment.showDialog(MessageDialogFragment.HOMECARE_CREATION_SUCCESS,context);
                            refresh(); //리프레쉬
                        }
                    });
                    userRef.child(uid).child("current_homecare").setValue(specificHomeCareRef.getKey());

                } else {
                    ProgressDialogHelper.dismiss();
                    MessageDialogFragment.showDialog(MessageDialogFragment.HOME_CARE_ALREADY_EXISTS, context);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void refresh(){
        if(recyclerView == null)
            return;

        homeCareRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                homeCareList.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    homeCareList.add(ds.getValue(HomeCare.class));
                }

                HomeCareAdapter homeCareAdapter = new HomeCareAdapter(homeCareList, context);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(homeCareAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
