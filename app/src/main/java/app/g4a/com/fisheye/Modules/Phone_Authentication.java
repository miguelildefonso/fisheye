package app.g4a.com.fisheye.Modules;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

import app.g4a.com.fisheye.R;

public class Phone_Authentication extends AppCompatActivity {

    private TextView text_resend, text_username, text_email;
    private Button button_send_otp;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private FirebaseDatabase database;

    private String email, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone__authentication);

        text_username = findViewById(R.id.textview_name_verify);
        text_email = findViewById(R.id.textview_email_verify);
        text_resend = findViewById(R.id.textview_resend_otp);
        button_send_otp = findViewById(R.id.button_submit_otp);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("data").child("users").child(user.getUid());

        email = getIntent().getExtras().getString("email");
        name = getIntent().getExtras().getString("name");

        text_email.setText(email);
        text_username.setText(name);

        user.reload();

        user.sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Phone_Authentication.this, "Verification email was sent to "+ email, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Phone_Authentication.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        user.reload();

        button_send_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user.isEmailVerified()){
                    Toast.makeText(Phone_Authentication.this, "Email is verified!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Phone_Authentication.this, Login_Activity.class);
                    startActivity(intent);
                    finish();
                }
                else
                    Toast.makeText(Phone_Authentication.this, "Check your email for verification first.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        user.reload();
    }
}
