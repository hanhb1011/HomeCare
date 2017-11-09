package org.androidtown.homecare.Activities;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import org.androidtown.homecare.R;


//게시글을 자세히 보여주는 액티비티
public class HomeCareActivity extends AppCompatActivity {

    ImageView profileImageView;
    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_care);

        initView();




    }

    private void initView() {

        profileImageView = findViewById(R.id.profile_image_view_in_activity_home_care);
        profileImageView.setBackground(new ShapeDrawable(new OvalShape()));
        profileImageView.setClipToOutline(true);
        profileImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        findViewById(R.id.back_button_in_activity_home_care).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}