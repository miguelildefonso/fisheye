package app.g4a.com.fisheye.Modules;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import app.g4a.com.fisheye.R;

public class MainActivity extends AppCompatActivity {

    ImageView image_back_menu;
    LinearLayout linear_water_quality, linear_settings, linear_statistics, linear_pondinfo;
    TextView text_pond_Name;
    Select_Pond select = new Select_Pond();

    String pondName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image_back_menu = findViewById(R.id.imageview_back_menu);
        linear_water_quality = findViewById(R.id.linearlayout_water_quality);
        linear_settings = findViewById(R.id.linearlayout_settings);
        linear_statistics = findViewById(R.id.linearlayout_statistics);
        linear_pondinfo = findViewById(R.id.linearlayout_pondinfo);
        text_pond_Name = findViewById(R.id.textview_pond_name);

        text_pond_Name.setText(select.getString());

        image_back_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Select_Pond.class);
                startActivity(intent);
            }
        });

        linear_water_quality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Water_Quality.class);
                startActivity(intent);
            }
        });

        linear_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Settings.class);
                startActivity(intent);
            }
        });

        linear_statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Statistics.class);
                startActivity(intent);
            }
        });

        linear_pondinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Pond_Info.class);
                startActivity(intent);
            }
        });
    }
}
