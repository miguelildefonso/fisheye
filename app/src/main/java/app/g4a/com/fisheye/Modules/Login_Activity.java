package app.g4a.com.fisheye.Modules;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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

    EditText edit_email, edit_password;
    Button btn_login;
    TextView text_signup, text_forgot_password;
    ProgressBar progress_login;
    ProgressDialog pd;

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    String emailtext, passtext;
    int trials=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        edit_email = findViewById(R.id.edittext_username);
        edit_password = findViewById(R.id.edittext_password);
        btn_login = findViewById(R.id.button_login);
        text_signup = findViewById(R.id.textview_signup);
        text_forgot_password = findViewById(R.id.textview_forgot_password);
        progress_login = findViewById(R.id.progressbar_login);
        pd = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(trials<3)
                    loginUser();
                else
                    openDialog();
            }
        });

        text_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Activity.this,Register_User.class);
                startActivity(intent);
            }
        });

        text_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRecoverPassword();
            }
        });
    }

    private void showRecoverPassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");
        LinearLayout linear = new LinearLayout(this);
        final EditText editemail = new EditText(this);
        editemail.setHint("Enter your email");
        editemail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        editemail.setMinEms(16);

        linear.addView(editemail);
        linear.setPadding(20,20,20,20);
        builder.setView(linear);
        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    String email = editemail.getText().toString().trim();
                    recover(email);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    private void recover(String email)
    {
        showLoadingPrompt(true);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                    Toast.makeText(Login_Activity.this, "Email sent", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(Login_Activity.this, "Email not sent", Toast.LENGTH_SHORT).show();
                    showLoadingPrompt(false);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loginUser()
    {
        emailtext=edit_email.getText().toString();
        passtext=edit_password.getText().toString();

        if(TextUtils.isEmpty(emailtext)){
            edit_email.setError("Field cannot be empty.");
            edit_email.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(passtext)){
            edit_password.setError("Field cannot be empty.");
            edit_password.requestFocus();
            return;
        }

        showLoadingPrompt(true);
        auth.signInWithEmailAndPassword(emailtext,passtext).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    showLoadingPrompt(false);
                    Toast.makeText(Login_Activity.this, "Login failed!" , Toast.LENGTH_SHORT).show();
                    trials++;
                } else {
                    Toast.makeText(Login_Activity.this, "Login success!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login_Activity.this, Select_Pond.class);
                    startActivity(intent);
                    finish();
                    trials=0;
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

    public void openDialog()
    {
        Dialog_Login dialogLogin = new Dialog_Login();
        dialogLogin.show(getSupportFragmentManager(),"dialog login");
    }
}
