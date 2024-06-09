package com.pvalentin.meeeam.data.network.response;

import com.google.gson.annotations.SerializedName;

public class AuthTokenResponse extends ApiResponse {
  
  @SerializedName("data")
  private Object data;
  
  public Object getData() {
    return data;
  }
  
  public void setData(Object data) {
    this.data = data;
  }
  
  public AuthTokenResponse(String status, String message, String access_token, String refresh_token) {
    super(status, message, access_token, refresh_token);
  }
}
