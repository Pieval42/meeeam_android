package com.pvalentin.meeeam.response;

public class SignUpResponse {
    private String status;
    private String message;
    private UserData data;
    private String token;

    public SignUpResponse(String status, String message, UserData data, String token) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class UserData {
        private int id_utilisateur;
        private int id_page_profil;
        private String pseudo_utilisateur;

        public UserData(int id_utilisateur, int id_page_profil, String pseudo_utilisateur) {
            this.id_utilisateur = id_utilisateur;
            this.id_page_profil = id_page_profil;
            this.pseudo_utilisateur = pseudo_utilisateur;
        }

        public int getId_utilisateur() {
            return id_utilisateur;
        }

        public void setId_utilisateur(int id_utilisateur) {
            this.id_utilisateur = id_utilisateur;
        }

        public int getId_page_profil() {
            return id_page_profil;
        }

        public void setId_page_profil(int id_page_profil) {
            this.id_page_profil = id_page_profil;
        }

        public String getPseudo_utilisateur() {
            return pseudo_utilisateur;
        }

        public void setPseudo_utilisateur(String pseudo_utilisateur) {
            this.pseudo_utilisateur = pseudo_utilisateur;
        }
    }
}