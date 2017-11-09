package org.androidtown.homecare.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.androidtown.homecare.R;

public class HomeCareAdditionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_care_addition);

        initView();
    }

    private void initView() {
        findViewById(R.id.back_button_in_activity_home_care_addition).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(MainActivity.HOMECARE_ADDITION_RESULT);
                finish();
            }
        });

    }
}
