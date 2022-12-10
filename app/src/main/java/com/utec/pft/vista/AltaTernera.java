package com.utec.pft.vista;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.utec.pft.R;
import com.utec.pft.api.ServiceApiTernera;
import com.utec.pft.controlador.ConstructorFecha;
import com.utec.pft.modelo.Ternera.RazaDTO;
import com.utec.pft.modelo.Ternera.TerneraDTO;
import com.utec.pft.util.ConnectionRest;
import com.utec.pft.util.DatePickerFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AltaTernera extends AppCompatActivity {

    String idioma = "";
    ImageView lblAltaTernera = null;


    //CAMPOS DE TEXTO
    EditText txtidLocal;
    EditText txtSnig;
    EditText txtSnigMadre;
    EditText txtSnigPadre;
    EditText txtFecNac = null;
    EditText txtPesoNac;
    Spinner cbxRaza = null;
    Spinner cbxTipoParto = null;

    Button btnAlta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_ternera);

        //Si se llama a la actividad desde la lectura de caravana se ingresa el snig en el campo de texto
        if (getIntent().getExtras() != null) {
            try {
                txtSnig = (EditText) findViewById(R.id.txtSnig);
                txtSnig.setText(getIntent().getStringExtra("Snig"));
            } catch (Exception e) {

            }
        }

        idioma = Locale.getDefault().getLanguage();

        lblAltaTernera = (ImageView) findViewById(R.id.labelRegistrarTernera);
        if (idioma.equals("en")) {// SI EL IDIOMA ES INGLES SE CAMBIAN LOS BOTONES
            lblAltaTernera.setImageResource(R.mipmap.labelregistrarterneraen);
        }

        txtFecNac = (EditText) findViewById(R.id.txtfecNac);
        txtFecNac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//Selecciona la fecha
                switch (view.getId()) {
                    case R.id.txtfecNac:
                        showDatePickerDialog();
                        break;
                }
            }
        });

        //Se rellena el ComboBox de razas
        cbxRaza = (Spinner) findViewById(R.id.cbxRaza);
        List<String> ra = rellenarCbxRaza();
        ra.add(this.getString(R.string.ternera_seleccionar));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ra);
        cbxRaza.setAdapter(adapter);

        //se rellena el ComboBox de tipos de parto
        cbxTipoParto = (Spinner) findViewById(R.id.cbxTipoParto);
        String[] TiposPartos = {"Cesarea", "Natural"};
        ArrayAdapter<String> adapterTipoParto = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, TiposPartos);
        cbxTipoParto.setAdapter(adapterTipoParto);

        btnAlta = (Button) findViewById(R.id.btnAlta);
        try {
            btnAlta.setOnClickListener(new View.OnClickListener() {//Boton alta de ternera
                @Override
                public void onClick(View view) {
                    String raza = cbxRaza.getSelectedItem().toString();
                    String tipoParto = cbxTipoParto.getSelectedItem().toString();
                    AltaTerneraa(crearTernera(), raza, tipoParto);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public TerneraDTO crearTernera() {//Metodo que obtiene los datos del formulario
        try {
            txtidLocal = (EditText) findViewById(R.id.txtidLocal);
            txtSnig = (EditText) findViewById(R.id.txtSnig);
            txtSnigMadre = (EditText) findViewById(R.id.txtSnigMadre);
            txtSnigPadre = (EditText) findViewById(R.id.txtSnigPadre);
            txtPesoNac = (EditText) findViewById(R.id.txtPesoNac);

            //-----------------validaciones----------------------
            if (txtidLocal.getText().toString().equals("")) {
                txtidLocal.setError(this.getString(R.string.userEmpty));
                txtidLocal.requestFocus();
            }else if (txtidLocal.getText().toString().length()<4){
                txtidLocal.setError(this.getString(R.string.ternera_minimoSnig));
                txtidLocal.requestFocus();
            } else if (txtSnig.getText().toString().equals("")) {
                txtSnig.setError(this.getString(R.string.userEmpty));
                txtSnig.requestFocus();
            }else if (txtSnig.getText().toString().length()<4){
                txtSnig.setError(this.getString(R.string.ternera_minimoSnig));
                txtSnig.requestFocus();
            } else if (txtSnigMadre.getText().toString().equals("")) {
                txtSnigMadre.setError(this.getString(R.string.userEmpty));
                txtSnigMadre.requestFocus();
            }else if(txtSnigMadre.getText().toString().length()<4) {
                txtSnigMadre.setError(this.getString(R.string.ternera_minimoSnig));
                txtSnigMadre.requestFocus();
            }else if(!(txtSnigPadre.getText().toString().equals(""))  && txtSnigPadre.getText().toString().length()<4 ){
                txtSnigPadre.setError(this.getString(R.string.ternera_minimoSnig));
                txtSnigPadre.requestFocus();
            } else if (txtFecNac.getText().toString().equals("")) {
                txtFecNac.setError(this.getString(R.string.userEmpty));
                txtFecNac.requestFocus();
            }else if(ConstructorFecha.stringToDate(txtFecNac.getText().toString()).getTime()>System.currentTimeMillis()){
                Toast.makeText(AltaTernera.this, AltaTernera.this.getString(R.string.ternera_fechaFutura), Toast.LENGTH_LONG).show();
                txtFecNac.requestFocus();
            } else if (txtPesoNac.getText().toString().equals("")) {
                txtPesoNac.setError(this.getString(R.string.userEmpty));
                txtPesoNac.requestFocus();
            } else if (cbxRaza.getSelectedItem().toString().equals(this.getString(R.string.ternera_seleccionar))) {
                Toast.makeText(AltaTernera.this, AltaTernera.this.getString(R.string.ternera_selectRaza), Toast.LENGTH_LONG).show();
            } else {//Si pasa todas las validaciones se hace crea el objeto ternera
                String raza = cbxRaza.getSelectedItem().toString();
                String tipoParto = cbxTipoParto.getSelectedItem().toString();
                TerneraDTO terneraNueva = new TerneraDTO();
                terneraNueva.setIdLocal(Integer.valueOf(txtidLocal.getText().toString()));
                terneraNueva.setSnig(Integer.valueOf(txtSnig.getText().toString()));
                terneraNueva.setSnigMadre(Integer.valueOf(txtSnigMadre.getText().toString()));
                if (!txtSnigPadre.getText().toString().equals("")) {
                    terneraNueva.setSnigPadre((Integer) Integer.valueOf(txtSnigPadre.getText().toString()));
                } else {
                    terneraNueva.setSnigPadre(null);
                }
                String fecNac = txtFecNac.getText().toString();
                terneraNueva.setFecNac(ConstructorFecha.stringToDate(fecNac));
                terneraNueva.setEstado(1);
                terneraNueva.setPesoNac(Float.valueOf(txtPesoNac.getText().toString()));
                return terneraNueva;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(AltaTernera.this, AltaTernera.this.getString(R.string.ternera_camposVacios), Toast.LENGTH_LONG).show();
        }
        return null;
    }


    public void AltaTerneraa(TerneraDTO terneraDTO, String nombreRaza, String tipoParto) {//metodo que hace el alta en la BD
        ServiceApiTernera api = ConnectionRest.getConnection().create(ServiceApiTernera.class);
        Call<TerneraDTO> call = api.altaTernera(terneraDTO, nombreRaza, tipoParto);
        call.enqueue(new Callback<TerneraDTO>() {
            @Override
            public void onResponse(Call<TerneraDTO> call, Response<TerneraDTO> response) {
                if (response.code() == 200) {
                    Toast.makeText(AltaTernera.this, AltaTernera.this.getString(R.string.ternera_altaTerneraExito), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AltaTernera.this, AltaTernera.this.getString(R.string.ternera_altaTerneraError), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TerneraDTO> call, Throwable t) {
            }
        });
    }

    public void showDatePickerDialog() {//pickeador de fecha
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate;
                if (month + 1 < 10) {//+1 porque Enero es 0
                    selectedDate = day + "/0" + (month + 1) + "/" + year;
                } else {
                    selectedDate = day + "/" + (month + 1) + "/" + year;
                }
                txtFecNac.setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public List<String> rellenarCbxRaza() {//Devuelve lista de razas obtenidas de BD
        ServiceApiTernera api = ConnectionRest.getConnection().create(ServiceApiTernera.class);
        Call<List<RazaDTO>> call = api.listarRazas();
        List<String> razasNombre = new ArrayList<>();
        call.enqueue(new Callback<List<RazaDTO>>() {
            @Override
            public void onResponse(Call<List<RazaDTO>> call, Response<List<RazaDTO>> response) {
                List<RazaDTO> listaRazas = response.body();
                for (RazaDTO rd : listaRazas) {
                    razasNombre.add(rd.getRaza());
                }
            }

            @Override
            public void onFailure(Call<List<RazaDTO>> call, Throwable t) {
            }
        });
        return razasNombre;
    }

}

