package com.utec.pft.api;

import com.utec.pft.PersonaDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceApi {
    @GET("listarPersonas")
    public abstract Call<List<PersonaDTO>> listaPersonas();
}
