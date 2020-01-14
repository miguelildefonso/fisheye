package app.g4a.com.fisheye.Modules;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.g4a.com.fisheye.Adapters.Update_Fragment_Adapter;
import app.g4a.com.fisheye.Adapters.Walkthrough_Adapter;
import app.g4a.com.fisheye.R;

public class Update_Configuration extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__configuration);

        viewPager = findViewById(R.id.viewpager2);

        Update_Fragment_Adapter adapter = new Update_Fragment_Adapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }
}
