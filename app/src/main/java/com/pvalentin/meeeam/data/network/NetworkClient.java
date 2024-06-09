package com.pvalentin.meeeam.data.network;

import static com.pvalentin.meeeam.util.Constants.BASE_URL;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.pvalentin.meeeam.data.network.response.ApiResponse;
import com.pvalentin.meeeam.data.network.response.LoginResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {
  
  private TokenAuthenticator tokenAuthenticator;
  private static ApiService apiService;
  private Retrofit retrofit;
  
  public NetworkClient(Context context) {
    tokenAuthenticator = new TokenAuthenticator(context);
    
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .authenticator(tokenAuthenticator)
        .build();
    
    retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    
    apiService = retrofit.create(ApiService.class);
  }
  public static ApiService getApiService() {
    return apiService;
  }
}
