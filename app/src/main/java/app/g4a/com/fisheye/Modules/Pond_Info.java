package app.g4a.com.fisheye.Modules;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import app.g4a.com.fisheye.R;

public class Pond_Info extends AppCompatActivity {

    ImageView image_back_pond_info;
    TextView text_pond_dimen, text_fish_type, text_date_release_info,
            text_date_harvest_info,text_do_info, text_temp_info,text_ph_info;

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

        image_back_pond_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Pond_Info.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
