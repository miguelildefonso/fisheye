package app.g4a.com.fisheye.Modules;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

import app.g4a.com.fisheye.R;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class Settings extends AppCompatActivity {

    ImageView image_back_settings;
    RelativeLayout relative_pond_config, relative_account_settings, relative_about, relative_download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        image_back_settings = findViewById(R.id.imageview_back_settings);
        relative_pond_config = findViewById(R.id.relativelayout_pond_config);
        relative_account_settings = findViewById(R.id.relativelayout_account_settings);
        relative_about = findViewById(R.id.relativelayout_about);
        relative_download = findViewById(R.id.relativelayout_download);

        image_back_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this,MainActivity.class);
                startActivity(intent);
            }
        });

        relative_pond_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this,Configuration_Setting.class);
                startActivity(intent);
            }
        });

        relative_account_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this,Account_Settings.class);
                startActivity(intent);
            }
        });

        relative_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this,About.class);
                startActivity(intent);
            }
        });

        relative_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();
                StorageReference ref = storageRef.child("csv/sensor_readings.csv");

                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String url = uri.toString();
                        downloadFiles(Settings.this,"sensor_readings",".csv",DIRECTORY_DOWNLOADS,url);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });

    }
    public void downloadFiles(Context context, String fileName, String fileExtension, String destinationDirectory, String url){
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context,destinationDirectory, fileName + fileExtension);

        downloadManager.enqueue(request);
    }
}
