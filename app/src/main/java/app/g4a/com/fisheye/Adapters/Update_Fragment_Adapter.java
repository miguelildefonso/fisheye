package app.g4a.com.fisheye.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import app.g4a.com.fisheye.Modules.Update_Fragment;

public class Update_Fragment_Adapter extends FragmentPagerAdapter {
    public Update_Fragment_Adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                return new Update_Fragment();
            default:
                return null;
        }

    }

    @Override
    public int getCount(){
        return 1;
    }


}
