package app.g4a.com.fisheye.Modules.Fragments.Walkthrough.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.g4a.com.fisheye.R;

public class FirstFragment extends Fragment {

    TextView textviewNext;
    ViewPager viewPager;

    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        viewPager = getActivity().findViewById(R.id.viewpager);
        textviewNext = view.findViewById(R.id.textview_intro_next);

        textviewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });
        return view;
    }

}
