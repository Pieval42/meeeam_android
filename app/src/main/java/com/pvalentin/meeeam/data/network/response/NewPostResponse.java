package com.pvalentin.meeeam.data.network.response;

public class NewPostResponse extends ApiResponse{
  public NewPostResponse(String status, String message, String access_token, String refresh_token) {
    super(status, message, access_token, refresh_token);
  }
}
