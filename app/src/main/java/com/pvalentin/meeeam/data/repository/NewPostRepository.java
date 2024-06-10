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
import com.pvalentin.meeeam.data.network.response.NewPostResponse;
import com.pvalentin.meeeam.util.Constants;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPostRepository {
  private static final String TAG = Constants.TAG + "." + NewPostRepository.class.getSimpleName();
  private static final NewPostResponse serviceResponse = new NewPostResponse(
      "", "", "", "");
  public static void callNewPostService(
      Context context, String text, byte[] imageData, String extension, NewPostCallback newPostCallback
  ) {
    
    SharedPreferences authPrefs = context.getSharedPreferences(AUTH_PREFS_FILE, Context.MODE_PRIVATE);
    int userId = authPrefs.getInt("meeeam_user_id", -1);
    int profileId = authPrefs.getInt("meeeam_user_profile_id", -1);
    String timestamp = Long.toString(new Timestamp(new Date().getTime()).getTime());
    
    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),
        imageData);
    MultipartBody.Part body = MultipartBody.Part.createFormData(
        "image", "IMG_" + Integer.toString(userId) + "_" + timestamp + "." + extension,
        requestFile);
    RequestBody textContent = RequestBody.create(MediaType.parse("multipart/form-data"), text);
    
    ApiService apiService = NetworkClient.getApiService();
    
    String accessToken = "Bearer " + authPrefs.getString("meeeam_access_token", null);
    
    Call<NewPostResponse> responseCall = apiService.sendNewPost(accessToken, body, textContent,
        userId, profileId);
    
    assert responseCall != null;
    try {
      responseCall.enqueue(new Callback<NewPostResponse>() {
        @Override
        public void onResponse(@NonNull Call<NewPostResponse> call,
                               @NonNull Response<NewPostResponse> response) {
          if (response.code() == 200) {
            if (response.body() != null) {
              serviceResponse.setStatus(response.body().getStatus());
              serviceResponse.setMessage(response.body().getMessage());
              serviceResponse.setAccess_token(response.body().getAccess_token());
              serviceResponse.setRefresh_token(response.body().getRefresh_token());
              Log.d(TAG, new Gson().toJson(response.body()));
              Log.d(TAG, response.body().getMessage());
            }
            newPostCallback.onSuccess(serviceResponse);
          } else {
            serviceResponse.setStatus("failed");
            serviceResponse.setMessage(context.getString(R.string.network_error));
            newPostCallback.onError(serviceResponse);
          }
        }
        
        @Override
        public void onFailure(@NonNull Call<NewPostResponse> call,
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
          newPostCallback.onError(serviceResponse);
        }
      });
    } catch (Exception e) {
      serviceResponse.setStatus("failed");
      serviceResponse.setMessage(Objects.requireNonNull(e.getMessage()));
      Log.d(TAG, Objects.requireNonNull(e.getMessage()));
    }
  }
  
  public interface NewPostCallback {
    void onSuccess(NewPostResponse response);
    
    void onError(NewPostResponse response);
  }
}
