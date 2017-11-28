package org.androidtown.homecare.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidtown.homecare.Firebase.FirebasePicture;
import org.androidtown.homecare.Firebase.FirebaseProfile;
import org.androidtown.homecare.R;

public class UserProfileActivity extends AppCompatActivity {

    private ImageView profileImage;
    private TextView starText;
    private RecyclerView recyclerView;

    private String uid;
    private String star;
    private String name;

    private FirebaseProfile firebaseProfile;
    private FirebasePicture firebasePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        initInstances();
        initView();
        getDataFromServer();
        
    }

    private void getDataFromServer() {
        firebaseProfile.readEstimations(uid, recyclerView);
        firebasePicture.downloadImage(uid, profileImage);
    }

    private void initInstances() {

        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        star = intent.getStringExtra("star");
        name = intent.getStringExtra("name");

        firebaseProfile = new FirebaseProfile(this);
        firebasePicture = new FirebasePicture(this);
    }


    private void initView() {

        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(name);

        profileImage = findViewById(R.id.profile_image_view_in_profile);
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.getPaint().setColor(Color.TRANSPARENT);
        profileImage.setBackground(shapeDrawable);
        profileImage.setClipToOutline(true);
        profileImage.setScaleType(ImageView.ScaleType.CENTER_CROP);


        starText = findViewById(R.id.star_text_view_in_profile);
        starText.setText(star);
        recyclerView = findViewById(R.id.recycler_view_in_profile);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
