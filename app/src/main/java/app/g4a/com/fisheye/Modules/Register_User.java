package app.g4a.com.fisheye.Modules;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.FirebaseDatabase;

import app.g4a.com.fisheye.Models.Users;
import app.g4a.com.fisheye.R;

public class Register_User extends AppCompatActivity {

    EditText edit_name, edit_contact, edit_password_reg, edit_password_retype_reg, edit_email_reg;
    Button btn_register;
    TextView text_cancel_reg;
    private String status;

    private FirebaseAuth mAuth;
    private static String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__user);

        edit_name = findViewById(R.id.edittext_name);
        edit_contact = findViewById(R.id.edittext_contact);
        edit_email_reg = findViewById(R.id.edittext_email_reg);
        edit_password_reg = findViewById(R.id.edittext_password_reg);
        edit_password_retype_reg = findViewById(R.id.edittext_retype_password_reg);
        btn_register = findViewById(R.id.button_register);
        text_cancel_reg = findViewById(R.id.textview_cancel_registration);
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
        phone = edit_contact.getText().toString();
    }

    public boolean validateFields(){
        final boolean[] valid = {true};

        final String name = edit_name.getText().toString().trim();
        final String email = edit_email_reg.getText().toString().trim();
        final String password = edit_password_reg.getText().toString().trim();
        final String rePassword = edit_password_retype_reg.getText().toString().trim();
        final String phone = edit_contact.getText().toString().trim();

        if (name.isEmpty()) {
            edit_name.setError(getString(R.string.input_error_name));
            edit_name.requestFocus();
            valid[0] = false;
        }else{
            edit_name.setError(null);
        }

        if (email.isEmpty()) {
            edit_email_reg.setError(getString(R.string.input_error_email));
            edit_email_reg.requestFocus();
            valid[0] = false;
        }else{
            edit_email_reg.setError(null);
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edit_email_reg.setError(getString(R.string.input_error_email_invalid));
            edit_email_reg.requestFocus();
            valid[0] = false;
        }else{
            edit_email_reg.setError(null);
        }

        if (password.isEmpty()) {
            edit_password_reg.setError(getString(R.string.input_error_password));
            edit_password_reg.requestFocus();
            valid[0] = false;
        }else{
            edit_password_reg.setError(null);
        }

        if (password.length() < 6) {
            edit_password_reg.setError(getString(R.string.input_error_password_length));
            edit_password_reg.requestFocus();
            valid[0] = false;
        }

        if(!TextUtils.equals(password,rePassword)){
            edit_password_retype_reg.setError(getString(R.string.input_error_password_match));
            edit_password_retype_reg.requestFocus();
            valid[0] = false;
        }else{
            edit_password_retype_reg.setError(null);
        }

        if (phone.isEmpty()) {
            edit_contact.setError(getString(R.string.input_error_phone));
            edit_contact.requestFocus();
            valid[0] = false;
        }else{
            edit_contact.setError(null);
        }

        if (phone.length() != 11) {
            edit_contact.setError(getString(R.string.input_error_phone_invalid));
            edit_contact.requestFocus();
            valid[0] = false;
        }else{
            edit_contact.setError(null);
        }

        return valid[0];
    }

    private void registerUser() {
        if (validateFields() == true) {
            final String name = edit_name.getText().toString().trim();
            final String email = edit_email_reg.getText().toString().trim();
            final String password = edit_password_reg.getText().toString().trim();
            final String rePassword = edit_password_retype_reg.getText().toString().trim();
            final String phone = edit_contact.getText().toString().trim();

            final ProgressDialog progress = new ProgressDialog(Register_User.this);
            progress.setTitle("Registering");
            progress.setMessage("Please wait...");
            progress.setCancelable(false);
            progress.show();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Users user = new Users(name, email, phone,"1");
                                FirebaseDatabase.getInstance().getReference("data").child("users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        progress.hide();
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Register_User.this, getString(R.string.registration_success), Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(Register_User.this, Phone_Authentication.class);
                                            intent.putExtra("email",email);
                                            intent.putExtra("name",name);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(Register_User.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                if(task.getException().getMessage().equals("The email address is already in use by another account.")){
                                    edit_email_reg.setError(getString(R.string.input_error_duplicate_email));
                                    edit_email_reg.requestFocus();
                                }
                                Toast.makeText(Register_User.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                progress.hide();
                            }
                        }
                    });

        }
    }

    public static String getString(){
        return phone;
    }
}
