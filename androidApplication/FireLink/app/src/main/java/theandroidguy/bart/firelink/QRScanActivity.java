package theandroidguy.bart.firelink;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class QRScanActivity extends AppCompatActivity {

    SurfaceView surfaceView;
    CameraSource cameraSource;
    TextView scannedText;
    BarcodeDetector barcodeDetector;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    String positioning;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        positioning = getIntent().getStringExtra("whichpos");

        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    MY_CAMERA_REQUEST_CODE);
        }

        surfaceView = findViewById(R.id.surfaceView);
        scannedText = findViewById(R.id.progressStatusText);

        barcodeDetector = new BarcodeDetector.Builder(getApplicationContext())
                .setBarcodeFormats(Barcode.QR_CODE).build();

        cameraSource = new CameraSource.Builder(getApplicationContext(), barcodeDetector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(640, 640).build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                try {
                    cameraSource.start(holder);
                }catch (IOException e) {

                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrCodes = detections.getDetectedItems();

                if(qrCodes.size() != 0){
                    scannedText.post(new Runnable() {
                        @Override
                        public void run() {
                            cameraSource.stop();
                            //surfaceView.setVisibility(View.INVISIBLE);
                            scannedText.setText("Scanning...");

                            Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(120);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    scannedText.setText(qrCodes.valueAt(0).displayValue);
                                    if(scannedText.getText().toString().length() == 152){
                                        //qr code looks to be valid unless the user found out how we work. then we're fucked lol
                                        Intent intent = new Intent(getBaseContext(), PickDeviceTypeActivity.class);
                                        intent.putExtra("positioning", positioning);
                                        intent.putExtra("deviceScannedKey", scannedText.getText().toString());
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            }, 500);
                        }
                    });
                }
            }
        });
    }


    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera Permission Granted!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Camera Permission Denied!", Toast.LENGTH_LONG).show();
                finish();
            }

        }}//end onRequestPermissionsResult
}
