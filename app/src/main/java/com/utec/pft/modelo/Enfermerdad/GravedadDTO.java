package com.utec.pft.modelo.Enfermerdad;

public class GravedadDTO {

    private int idGravedad;
    private String gravedad;

    public GravedadDTO() {
    }

    public GravedadDTO(int idGravedad, String gravedad) {
        this.idGravedad = idGravedad;
        this.gravedad = gravedad;
    }

    public int getIdGravedad() {
        return idGravedad;
    }

    public void setIdGravedad(int idGravedad) {
        this.idGravedad = idGravedad;
    }

    public String getGravedad() {
        return gravedad;
    }

    public void setGravedad(String gravedad) {
        this.gravedad = gravedad;
    }
}
