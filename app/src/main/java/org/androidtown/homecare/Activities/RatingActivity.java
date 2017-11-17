package org.androidtown.homecare.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import org.androidtown.homecare.Firebase.FirebaseProfile;
import org.androidtown.homecare.Models.Estimation;
import org.androidtown.homecare.R;

public class RatingActivity extends AppCompatActivity {

    private Button backButton, submitButton;
    private RatingBar kindnessRating, wellnessRting, faithRating;
    private EditText commentEdit;

    private FirebaseProfile firebaseProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        initView();
        initInstances();


    }

    private void initInstances() {
        firebaseProfile = new FirebaseProfile(RatingActivity.this);

        if (MainActivity.getHomeCareOfCurrentUser() == null || MainActivity.getUidOfOpponentUser() == null) {
            Toast.makeText(this, "비정상적인 접근입니다.", Toast.LENGTH_SHORT).show();
            finish();
        }


    }

    private void initView() {
        backButton = findViewById(R.id.back_button_in_activity_rating);
        submitButton = findViewById(R.id.submit_button_in_rating);
        kindnessRating = findViewById(R.id.kindness_rating_bar);
        wellnessRting = findViewById(R.id.wellness_rating_bar);
        faithRating = findViewById(R.id.faithfulness_rating_bar);
        commentEdit = findViewById(R.id.comment_edit_text_in_rating);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Estimation estimation = new Estimation(MainActivity.getHomeCareOfCurrentUser().getKey(), commentEdit.getText().toString(), (double)kindnessRating.getRating(),
                        (double)wellnessRting.getRating(), (double)faithRating.getRating());
                firebaseProfile.evaluate(MainActivity.getUidOfOpponentUser(), estimation);

            }
        });
    }
}
