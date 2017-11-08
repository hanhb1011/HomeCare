package org.androidtown.homecare.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.androidtown.homecare.Fragments.BookMarkFragment;
import org.androidtown.homecare.Fragments.MyPageFragment;
import org.androidtown.homecare.Fragments.TimeLineFragment;

/**
 * Created by hanhb on 2017-11-07.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    TimeLineFragment timeLineFragment;
    BookMarkFragment bookMarkFragment;
    MyPageFragment myPageFragment;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        timeLineFragment = new TimeLineFragment();
        bookMarkFragment = new BookMarkFragment();
        myPageFragment = new MyPageFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0 :
                return timeLineFragment;
            case 1 :
                return bookMarkFragment;
            case 2 :
                return myPageFragment;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
