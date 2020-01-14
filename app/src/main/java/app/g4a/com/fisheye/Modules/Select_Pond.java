package app.g4a.com.fisheye.Modules;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import app.g4a.com.fisheye.Models.UserLog;
import app.g4a.com.fisheye.R;

public class Select_Pond extends AppCompatActivity {

    ImageView image_back;
    LinearLayout linear_pond1, linear_pond2, linear_pond3, linear_pond4;
    TextView text_pond1, text_pond2, text_pond3, text_pond4;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    UserLog log;

    private static String pondName;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__pond);

        image_back = findViewById(R.id.imageview_back);
        linear_pond1 = findViewById(R.id.linearlayout_pond1);
        linear_pond2 = findViewById(R.id.linearlayout_pond2);
        linear_pond3 = findViewById(R.id.linearlayout_pond3);
        linear_pond4 = findViewById(R.id.linearlayout_pond4);
        text_pond1 = findViewById(R.id.textview_pond1);
        text_pond2 = findViewById(R.id.textview_pond2);
        text_pond3 = findViewById(R.id.textview_pond3);
        text_pond4 = findViewById(R.id.textview_pond4);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        uid = user.getUid();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("data");

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog();
            }
        });

        linear_pond1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pondName = text_pond1.getText().toString();
                saveUserLog();
                Intent intent = new Intent(Select_Pond.this,MainActivity.class);
                startActivity(intent);
            }
        });

        linear_pond2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*pondName = text_pond2.getText().toString();
                saveUserLog();
                Intent intent = new Intent(Select_Pond.this,MainActivity.class);
                startActivity(intent);*/
            }
        });

        linear_pond3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*pondName = text_pond3.getText().toString();
                saveUserLog();
                Intent intent = new Intent(Select_Pond.this,MainActivity.class);
                startActivity(intent);*/
            }
        });

        linear_pond4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*pondName = text_pond4.getText().toString();
                saveUserLog();
                Intent intent = new Intent(Select_Pond.this,MainActivity.class);
                startActivity(intent);*/
            }
        });
    }
    public static String getString(){
        return pondName;
    }

    public void saveUserLog(){
        log = new UserLog(uid,pondName);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("data").child("user_log").child("currentUser");
        mDatabase.setValue(log);
    }

    private void alertDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Are you sure you want to logout?");
        dialog.setTitle("Confirm Logout");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Select_Pond.this,Login_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        dialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }
}
