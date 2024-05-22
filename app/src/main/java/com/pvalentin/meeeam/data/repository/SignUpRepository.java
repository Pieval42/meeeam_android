package com.pvalentin.meeeam.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.pvalentin.meeeam.R;
import com.pvalentin.meeeam.util.Constants;
import com.pvalentin.meeeam.data.network.request.SignUpRequest;
import com.pvalentin.meeeam.data.network.ApiService;
import com.pvalentin.meeeam.data.network.NetworkClient;
import com.pvalentin.meeeam.data.network.response.SignUpResponse;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpRepository {
  private static final SignUpResponse serviceResponse = new SignUpResponse(
      "", "", new SignUpResponse.SignUpData(), "");
  
  public static void callSignUpService(SignUpRequest signUpRequest, Context context,
                                       SignUpCallback signUpCallback) {
    final String TAG = Constants.TAG;
    
    ApiService apiService = NetworkClient.getApiService();
    
    Call<SignUpResponse> responseCall = apiService.sendSignUpForm(signUpRequest);
    
    assert responseCall != null;
    try {
      responseCall.enqueue(new Callback<SignUpResponse>() {
        @Override
        public void onResponse(@NonNull Call<SignUpResponse> call,
                               @NonNull Response<SignUpResponse> response) {
          
          assert response.body() != null;
          serviceResponse.setStatus(response.body().getStatus());
          serviceResponse.setMessage(response.body().getMessage());
          serviceResponse.setData(response.body().getData());
          serviceResponse.setToken(response.body().getToken());
          
          if (response.code() == 200) {
            Log.d(TAG, new Gson().toJson(response.body()));
            assert response.body() != null;
            Log.d(TAG, response.body().getMessage());
          }
          signUpCallback.onSuccess(serviceResponse);
        }
        
        @Override
        public void onFailure(@NonNull Call<SignUpResponse> call,
                              @NonNull Throwable throwable) {
          serviceResponse.setStatus("failed");
          serviceResponse.setMessage(context.getString(R.string.network_error));
          Log.d(TAG, context.getString(R.string.network_error));
          try {
            throw throwable;
          } catch (Throwable e) {
            Log.d(TAG, Objects.requireNonNull(e.getMessage()));
          }
          signUpCallback.onError(serviceResponse);
        }
      });
    } catch (Exception e) {
      serviceResponse.setStatus("failed");
      serviceResponse.setMessage(Objects.requireNonNull(e.getMessage()));
      Log.d(TAG, Objects.requireNonNull(e.getMessage()));
    }
  }
  
  // Interface de callback pour l'inscription
  public interface SignUpCallback {
    void onSuccess(SignUpResponse response);
    
    void onError(SignUpResponse response);
  }
}
