package com.utec.pft.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.utec.pft.util.ConnectionRest;

import com.utec.pft.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CambiarIP extends AppCompatActivity {

    EditText ip;
    Button cambiarIp;
    ConnectionRest cn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_ip);

        ip=(EditText) findViewById(R.id.txtIp);
        cambiarIp=(Button) findViewById(R.id.btnCambiarIp);

        cambiarIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ValidateIP(ip.getText().toString())){
                    cn=new ConnectionRest();
                    cn.setIp(ip.getText().toString());
                    Toast.makeText(CambiarIP.this,"IP CAMBIADA", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(CambiarIP.this,"IP MAL INGRESADA", Toast.LENGTH_LONG).show();
                }
            }
        });



    }

    public static boolean ValidateIP(String input_IP)
    {
        String numRange = "(\\d{1,2}|(0|1)\\" + "d{2}|2[0-4]\\d|25[0-5])" + "\\."
                + "(\\d{1,2}|(0|1)\\" + "d{2}|2[0-4]\\d|25[0-5])" + "\\."
                + "(\\d{1,2}|(0|1)\\" + "d{2}|2[0-4]\\d|25[0-5])" + "\\."
                + "(\\d{1,2}|(0|1)\\" + "d{2}|2[0-4]\\d|25[0-5])";

        Pattern ip_pattern = Pattern.compile(numRange);
        Matcher match= ip_pattern.matcher(input_IP);
        return match.matches();
    }

}