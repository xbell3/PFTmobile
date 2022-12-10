package com.utec.pft.vista;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.utec.pft.R;
import com.utec.pft.modelo.Persona.PersonaDTO;

import java.util.Locale;

public class Menu extends AppCompatActivity {

    PersonaDTO personaLogueada;
    Button btnLogOut = null;
    String idioma = "";
    ImageView tituloGstTernera, btnAltaTernera, btnListaTernera, tituloGstEnfermedad, btnAltaHistoricos, btnListaHistoricos, btnLeerCaravana;
    TextView lblNombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //-------------------Inicializando imagenes------------------------
        tituloGstTernera = (ImageView) findViewById(R.id.imgTituloTernera);
        btnAltaTernera = (ImageView) findViewById(R.id.imgNuevaTernera);
        btnListaTernera = (ImageView) findViewById(R.id.imgListaTernera);
        tituloGstEnfermedad = (ImageView) findViewById(R.id.imgTituloEnfermedad);
        btnAltaHistoricos = (ImageView) findViewById(R.id.imgAltaHistorico);
        btnListaHistoricos = (ImageView) findViewById(R.id.imgListaHistorico);
        btnLeerCaravana = (ImageView) findViewById(R.id.imgLeerCaravana);

        //Lee la persona que viene del login para hacer diferentes acciones
        if (getIntent().getExtras() != null) {
            personaLogueada = (PersonaDTO) getIntent().getSerializableExtra("persona");
            Toast mensaje = Toast.makeText(this, "¡ " + Menu.this.getString(R.string.menu_welcome) + " " + personaLogueada.getNombre() + " " + personaLogueada.getApellido() + "!", Toast.LENGTH_LONG);
            mensaje.show();
            lblNombreUsuario = (TextView) findViewById(R.id.lblusuario);
            lblNombreUsuario.setText(personaLogueada.getNombreUsuario());
            if (personaLogueada.getIdRol() == 0) {//administrador
                //tiene todo activado
            } else if (personaLogueada.getIdRol() == 1) {//encargado
                //tiene todo activado
            } else {//personal
                btnAltaTernera.setEnabled(false);
                btnAltaTernera.setBackgroundColor(Color.rgb(242, 148, 148));
                btnAltaHistoricos.setEnabled(false);
                btnAltaHistoricos.setBackgroundColor(Color.rgb(242, 148, 148));
            }
        }

        idioma = Locale.getDefault().getLanguage();//Se obtiene el idioma actual del dispositivo

        if (idioma.equals("en")) {// SI EL IDIOMA ES INGLES SE CAMBIAN LOS BOTONES
            tituloGstTernera.setImageResource(R.mipmap.titulogstterneraen);
            btnAltaTernera.setImageResource(R.mipmap.btnnuevaterneraen);
            btnListaTernera.setImageResource(R.mipmap.btnlistarternerasen);
            tituloGstEnfermedad.setImageResource(R.mipmap.titulogstenfermedaden);
            btnAltaHistoricos.setImageResource(R.mipmap.btningresarhistoricosen);
            btnListaHistoricos.setImageResource(R.mipmap.btnlistarhistoricoen);
            btnLeerCaravana.setImageResource(R.mipmap.btnleercaravanaen);
        }

        //------------------Boton hacia alta ternera--------------------
        btnAltaTernera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, AltaTernera.class);
                startActivity(intent);
            }
        });

        //---------------boton hacia lista de terneras-------------------
        btnListaTernera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, ListaTernera.class);
                startActivity(intent);
            }
        });

        //--------------------boton hacia carga de datos--------------------
        btnAltaHistoricos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, CargarDatos.class);
                startActivity(intent);
            }
        });

        btnListaHistoricos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, ListaHistorico.class);
                startActivity(intent);
            }
        });


        btnLeerCaravana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, DetectarCaravana.class);
                startActivity(intent);
            }
        });


        //------------------------boton de cerrar sesion------------------------
        btnLogOut = (Button) findViewById(R.id.btnCerrarSesion);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}