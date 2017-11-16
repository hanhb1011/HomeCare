package org.androidtown.homecare.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import org.androidtown.homecare.R;

public class RatingActivity extends AppCompatActivity {

    private Button backButton, submitButton;
    private RatingBar kindnessRating, wellnessRting, faithRating;
    private String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        initView();





    }

    private void initView() {
        backButton = findViewById(R.id.back_button_in_activity_rating);
        submitButton = findViewById(R.id.submit_button_in_rating);
        kindnessRating = findViewById(R.id.kindness_rating_bar);
        wellnessRting = findViewById(R.id.wellness_rating_bar);
        faithRating = findViewById(R.id.faithfulness_rating_bar);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
