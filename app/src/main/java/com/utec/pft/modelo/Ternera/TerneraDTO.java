package com.utec.pft.modelo.Ternera;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class TerneraDTO implements Serializable {

    private Long idTernera;
    private Integer idLocal;
    private Integer snig;
    private Integer snigMadre;
    private Integer snigPadre;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date fecNac;
    private float pesoNac;
    private RazaDTO raza;
    private TipoPartoDTO tipoParto;
    private int estado;

    public TerneraDTO() {

    }

    public TerneraDTO(Long idTernera, Integer idLocal, Integer snig, Integer snigMadre, Integer snigPadre, Date fecNac, float pesoNac, RazaDTO raza, TipoPartoDTO tipoParto, int estado) {
        this.idTernera = idTernera;
        this.idLocal = idLocal;
        this.snig = snig;
        this.snigMadre = snigMadre;
        this.snigPadre = snigPadre;
        this.fecNac = fecNac;
        this.pesoNac = pesoNac;
        this.raza = raza;
        this.tipoParto = tipoParto;
        this.estado = estado;
    }

    public Long getIdTernera() {
        return idTernera;
    }

    public void setIdTernera(Long idTernera) {
        this.idTernera = idTernera;
    }

    public Integer getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(Integer idLocal) {
        this.idLocal = idLocal;
    }

    public Integer getSnig() {
        return snig;
    }

    public void setSnig(Integer snig) {
        this.snig = snig;
    }

    public Integer getSnigMadre() {
        return snigMadre;
    }

    public void setSnigMadre(Integer snigMadre) {
        this.snigMadre = snigMadre;
    }

    public Integer getSnigPadre() {
        return snigPadre;
    }

    public void setSnigPadre(Integer snigPadre) {
        this.snigPadre = snigPadre;
    }

    public Date getFecNac() {
        return fecNac;
    }

    public void setFecNac(Date fecNac) {
        this.fecNac = fecNac;
    }

    public float getPesoNac() {
        return pesoNac;
    }

    public void setPesoNac(float pesoNac) {
        this.pesoNac = pesoNac;
    }

    public RazaDTO getRaza() {
        return raza;
    }

    public void setRaza(RazaDTO raza) {
        this.raza = raza;
    }

    public TipoPartoDTO getTipoParto() {
        return tipoParto;
    }

    public void setTipoParto(TipoPartoDTO tipoParto) {
        this.tipoParto = tipoParto;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
