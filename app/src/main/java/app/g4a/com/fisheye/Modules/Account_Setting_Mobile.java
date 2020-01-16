package app.g4a.com.fisheye.Modules;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import app.g4a.com.fisheye.Models.Users;
import app.g4a.com.fisheye.R;

public class Account_Setting_Mobile extends AppCompatActivity {

    ImageView image_back_account_username;
    EditText edit_change_mobile, edit_user_password;
    Button button_user_change;
    TextView text_cancel_user;

    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account__setting__mobile);

        image_back_account_username = findViewById(R.id.imageview_back_account_username);
        edit_change_mobile = findViewById(R.id.edittext_change_mobile);
        edit_user_password = findViewById(R.id.edittext_user_password);
        button_user_change = findViewById(R.id.button_user_change);
        text_cancel_user = findViewById(R.id.textview_cancel_user);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("data").child("users").
                child(user.getUid());

        image_back_account_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Account_Setting_Mobile.this,Account_Settings.class);
                startActivity(intent);
            }
        });

        text_cancel_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Account_Setting_Mobile.this, Account_Settings.class);
                startActivity(intent);
            }
        });

        button_user_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateField()){
                    if (user != null && user.getEmail() != null) {
                        AuthCredential credential2 = EmailAuthProvider.getCredential(user.getEmail(), edit_user_password.getText().toString().trim());
                        user.reauthenticate(credential2).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                    changeContact();
                                else
                                    Toast.makeText(Account_Setting_Mobile.this, "Password error", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
        });
    }

    public boolean validateField(){
        boolean valid = true;
        final String contact = edit_change_mobile.getText().toString().trim();
        final String password = edit_change_mobile.getText().toString().trim();

        if(contact.isEmpty()){
            edit_change_mobile.setError("Field must not be empty!");
            edit_change_mobile.requestFocus();
            valid = false;
        } else {
            edit_change_mobile.setError(null);
        }

        if(password.isEmpty()){
            edit_user_password.setError("Field must not be empty!");
            edit_user_password.requestFocus();
            valid = false;
        } else {
            edit_user_password.setError(null);
        }

        if(contact.length() != 11){
            edit_change_mobile.setError("Enter a valid phone number");
            edit_change_mobile.requestFocus();
            valid = false;
        } else {
            edit_change_mobile.setError(null);
        }

        return valid;
    }

    public void changeContact(){
        final String newContact = edit_change_mobile.getText().toString().trim();
        final ProgressDialog progress = new ProgressDialog(Account_Setting_Mobile.this);
        progress.setTitle("Updating Mobile Number");
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();
        reference.child("phone").setValue(newContact).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progress.hide();
                        if (task.isSuccessful()) {
                            Toast.makeText(Account_Setting_Mobile.this, "Your contact number was successfully updated!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Account_Setting_Mobile.this, Account_Settings.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Account_Setting_Mobile.this, "Contact update failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
