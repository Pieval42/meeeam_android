package com.pvalentin.meeeam.data.network.response;

public class SignUpResponse extends ApiResponse {
  
  private SignUpData data;
  
  public SignUpResponse(String status, String message, SignUpData data, String access_token, String refresh_token) {
    super(status, message, access_token, refresh_token);
    this.data = data;
  }
  
  public SignUpData getData() {
    return data;
  }
  
  public void setData(SignUpData data) {
    this.data = data;
  }
  
  
  public static class SignUpData {
    private String pseudo;
    private String password;
    private String email;
    private int id_page_profil;
    
    public SignUpData(String pseudo, String password, String email, int id_page_profil) {
      this.pseudo = pseudo;
      this.password = password;
      this.email = email;
      this.id_page_profil = id_page_profil;
    }
    
    public SignUpData() {
      this.pseudo = "";
      this.password = "";
      this.email = "";
      this.id_page_profil = -1;
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
    
    public String getEmail() {
      return email;
    }
    
    public void setEmail(String email) {
      this.email = email;
    }
    
    public int getId_page_profil() {
      return id_page_profil;
    }
    
    public void setId_page_profil(int id_page_profil) {
      this.id_page_profil = id_page_profil;
    }
  }
}
