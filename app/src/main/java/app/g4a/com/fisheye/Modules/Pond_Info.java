package app.g4a.com.fisheye.Modules;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import app.g4a.com.fisheye.R;

public class Pond_Info extends AppCompatActivity {

    ImageView image_back_pond_info;
    TextView text_name, text_water, text_fish1, text_fish2, text_fish3, text_fish4, text_release1, text_release2,
        text_release3, text_release4, text_harvest1, text_harvest2, text_harvest3, text_harvest4, text_pond_dimen;
    Button button_update;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    Select_Pond select = new Select_Pond();

    String length, width, depth, fish1, fish2, fish3, fish4, dateRelease1, dateRelease2, dateRelease3, dateRelease4,
            dateHarvest1, dateHarvest2, dateHarvest3, dateHarvest4, water;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pond__info);

        image_back_pond_info = findViewById(R.id.imageview_back_pond_info);
        button_update = findViewById(R.id.button_update_pond);

        text_name = findViewById(R.id.textview_pondname);
        text_water = findViewById(R.id.textview_water_type);
        text_fish1 = findViewById(R.id.textview_fish1);
        text_fish2 = findViewById(R.id.textview_fish2);
        text_fish3 = findViewById(R.id.textview_fish3);
        text_fish4 = findViewById(R.id.textview_fish4);
        text_release1 = findViewById(R.id.textview_release1);
        text_release2 = findViewById(R.id.textview_release2);
        text_release3 = findViewById(R.id.textview_release3);
        text_release4 = findViewById(R.id.textview_release4);
        text_harvest1 = findViewById(R.id.textview_harvest1);
        text_harvest2 = findViewById(R.id.textview_harvest2);
        text_harvest3 = findViewById(R.id.textview_harvest3);
        text_harvest4 = findViewById(R.id.textview_harvest4);
        text_pond_dimen = findViewById(R.id.textview_pond_dimen);

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

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Pond_Info.this,Update_Configuration.class);
                startActivity(intent);
            }
        });

        getPondInfo();
    }

    public void getPondInfo(){
        final String pondName = select.getString();
        mDatabase.child(pondName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    length = dataSnapshot.child("length").getValue().toString();
                    width = dataSnapshot.child("width").getValue().toString();
                    depth = dataSnapshot.child("depth").getValue().toString();
                    water = dataSnapshot.child("water").getValue().toString();
                    fish1 = dataSnapshot.child("fish1").getValue().toString();
                    fish2 = dataSnapshot.child("fish2").getValue().toString();
                    fish3 = dataSnapshot.child("fish3").getValue().toString();
                    fish4 = dataSnapshot.child("fish4").getValue().toString();
                    dateRelease1 = dataSnapshot.child("release1").getValue().toString();
                    dateRelease2 = dataSnapshot.child("release2").getValue().toString();
                    dateRelease3 = dataSnapshot.child("release3").getValue().toString();
                    dateRelease4 = dataSnapshot.child("release4").getValue().toString();
                    dateHarvest1 = dataSnapshot.child("harvest1").getValue().toString();
                    dateHarvest2 = dataSnapshot.child("harvest2").getValue().toString();
                    dateHarvest3 = dataSnapshot.child("harvest3").getValue().toString();
                    dateHarvest4 = dataSnapshot.child("harvest4").getValue().toString();
                    text_name.setText(pondName);
                    text_pond_dimen.setText(length + "m x " + width + "m x " + depth + "m");
                    text_water.setText(water);
                    text_fish1.setText(fish1);
                    text_fish2.setText(fish2);
                    text_fish3.setText(fish3);
                    text_fish4.setText(fish4);
                    text_release1.setText(dateRelease1);
                    text_release2.setText(dateRelease2);
                    text_release3.setText(dateRelease3);
                    text_release4.setText(dateRelease4);
                    text_harvest1.setText(dateHarvest1);
                    text_harvest2.setText(dateHarvest2);
                    text_harvest3.setText(dateHarvest3);
                    text_harvest4.setText(dateHarvest4);
                }
                else{
                    Toast.makeText(Pond_Info.this, "Pond is not configured yet! Configure Pond first.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Pond_Info.this,MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
