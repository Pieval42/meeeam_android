package com.pvalentin.meeeam.data.network.response;

public class ApiResponse {
    private String status;
    private String message;
    private String token;

    public ApiResponse(String status, String message, String token) {
        this.status = status;
        this.message = message;
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
    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class Data {
        private int id_utilisateur;
        private int id_page_profil;
        private String pseudo_utilisateur;

        public Data(int id_utilisateur, int id_page_profil, String pseudo_utilisateur) {
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
