package app.g4a.com.fisheye.Modules;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.g4a.com.fisheye.Adapters.Walkthrough_Adapter;
import app.g4a.com.fisheye.R;

public class Walk_Through extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk__through);

        viewPager = findViewById(R.id.viewpager);

        Walkthrough_Adapter adapter = new Walkthrough_Adapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }
}
