package com.pvalentin.meeeam.data.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LoginResponse extends ApiResponse {

  @SerializedName("data")
  private LoginData data;
  
  public LoginResponse(String status, String message, LoginData data, String access_token,
                       String refresh_token) {
    super(status, message, access_token, refresh_token);
    this.data = data;
  }
  
  public LoginData getData() {
    return data;
  }
  
  public void setData(LoginData data) {
    this.data = data;
  }
  
  public static class LoginData {
    @SerializedName("id_utilisateur")
    private int id_utilisateur;
    @SerializedName("id_page_profil")
    private int id_page_profil;
    @SerializedName("pseudo_utilisateur")
    private String pseudo_utilisateur;
    @SerializedName("details_utilisateur")
    private UserDetails details;
    
    public LoginData(int id_utilisateur, int id_page_profil, String pseudo_utilisateur) {
      this.id_utilisateur = id_utilisateur;
      this.id_page_profil = id_page_profil;
      this.pseudo_utilisateur = pseudo_utilisateur;
    }
    
    public LoginData() {
      this.id_utilisateur = -1;
      this.id_page_profil = -1;
      this.pseudo_utilisateur = "";
      this.details = new UserDetails();
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
    
    public UserDetails getDetails() {
      return details;
    }
    
    public void setDetails(UserDetails details) {
      this.details = details;
    }
  }
  
  public static class UserDetails {
    @SerializedName("id_utilisateur")
    private int userId;
    @SerializedName("pseudo_utilisateur")
    private String userPseudo;
    @SerializedName("date_naissance")
    private String birthDate;
    @SerializedName("genre")
    private String gender;
    @SerializedName("nom_utilisateur")
    private String userLastName;
    @SerializedName("pays")
    private String country;
    @SerializedName("prenom_utilisateur")
    private String userFirstName;
    @SerializedName("sites_web")
    private ArrayList<Website> websites;
    @SerializedName("ville")
    private String ville;
    
    public UserDetails(int userId, String userPseudo, String birthDate, String gender,
                       String userLastName, String country, String userFirstName,
                       ArrayList<Website> websites, String ville) {
      this.userId = userId;
      this.userPseudo = userPseudo;
      this.birthDate = birthDate;
      this.gender = gender;
      this.userLastName = userLastName;
      this.country = country;
      this.userFirstName = userFirstName;
      this.websites = websites;
      this.ville = ville;
    }
    
    public UserDetails() {
      this.userId = -1;
      this.userPseudo = "";
      this.birthDate = "";
      this.gender = null;
      this.userLastName = "";
      this.country = "";
      this.userFirstName = "";
      this.websites = new ArrayList<>();
      this.ville = "";
    }
    
    public int getUserId() {
      return userId;
    }
    
    public void setUserId(int userId) {
      this.userId = userId;
    }
    
    public String getUserPseudo() {
      return userPseudo;
    }
    
    public void setUsrPseudo(String userPseudo) {
      this.userPseudo = userPseudo;
    }
    
    public String getBirthDate() {
      return birthDate;
    }
    
    public void setBirthDate(String birthDate) {
      this.birthDate = birthDate;
    }
    
    public String getGender() {
      return gender;
    }
    
    public void setGender(String gender) {
      this.gender = gender;
    }
    
    public String getUserLastName() {
      return userLastName;
    }
    
    public void setUserLastName(String userLastName) {
      this.userLastName = userLastName;
    }
    
    public String getCountry() {
      return country;
    }
    
    public void setCountry(String country) {
      this.country = country;
    }
    
    public String getUserFirstName() {
      return userFirstName;
    }
    
    public void setUserFirstName(String userFirstName) {
      this.userFirstName = userFirstName;
    }
    
    public ArrayList<Website> getWebsites() {
      return websites;
    }
    
    public void setWebsites(ArrayList<Website> websites) {
      this.websites = websites;
    }
    
    public String getVille() {
      return ville;
    }
    
    public void setVille(String ville) {
      this.ville = ville;
    }
  }
  
  public static class Website {
    @SerializedName("id_utilisateur_site_web")
    private int websiteUserId;
    @SerializedName("adresse_site_web_liste")
    private String urlWebsite;
    
    public Website(int websiteUserId, String urlWebsite) {
      this.websiteUserId = websiteUserId;
      this.urlWebsite = urlWebsite;
    }
    
    public Website() {
      this.websiteUserId = -1;
      this.urlWebsite = "";
    }
    
    public int getWebsiteUserId() {
      return websiteUserId;
    }
    
    public void setWebsiteUserId(int websiteUserId) {
      this.websiteUserId = websiteUserId;
    }
    
    public String getUrlWebsite() {
      return urlWebsite;
    }
    
    public void setUrlWebsite(String urlWebsite) {
      this.urlWebsite = urlWebsite;
    }
  }
}
