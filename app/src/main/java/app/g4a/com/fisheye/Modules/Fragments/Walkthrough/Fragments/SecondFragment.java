package app.g4a.com.fisheye.Modules.Fragments.Walkthrough.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.g4a.com.fisheye.R;

public class SecondFragment extends Fragment {

    TextView textviewNext, textviewBack;
    ViewPager viewPager;

    public SecondFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        viewPager = getActivity().findViewById(R.id.viewpager);
        textviewNext = view.findViewById(R.id.textview_intro_next2);
        textviewBack = view.findViewById(R.id.textview_intro_back2);

        textviewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
            }
        });

        textviewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });

        return view;
    }

}
