package app.g4a.com.fisheye.Modules;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import app.g4a.com.fisheye.Models.Pond_Configurations;
import app.g4a.com.fisheye.R;

public class Update_Fragment extends Fragment {

    TextView text_name, text_cancel, text_fish1, text_fish2, text_fish3, text_fish4;
    EditText edit_release1, edit_release2,edit_release3, edit_release4, edit_length, edit_width, edit_depth;
    Spinner spinner_fish1, spinner_fish2, spinner_fish3, spinner_fish4;
    ImageView image_delete1, image_delete2, image_delete3, image_delete4;
    Switch switch_fish;
    Button button_update;

    private String length, width, depth, fish1, fish2, fish3, fish4, dateRelease1, dateRelease2, dateRelease3, dateRelease4, water;
    String [] fishList = {"","Milkfish (Bangus)","Tilapia","Prawns", "Crabs"};

    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    Pond_Configurations config;
    Select_Pond select = new Select_Pond();

    Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    int day = cal.get(Calendar.DAY_OF_MONTH);

    SimpleDateFormat mdformat = new SimpleDateFormat("MM/dd/yyyy");
    DatePickerDialog.OnDateSetListener mDateSetListener1,mDateSetListener2,mDateSetListener3,mDateSetListener4;

    public Update_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_, container, false);

        text_name = view.findViewById(R.id.textview_pond_no_config_update);
        text_cancel = view.findViewById(R.id.textview_cancel_config_update);
        spinner_fish1 = view.findViewById(R.id.spinner_fish_type1_update);
        spinner_fish2 = view.findViewById(R.id.spinner_fish_type2_update);
        spinner_fish3 = view.findViewById(R.id.spinner_fish_type3_update);
        spinner_fish4 = view.findViewById(R.id.spinner_fish_type4_update);
        edit_release1 = view.findViewById(R.id.edittext_date_released1_update);
        edit_release2 = view.findViewById(R.id.edittext_date_released2_update);
        edit_release3 = view.findViewById(R.id.edittext_date_released3_update);
        edit_release4 = view.findViewById(R.id.edittext_date_released4_update);
        image_delete1 = view.findViewById(R.id.imageview_delete1_update);
        image_delete2 = view.findViewById(R.id.imageview_delete2_update);
        image_delete3 = view.findViewById(R.id.imageview_delete3_update);
        image_delete4 = view.findViewById(R.id.imageview_delete4_update);
        edit_length = view.findViewById(R.id.edittext_length_update);
        edit_width = view.findViewById(R.id.edittext_width_update);
        edit_depth = view.findViewById(R.id.edittext_depth_update);
        switch_fish = view.findViewById(R.id.switch_freshwater_update);
        button_update = view.findViewById(R.id.button_apply_update);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("data").child("users").child(user.getUid()).child("pond");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, fishList);
        spinner_fish1.setAdapter(adapter);
        spinner_fish2.setAdapter(adapter);
        spinner_fish3.setAdapter(adapter);
        spinner_fish4.setAdapter(adapter);

        retrievePondConfig();
        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateFields())
                    applyPondUpdates();
            }
        });

        text_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),Pond_Info.class);
                startActivity(intent);
            }
        });

        edit_release1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
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
                edit_release1.setText(month + "/" + day + "/" + year);
            }
        };


        edit_release2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
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
                edit_release2.setText(month + "/" + day + "/" + year);
            }
        };


        edit_release3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
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
                edit_release3.setText(month + "/" + day + "/" + year);
            }
        };

        edit_release4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog4 = new DatePickerDialog(
                        getContext(),
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
                edit_release4.setText(month + "/" + day + "/" + year);
            }
        };

        image_delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner_fish1.setSelection(0);
                edit_release1.setText("");
            }
        });

        image_delete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner_fish2.setSelection(0);
                edit_release2.setText("");
            }
        });

        image_delete3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner_fish3.setSelection(0);
                edit_release3.setText("");
            }
        });

        image_delete4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner_fish4.setSelection(0);
                edit_release4.setText("");
            }
        });



        return view;
    }
    public void retrievePondConfig(){

        reference.child(select.getString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    length = dataSnapshot.child("length").getValue().toString();
                    width = dataSnapshot.child("width").getValue().toString();
                    depth = dataSnapshot.child("depth").getValue().toString();
                    water = dataSnapshot.child("water").getValue().toString();
                    fish1 = dataSnapshot.child("fish1").getValue().toString();
                    fish2 = dataSnapshot.child("fish2").getValue().toString();
                    fish3 = dataSnapshot.child("fish3").getValue().toString();
                    fish4 = dataSnapshot.child("fish4").getValue().toString();
                    dateRelease1 = dataSnapshot.child("release1").getValue().toString();
                    dateRelease2 = dataSnapshot.child("release2").getValue().toString();
                    dateRelease3 = dataSnapshot.child("release3").getValue().toString();
                    dateRelease4 = dataSnapshot.child("release4").getValue().toString();

                    text_name.setText(select.getString());
                    edit_length.setText(length);
                    edit_width.setText(width);
                    edit_depth.setText(depth);
                    edit_release1.setText(dateRelease1);
                    edit_release2.setText(dateRelease2);
                    edit_release3.setText(dateRelease3);
                    edit_release4.setText(dateRelease4);

                    if(water.equals("Freshwater"))
                        switch_fish.setChecked(true);
                    else
                        switch_fish.setChecked(false);

                    TextView fish1Text = (TextView) spinner_fish1.getSelectedView();
                    TextView fish2Text = (TextView) spinner_fish2.getSelectedView();
                    TextView fish3Text = (TextView) spinner_fish3.getSelectedView();
                    TextView fish4Text = (TextView) spinner_fish4.getSelectedView();


                    if(fish1.equals("Milkfish (Bangus)")){
                        spinner_fish1.setSelection(1);
                    }else if(fish1.equals("Tilapia")){
                        spinner_fish1.setSelection(2);
                    }else if(fish1.equals("Prawns")){
                        spinner_fish1.setSelection(3);
                    }else if(fish1.equals("Crabs")) {
                        spinner_fish1.setSelection(4);
                    }else
                        fish1Text.setText(fish1);

                    if(fish2.equals("Milkfish (Bangus")){
                        spinner_fish2.setSelection(1);
                    }else if(fish2.equals("Tilapia")){
                        spinner_fish2.setSelection(2);
                    }else if(fish2.equals("Prawns")){
                        spinner_fish2.setSelection(3);
                    }else if(fish2.equals("Crabs")){
                        spinner_fish2.setSelection(4);
                    }else
                        fish2Text.setText(fish2);

                    if(fish3.equals("Milkfish (Bangus")){
                        spinner_fish3.setSelection(1);
                    }else if(fish3.equals("Tilapia")){
                        spinner_fish3.setSelection(2);
                    }else if(fish3.equals("Prawns")){
                        spinner_fish3.setSelection(3);
                    }else if(fish3.equals("Crabs")){
                        spinner_fish3.setSelection(4);
                    }else
                        fish3Text.setText(fish3);

                    if(fish4.equals("Milkfish (Bangus")){
                        spinner_fish4.setSelection(1);
                    }else if(fish4.equals("Tilapia")){
                        spinner_fish4.setSelection(2);
                    }else if(fish4.equals("Prawns")){
                        spinner_fish4.setSelection(3);
                    }else if(fish4.equals("Crabs")){
                        spinner_fish4.setSelection(4);
                    }else
                        fish4Text.setText(fish4);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    public void applyPondUpdates(){
        String length, width, depth, pondName, water, fish1, fish2,
                fish3, fish4, release1, release2, release3, release4,
                harvest1 = null, harvest2 = null, harvest3 = null, harvest4 = null;
        pondName = select.getString();
        length = edit_length.getText().toString();
        width = edit_width.getText().toString();
        depth = edit_depth.getText().toString();
        fish1 = spinner_fish1.getSelectedItem().toString();
        fish2 = spinner_fish2.getSelectedItem().toString();
        fish3 = spinner_fish3.getSelectedItem().toString();
        fish4 = spinner_fish4.getSelectedItem().toString();

        release1 = edit_release1.getText().toString();
        release2 = edit_release2.getText().toString();
        release3 = edit_release3.getText().toString();
        release4 = edit_release4.getText().toString();

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

        final ProgressDialog progress = new ProgressDialog(getContext());
        progress.setTitle("Updating Pond Configuration");
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();
        config = new Pond_Configurations(pondName, length, width, depth, water,fish1, fish2, fish3, fish4,
                release1, release2, release3, release4, harvest1, harvest2, harvest3,harvest4);
        reference.child(select.getString()).setValue(config)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progress.hide();
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Configuration updated", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getContext(), Pond_Info.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getContext(), "Update configurations failed. Please try again.", Toast.LENGTH_SHORT).show();
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
        final String date1 = edit_release1.getText().toString();
        final String date2 = edit_release2.getText().toString();
        final String date3 = edit_release3.getText().toString();
        final String date4 = edit_release4.getText().toString();

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

        if(fish1.equals("") && !date1.isEmpty()){
            TextView errorText = (TextView) spinner_fish1.getSelectedView();
            errorText.setError("");
            errorText.setText("Pond must have at least one configuration");
            valid = false;
        }

        if(fish2.equals("") && !date2.isEmpty()){
            TextView errorText = (TextView) spinner_fish2.getSelectedView();
            errorText.setError("");
            errorText.setText("Pond must have at least one configuration");
            valid = false;
        }

        if(fish3.equals("") && !date3.isEmpty()){
            TextView errorText = (TextView) spinner_fish3.getSelectedView();
            errorText.setError("");
            errorText.setText("Pond must have at least one configuration");
            valid = false;
        }

        if(fish4.equals("") && !date4.isEmpty()){
            TextView errorText = (TextView) spinner_fish4.getSelectedView();
            errorText.setError("");
            errorText.setText("Pond must have at least one configuration");
            valid = false;
        }

        if(date1.isEmpty() && date2.isEmpty() && date3.isEmpty() && date4.isEmpty()){
            edit_release1.setError("Pond must have at least one configuration");
            edit_release1.requestFocus();
            valid = false;
        }else{
            edit_release1.setError(null);
        }

        if(!fish1.isEmpty() && date1.isEmpty()){
            edit_release1.setError("Choose a date");
            edit_release1.requestFocus();
            valid = false;
        }else{
            edit_release1.setError(null);
        }

        if(!fish2.isEmpty() && date2.isEmpty()){
            edit_release2.setError("Choose a date");
            edit_release2.requestFocus();
            valid = false;
        }else{
            edit_release2.setError(null);
        }

        if(!fish3.isEmpty() && date3.isEmpty()){
            edit_release3.setError("Choose a date");
            edit_release3.requestFocus();
            valid = false;
        }else{
            edit_release3.setError(null);
        }

        if(!fish4.isEmpty() && date4.isEmpty()){
            edit_release4.setError("Choose a date");
            edit_release4.requestFocus();
            valid = false;
        }else{
            edit_release4.setError(null);
        }

        return valid;
    }
}
