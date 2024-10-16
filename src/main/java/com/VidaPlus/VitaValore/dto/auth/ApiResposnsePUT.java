package com.VidaPlus.VitaValore.dto.auth;

public class ApiResposnsePUT {
    private String message;
    private String token;

    public ApiResposnsePUT(String message, String token) {
        this.message = message;
        this.token = token;
    }

    // Getters e setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}