package org.androidtown.homecare.Utils;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import org.androidtown.homecare.R;

/**
 * Created by hanhb on 2017-11-28.
 */

public class PageChangeListener implements ViewPager.OnPageChangeListener {
    Button hiringButton, messageButton, myPageButton, addOrCheckHomeCareButton, filterButton, logOutButton;


    public PageChangeListener(Button hiringButton, Button messageButton, Button myPageButton, Button addOrCheckHomeCareButton, Button filterButton, Button logOutButton) {
        this.hiringButton = hiringButton;
        this.messageButton = messageButton;
        this.myPageButton = myPageButton;
        this.addOrCheckHomeCareButton = addOrCheckHomeCareButton;
        this.filterButton = filterButton;
        this.logOutButton = logOutButton;
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

                addOrCheckHomeCareButton.setVisibility(View.VISIBLE);
                filterButton.setVisibility(View.VISIBLE);
                break;
            case 1 :
                messageButton.setBackgroundResource(R.drawable.message_fragment_button_image2);
                hiringButton.setBackgroundResource(R.drawable.hiring_fragment_button_image1);
                myPageButton.setBackgroundResource(R.drawable.my_page_fragment_button_image1);

                addOrCheckHomeCareButton.setVisibility(View.GONE);
                filterButton.setVisibility(View.GONE);
                break;
            case 2 :
                myPageButton.setBackgroundResource(R.drawable.my_page_fragment_button_image2);
                messageButton.setBackgroundResource(R.drawable.message_fragment_button_image1);
                hiringButton.setBackgroundResource(R.drawable.hiring_fragment_button_image1);

                addOrCheckHomeCareButton.setVisibility(View.GONE);
                filterButton.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
