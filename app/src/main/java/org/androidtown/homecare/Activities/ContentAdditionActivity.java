package org.androidtown.homecare.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.androidtown.homecare.R;

public class ContentAdditionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_addition);

        initView();
    }

    private void initView() {
        findViewById(R.id.back_button_in_activity_content_addition).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(MainActivity.CONTENT_ADDITION_RESULT);
                finish();
            }
        });

    }
}
