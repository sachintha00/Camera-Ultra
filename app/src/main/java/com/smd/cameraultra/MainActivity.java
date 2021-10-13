package com.smd.cameraultra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraX;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.opengl.EGLExt;
import android.opengl.Matrix;
import android.os.Bundle;
import android.util.Rational;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.ViewGroup;

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
        CameraX.unbindAll();

        Rational aspectRetio = new Rational(textureView.getWidth(),textureView.getHeight());
        Size screen = new Size(textureView.getWidth(),textureView.getHeight());

        PreviewConfig pConfig = new PreviewConfig.Builder()
                .setTargetAspectRatio(aspectRetio)
                .setTargetResolution(screen).build();
        Preview preview = new Preview(pConfig);

        preview.setOnPreviewOutputUpdateListener(
                new Preview.OnPreviewOutputUpdateListener() {
                    @Override
                    public void onUpdated(Preview.PreviewOutput output) {
                        ViewGroup parent = (ViewGroup) textureView.getParent();
                        parent.removeView(textureView);
                        parent.addView(textureView);

                        textureView.setSurfaceTexture(output.getSurfaceTexture());
                        updateTranform();
                    }
                }
        );
    }

    private void updateTranform() {
        Matrix matrix = new Matrix();
        float w = textureView.getMeasuredWidth();
        float h = textureView.getMeasuredHeight();

        float cx = w/2f;
        float cy = h/2f;

        int rotaionDgr;
        int rotation = (int) textureView.getRotation();

        switch (rotation){
            case Surface.ROTATION_0:
                rotaionDgr = 0;
                break;
            case Surface.ROTATION_90:
                rotaionDgr = 90;
                break;
            case Surface.ROTATION_180:
                rotaionDgr = 180;
                break;
            case Surface.ROTATION_270:
                rotaionDgr = 270;
                break;
            default:
                return;
        }

//        matrix.postRotate((float)rotaionDgr, cx,cy);
//        textureView.setTransform(mx);
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