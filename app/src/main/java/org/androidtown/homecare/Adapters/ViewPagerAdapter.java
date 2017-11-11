package org.androidtown.homecare.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.androidtown.homecare.Fragments.HiringFragment;
import org.androidtown.homecare.Fragments.MessageFragment;
import org.androidtown.homecare.Fragments.MyPageFragment;

/**
 * Created by hanhb on 2017-11-07.
 */


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    HiringFragment hiringFragment;
    MessageFragment messageFragment;
    MyPageFragment myPageFragment;
    Context context;

    public ViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        hiringFragment = new HiringFragment();
        messageFragment = new MessageFragment();
        myPageFragment = new MyPageFragment();
        this.context = context;
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

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
