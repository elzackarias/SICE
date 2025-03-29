package com.nuzack.sice.models;

public class ListResponse {
    private String status;
    private String msg;
    private UserData data;  // Aquí se guardan los datos del usuario

    public String getStatus() { return status; }
    public String getMsg() { return msg; }
    public UserData getData() { return data; }
}
