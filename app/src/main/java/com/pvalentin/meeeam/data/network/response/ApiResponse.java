package com.pvalentin.meeeam.data.network.response;

public class ApiResponse {
  
  private String refresh_token;
  private String status;
  private String message;
  private String access_token;
  
  public ApiResponse(String status, String message, String access_token, String refresh_token) {
    this.status = status;
    this.message = message;
    this.access_token = access_token;
    this.refresh_token = refresh_token;
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
  
  public String getAccess_token() {
    return access_token;
  }
  
  public void setAccess_token(String access_token) {
    this.access_token = access_token;
  }
  
  public String getRefresh_token() {
    return refresh_token;
  }
  
  public void setRefresh_token(String refresh_token) {
    this.refresh_token = refresh_token;
  }
  
}
