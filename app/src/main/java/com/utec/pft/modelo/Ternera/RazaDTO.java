package com.utec.pft.modelo.Ternera;

import java.io.Serializable;

public class RazaDTO implements Serializable {

    private int idRaza;

    private String raza;

    public RazaDTO(){}

    public RazaDTO(int idRaza, String raza) {
        this.idRaza = idRaza;
        this.raza = raza;
    }

    public int getIdRaza() {
        return idRaza;
    }

    public void setIdRaza(int idRaza) {
        this.idRaza = idRaza;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }
}
