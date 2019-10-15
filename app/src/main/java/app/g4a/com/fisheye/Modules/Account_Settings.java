package app.g4a.com.fisheye.Modules;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import app.g4a.com.fisheye.R;

public class Account_Settings extends AppCompatActivity {

    ImageView image_back_account_settings;
    TextView text_change_username, text_change_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account__settings);

        image_back_account_settings = findViewById(R.id.imageview_back_account_settings);
        text_change_username = findViewById(R.id.textview_change_username);
        text_change_password = findViewById(R.id.textview_change_password);

        image_back_account_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Account_Settings.this,Settings.class);
                startActivity(intent);
            }
        });

        text_change_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Account_Settings.this, Account_Setting_Mobile.class);
                startActivity(intent);
            }
        });

        text_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Account_Settings.this,Account_Setting_Pword.class);
                startActivity(intent);
            }
        });
    }
}
