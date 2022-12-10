package com.utec.pft.modelo.Enfermerdad;

import java.util.Date;

public class EnfermedadHistoricaDTO {
    private int idTernera;
    private int snig;
    private String nombreEnfermedad;
    private String variante;
    private String severidad;
    private Date fecDeteccion;

    public EnfermedadHistoricaDTO() {
    }

    public EnfermedadHistoricaDTO(int idTernera, int snig, String nombreEnfermedad, String variante, String severidad, Date fecDeteccion) {
        this.idTernera = idTernera;
        this.snig = snig;
        this.nombreEnfermedad = nombreEnfermedad;
        this.variante = variante;
        this.severidad = severidad;
        this.fecDeteccion = fecDeteccion;
    }

    public int getIdTernera() {
        return idTernera;
    }

    public void setIdTernera(int idTernera) {
        this.idTernera = idTernera;
    }

    public int getSnig() {
        return snig;
    }

    public void setSnig(int snig) {
        this.snig = snig;
    }

    public String getNombreEnfermedad() {
        return nombreEnfermedad;
    }

    public void setNombreEnfermedad(String nombreEnfermedad) {
        this.nombreEnfermedad = nombreEnfermedad;
    }

    public String getVariante() {
        return variante;
    }

    public void setVariante(String variante) {
        this.variante = variante;
    }

    public String getSeveridad() {
        return severidad;
    }

    public void setSeveridad(String severidad) {
        this.severidad = severidad;
    }

    public Date getFecDeteccion() {
        return fecDeteccion;
    }

    public void setFecDeteccion(Date fecDeteccion) {
        this.fecDeteccion = fecDeteccion;
    }
}
