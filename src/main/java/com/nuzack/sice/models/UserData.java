package com.nuzack.sice.models;

public class UserData {
    private String uid;
    private String name;
    private String apellidos;
    private String role;
    private String user;
    private String CCT;
    private String escuela;
    private String ciudad;
    private String colonia;
    private String token;

    // Getters
    public String getUid() { return uid; }
    public String getName() { return name; }
    public String getApellidos() { return apellidos; }
    public String getRole() { return role; }
    public String getUser() { return user; }
    public String getCCT() { return CCT; }
    public String getEscuela() { return escuela; }
    public String getCiudad() { return ciudad; }
    public String getColonia() { return colonia; }
    public String getToken() { return token; }

    // Setters (los que faltaban)
    public void setUid(String uid) { this.uid = uid; }
    public void setName(String name) { this.name = name; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public void setRole(String role) { this.role = role; }
    public void setUser(String user) { this.user = user; }
    public void setCCT(String CCT) { this.CCT = CCT; }
    public void setEscuela(String escuela) { this.escuela = escuela; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public void setColonia(String colonia) { this.colonia = colonia; }
    public void setToken(String token) { this.token = token; }
}