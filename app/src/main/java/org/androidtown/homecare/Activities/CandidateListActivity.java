package org.androidtown.homecare.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import org.androidtown.homecare.Firebase.FirebaseHomeCare;
import org.androidtown.homecare.R;

public class CandidateListActivity extends AppCompatActivity {

    private Button backButton;
    private RecyclerView recyclerView;
    private FirebaseHomeCare firebaseHomeCare;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_list);
        key = getIntent().getStringExtra("key");
        initView();
        initFirebase();
    }

    private void initFirebase() {
        firebaseHomeCare = new FirebaseHomeCare(this);
        firebaseHomeCare.setCandidatesRecyclerView(recyclerView);
        firebaseHomeCare.refreshCandidates(key);
    }

    private void initView() {
        backButton = findViewById(R.id.back_button_in_activity_candidate_list);
        recyclerView = findViewById(R.id.candidate_recycler_view);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    public FirebaseHomeCare getFirebaseHomeCare() {
        return firebaseHomeCare;
    }

    public String getKey() {
        return key;
    }
}
