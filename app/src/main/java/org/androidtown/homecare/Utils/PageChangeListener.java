package org.androidtown.homecare.Utils;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidtown.homecare.R;

/**
 * Created by hanhb on 2017-11-28.
 */

public class PageChangeListener implements ViewPager.OnPageChangeListener {
    Button hiringButton, messageButton, myPageButton, addOrCheckHomeCareButton, filterButton, logOutButton;
    TextView titleText;
    RelativeLayout actionBarLayout;

    public PageChangeListener(Button hiringButton, Button messageButton, Button myPageButton, Button addOrCheckHomeCareButton,
                              Button filterButton, Button logOutButton, TextView titleText, RelativeLayout actionBarLayout) {
        this.hiringButton = hiringButton;
        this.messageButton = messageButton;
        this.myPageButton = myPageButton;
        this.addOrCheckHomeCareButton = addOrCheckHomeCareButton;
        this.filterButton = filterButton;
        this.logOutButton = logOutButton;
        this.titleText = titleText;
        this.actionBarLayout = actionBarLayout;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0 :
                hiringButton.setBackgroundResource(R.drawable.hiring_fragment_button_image2);
                messageButton.setBackgroundResource(R.drawable.message_fragment_button_image1);
                myPageButton.setBackgroundResource(R.drawable.my_page_fragment_button_image1);

                titleText.setText("구인");
                titleText.setTextColor(Color.parseColor("#2f355b"));
                actionBarLayout.setBackgroundResource(R.color.white);

                logOutButton.setVisibility(View.GONE);
                addOrCheckHomeCareButton.setVisibility(View.VISIBLE);
                filterButton.setVisibility(View.VISIBLE);
                break;
            case 1 :
                messageButton.setBackgroundResource(R.drawable.message_fragment_button_image2);
                hiringButton.setBackgroundResource(R.drawable.hiring_fragment_button_image1);
                myPageButton.setBackgroundResource(R.drawable.my_page_fragment_button_image1);

                titleText.setText("내 홈케어");
                titleText.setTextColor(Color.parseColor("#2f355b"));
                actionBarLayout.setBackgroundResource(R.color.white);

                logOutButton.setVisibility(View.GONE);
                addOrCheckHomeCareButton.setVisibility(View.VISIBLE);
                filterButton.setVisibility(View.VISIBLE);
                break;
            case 2 :
                myPageButton.setBackgroundResource(R.drawable.my_page_fragment_button_image2);
                messageButton.setBackgroundResource(R.drawable.message_fragment_button_image1);
                hiringButton.setBackgroundResource(R.drawable.hiring_fragment_button_image1);

                titleText.setText("내 정보");
                titleText.setTextColor(Color.WHITE);

                logOutButton.setVisibility(View.VISIBLE);
                addOrCheckHomeCareButton.setVisibility(View.GONE);
                filterButton.setVisibility(View.GONE);
                actionBarLayout.setBackgroundResource(R.color.colorPrimaryDark);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
