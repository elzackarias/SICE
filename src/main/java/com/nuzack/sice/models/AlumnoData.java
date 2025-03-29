package com.nuzack.sice.models;

import com.google.gson.annotations.SerializedName;

public class AlumnoData {
    private String id;
    private String nombre;
    @SerializedName("apellido_paterno")
    private String apellidoPaterno;
    @SerializedName("apellido_materno")
    private String apellidoMaterno;
    private String sexo;

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    // Método para nombre completo
    public String getNombreCompleto() {
        return  apellidoPaterno + " " + apellidoMaterno + " " + nombre;
    }
}