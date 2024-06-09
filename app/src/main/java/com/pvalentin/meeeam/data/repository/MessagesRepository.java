package com.pvalentin.meeeam.data.repository;

import static com.pvalentin.meeeam.util.Constants.AUTH_PREFS_FILE;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.pvalentin.meeeam.R;
import com.pvalentin.meeeam.data.network.ApiService;
import com.pvalentin.meeeam.data.network.NetworkClient;
import com.pvalentin.meeeam.data.network.response.MessagesResponse;
import com.pvalentin.meeeam.util.Constants;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessagesRepository {
  private static final String TAG = Constants.TAG + "." + MessagesRepository.class.getSimpleName();
  private static final MessagesResponse serviceResponse = new MessagesResponse(
      "", "", "", "");
  
  public static void callMessagesService(
      Context context, MessagesCallback messagesCallback
  ) {
    
    ApiService apiService = NetworkClient.getApiService();
    
    String accessToken = "Bearer " + context.getSharedPreferences(AUTH_PREFS_FILE, Context.MODE_PRIVATE).getString("meeeam_access_token", null);
    
    Call<MessagesResponse> responseCall = apiService.getMessages(accessToken, 17, 20);
    
    assert responseCall != null;
    try {
      responseCall.enqueue(new Callback<MessagesResponse>() {
        @Override
        public void onResponse(@NonNull Call<MessagesResponse> call,
                               @NonNull Response<MessagesResponse> response) {
          if (response.code() == 200) {
            if (response.body() != null) {
              serviceResponse.setStatus(response.body().getStatus());
              serviceResponse.setMessage(response.body().getMessage());
              serviceResponse.setMessages(response.body().getMessages());
              serviceResponse.setAccess_token(response.body().getAccess_token());
              serviceResponse.setRefresh_token(response.body().getRefresh_token());
              Log.d(TAG, new Gson().toJson(response.body()));
              Log.d(TAG, response.body().getMessage());
            }
            messagesCallback.onSuccess(serviceResponse);
          } else {
            serviceResponse.setStatus("failed");
            serviceResponse.setMessage(context.getString(R.string.network_error));
            messagesCallback.onError(serviceResponse);
          }
        }
        
        @Override
        public void onFailure(@NonNull Call<MessagesResponse> call,
                              @NonNull Throwable throwable) {
          serviceResponse.setStatus("failed");
          serviceResponse.setMessage(context.getString(R.string.network_error));
          Log.d(TAG, context.getString(R.string.network_error));
          try {
            throw throwable;
          } catch (Throwable e) {
            Log.d(TAG, Objects.requireNonNull(e.getMessage()));
            Log.e(TAG, Log.getStackTraceString(e));
          }
          messagesCallback.onError(serviceResponse);
        }
      });
    } catch (Exception e) {
      serviceResponse.setStatus("failed");
      serviceResponse.setMessage(Objects.requireNonNull(e.getMessage()));
      Log.d(TAG, Objects.requireNonNull(e.getMessage()));
    }
  }
  
  // Interface de callback pour l'inscription
  public interface MessagesCallback {
    void onSuccess(MessagesResponse response);
    
    void onError(MessagesResponse response);
  }
}
