package com.pvalentin.meeeam.data.network.request;

public class SignUpRequest {
    private String pseudo;
    private String email;
    private String password;
    private String prenom;
    private String nom;
    private String date_de_naissance;
    private String code_postal;
    private String nom_ville;
    private int id_pays;
    private String id_genre;
    private String site_web;

    public SignUpRequest(String pseudo, String password, String firstName,
                         String lastName, String birthDate, String email, String code_postal, String city,
                         int country, String gender, String website) {
        this.pseudo = pseudo;
        this.password = password;
        this.prenom = firstName;
        this.nom = lastName;
        this.date_de_naissance = birthDate;
        this.email = email;
        this.code_postal = code_postal;
        this.nom_ville = city;
        this.id_pays = country;
        this.id_genre = gender;
        this.site_web = website;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return prenom;
    }

    public void setFirstName(String firstName) {
        this.prenom = firstName;
    }

    public String getLastName() {
        return nom;
    }

    public void setLastName(String lastName) {
        this.nom = lastName;
    }

    public String getBirthDate() {
        return date_de_naissance;
    }

    public void setBirthDate(String birthDate) {
        this.date_de_naissance = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode_postal() {
        return code_postal;
    }

    public void setCode_postal(String code_postal) {
        this.code_postal = code_postal;
    }

    public String getNom_ville() {
        return nom_ville;
    }

    public void setNom_ville(String nom_ville) {
        this.nom_ville = nom_ville;
    }

    public int getId_pays() {
        return id_pays;
    }

    public void setId_pays(int id_pays) {
        this.id_pays = id_pays;
    }

    public String getId_genre() {
        return id_genre;
    }

    public void setId_genre(String id_genre) {
        this.id_genre = id_genre;
    }

    public String getSite_web() {
        return site_web;
    }

    public void setSite_web(String site_web) {
        this.site_web = site_web;
    }
}
