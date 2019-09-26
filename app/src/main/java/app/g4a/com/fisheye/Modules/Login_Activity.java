package app.g4a.com.fisheye.Modules;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import app.g4a.com.fisheye.R;

public class Login_Activity extends AppCompatActivity {

    EditText edit_username, edit_password;
    Button btn_login;
    TextView text_signup, text_forgot_password;
    ProgressBar progress_login;

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    String emailtext, usertext, passtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        edit_username = findViewById(R.id.edittext_username);
        edit_password = findViewById(R.id.edittext_password);
        btn_login = findViewById(R.id.button_login);
        text_signup = findViewById(R.id.textview_signup);
        text_forgot_password = findViewById(R.id.textview_forgot_password);
        progress_login = findViewById(R.id.progressbar_login);
        auth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        text_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Activity.this,Register_User.class);
                startActivity(intent);
            }
        });
    }

    public void loginUser()
    {
        usertext=edit_username.getText().toString();
        passtext=edit_username.getText().toString();
        if(TextUtils.isEmpty(emailtext)){
            edit_username.setError("Field cannot be empty.");
            edit_username.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(passtext)){
            edit_password.setError("Field cannot be empty.");
            edit_password.requestFocus();
            return;
        }


        showLoadingPrompt(true);
        getEmail();
        auth.signInWithEmailAndPassword(emailtext,passtext).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    showLoadingPrompt(false);
                    Toast.makeText(Login_Activity.this, "Login failed!" , Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Login_Activity.this, "Login success!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login_Activity.this, Select_Pond.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void showLoadingPrompt(boolean isShown) {
        if (isShown) {
            progress_login.bringToFront();
            btn_login.setEnabled(false);
            progress_login.setVisibility(View.VISIBLE);
        } else {
            progress_login.bringToFront();
            btn_login.setEnabled(true);
            progress_login.setVisibility(View.GONE);
        }
    }

    public void getEmail()
    {
        reference = FirebaseDatabase.getInstance().getReference().child("data").child("users").child("username");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                emailtext= dataSnapshot.child("email").getValue().toString();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
