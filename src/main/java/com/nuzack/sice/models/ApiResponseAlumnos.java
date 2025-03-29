package com.nuzack.sice.models;

import java.util.List;

public class ApiResponseAlumnos extends ApiResponse {
    private List<AlumnoData> data;

    public List<AlumnoData> getData() {
        return data;
    }

    public void setData(List<AlumnoData> data) {
        this.data = data;
    }
}