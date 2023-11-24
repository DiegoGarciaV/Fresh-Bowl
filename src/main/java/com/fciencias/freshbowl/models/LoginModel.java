package com.fciencias.freshbowl.models;

public class LoginModel {
    
    private String username;
    private String password;
    private String requestedResource;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRequestedResource() {
        return requestedResource;
    }
    public void setRequestedResource(String requestedResource) {
        this.requestedResource = requestedResource;
    }
    
}
