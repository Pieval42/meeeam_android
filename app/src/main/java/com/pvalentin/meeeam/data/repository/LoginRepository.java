package com.pvalentin.meeeam.data.repository;

import com.pvalentin.meeeam.util.Constants;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.pvalentin.meeeam.R;
import com.pvalentin.meeeam.data.network.request.LoginRequest;
import com.pvalentin.meeeam.data.network.ApiService;
import com.pvalentin.meeeam.data.network.NetworkClient;
import com.pvalentin.meeeam.data.network.response.LoginResponse;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
  private static final String TAG = Constants.TAG + "." + LoginRepository.class.getSimpleName();
  private static final LoginResponse serviceResponse = new LoginResponse(
      "", "", new LoginResponse.LoginData(), "", "");
  private static final LoginResponse.LoginData loginData = new LoginResponse.LoginData();
  
  public static void callLoginService(LoginRequest loginRequest, Context context,
                                      LoginCallback loginCallback) {
    
    ApiService apiService = NetworkClient.getApiService();
    
    Call<LoginResponse> responseCall = apiService.sendLoginForm(loginRequest);
    
    assert responseCall != null;
    try {
      responseCall.enqueue(new Callback<LoginResponse>() {
        @Override
        public void onResponse(@NonNull Call<LoginResponse> call,
                               @NonNull Response<LoginResponse> response) {
          
          if (response.code() == 200) {
            if (response.body() != null) {
              serviceResponse.setStatus(response.body().getStatus());
              serviceResponse.setMessage(response.body().getMessage());
              serviceResponse.setData(response.body().getData());
              serviceResponse.setAccess_token(response.body().getAccess_token());
              serviceResponse.setRefresh_token(response.body().getRefresh_token());
              loginData.setDetails(response.body().getData().getDetails());
              Log.d(TAG, new Gson().toJson(response.body()));
              Log.d(TAG, new Gson().toJson(response.body().getMessage()));
            }
            loginCallback.onSuccess(serviceResponse);
          } else {
            serviceResponse.setStatus("failed");
            serviceResponse.setMessage(context.getString(R.string.network_error));
            loginCallback.onError(serviceResponse);
          }
        }
        
        @Override
        public void onFailure(@NonNull Call<LoginResponse> call,
                              @NonNull Throwable throwable) {
          serviceResponse.setStatus("failed");
          serviceResponse.setMessage(context.getString(R.string.network_error));
          Log.d(TAG, context.getString(R.string.network_error));
          try {
            throw throwable;
          } catch (Throwable e) {
            Log.d(TAG, Objects.requireNonNull(e.getMessage()));
          }
          loginCallback.onError(serviceResponse);
        }
      });
    } catch (Exception e) {
      serviceResponse.setStatus("failed");
      serviceResponse.setMessage(Objects.requireNonNull(e.getMessage()));
      Log.d(TAG, Objects.requireNonNull(e.getMessage()));
    }
  }
  
  // Interface de callback pour la connexion
  public interface LoginCallback {
    void onSuccess(LoginResponse response);
    
    void onError(LoginResponse response);
  }
}
