package app.g4a.com.fisheye.Modules;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import app.g4a.com.fisheye.R;

public class Account_Setting_Username extends AppCompatActivity {

    ImageView image_back_account_username;
    EditText edit_change_username, edit_user_password;
    Button button_user_change;
    TextView text_cancel_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account__setting__username);

        image_back_account_username = findViewById(R.id.imageview_back_account_username);
        edit_change_username = findViewById(R.id.edittext_change_username);
        edit_user_password = findViewById(R.id.edittext_user_password);
        button_user_change = findViewById(R.id.button_user_change);
        text_cancel_user = findViewById(R.id.textview_cancel_user);

        image_back_account_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Account_Setting_Username.this,Account_Settings.class);
                startActivity(intent);
            }
        });
    }
}
