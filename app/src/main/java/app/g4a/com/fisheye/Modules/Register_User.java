package app.g4a.com.fisheye.Modules;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import app.g4a.com.fisheye.Models.Users;
import app.g4a.com.fisheye.R;

public class Register_User extends AppCompatActivity {

    EditText edit_name, edit_contact, edit_username_reg, edit_password_reg, edit_email_reg;
    Button btn_register;
    TextView text_cancel_reg;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__user);

        edit_name = findViewById(R.id.edittext_name);
        edit_contact = findViewById(R.id.edittext_contact);
        edit_email_reg = findViewById(R.id.edittext_email_reg);
        edit_username_reg = findViewById(R.id.edittext_username_reg);
        edit_password_reg = findViewById(R.id.edittext_password_reg);
        btn_register = findViewById(R.id.button_register);
        text_cancel_reg = findViewById(R.id.textview_cancel_registration);
        progressBar = findViewById(R.id.progressbar_register);
        mAuth = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        text_cancel_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register_User.this,Login_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void registerUser(){
        final String name = edit_name.getText().toString().trim();
        final String email = edit_email_reg.getText().toString().trim();
        final String username = edit_username_reg.getText().toString().trim();
        String password = edit_password_reg.getText().toString().trim();
        final String phone = edit_contact.getText().toString().trim();

        //validations
        if (name.isEmpty()) {
            edit_name.setError(getString(R.string.input_error_name));
            edit_name.requestFocus();
        }

        if (email.isEmpty()) {
            edit_email_reg.setError(getString(R.string.input_error_email));
            edit_email_reg.requestFocus();
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edit_email_reg.setError(getString(R.string.input_error_email_invalid));
            edit_email_reg.requestFocus();
        }

        if (username.isEmpty()) {
            edit_username_reg.setError(getString(R.string.input_error_username));
            edit_username_reg.requestFocus();
        }

        if (password.isEmpty()) {
            edit_password_reg.setError(getString(R.string.input_error_password));
            edit_password_reg.requestFocus();
        }

        if (password.length() < 6) {
            edit_password_reg.setError(getString(R.string.input_error_password_length));
            edit_password_reg.requestFocus();
        }

        if (phone.isEmpty()) {
            edit_contact.setError(getString(R.string.input_error_phone));
            edit_contact.requestFocus();
        }

        if (phone.length() != 11) {
            edit_contact.setError(getString(R.string.input_error_phone_invalid));
            edit_contact.requestFocus();
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            Users user = new Users(name, username, email, phone);

                            FirebaseDatabase.getInstance().getReference("data").child("users")
                                    .child(username)                                            //FirebaseAuth.getInstance().getCurrentUser().getUid()
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Register_User.this, getString(R.string.registration_success), Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(Register_User.this,Login_Activity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(Register_User.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(Register_User.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
