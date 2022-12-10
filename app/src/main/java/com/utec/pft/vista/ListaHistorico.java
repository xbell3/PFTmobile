package com.utec.pft.vista;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.utec.pft.R;
import com.utec.pft.api.ServiceApiEnfermedad;
import com.utec.pft.modelo.Enfermerdad.EnfermedadHistoricaDTO;
import com.utec.pft.modelo.adapter.HistoricoAdapter;
import com.utec.pft.util.ConnectionRest;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaHistorico extends AppCompatActivity {

    String idioma = "";
    ImageView labelListaHistorico = null;
    List<EnfermedadHistoricaDTO> historicos = new ArrayList<EnfermedadHistoricaDTO>();
    HistoricoAdapter adaptador = null;
    ListView lstHistoricos = null;
    EditText txtBuscarHistorico = null;
    Button btnBuscar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_historico);

        labelListaHistorico = (ImageView) findViewById(R.id.labelListarHistorico);
        btnBuscar = (Button) findViewById(R.id.btnBuscarHistorico);
        idioma = Locale.getDefault().getLanguage();

        adaptador = new HistoricoAdapter(ListaHistorico.this, R.layout.activity_lista_historico, historicos);
        lstHistoricos = (ListView) findViewById(R.id.lstHistoricos);
        lstHistoricos.setAdapter(adaptador);
        cargaHistoricos();
        adaptador.notifyDataSetChanged();


        //Si se llama a la actividad desde la lectura de caravana se ingresa el snig en el campo de texto
        if (getIntent().getExtras() != null) {
            try {
                txtBuscarHistorico = (EditText) findViewById(R.id.txtBuscarSnigHistorico);
                txtBuscarHistorico.setText(getIntent().getStringExtra("Snig"));
                filtrarHistoricoxSnig(Integer.valueOf(txtBuscarHistorico.getText().toString()));
            } catch (Exception e) {

            }
        }


        if (idioma.equals("en")) {// SI EL IDIOMA ES INGLES SE CAMBIAN LOS BOTONES
            labelListaHistorico.setImageResource(R.mipmap.labellistarhistoricosen);
            btnBuscar.setText(R.string.list_search);
        }



        txtBuscarHistorico = (EditText) findViewById(R.id.txtBuscarSnigHistorico);

        //Boton de filtrar
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String snig = txtBuscarHistorico.getText().toString();
                if (!snig.equals("")) {//si el campo de texto de filtrado de snig esta relleno
                    filtrarHistoricoxSnig(Integer.valueOf(snig));
                } else {//sino lista todos los registros
                    lstHistoricos.setAdapter(adaptador);
                    cargaHistoricos();
                }
                adaptador.notifyDataSetChanged();
            }
        });
    }

    public void cargaHistoricos() {//lista los datos historicos
        ServiceApiEnfermedad api = ConnectionRest.getConnection().create(ServiceApiEnfermedad.class);
        Call<List<EnfermedadHistoricaDTO>> call = api.listarHistoricos();
        call.enqueue(new Callback<List<EnfermedadHistoricaDTO>>() {
            @Override
            public void onResponse(Call<List<EnfermedadHistoricaDTO>> call, Response<List<EnfermedadHistoricaDTO>> response) {
                if (response.isSuccessful()) {
                    List<EnfermedadHistoricaDTO> datos = response.body();
                    historicos.clear();
                    historicos.addAll(datos);
                    adaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<EnfermedadHistoricaDTO>> call, Throwable t) {
            }
        });
    }

    public void filtrarHistoricoxSnig(int snig) {//filtra los datos historicos filtrados por snig de ternera
        ServiceApiEnfermedad api = ConnectionRest.getConnection().create(ServiceApiEnfermedad.class);
        Call<List<EnfermedadHistoricaDTO>> call = api.listarHistoricosxSnig(snig);
        call.enqueue(new Callback<List<EnfermedadHistoricaDTO>>() {
            @Override
            public void onResponse(Call<List<EnfermedadHistoricaDTO>> call, Response<List<EnfermedadHistoricaDTO>> response) {
                if (response.code() != 204 && response.body().size() != 0) {
                    List<EnfermedadHistoricaDTO> datos = response.body();
                    historicos.clear();
                    historicos.addAll(datos);
                    adaptador.notifyDataSetChanged();
                } else {
                    Toast.makeText(ListaHistorico.this, ListaHistorico.this.getString(R.string.historicoList_snigNoEncontrado) + snig, Toast.LENGTH_LONG).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<List<EnfermedadHistoricaDTO>> call, Throwable t) {
            }
        });
    }

}