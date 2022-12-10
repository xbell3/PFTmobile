package com.utec.pft;



public class PersonaDTO {

    private Integer idpersona;

    private String nombre;

    private String apellido;

    private int cedula;

    private String nombreUsuario;

    private String contrasenia;

    private String titulo;

    private Integer cantHoras;

    private int estado;

    public PersonaDTO() {
    }

    public PersonaDTO(Integer idpersona, String nombre, String apellido, int cedula, String nombreUsuario, String contrasenia, String titulo, Integer cantHoras, int estado) {
        this.idpersona = idpersona;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.titulo = titulo;
        this.cantHoras = cantHoras;
        this.estado = estado;
    }

    public PersonaDTO(Integer idpersona, String nombre, String apellido){
        this.idpersona=idpersona;
        this.nombre=nombre;
        this.apellido=apellido;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getCantHoras() {
        return cantHoras;
    }

    public void setCantHoras(Integer cantHoras) {
        this.cantHoras = cantHoras;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "PersonaDTO{" +
                "idpersona=" + idpersona +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", cedula=" + cedula +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", titulo='" + titulo + '\'' +
                ", cantHoras=" + cantHoras +
                ", estado=" + estado +
                '}';
    }
}
