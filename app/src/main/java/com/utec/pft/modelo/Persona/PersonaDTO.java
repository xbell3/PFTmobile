

package com.utec.pft.modelo.Persona;


import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Keep
public class PersonaDTO implements Serializable {

    @SerializedName("idpersona")
    private Integer idpersona;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("apellido")
    private String apellido;

    @SerializedName("cedula")
    private int cedula;

    @SerializedName("nombreUsuario")
    private String nombreUsuario;

    @SerializedName("contrasenia")
    private String contrasenia;

    @SerializedName("idRol")
    private Integer idRol;

    @SerializedName("nombreRol")
    private String nombreRol;

    @SerializedName("estado")
    private int estado;


    public PersonaDTO() {
    }

    public PersonaDTO(Integer idpersona, String nombre, String apellido, int cedula, String nombreUsuario, String contrasenia, Integer idRol, String nombreRol, int estado) {
        this.idpersona = idpersona;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.idRol = idRol;
        this.nombreRol = nombreRol;
        this.estado = estado;
    }

    public Integer getIdpersona() {
        return idpersona;
    }

    public void setIdpersona(Integer idpersona) {
        this.idpersona = idpersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
