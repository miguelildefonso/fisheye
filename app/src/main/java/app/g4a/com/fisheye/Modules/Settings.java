package app.g4a.com.fisheye.Modules;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import app.g4a.com.fisheye.R;

public class Settings extends AppCompatActivity {

    ImageView image_back_settings;
    RelativeLayout relative_pond_config, relative_account_settings, relative_about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        image_back_settings = findViewById(R.id.imageview_back_settings);
        relative_pond_config = findViewById(R.id.relativelayout_pond_config);
        relative_account_settings = findViewById(R.id.relativelayout_account_settings);
        relative_about = findViewById(R.id.relativelayout_about);

        image_back_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this,MainActivity.class);
                startActivity(intent);
            }
        });

        relative_pond_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this,Pond_Configuration.class);
                startActivity(intent);
            }
        });

        relative_account_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this,Account_Settings.class);
                startActivity(intent);
            }
        });

        relative_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this,About.class);
                startActivity(intent);
            }
        });
    }
}
