package app.g4a.com.fisheye.Modules;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import app.g4a.com.fisheye.R;

public class Pond_Configuration extends AppCompatActivity {

    ImageView image_back_pond_config;
    TextView text_pond_no_config, text_cancel_config;
    Spinner spin_fish_type;
    EditText edit_length, edit_width, edit_depth, edit_date_released;
    Button button_apply;

    String [] fish = {"Milkfish (Bangus)","Tilapia"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pond__configuration);

        image_back_pond_config = findViewById(R.id.imageview_back_pond_config);
        text_pond_no_config = findViewById(R.id.textview_pond_no_config);
        text_cancel_config = findViewById(R.id.textview_cancel_config);
        spin_fish_type = findViewById(R.id.spinner_fish_type);
        edit_length = findViewById(R.id.edittext_length);
        edit_width = findViewById(R.id.edittext_width);
        edit_depth = findViewById(R.id.edittext_depth);
        edit_date_released = findViewById(R.id.edittext_date_released);
        button_apply = findViewById(R.id.button_apply);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,fish);
        spin_fish_type.setAdapter(adapter);

        image_back_pond_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Pond_Configuration.this,Settings.class);
                startActivity(intent);
            }
        });
    }
}
