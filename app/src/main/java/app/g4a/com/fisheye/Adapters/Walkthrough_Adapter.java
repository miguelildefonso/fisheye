package app.g4a.com.fisheye.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import app.g4a.com.fisheye.Modules.Fragments.Walkthrough.Fragments.FifthFragment;
import app.g4a.com.fisheye.Modules.Fragments.Walkthrough.Fragments.FirstFragment;
import app.g4a.com.fisheye.Modules.Fragments.Walkthrough.Fragments.FourthFragment;
import app.g4a.com.fisheye.Modules.Fragments.Walkthrough.Fragments.SecondFragment;
import app.g4a.com.fisheye.Modules.Fragments.Walkthrough.Fragments.ThirdFragment;

public class Walkthrough_Adapter extends FragmentPagerAdapter {
    public Walkthrough_Adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                return new FirstFragment();
            case 1:
                return new SecondFragment();
            case 2:
                return new ThirdFragment();
            case 3:
                return new FourthFragment();
            case 4:
                return new FifthFragment();
            default:
                return null;
        }

    }

    @Override
    public int getCount(){
        return 5;
    }


}
