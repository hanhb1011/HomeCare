package org.androidtown.homecare.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import org.androidtown.homecare.R;

public class RatingActivity extends AppCompatActivity {

    private Button backButton, submitButton;
    private RatingBar kindnessRating, wellnessRting, faithRating;
    private EditText commentEdit;

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
                String kindVal = String.valueOf(kindnessRating.getRating());
                String wellVal = String.valueOf(wellnessRting.getRating());
                String faithVal = String.valueOf(faithRating.getRating());
                String avg = String.valueOf((kindnessRating.getRating() + wellnessRting.getRating() + faithRating.getRating())/3);
                String comment = commentEdit.getText().toString();
                Toast.makeText(RatingActivity.this, "친절함 : " + kindVal + "\n일처리 : "+ wellVal +
                        "\n성실함 : " + faithVal + "\n평균 : " + avg + "\n한줄평 : " + comment, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
