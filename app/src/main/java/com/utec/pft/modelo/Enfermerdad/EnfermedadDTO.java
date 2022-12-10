package com.utec.pft.modelo.Enfermerdad;

public class EnfermedadDTO {
    private Long idEnfermedad;

    private String nombre;

    private String descripcion;

    private String tratamiento;

    private String variante;

    private GravedadDTO gravedad;

    public EnfermedadDTO() {
    }

    public EnfermedadDTO(Long idEnfermedad, String nombre, String descripcion, String tratamiento, String variante, GravedadDTO gravedad) {
        this.idEnfermedad = idEnfermedad;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tratamiento = tratamiento;
        this.variante = variante;
        this.gravedad = gravedad;
    }

    public Long getIdEnfermedad() {
        return idEnfermedad;
    }

    public void setIdEnfermedad(Long idEnfermedad) {
        this.idEnfermedad = idEnfermedad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getVariante() {
        return variante;
    }

    public void setVariante(String variante) {
        this.variante = variante;
    }

    public GravedadDTO getGravedad() {
        return gravedad;
    }

    public void setGravedad(GravedadDTO gravedad) {
        this.gravedad = gravedad;
    }
}
