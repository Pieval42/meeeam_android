package com.pvalentin.meeeam.data.repository;

import static com.pvalentin.meeeam.util.Constants.TAG;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.pvalentin.meeeam.R;
import com.pvalentin.meeeam.data.network.ApiService;
import com.pvalentin.meeeam.data.network.NetworkClient;
import com.pvalentin.meeeam.data.network.response.CountryResponse;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryRepository {
  private static final CountryResponse serviceResponse = new CountryResponse(
      "", "", "");
  
  public static void callCountryService(Context context, CountryCallback countryCallback) {
    try {
      ApiService apiService = NetworkClient.getApiService();
      
      Call<CountryResponse> responseCall = apiService.getCountries();
      
      assert responseCall != null;
      responseCall.enqueue(new Callback<CountryResponse>() {
        @Override
        public void onResponse(@NonNull Call<CountryResponse> call,
                               @NonNull Response<CountryResponse> response) {
          assert response.body() != null;
          serviceResponse.setStatus(response.body().getStatus());
          serviceResponse.setMessage(response.body().getMessage());
          serviceResponse.setCountries(response.body().getCountries());
          
          if (response.code() == 200) {
            try {
              
              countryCallback.onSuccess(serviceResponse);
              
            } catch (Exception e) {
              Log.d(TAG, Objects.requireNonNull(e.getMessage()));
            }
            
          } else {
            try {
              assert response.errorBody() != null;
              Log.d(TAG, response.errorBody().toString());
              countryCallback.onError(serviceResponse);
            } catch (Exception e) {
              Log.d(TAG, Objects.requireNonNull(e.getMessage()));
            }
          }
        }
        
        @Override
        public void onFailure(
            @NonNull Call<CountryResponse> call, @NonNull Throwable throwable
        ) {
          serviceResponse.setStatus("failed");
          serviceResponse.setMessage(context.getString(R.string.network_error) + " "
              + context.getString(R.string.country_list_not_loaded)
          );
          try {
            throw throwable;
          } catch (Throwable e) {
            Log.d(TAG, Objects.requireNonNull(e.getMessage()));
          }
          countryCallback.onError(serviceResponse);
        }
      });
    } catch (Exception e) {
      serviceResponse.setStatus("failed");
      serviceResponse.setMessage(Objects.requireNonNull(e.getMessage()));
      Log.d(TAG, Objects.requireNonNull(e.getMessage()));
    }
    
  }
  
  public interface CountryCallback {
    void onSuccess(CountryResponse response);
    
    void onError(CountryResponse response);
  }
}
