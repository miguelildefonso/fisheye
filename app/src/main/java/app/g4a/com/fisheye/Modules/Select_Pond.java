package app.g4a.com.fisheye.Modules;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.g4a.com.fisheye.R;

public class Select_Pond extends AppCompatActivity {

    ImageView image_back;
    LinearLayout linear_pond1, linear_pond2, linear_pond3, linear_pond4;
    TextView text_pond1, text_pond2, text_pond3, text_pond4;

    String pondName;

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

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Select_Pond.this,Login_Activity.class);
                startActivity(intent);
            }
        });

        linear_pond1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pondName = text_pond1.getText().toString();
                Intent intent = new Intent(Select_Pond.this,MainActivity.class);
                intent.putExtra("Pond_No",pondName);
                startActivity(intent);
            }
        });

        linear_pond2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pondName = text_pond2.getText().toString();
                Intent intent = new Intent(Select_Pond.this,MainActivity.class);
                intent.putExtra("Pond_No",pondName);
                startActivity(intent);
            }
        });

        linear_pond3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pondName = text_pond3.getText().toString();
                Intent intent = new Intent(Select_Pond.this,MainActivity.class);
                intent.putExtra("Pond_No",pondName);
                startActivity(intent);
            }
        });

        linear_pond4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pondName = text_pond4.getText().toString();
                Intent intent = new Intent(Select_Pond.this,MainActivity.class);
                intent.putExtra("Pond_No",pondName);
                startActivity(intent);
            }
        });
    }
}
