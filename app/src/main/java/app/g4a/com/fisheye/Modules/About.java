package app.g4a.com.fisheye.Modules;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import app.g4a.com.fisheye.R;

public class About extends AppCompatActivity {

    ImageView image_back_about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        image_back_about = findViewById(R.id.imageview_back_about);

        image_back_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(About.this,Settings.class);
                startActivity(intent);
            }
        });
    }
}
