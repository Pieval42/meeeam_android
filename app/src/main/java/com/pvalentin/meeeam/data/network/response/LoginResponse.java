package com.pvalentin.meeeam.data.network.response;

public class LoginResponse extends ApiResponse {
    private LoginData data;

    public LoginData getData() {
        return data;
    }

    public void setData(LoginData data) {
        this.data = data;
    }


    public LoginResponse(String status, String message, LoginData data, String token) {
        super(status, message, token);
        this.data = data;
    }

    public static class LoginData {
        private int id_utilisateur;
        private int id_page_profil;
        private String pseudo_utilisateur;

        public LoginData(int id_utilisateur, int id_page_profil, String pseudo_utilisateur) {
            this.id_utilisateur = id_utilisateur;
            this.id_page_profil = id_page_profil;
            this.pseudo_utilisateur = pseudo_utilisateur;
        }

        public LoginData() {
            this.id_utilisateur = -1;
            this.id_page_profil = -1;
            this.pseudo_utilisateur = "";
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
