package app.g4a.com.fisheye.Modules;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import app.g4a.com.fisheye.Models.Pond_Configurations;
import app.g4a.com.fisheye.R;

public class Configuration_Setting extends AppCompatActivity{

    ImageView image_back_pond_config, image_delete1, image_delete2, image_delete3, image_delete4;
    TextView text_pond_no_config, text_cancel_config;

    Switch switch_fish;
    Spinner spinner_fish1, spinner_fish2, spinner_fish3, spinner_fish4;
    EditText edit_length, edit_width, edit_depth, edit_date1, edit_date2, edit_date3, edit_date4;
    Button button_apply;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    Pond_Configurations config;

    Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    int day = cal.get(Calendar.DAY_OF_MONTH);

    SimpleDateFormat mdformat = new SimpleDateFormat("MM/dd/yyyy");

    private DatePickerDialog.OnDateSetListener mDateSetListener1,mDateSetListener2,mDateSetListener3,mDateSetListener4;
    private String length, width, depth, pondName, water, fish1, fish2,
            fish3, fish4, release1, release2, release3, release4,
            harvest1, harvest2, harvest3, harvest4;
    String [] fishList = {"","Milkfish (Bangus)","Tilapia","Prawns", "Crabs"};

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration__setting);

        image_back_pond_config = findViewById(R.id.imageview_back_pond_config);
        text_pond_no_config = findViewById(R.id.textview_pond_no_config);
        text_cancel_config = findViewById(R.id.textview_cancel_config);
        switch_fish = findViewById(R.id.switch_freshwater);
        edit_length = findViewById(R.id.edittext_length);
        edit_width = findViewById(R.id.edittext_width);
        edit_depth = findViewById(R.id.edittext_depth);

        edit_date1 = findViewById(R.id.edittext_date_released1);
        edit_date2 = findViewById(R.id.edittext_date_released2);
        edit_date3 = findViewById(R.id.edittext_date_released3);
        edit_date4 = findViewById(R.id.edittext_date_released4);

        spinner_fish1 = findViewById(R.id.spinner_fish_type1);
        spinner_fish2 = findViewById(R.id.spinner_fish_type2);
        spinner_fish3 = findViewById(R.id.spinner_fish_type3);
        spinner_fish4 = findViewById(R.id.spinner_fish_type4);

        image_delete1 = findViewById(R.id.imageview_delete1);
        image_delete2 = findViewById(R.id.imageview_delete2);
        image_delete3 = findViewById(R.id.imageview_delete3);
        image_delete4 = findViewById(R.id.imageview_delete4);
        button_apply = findViewById(R.id.button_apply);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("data").child("users").child(user.getUid());

        pondName = Select_Pond.getString();
        text_pond_no_config.setText("Pond Name: " + pondName);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, fishList);
        spinner_fish1.setAdapter(adapter);
        spinner_fish2.setAdapter(adapter);
        spinner_fish3.setAdapter(adapter);
        spinner_fish4.setAdapter(adapter);

        //arrow back button
        image_back_pond_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Configuration_Setting.this, Settings.class);
                startActivity(intent);
            }
        });

        button_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateFields())
                    saveConfiguration();
            }
        });

        edit_date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(
                        Configuration_Setting.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener1,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                edit_date1.setText(month + "/" + day + "/" + year);
            }
        };


        edit_date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(
                        Configuration_Setting.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener2,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });
        mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                edit_date2.setText(month + "/" + day + "/" + year);
            }
        };


        edit_date3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(
                        Configuration_Setting.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener3,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener3 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                edit_date3.setText(month + "/" + day + "/" + year);
            }
        };

        edit_date4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog4 = new DatePickerDialog(
                        Configuration_Setting.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener4,
                        year, month, day);
                dialog4.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog4.show();
            }
        });

        mDateSetListener4 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                edit_date4.setText(month + "/" + day + "/" + year);
            }
        };


        image_delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner_fish1.setSelection(0);
                edit_date1.setText("");
            }
        });

        image_delete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner_fish2.setSelection(0);
                edit_date2.setText("");
            }
        });

        image_delete3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner_fish3.setSelection(0);
                edit_date3.setText("");
            }
        });

        image_delete4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner_fish4.setSelection(0);
                edit_date4.setText("");
            }
        });
    }

    public void saveConfiguration(){
        length = edit_length.getText().toString();
        width = edit_width.getText().toString();
        depth = edit_depth.getText().toString();
        fish1 = spinner_fish1.getSelectedItem().toString();
        fish2 = spinner_fish2.getSelectedItem().toString();
        fish3 = spinner_fish3.getSelectedItem().toString();
        fish4 = spinner_fish4.getSelectedItem().toString();

        release1 = edit_date1.getText().toString();
        release2 = edit_date2.getText().toString();
        release3 = edit_date3.getText().toString();
        release4 = edit_date4.getText().toString();

        if(switch_fish.isChecked())
            water="Freshwater";
        else
            water="Brackish";

        Calendar calendar1, calendar2, calendar3, calendar4;
        calendar1 = Calendar.getInstance();
        calendar2 = Calendar.getInstance();
        calendar3 = Calendar.getInstance();
        calendar4 = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("MM/dd/yyyy");
        try {
            calendar1.setTime(mdformat.parse(release1));
            calendar2.setTime(mdformat.parse(release2));
            calendar3.setTime(mdformat.parse(release3));
            calendar4.setTime(mdformat.parse(release4));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        for(int i=0; i<5;i++){
            if(spinner_fish1.getSelectedItemPosition()==i){
                if(i==0){
                    harvest1 = "";
                }
                else if(i==1){
                    calendar1.add(Calendar.DATE, 300);
                    harvest1 = sdf.format(calendar1.getTime());
                }
                else if(i==2){
                    calendar1.add(Calendar.DATE, 240);
                    harvest1 = sdf.format(calendar1.getTime());
                }
                else if(i==3){
                    calendar1.add(Calendar.DATE, 300);
                    harvest1 = sdf.format(calendar1.getTime());
                }
                else if(i==4){
                    calendar1.add(Calendar.DATE, 300);
                    harvest1 = sdf.format(calendar1.getTime());
                }
            }
        }

        for(int i=0; i<5;i++){
            if(spinner_fish2.getSelectedItemPosition()==i){
                if(i==0){
                    harvest2 = "";
                }
                else if(i==1){
                    calendar2.add(Calendar.DATE, 300);
                    harvest2 = sdf.format(calendar2.getTime());
                }
                else if(i==2){
                    calendar2.add(Calendar.DATE, 240);
                    harvest2 = sdf.format(calendar2.getTime());
                }
                else if(i==3){
                    calendar2.add(Calendar.DATE, 300);
                    harvest2 = sdf.format(calendar2.getTime());
                }
                else if(i==4){
                    calendar2.add(Calendar.DATE, 300);
                    harvest2 = sdf.format(calendar2.getTime());
                }
            }
        }

        for(int i=0; i<5;i++){
            if(spinner_fish3.getSelectedItemPosition()==i){
                if(i==0){
                    harvest3 = "";
                }
                else if(i==1){
                    calendar3.add(Calendar.DATE, 300);
                    harvest3 = sdf.format(calendar3.getTime());
                }
                else if(i==2){
                    calendar3.add(Calendar.DATE, 240);
                    harvest3 = sdf.format(calendar3.getTime());
                }
                else if(i==3){
                    calendar3.add(Calendar.DATE, 300);
                    harvest3 = sdf.format(calendar3.getTime());
                }
                else if(i==4){
                    calendar3.add(Calendar.DATE, 300);
                    harvest3 = sdf.format(calendar3.getTime());
                }
            }
        }

        for(int i=0; i<5;i++){
            if(spinner_fish4.getSelectedItemPosition()==i){
                if(i==0){
                    harvest4 = "";
                }
                else if(i==1){
                    calendar4.add(Calendar.DATE, 300);
                    harvest4 = sdf.format(calendar4.getTime());
                }
                else if(i==2){
                    calendar4.add(Calendar.DATE, 240);
                    harvest4 = sdf.format(calendar4.getTime());
                }
                else if(i==3){
                    calendar4.add(Calendar.DATE, 300);
                    harvest4 = sdf.format(calendar4.getTime());
                }
                else if(i==4){
                    calendar4.add(Calendar.DATE, 300);
                    harvest4 = sdf.format(calendar4.getTime());
                }
            }
        }

        final ProgressDialog progress = new ProgressDialog(Configuration_Setting.this);
        progress.setTitle("Saving Pond Configuration");
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();
        config = new Pond_Configurations(pondName, length, width, depth, water,fish1, fish2, fish3, fish4,
                release1, release2, release3, release4, harvest1, harvest2, harvest3,harvest4);
        mDatabase.child("pond").child(pondName).setValue(config)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progress.hide();
                        if (task.isSuccessful()) {
                            Toast.makeText(Configuration_Setting.this, "Configuration saved", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Configuration_Setting.this, Settings.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Configuration_Setting.this, "Saving configurations failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public boolean validateFields() {
        boolean valid = true;
        final String length = edit_length.getText().toString();
        final String width = edit_width.getText().toString();
        final String depth = edit_depth.getText().toString();
        final String fish1 = spinner_fish1.getSelectedItem().toString();
        final String fish2 = spinner_fish2.getSelectedItem().toString();
        final String fish3 = spinner_fish3.getSelectedItem().toString();
        final String fish4 = spinner_fish4.getSelectedItem().toString();
        final String date1 = edit_date1.getText().toString();
        final String date2 = edit_date2.getText().toString();
        final String date3 = edit_date3.getText().toString();
        final String date4 = edit_date4.getText().toString();

        //validations for pond length
        if(length.isEmpty()){
            edit_length.setError("Invalid measurement!");
            edit_length.requestFocus();
            valid = false;
        }else{
            edit_length.setError(null);
        }

        //validations for pond width
        if(width.isEmpty()){
            edit_width.setError("Invalid measurement!");
            edit_width.requestFocus();
            valid = false;
        }else{
            edit_width.setError(null);
        }

        //validations for pond depth
        if(depth.isEmpty()){
            edit_depth.setError("Invalid measurement!");
            edit_depth.requestFocus();
            valid = false;
        }else{
            edit_depth.setError(null);
        }

        if(fish1.isEmpty() && fish2.isEmpty() && fish3.isEmpty() && fish4.isEmpty()){
            TextView errorText = (TextView) spinner_fish1.getSelectedView();
            errorText.setError("");
            errorText.setText("Pond must have at least one configuration");
            valid = false;
        }

        if(date1.isEmpty() && date2.isEmpty() && date3.isEmpty() && date4.isEmpty()){
            edit_date1.setError("Pond must have at least one configuration");
            edit_date1.requestFocus();
            valid = false;
        }else{
            edit_date1.setError(null);
        }

        if(!fish1.isEmpty() && date1.isEmpty()){
            edit_date1.setError("Choose a date");
            edit_date1.requestFocus();
            valid = false;
        }else{
            edit_date1.setError(null);
        }

        if(!fish2.isEmpty() && date2.isEmpty()){
            edit_date2.setError("Choose a date");
            edit_date2.requestFocus();
            valid = false;
        }else{
            edit_date2.setError(null);
        }

        if(!fish3.isEmpty() && date3.isEmpty()){
            edit_date3.setError("Choose a date");
            edit_date3.requestFocus();
            valid = false;
        }else{
            edit_date3.setError(null);
        }

        if(!fish4.isEmpty() && date4.isEmpty()){
            edit_date4.setError("Choose a date");
            edit_date4.requestFocus();
            valid = false;
        }else{
            edit_date4.setError(null);
        }

        return valid;
    }
}


