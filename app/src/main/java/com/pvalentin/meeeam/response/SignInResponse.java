package com.pvalentin.meeeam.response;

public class SignInResponse extends RetrofitResponse {

    public SignInResponse(String status, String message, String data, String token) {
        super(status, message, data, token);
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
