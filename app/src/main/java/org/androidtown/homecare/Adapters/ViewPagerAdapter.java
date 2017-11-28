package org.androidtown.homecare.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.androidtown.homecare.Fragments.HiringFragment;
import org.androidtown.homecare.Fragments.HomeCareFragment;
import org.androidtown.homecare.Fragments.MyPageFragment;

/**
 * Created by hanhb on 2017-11-07.
 */


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    HiringFragment hiringFragment;
    HomeCareFragment homeCareFragment;
    MyPageFragment myPageFragment;
    Context context;

    public ViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        hiringFragment = new HiringFragment();
        homeCareFragment = new HomeCareFragment();
        myPageFragment = new MyPageFragment();
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0 :
                return hiringFragment;
            case 1 :
                return homeCareFragment;
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
