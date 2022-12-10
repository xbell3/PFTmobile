package com.utec.pft;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.utec.pft.api.ServiceApi;
import com.utec.pft.util.ConnectionRest;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> usuarios =new ArrayList<String>();
    ArrayAdapter adaptador = null;
    ListView lstPersonas =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lstPersonas=(ListView) findViewById(R.id.lstUsuarios);
        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,usuarios);
        lstPersonas.setAdapter(adaptador);

        cargaPersonas();
    }


    public void cargaPersonas(){
        ServiceApi api = ConnectionRest.getConnection().create(ServiceApi.class);
        Call<List<PersonaDTO>> call = api.listaPersonas();
        call.enqueue(new Callback<List<PersonaDTO>>() {
            @Override
            public void onResponse(Call<List<PersonaDTO>> call, Response<List<PersonaDTO>> response) {
                List<PersonaDTO> lista = response.body();
                for(PersonaDTO p:lista){
                    usuarios.add(p.getNombre() + " " + p.getApellido() + " " + p.getCedula());
                }

               adaptador.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<PersonaDTO>> call, Throwable t) {

            }
        });
    }
}