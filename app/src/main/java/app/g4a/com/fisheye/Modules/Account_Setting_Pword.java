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

public class Account_Setting_Pword extends AppCompatActivity {

    ImageView image_back_account_password;
    EditText edit_change_password, edit_password_password, edit_retype_password;
    Button button_password_change;
    TextView text_cancel_password;

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

        image_back_account_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Account_Setting_Pword.this,Account_Settings.class);
                startActivity(intent);
            }
        });
    }
}
