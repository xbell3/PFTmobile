package com.utec.pft.vista;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.utec.pft.R;
import com.utec.pft.controlador.enfermedad.LeerExcel;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;


public class CargarDatos extends AppCompatActivity {

    String idioma = "";
    ImageView labelCargarDatos, btnSeleccionarArchivo = null;
    Button btnCargar;
    TextView txtNombreArchivo, txtCantRegistros, txtHistoricoDescripcion;
    Uri archivoSeleccionado;
    File archivoFinal;
    int estadoCarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_datos);

        idioma = Locale.getDefault().getLanguage();
        txtHistoricoDescripcion = (TextView) findViewById(R.id.txtCargaDescripcion);
        labelCargarDatos = (ImageView) findViewById(R.id.labelCargarDatos);
        btnSeleccionarArchivo = (ImageView) findViewById(R.id.btnSeleccionarArchivo);
        txtNombreArchivo = (TextView) findViewById(R.id.txtNombreArchivo);
        txtCantRegistros = (TextView) findViewById(R.id.txtCantRegistros);
        archivoSeleccionado = null;
        btnCargar = (Button) findViewById(R.id.btnCargar);

        if (idioma.equals("en")) {// SI EL IDIOMA ES INGLES SE CAMBIAN LOS BOTONES
            labelCargarDatos.setImageResource(R.mipmap.labelimportardatosen);
            btnSeleccionarArchivo.setImageResource(R.mipmap.btncargaren);
        }

        btnSeleccionarArchivo.setOnClickListener(new View.OnClickListener() {//Seleciconador de archivo
            @Override
            public void onClick(View view) {
                openFileChooser(view);//Abre seleccionador de archivos
            }
        });

        btnCargar.setOnClickListener(new View.OnClickListener() {//Boton de cargar hacia BD
            @Override
            public void onClick(View view) {
                try {
                    if (estadoCarga == 1) {//Si se realizo la seleccion de archivo correctamente->
                        LeerExcel leer = new LeerExcel(CargarDatos.this);//Hacemos una instancia de LeerExcel y le pasamos el contexto para generar mensajes
                        leer.cargar(archivoFinal);
                        Toast.makeText(CargarDatos.this, R.string.historico_cargaExitosa, Toast.LENGTH_LONG).show();
                    } else {//Sino se muestra toast
                        Toast.makeText(CargarDatos.this, R.string.historico_SeleccioneArchivo, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    int requestCode = 1;


    public void onActivityResult(int requestCode, int resulcode, Intent data) {//metodo que se encarga de seleccionar archivo
        super.onActivityResult(requestCode, resulcode, data);
        Context context = getApplicationContext();
        if (requestCode == requestCode && resulcode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }
            try {
                archivoSeleccionado = data.getData();
                String nombreArchivo = archivoSeleccionado.getPath().split(":")[1];//Se obtiene nombre del archivo
                String extensionArchivo = FilenameUtils.getExtension(nombreArchivo);//Se obtiene la extension del archivo
                if (extensionArchivo.equals("xls")) {//Si la extension es .xls se continua la ejecucion
                    File archivo = new File(Environment.getExternalStorageDirectory() + "/" + nombreArchivo);
                    if (verificarPeso(archivo)) {
                        archivoFinal = archivo;
                        txtNombreArchivo.setText(this.getString(R.string.historico_nombreArchivo) + " " + nombreArchivo);
                        txtCantRegistros.setText(this.getString(R.string.historico_cantidadRegistro) + String.valueOf(cantidadRegistros(archivo)));
                        estadoCarga = 1;
                    }
                } else {
                    Toast.makeText(CargarDatos.this, R.string.historico_extensionArchivo, Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void openFileChooser(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, requestCode);
    }

    private Boolean verificarPeso(File file) {//El peso del archivod debe ser menor a 50mb
        if (file.length() / 1024 >= 0 && file.length() / 1024 <= 10000) {//
            return true;
        } else {
            Toast.makeText(CargarDatos.this, R.string.historico_pesoArchivo, Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public int cantidadRegistros(File archivo) throws IOException {//Devuelve la cantidad de registros que tiene el xls
        FileInputStream fis = new FileInputStream(archivo);
        HSSFWorkbook wb = new HSSFWorkbook(fis);
        HSSFSheet sheet = wb.getSheetAt(0);
        return sheet.getPhysicalNumberOfRows() - 1;
    }

}