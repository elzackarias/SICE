package com.nuzack.sice.models;

public class GradoGrupoRequest {
    private String grado;
    private String grupo;

    public GradoGrupoRequest(String grado, String grupo) {
        this.grado = grado;
        this.grupo = grupo;
    }

    // Getters
    public String getGrado() {
        return grado;
    }

    public String getGrupo() {
        return grupo;
    }
}