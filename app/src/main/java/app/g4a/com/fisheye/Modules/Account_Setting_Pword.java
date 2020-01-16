package app.g4a.com.fisheye.Modules;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.PasswordAuthentication;

import app.g4a.com.fisheye.R;

public class Account_Setting_Pword extends AppCompatActivity {

    ImageView image_back_account_password;
    EditText edit_change_password, edit_password_password, edit_retype_password;
    Button button_password_change;
    TextView text_cancel_password;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account__setting__pword);

        image_back_account_password = findViewById(R.id.imageview_back_account_password);
        edit_change_password = findViewById(R.id.edittext_change_password);
        edit_password_password = findViewById(R.id.edittext_password_password);
        edit_retype_password = findViewById(R.id.edittext_retype_password);
        button_password_change = findViewById(R.id.button_password_change);
        text_cancel_password = findViewById(R.id.textview_cancel_password);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        image_back_account_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Account_Setting_Pword.this,Account_Settings.class);
                startActivity(intent);
            }
        });

        text_cancel_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Account_Setting_Pword.this, Account_Settings.class);
                startActivity(intent);
            }
        });

        button_password_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateFields()) {
                    if (user != null && user.getEmail() != null) {
                        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), edit_password_password.getText().toString().trim());
                        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                    changePassword();
                                else
                                    Toast.makeText(Account_Setting_Pword.this, "Password error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }

    public boolean validateFields() {
        boolean valid = true;

        final String newPass = edit_change_password.getText().toString().trim();
        final String retype = edit_retype_password.getText().toString().trim();
        final String pass = edit_password_password.getText().toString().trim();

        if (newPass.isEmpty()) {
            edit_change_password.setError("Field must not be empty!");
            edit_change_password.requestFocus();
            valid = false;
        } else {
            edit_change_password.setError(null);
        }

        if (newPass.length() < 6) {
            edit_change_password.setError(getString(R.string.input_error_password_length));
            edit_change_password.requestFocus();
            valid = false;
        } else {
            edit_change_password.setError(null);
        }

        if (retype.isEmpty()) {
            edit_retype_password.setError("Field must not be empty!");
            edit_retype_password.requestFocus();
            valid = false;
        } else {
            edit_retype_password.setError(null);
        }

        if(!TextUtils.equals(newPass,retype)){
            edit_retype_password.setError(getString(R.string.input_error_password_match));
            edit_retype_password.requestFocus();
            valid = false;
        }else{
            edit_retype_password.setError(null);
        }

        if (pass.isEmpty()) {
            edit_password_password.setError("Field must not be empty!");
            edit_password_password.requestFocus();
            valid = false;
        } else {
            edit_password_password.setError(null);
        }
        return valid;
    }

        public void changePassword(){
        String newPassword = edit_change_password.getText().toString().trim();
            final ProgressDialog progress = new ProgressDialog(Account_Setting_Pword.this);
            progress.setTitle("Updating Password");
            progress.setMessage("Please wait...");
            progress.setCancelable(false);
            progress.show();
        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progress.hide();
                        if (task.isSuccessful()) {
                            Toast.makeText(Account_Setting_Pword.this, "Password is updated!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Account_Setting_Pword.this, Account_Settings.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Account_Setting_Pword.this, "Failed to update password!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
