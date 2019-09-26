package app.g4a.com.fisheye.Modules;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import app.g4a.com.fisheye.R;

public class Statistics extends AppCompatActivity {

    ImageView image_back_statistics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        image_back_statistics = findViewById(R.id.imageview_back_statistics);

        image_back_statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Statistics.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
