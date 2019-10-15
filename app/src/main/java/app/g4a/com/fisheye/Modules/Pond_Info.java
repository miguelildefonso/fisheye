package app.g4a.com.fisheye.Modules;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import app.g4a.com.fisheye.R;

public class Pond_Info extends AppCompatActivity {

    ImageView image_back_pond_info;
    TextView text_pond_dimen, text_fish_type, text_date_release_info,
            text_date_harvest_info,text_do_info, text_temp_info,text_ph_info;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    Select_Pond select = new Select_Pond();

    String name, length, width, depth, fish, dateRelease, dateHarvest, do2, temp, ph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pond__info);

        image_back_pond_info = findViewById(R.id.imageview_back_pond_info);
        text_pond_dimen = findViewById(R.id.textview_pond_dimen);
        text_fish_type = findViewById(R.id.textview_fish_type);
        text_date_release_info = findViewById(R.id.textview_date_release_info);
        text_date_harvest_info = findViewById(R.id.textview_date_harvest_info);
        text_do_info = findViewById(R.id.textview_do_info);
        text_temp_info = findViewById(R.id.textview_temp_info);
        text_ph_info = findViewById(R.id.textview_ph_info);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("data").child("users").
                child(user.getUid()).child("pond");

        image_back_pond_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Pond_Info.this,MainActivity.class);
                startActivity(intent);
            }
        });

        getPondInfo();

    }

    public void getPondInfo(){
        String pondName = select.getString();
        mDatabase.child(pondName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                length = dataSnapshot.child("length").getValue().toString();
                width = dataSnapshot.child("width").getValue().toString();
                depth = dataSnapshot.child("depth").getValue().toString();
                fish = dataSnapshot.child("fishType").getValue().toString();
                dateRelease = dataSnapshot.child("date").getValue().toString();

                text_fish_type.setText(fish);
                text_pond_dimen.setText(length+"m x "+width+"m x "+depth+"m");
                text_date_release_info.setText(dateRelease);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
