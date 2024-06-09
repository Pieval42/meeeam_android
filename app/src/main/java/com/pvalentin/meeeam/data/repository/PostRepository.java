package com.pvalentin.meeeam.data.repository;

import static com.pvalentin.meeeam.util.Constants.AUTH_PREFS_FILE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.pvalentin.meeeam.R;
import com.pvalentin.meeeam.data.network.ApiService;
import com.pvalentin.meeeam.data.network.NetworkClient;
import com.pvalentin.meeeam.data.network.response.PostResponse;
import com.pvalentin.meeeam.util.Constants;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostRepository {
  private static final String TAG = Constants.TAG + "." + PostRepository.class.getSimpleName();
  private static final PostResponse serviceResponse = new PostResponse(
      "", "", "", "");
  
  public static void callPostService(
      Context context, PostRepository.PostCallback postCallback
  ) {
    
    ApiService apiService = NetworkClient.getApiService();
    
    SharedPreferences authPrefs = context.getSharedPreferences(AUTH_PREFS_FILE, Context.MODE_PRIVATE);
    
    String accessToken = "Bearer " + authPrefs.getString("meeeam_access_token", null);
    int userId = authPrefs.getInt("meeeam_user_id", -1);
    int userProfileId = authPrefs.getInt("meeeam_user_profile_id", -1);
    
    Call<PostResponse> responseCall = apiService.getPosts(accessToken, userId, userProfileId);
    
    assert responseCall != null;
    try {
      responseCall.enqueue(new Callback<PostResponse>() {
        @Override
        public void onResponse(@NonNull Call<PostResponse> call,
                               @NonNull Response<PostResponse> response) {
          if (response.code() == 200) {
            if (response.body() != null) {
              serviceResponse.setStatus(response.body().getStatus());
              serviceResponse.setMessage(response.body().getMessage());
              serviceResponse.setPosts(response.body().getPosts());
              serviceResponse.setAccess_token(response.body().getAccess_token());
              serviceResponse.setRefresh_token(response.body().getRefresh_token());
              Log.d(TAG, new Gson().toJson(response.body()));
              Log.d(TAG, response.body().getMessage());
            }
            postCallback.onSuccess(serviceResponse);
          } else {
            serviceResponse.setStatus("failed");
            serviceResponse.setMessage(context.getString(R.string.network_error));
            postCallback.onError(serviceResponse);
          }
        }
        
        @Override
        public void onFailure(@NonNull Call<PostResponse> call,
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
          postCallback.onError(serviceResponse);
        }
      });
    } catch (Exception e) {
      serviceResponse.setStatus("failed");
      serviceResponse.setMessage(Objects.requireNonNull(e.getMessage()));
      Log.d(TAG, Objects.requireNonNull(e.getMessage()));
    }
  }
  
  // Interface de callback pour l'inscription
  public interface PostCallback {
    void onSuccess(PostResponse response);
    
    void onError(PostResponse response);
  }
}
