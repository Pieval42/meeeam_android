package com.pvalentin.meeeam.data.network.response;

import static com.pvalentin.meeeam.util.Constants.HOST;

import com.google.gson.annotations.SerializedName;
import com.pvalentin.meeeam.util.dateUtils.ConvertDateFormat;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Objects;

public class PostResponse extends ApiResponse {
  @SerializedName("data")
  public ArrayList<PostResponse.Post> posts;
  
  
  public ArrayList<PostResponse.Post> getPosts() {
    return posts;
  }
  
  public void setPosts(ArrayList<PostResponse.Post> posts) {
    this.posts = posts;
  }
  
  public PostResponse(String status, String message, String access_token, String refresh_token) {
    super(status, message, access_token, refresh_token);
  }
  
  public static class Post {
    @SerializedName("id_publication")
    private int idPost;
    @SerializedName("contenu_publication")
    private String postContent;
    @SerializedName("date_heure_publication")
    private String dateTimePost;
    @SerializedName("url_fichier_publication")
    private String urlPostFile;
    @SerializedName("id_page_publique_publication")
    private int idPublicPage;
    @SerializedName("id_page_profil_publication")
    private int idProfilePage;
    @SerializedName("id_utilisateur_page_profil")
    private int idProfileUser;
    
    @SerializedName("id_createur_publication")
    private int idPostCreator;
    
    
    public Post(int idPost, String postContent, String dateTimePost, String urlPostFile,
                int idPublicPage, int idProfilePage, int idProfileUser, int idPostCreator) {
      this.idPost = idPost;
      this.postContent = postContent;
      this.dateTimePost = dateTimePost;
      this.urlPostFile = urlPostFile;
      this.idPublicPage = idPublicPage;
      this.idProfilePage = idProfilePage;
      this.idProfileUser = idProfileUser;
      this.idPostCreator = idPostCreator;
    }
    
    public int getIdPost() {
      return idPost;
    }
    
    public void setIdPost(int idPost) {
      this.idPost = idPost;
    }
    
    public String getPostContent() {
      return postContent;
    }
    
    public void setPostContent(String postContent) {
      this.postContent = postContent;
    }
    
    public String getDateTimePost() throws ParseException {
      DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      LocalDateTime localDateTime = LocalDateTime.parse(dateTimePost, inputFormatter);
      
      ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("UTC"))
          .withZoneSameInstant(ZoneId.of("Europe/Paris"));
      
      DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
      
      return zonedDateTime.format(outputFormatter);
      
//      String date = ConvertDateFormat.convertDateFormatSqlToFr(dateTimePost.split(" ")[0]);
//      String time = dateTimePost.split(" ")[1];
//      return date + " " + time;
    }
    
    public void setDateTimePost(String dateTimePost) {
      this.dateTimePost = dateTimePost;
    }
    
    public String getUrlPostFile() throws URISyntaxException {
      return urlPostFile;
    }
    
    public void setUrlPostFile(String urlPostFile) {
      this.urlPostFile = urlPostFile;
    }
    
    public int getIdPublicPage() {
      return idPublicPage;
    }
    
    public void setIdPublicPage(int idPublicPage) {
      this.idPublicPage = idPublicPage;
    }
    
    public int getIdProfilePage() {
      return idProfilePage;
    }
    
    public void setIdProfilePage(int idProfilePage) {
      this.idProfilePage = idProfilePage;
    }
    
    public int getIdProfileUser() {
      return idProfileUser;
    }
    
    public void setIdProfileUser(int idProfileUser) {
      this.idProfileUser = idProfileUser;
    }
    
    public int getIdPostCreator() {
      return idPostCreator;
    }
    
    public void setIdPostCreator(int idPostCreator) {
      this.idPostCreator = idPostCreator;
    }
  }
}
