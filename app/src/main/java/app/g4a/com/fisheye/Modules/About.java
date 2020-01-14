package app.g4a.com.fisheye.Modules;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import app.g4a.com.fisheye.R;

public class About extends AppCompatActivity {

    ImageView image_back_about;
    TextView text_credits, text_tutorial;
    private static String strabout="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        image_back_about = findViewById(R.id.imageview_back_about);
        text_credits = findViewById(R.id.textview_credits);
        text_tutorial = findViewById(R.id.textview_tutorial);

        image_back_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(About.this,Settings.class);
                startActivity(intent);
            }
        });

        text_credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(About.this);
                View mview = getLayoutInflater().inflate(R.layout.layout_dialog_credits,null);
                builder.setPositiveButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setView(mview);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        text_tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strabout="1";
                Intent intent = new Intent(About.this, Walk_Through.class);
                startActivity(intent);
            }
        });
    }
    public void setAbout(String a){
        this.strabout = a;
    }
    public static String about(){

        return strabout;
    }

}
