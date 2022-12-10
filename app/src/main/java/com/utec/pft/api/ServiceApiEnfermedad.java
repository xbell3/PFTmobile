package com.utec.pft.api;

import com.utec.pft.modelo.Enfermerdad.EnfermedadDTO;
import com.utec.pft.modelo.Enfermerdad.EnfermedadHistoricaDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServiceApiEnfermedad {
    @GET("enfermedad/listarEnfermedades")
    public abstract Call<List<EnfermedadDTO>> listarEnfermedades();

    @GET("enfermedad/listarHistoricos")
    public abstract Call<List<EnfermedadHistoricaDTO>> listarHistoricos();

    @GET("enfermedad/listarHistoricosxSnig/{snig}")
    public abstract Call<List<EnfermedadHistoricaDTO>> listarHistoricosxSnig(@Path("snig") int snig);

    @POST("enfermedad/cargaHistoricos")
    Call<List<EnfermedadHistoricaDTO>> cargaHistoricos(@Body List<EnfermedadHistoricaDTO> enfermedadHistoricas);


}
