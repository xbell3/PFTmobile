package com.utec.pft.api;

import com.utec.pft.controlador.servicios.ClientResponse;
import com.utec.pft.modelo.Ternera.RazaDTO;
import com.utec.pft.modelo.Ternera.TerneraDTO;
import com.utec.pft.modelo.Ternera.TipoPartoDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServiceApiTernera {

    @GET("ternera/listarTerneras")
    public abstract Call<List<TerneraDTO>> listarTerneras();

    @GET("ternera/filtrarTernera/{SNIG}")
    public abstract Call<TerneraDTO> filtrarTernera(@Path("SNIG") int snig);

    @GET("ternera/obtenerRaza/{nombreRaza}")
    public abstract Call<RazaDTO> obtenerRaza(@Path("nombreRaza") String nombreRaza);

    @GET("ternera/obtenerTipoParto/{nombreTipoParto}")
    public abstract Call<TipoPartoDTO> obtenerTipoParto(@Path("nombreTipoParto") String nombreTipoParto);

    @GET("ternera/listarRazas")
    public abstract Call<List<RazaDTO>> listarRazas();

    @GET("ternera/listarTiposParto")
    public abstract Call<List<TipoPartoDTO>> listarTiposParto();

    @POST("ternera/altaTernera/{nombreRaza}-{nombreTipoParto}")
    Call<TerneraDTO> altaTernera(@Body TerneraDTO ternera, @Path("nombreRaza") String nombreRaza, @Path("nombreTipoParto") String nombreTipoParto);

    @DELETE("ternera/bajaTernera/{SNIG}")
    Call<ClientResponse> bajaTernera(@Path("SNIG") Integer snig);
}
