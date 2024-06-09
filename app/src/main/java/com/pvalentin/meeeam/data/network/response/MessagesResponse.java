package com.pvalentin.meeeam.data.network.response;

import com.google.gson.annotations.SerializedName;
import com.pvalentin.meeeam.data.network.response.ApiResponse;

import java.util.ArrayList;

public class MessagesResponse extends ApiResponse {
  
  @SerializedName("data")
  public ArrayList<Message> messages;
  
  
  public ArrayList<Message> getMessages() {
    return messages;
  }
  
  public void setMessages(ArrayList<Message> messages) {
    this.messages = messages;
  }
  
  public MessagesResponse(String status, String message, String access_token, String refresh_token) {
    super(status, message, access_token, refresh_token);
  }
  
  public static class Message {
    @SerializedName("id_message_prive")
    private int id_message_prive;
    @SerializedName("id_expediteur_prive")
    private int id_expediteur_prive;
    @SerializedName("pseudo_expediteur")
    private String pseudo_expediteur;
    @SerializedName("contenu_message")
    private String contenu_message;
    @SerializedName("id_destinataire_prive")
    private int id_destinataire_prive;
    @SerializedName("pseudo_destinataire")
    private String pseudo_destinataire;
    @SerializedName("date_heure_message")
    private String date_heure_message;
    
    // Constructeur
    public Message() {
      this.id_message_prive = -1;
      this.id_expediteur_prive = -1;
      this.pseudo_expediteur = "";
      this.contenu_message = "";
      this.id_destinataire_prive = -1;
      this.pseudo_destinataire = "";
      this.date_heure_message = "";
    }
    
    // Getters et Setters
    public int getId_message_prive() {
      return id_message_prive;
    }
    
    public void setId_message_prive(int id_message_prive) {
      this.id_message_prive = id_message_prive;
    }
    
    public int getId_expediteur_prive() {
      return id_expediteur_prive;
    }
    
    public void setId_expediteur_prive(int id_expediteur_prive) {
      this.id_expediteur_prive = id_expediteur_prive;
    }
    
    public String getPseudo_expediteur() {
      return pseudo_expediteur;
    }
    
    public void setPseudo_expediteur(String pseudo_expediteur) {
      this.pseudo_expediteur = pseudo_expediteur;
    }
    
    public String getContenu_message() {
      return contenu_message;
    }
    
    public void setContenu_message(String contenu_message) {
      this.contenu_message = contenu_message;
    }
    
    public int getId_destinataire_prive() {
      return id_destinataire_prive;
    }
    
    public void setId_destinataire_prive(int id_destinataire_prive) {
      this.id_destinataire_prive = id_destinataire_prive;
    }
    
    public String getPseudo_destinataire() {
      return pseudo_destinataire;
    }
    
    public void setPseudo_destinataire(String pseudo_destinataire) {
      this.pseudo_destinataire = pseudo_destinataire;
    }
    
    public String getDate_heure_message() {
      return date_heure_message;
    }
    
    public void setDate_heure_message(String date_heure_message) {
      this.date_heure_message = date_heure_message;
    }
  }
}
