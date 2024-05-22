package com.pvalentin.meeeam.data.network.request;

public class LoginRequest {
    private String email;
    private String mot_de_passe;

    public LoginRequest(String email, String mot_de_passe) {
        this.email = email;
        this.mot_de_passe = mot_de_passe;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return mot_de_passe;
    }

    public void setPassword(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }
}
