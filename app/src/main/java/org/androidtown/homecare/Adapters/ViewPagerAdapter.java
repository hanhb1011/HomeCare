package org.androidtown.homecare.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.androidtown.homecare.Fragments.MessageFragment;
import org.androidtown.homecare.Fragments.MyPageFragment;
import org.androidtown.homecare.Fragments.HiringFragment;

/**
 * Created by hanhb on 2017-11-07.
 */


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    HiringFragment hiringFragment;
    MessageFragment messageFragment;
    MyPageFragment myPageFragment;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        hiringFragment = new HiringFragment();
        messageFragment = new MessageFragment();
        myPageFragment = new MyPageFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0 :
                return hiringFragment;
            case 1 :
                return messageFragment;
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
