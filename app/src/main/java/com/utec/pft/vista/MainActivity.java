package com.utec.pft.vista;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.utec.pft.R;
import com.utec.pft.api.ServiceApiPersona;
import com.utec.pft.modelo.Persona.PersonaDTO;
import com.utec.pft.util.ConnectionRest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btnIngresar= null;
    int REQUEST_CODE = 200;
    TextView txtUtekne;
    int contadorSecreto=0;
    int contadorIp=0;
    ImageView logo;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        solicitarPermisos();

        btnIngresar = (Button) findViewById(R.id.btnIngresar);//ACCION AL APRETAR BOTON
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//BOTON INGRESAR
                loguear(view);
            }
        });

        txtUtekne=(TextView) findViewById(R.id.txtUtekne);
        txtUtekne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contadorSecreto++;
                if(contadorSecreto==5){
                    Intent intent = new Intent(MainActivity.this, Menu.class);
                    startActivity(intent);
                }
            }
        });

        logo=(ImageView) findViewById(R.id.imgLogo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contadorIp++;
                if(contadorIp==20){
                    Intent intent = new Intent(MainActivity.this, CambiarIP.class);
                    startActivity(intent);
                }
            }
        });

    }

    public void loguear(View view) {//Lee los campos de texto y los manda al metodo de login
        EditText txtNombreUsuario = (EditText) findViewById(R.id.txtNombreUsuario);
        TextInputEditText txtContrasenia = (TextInputEditText) findViewById(R.id.txtInputContrasenia);
        String nombreUsuario = txtNombreUsuario.getText().toString();
        String contrasenia = txtContrasenia.getText().toString();

        if (!nombreUsuario.equals("") && !contrasenia.equals("")) {// Si no estan vacios los campos sigue
            login(nombreUsuario, contrasenia);

        } else if (nombreUsuario.equals("")) {//si esta vacio el nombre de usuario da error
            txtNombreUsuario.setError(this.getString(R.string.userEmpty));
            Toast.makeText(MainActivity.this, MainActivity.this.getString(R.string.login_incomplete), Toast.LENGTH_LONG).show();

        } else {//si esta vacio la contraseña da error
            txtContrasenia.setError(this.getString(R.string.userEmpty));
            Toast.makeText(MainActivity.this, MainActivity.this.getString(R.string.login_incomplete), Toast.LENGTH_SHORT).show();
        }
    }

    public void login(String nombreUsuario, String contrasenia) {//Metodo que se comunica con el servidor
        try {
            ServiceApiPersona api = ConnectionRest.getConnection().create(ServiceApiPersona.class);
            Call<PersonaDTO> call = api.login(nombreUsuario, contrasenia);
            call.enqueue(new Callback<PersonaDTO>() {
                @Override
                public void onResponse(Call<PersonaDTO> call, Response<PersonaDTO> response) {
                    if (response.body() != null) {//Si el cuerpo de la respuesta no es nulo(devuelve una persona) envia al menu pasando la persona
                        PersonaDTO persona = response.body();
                        Intent intent = new Intent(MainActivity.this, Menu.class);
                        intent.putExtra("persona", persona);
                        startActivity(intent);
                        finish();
                        return;
                    } else {//Si el cuerpo de la respuesta esta vacio, avisa por medio de un mensaje
                        Toast.makeText(MainActivity.this, MainActivity.this.getString(R.string.login_incorrect), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<PersonaDTO> call, Throwable t) {//Si falla muestra error
                    Toast.makeText(MainActivity.this, MainActivity.this.getString(R.string.login_incorrect), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {//no hay conexion con el servidor
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void solicitarPermisos() {
        int permisoCamara = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        int permisoAlmacenamiento1 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.MANAGE_EXTERNAL_STORAGE);
        int permisoAlmacenamiento2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int permisoAlmacenamiento3 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        boolean acedido = permisoCamara == PackageManager.PERMISSION_GRANTED && permisoAlmacenamiento1 == PackageManager.PERMISSION_GRANTED && permisoAlmacenamiento2 == PackageManager.PERMISSION_GRANTED && permisoAlmacenamiento3 == PackageManager.PERMISSION_GRANTED;
        if (acedido) {
        } else {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.MANAGE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        }

    }


}