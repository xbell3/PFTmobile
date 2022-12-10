package com.utec.pft.vista;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.utec.pft.R;
import com.utec.pft.api.ServiceApiTernera;
import com.utec.pft.controlador.servicios.ClientResponse;
import com.utec.pft.modelo.Ternera.TerneraDTO;
import com.utec.pft.modelo.adapter.TerneraAdapter;
import com.utec.pft.util.ConnectionRest;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaTernera extends AppCompatActivity {

    String idioma = "";
    ImageView labelListaTernera = null;

    List<TerneraDTO> terneras = new ArrayList<TerneraDTO>();
    TerneraAdapter adaptador = null;
    ListView lstTerneras = null;
    EditText txtBuscarSnig;
    Button btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ternera);

        labelListaTernera = (ImageView) findViewById(R.id.labelListarTernera);
        btnBuscar = (Button) findViewById(R.id.btnBuscar);
        idioma = Locale.getDefault().getLanguage();

        adaptador = new TerneraAdapter(ListaTernera.this, android.R.layout.activity_list_item, terneras);
        lstTerneras = (ListView) findViewById(R.id.lstHistoricos);
        lstTerneras.setAdapter(adaptador);
        cargaTerneras();
        adaptador.notifyDataSetChanged();


        lstTerneras.setOnItemClickListener(new AdapterView.OnItemClickListener() {//Click en el listado
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//obtiene el item y lo convierte a TerneraDTO
                TerneraDTO ternera = (TerneraDTO)parent.getItemAtPosition(position);
                Integer snig=ternera.getSnig();//obtenemos el SNIG del objeto
                borrarTernera(snig);//y lo mandamos hacia el metodo de borrar
            }
        });


        //Si se llama a la actividad desde la lectura de caravana se ingresa el snig en el campo de texto
        if (getIntent().getExtras() != null) {
            try {
                txtBuscarSnig = (EditText) findViewById(R.id.txtBuscarSnig);
                txtBuscarSnig.setText(getIntent().getStringExtra("Snig"));
                filtrarTernera(Integer.valueOf(txtBuscarSnig.getText().toString()));
            } catch (Exception e) {

            }
        }
        if (idioma.equals("en")) {// SI EL IDIOMA ES INGLES SE CAMBIAN LOS BOTONES
            labelListaTernera.setImageResource(R.mipmap.labellistarterneraen);
            btnBuscar.setText(R.string.list_search);
        }



        txtBuscarSnig = (EditText) findViewById(R.id.txtBuscarSnig);

        //Boton de filtrar
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String snig = txtBuscarSnig.getText().toString();
                if (!snig.equals("")) {//si el campo de texto de filtrado de snig esta relleno
                    filtrarTernera(Integer.valueOf(snig));
                } else {//sino lista todas las terneras
                    lstTerneras.setAdapter(adaptador);
                    cargaTerneras();
                }
                adaptador.notifyDataSetChanged();
            }
        });

    }

    public void cargaTerneras() {//Lista las terneras
        ServiceApiTernera api = ConnectionRest.getConnection().create(ServiceApiTernera.class);
        Call<List<TerneraDTO>> call = api.listarTerneras();
        call.enqueue(new Callback<List<TerneraDTO>>() {
            @Override
            public void onResponse(Call<List<TerneraDTO>> call, Response<List<TerneraDTO>> response) {
                if (response.isSuccessful()) {
                    List<TerneraDTO> datos = response.body();
                    terneras.clear();
                    terneras.addAll(datos);
                    adaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<TerneraDTO>> call, Throwable t) {
            }
        });
    }

    public void filtrarTernera(int snig) {//lista ternera x snig
        try {
            ServiceApiTernera api = ConnectionRest.getConnection().create(ServiceApiTernera.class);
            Call<TerneraDTO> call = api.filtrarTernera(snig);
            call.enqueue(new Callback<TerneraDTO>() {
                @Override
                public void onResponse(Call<TerneraDTO> call, Response<TerneraDTO> response) {
                    if (response.code() != 204) {
                        TerneraDTO ternera = response.body();
                        terneras.clear();
                        terneras.add(ternera);
                        adaptador.notifyDataSetChanged();
                    } else {
                        Toast.makeText(ListaTernera.this, ListaTernera.this.getString(R.string.terneraList_snigNoEncontrado) + snig, Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                @Override
                public void onFailure(Call<TerneraDTO> call, Throwable t) {
                    Toast.makeText(ListaTernera.this, ListaTernera.this.getString(R.string.terneraList_vacio), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(ListaTernera.this, ListaTernera.this.getString(R.string.terneraList_snigNoEncontrado) + snig, Toast.LENGTH_LONG).show();
        }
    }

    public void borrarTernera(Integer snig){//borrar ternera
        AlertDialog.Builder opciones = new AlertDialog.Builder(ListaTernera.this);
        String[] opc = {ListaTernera.this.getString(R.string.bajaTernera)};
        opciones.setItems(opc, new DialogInterface.OnClickListener() {//Se despliega cartel emergente
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {//baja ternera
                    AlertDialog.Builder alerta = new AlertDialog.Builder(ListaTernera.this);
                    alerta.setMessage(ListaTernera.this.getString(R.string.reconocer_SnigLeido) + snig )
                            .setCancelable(true)
                            .setPositiveButton(ListaTernera.this.getString(R.string.reconocer_si), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {//BOTON SI DEL CARTEL EMERGENTE
                                    //hace llamado al rest
                                    ServiceApiTernera api= ConnectionRest.getConnection().create(ServiceApiTernera.class);
                                    Call<ClientResponse> call=api.bajaTernera(snig);
                                    call.enqueue(new Callback<ClientResponse>() {
                                        @Override
                                        public void onResponse(Call<ClientResponse> call, Response<ClientResponse> response) {
                                            ClientResponse cr=response.body();//por medio del ClientResponse que mandamos del servidor obtenemos el resultado
                                            if(cr.getStatusCode()==200){
                                                Toast.makeText(ListaTernera.this, ListaTernera.this.getString(R.string.ternera_bajaTerneraExito), Toast.LENGTH_LONG).show();
                                                lstTerneras.setAdapter(adaptador);
                                                cargaTerneras();
                                                adaptador.notifyDataSetChanged();
                                            }else if(cr.getStatusCode()==500){
                                                Toast.makeText(ListaTernera.this, ListaTernera.this.getString(R.string.ternera_bajaTerneraError), Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ClientResponse> call, Throwable t) {}
                                    });
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog titulo = alerta.create();
                    titulo.setTitle(R.string.borrar_titulo);
                    titulo.show();
                }
            }
        });
        AlertDialog titulo = opciones.create();
        titulo.setTitle(R.string.reconocer_opciones);
        titulo.show();
    }

}