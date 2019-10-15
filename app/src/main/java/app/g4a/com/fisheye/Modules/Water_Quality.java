package app.g4a.com.fisheye.Modules;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import app.g4a.com.fisheye.R;

public class Water_Quality extends AppCompatActivity {

    ImageView image_back_waterq;
    TextView text_date, text_DO, text_water_temp, text_temp, text_ph;
    TextClock text_time;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    String currentDate,water_temp,ph,do2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water__quality);

        image_back_waterq = findViewById(R.id.imageview_back_waterq);
        text_date = findViewById(R.id.textview_date);
        text_time = findViewById(R.id.textclock_time);
        text_DO = findViewById(R.id.textview_DO);
        text_water_temp = findViewById(R.id.textview_water_temp);
        text_temp = findViewById(R.id.textview_temp);
        text_ph = findViewById(R.id.textview_ph);
        database = FirebaseDatabase.getInstance();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = mdformat.format(calendar.getTime());
        text_date.setText(currentDate);


        image_back_waterq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Water_Quality.this,MainActivity.class);
                startActivity(intent);
            }
        });

        reference = database.getInstance().getReference().child("data").child("readings").child(currentDate);
        Query lastQuery = reference.orderByKey().limitToLast(1);
        lastQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                water_temp = dataSnapshot.child("water_temp").getValue().toString();
                ph = dataSnapshot.child("pH").getValue().toString();
                do2 = dataSnapshot.child("dislv_O2").getValue().toString();
                text_water_temp.setText(water_temp);
                text_ph.setText(ph);
                text_DO.setText(do2);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
