package app.g4a.com.fisheye.Modules.Fragments.Walkthrough.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import app.g4a.com.fisheye.Modules.About;
import app.g4a.com.fisheye.Modules.Select_Pond;
import app.g4a.com.fisheye.R;

public class FifthFragment extends Fragment {

    TextView textviewNext, textviewBack;
    ViewPager viewPager;

    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    About abt = new About();

    public FifthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fifth, container, false);
        viewPager = getActivity().findViewById(R.id.viewpager);
        textviewNext = view.findViewById(R.id.textview_intro_next5);
        textviewBack = view.findViewById(R.id.textview_intro_back5);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("data").child("users").child(user.getUid());

        textviewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child("status").setValue("0").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(), "Tutorial DONE!", Toast.LENGTH_LONG).show();
                    }
                });
                String about=abt.about();
                if(about.equals("1")){
                    Intent intent = new Intent(getContext(), About.class);
                    startActivity(intent);
                    abt.setAbout("0");
                }
                else {
                    Intent intent = new Intent(getContext(), Select_Pond.class);
                    startActivity(intent);
                }
            }
        });

        textviewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(3);
            }
        });
        return view;
    }
}