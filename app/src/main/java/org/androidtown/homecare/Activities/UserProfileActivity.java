package org.androidtown.homecare.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidtown.homecare.R;

public class UserProfileActivity extends AppCompatActivity {

    private ImageView profileImage;
    private TextView starText;
    private RecyclerView recyclerView;

    private String uid;
    private String star;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        initInstances();
        initView();
        getDataFromServer();
        
    }

    private void getDataFromServer() {

    }

    private void initInstances() {

        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        star = intent.getStringExtra("star");
        name = intent.getStringExtra("name");

    }


    private void initView() {

        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(name);
        profileImage = findViewById(R.id.profile_image_view_in_profile);
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
