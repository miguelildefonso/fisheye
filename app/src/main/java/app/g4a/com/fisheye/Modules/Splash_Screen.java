package app.g4a.com.fisheye.Modules;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import app.g4a.com.fisheye.R;

public class Splash_Screen extends AppCompatActivity {

    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);

        if(isConnected()){
            thread();
        }
        else
            openDialog();


    }

    public void thread(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                for(i=0 ; i<=99;){
                    try{
                        sleep(1000);
                        if(i==99){
                            Intent intent = new Intent(Splash_Screen.this, Login_Activity.class);
                            startActivity(intent);
                            finish();
                        }
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    i+=33;
                }
            }
        };
        thread.start();
    }
    public void openDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(Splash_Screen.this);
        View mview = getLayoutInflater().inflate(R.layout.layout_dialog_check_connection,null);
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setView(mview);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public boolean isConnected(){
        boolean isConnected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState()==NetworkInfo.State.CONNECTED){
            isConnected = true;
        }else
            isConnected = false;
        return isConnected;
    }
}
