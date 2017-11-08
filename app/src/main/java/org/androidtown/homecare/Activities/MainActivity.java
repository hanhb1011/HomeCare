package org.androidtown.homecare.Activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import org.androidtown.homecare.Adapters.ViewPagerAdapter;
import org.androidtown.homecare.R;


public class MainActivity extends AppCompatActivity {

    Button timeLineButton, bookMarkButton, myPageButton;
    ViewPager mainViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        setContentView(R.layout.activity_main);
        initView(); //뷰 초기화


    }

    private void initView() {

        //버튼
        timeLineButton = findViewById(R.id.time_line_fragment_button);
        timeLineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {mainViewPager.setCurrentItem(0);
            }
        });
        bookMarkButton = findViewById(R.id.book_mark_fragment_button);
        bookMarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {mainViewPager.setCurrentItem(1);
            }
        });
        myPageButton = findViewById(R.id.my_page_fragment_button);
        myPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {mainViewPager.setCurrentItem(2) ;
            }
        });

        //뷰페이저
        mainViewPager = findViewById(R.id.main_view_pager);
        mainViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        mainViewPager.setCurrentItem(0);



    }
}
