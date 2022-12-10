package com.utec.pft.vista;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.utec.pft.R;

import java.io.IOException;

public class DetectarCaravana extends AppCompatActivity {

    SurfaceView cameraView;
    TextView snigReconocido, SNIG;
    CameraSource cameraSource;
    Button btnDetectar;
    final int RequestCameraPermissionID = 1001;
    String snigDetectado;

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detectar_caravana);

        cameraView = (SurfaceView) findViewById(R.id.surface_view);
        snigReconocido = (TextView) findViewById(R.id.snigDetectado);
        btnDetectar = (Button) findViewById(R.id.btnDetectar);
        SNIG = (TextView) findViewById(R.id.SNIG);

        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
            Log.w("DetectarCaravana", "dependencias no disponibles");
        } else {
            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();
            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                    try {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(DetectarCaravana.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    RequestCameraPermissionID);
                            return;
                        }
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

                }

                @Override
                public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                    cameraSource.stop();
                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {
                    final SparseArray<TextBlock> items = detections.getDetectedItems();
                    if (items.size() != 0) {
                        snigReconocido.post(new Runnable() {
                            @Override
                            public void run() {
                                StringBuilder stringBuilder = new StringBuilder();
                                for (int i = 0; i < items.size(); i++) {
                                    TextBlock item = items.valueAt(i);
                                    stringBuilder.append(item.getValue());
                                    stringBuilder.append("\n");
                                }
                                snigReconocido.setText(stringBuilder.toString());
                                if (stringBuilder.toString().split("\n").length >= 3) {
                                    SNIG.setText(stringBuilder.toString().split("\n")[stringBuilder.toString().split("\n").length - 1]);

                                }

                            }
                        });
                    }

                }
            });
        }

        btnDetectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    snigDetectado = SNIG.getText().toString();
                    if (snigDetectado.length() == 4 && snigDetectado.matches("[0-9]+")) {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(DetectarCaravana.this);
                        alerta.setMessage(DetectarCaravana.this.getString(R.string.reconocer_SnigLeido) + snigDetectado)
                                .setCancelable(true)
                                .setPositiveButton(DetectarCaravana.this.getString(R.string.reconocer_si), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        AlertDialog.Builder opciones = new AlertDialog.Builder(DetectarCaravana.this);
                                        String[] opc = {DetectarCaravana.this.getString(R.string.nuevaTernera),
                                                DetectarCaravana.this.getString(R.string.listarTenera),
                                                DetectarCaravana.this.getString(R.string.listarHistorico)};
                                        opciones.setItems(opc, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                if (i == 0) {//alta ternera
                                                    Intent intent = new Intent(DetectarCaravana.this, AltaTernera.class);
                                                    intent.putExtra("Snig", snigDetectado);
                                                    startActivity(intent);
                                                } else if (i == 1) {//datos de ternera
                                                    Intent intent = new Intent(DetectarCaravana.this, ListaTernera.class);
                                                    intent.putExtra("Snig", snigDetectado);
                                                    startActivity(intent);
                                                } else if (i == 2) {//datos historicos
                                                    Intent intent = new Intent(DetectarCaravana.this, ListaHistorico.class);
                                                    intent.putExtra("Snig", snigDetectado);
                                                    startActivity(intent);
                                                }
                                            }
                                        });
                                        AlertDialog titulo = opciones.create();
                                        titulo.setTitle(R.string.reconocer_opciones);
                                        titulo.show();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                        AlertDialog titulo = alerta.create();
                        titulo.setTitle(R.string.reconocer_confirmar);
                        titulo.show();
                    } else {//si tiene mas de 4 digitos
                        Toast.makeText(DetectarCaravana.this, R.string.reconocer_largoSnig, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {

                }
            }
        });

    }
}