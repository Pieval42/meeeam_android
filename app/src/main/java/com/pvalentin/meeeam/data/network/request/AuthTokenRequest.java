package com.pvalentin.meeeam.data.network.request;

public class AuthTokenRequest {
  private int id_utilisateur;
  private int id_page_profil;
  private String pseudo_utilisateur;
  
  private String meeeam_refresh_token;
  
  public AuthTokenRequest(int id_utilisateur, int id_page_profil, String pseudo_utilisateur, String meeeam_refresh_token) {
    this.id_utilisateur = id_utilisateur;
    this.id_page_profil = id_page_profil;
    this.pseudo_utilisateur = pseudo_utilisateur;
    this.meeeam_refresh_token = meeeam_refresh_token;
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
  
  public String getMeeeam_refresh_token() {
    return meeeam_refresh_token;
  }
  
  public void setMeeeam_refresh_token(String meeeam_refresh_token) {
    this.meeeam_refresh_token = meeeam_refresh_token;
  }
  
}
