package app.g4a.com.fisheye.Modules;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

import app.g4a.com.fisheye.R;

public class Water_Quality extends AppCompatActivity {

    ImageView image_back_waterq;
    TextView text_date, text_DO, text_water_temp, text_temp, text_ph;
    TextClock text_time;

    String currentDate;

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

        Calendar calendar = Calendar.getInstance();
        currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        text_date.setText(currentDate);


        image_back_waterq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Water_Quality.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
