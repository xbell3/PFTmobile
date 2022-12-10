package com.utec.pft.api;

import com.utec.pft.modelo.Persona.PersonaDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ServiceApiPersona {
    @GET("persona/listarPersonas")
    public abstract Call<List<PersonaDTO>> listaPersonas();

    @GET("persona/login{nombreUsuario}-{contrasenia}")
    public abstract Call<PersonaDTO> login(@Path("nombreUsuario") String nombreUsuario, @Path("contrasenia") String contrasenia);
}
