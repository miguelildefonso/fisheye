package app.g4a.com.fisheye.Modules;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import app.g4a.com.fisheye.R;

public class Splash_Screen extends AppCompatActivity {

    ProgressBar progressSplash;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);

        progressSplash = findViewById(R.id.progressBar_splash);
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                for(i=0 ; i<=100;){
                    try{
                        sleep(1000);
                        if(i==100){
                            Intent intent = new Intent(Splash_Screen.this,Login_Activity.class);
                            startActivity(intent);
                            finish();
                        }
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    progressSplash.setProgress(i);
                    i+=10;
                }
            }
        };
        thread.start();
    }
}
