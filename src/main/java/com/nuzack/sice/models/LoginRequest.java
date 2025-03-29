package com.nuzack.sice.models;

public class LoginRequest {
    public String user;
    public String password;

    // Constructor
    public LoginRequest(String user, String password) {
        this.user = user;
        this.password = password;
    }

    // Getters
    public String getUser() { return user; }
    public String getPassword() { return password; }
}
