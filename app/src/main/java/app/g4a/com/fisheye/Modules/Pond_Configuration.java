package app.g4a.com.fisheye.Modules;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import app.g4a.com.fisheye.Models.Configuration;
import app.g4a.com.fisheye.Models.Users;
import app.g4a.com.fisheye.R;

public class Pond_Configuration extends AppCompatActivity {

    ImageView image_back_pond_config;
    TextView text_pond_no_config, text_cancel_config;
    Spinner spin_fish_type;
    EditText edit_length, edit_width, edit_depth, edit_date_released;
    Button button_apply;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    Configuration config;

    String [] fish = {"Milkfish (Bangus)","Tilapia"};
    String pondName, length, width, depth, fishType, date;

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
        progressBar = findViewById(R.id.progressbar_configuration);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("data").child("users").child(user.getUid());

        pondName = Select_Pond.getString();
        text_pond_no_config.setText("Pond Name: "+pondName);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,fish);
        spin_fish_type.setAdapter(adapter);


        image_back_pond_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Pond_Configuration.this,Settings.class);
                startActivity(intent);
            }
        });

        button_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               saveConfiguration();
            }
        });
    }

    public void saveConfiguration(){
        length = edit_length.getText().toString();
        width = edit_width.getText().toString();
        depth = edit_depth.getText().toString();
        fishType = spin_fish_type.getSelectedItem().toString();
        date = edit_date_released.getText().toString();

        progressBar.setVisibility(View.VISIBLE);
        config = new Configuration(pondName, length, width, depth, fishType, date);
        mDatabase.child("pond").child(pondName).setValue(config)
        .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                 progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Toast.makeText(Pond_Configuration.this, "Configuration saved", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Pond_Configuration.this,Settings.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Pond_Configuration.this, "Saving configurations failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
