package com.smd.cameraultra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.TextureView;

public class MainActivity extends AppCompatActivity {

    private int REQUEST_CODE_PERMISSIONS = 101;
    private String[] REQUIRED_PERMISSION = new String[]{"" +
            "android.permission.CAMERA, " +
            "android.permission.WRITE_EXTERNAL_STORAGE" +
            ""};

    TextureView textureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textureView = (TextureView)findViewById(R.id.view_finder);
        if(allPermissionGranted()){
            startCamera();
        }
        else{
            ActivityCompat.requestPermissions(this,REQUIRED_PERMISSION,REQUEST_CODE_PERMISSIONS);
        }
    }

    private void startCamera() {
    }

    private boolean allPermissionGranted() {
        for(String permission : REQUIRED_PERMISSION){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }
}