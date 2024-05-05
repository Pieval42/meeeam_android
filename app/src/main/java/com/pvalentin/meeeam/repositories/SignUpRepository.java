package com.pvalentin.meeeam.repositories;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.pvalentin.meeeam.R;
import com.pvalentin.meeeam.config.Constants;
import com.pvalentin.meeeam.models.SignUpFormModel;
import com.pvalentin.meeeam.network.MeeeamApiService;
import com.pvalentin.meeeam.request.ApiService;
import com.pvalentin.meeeam.response.RetrofitResponse;
import com.pvalentin.meeeam.response.SignUpResponse;
import com.pvalentin.meeeam.views.SignUpFragment;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpRepository {
    private static final RetrofitResponse serviceResponse = new RetrofitResponse(
            "", "", "", "");

    public static void callSignUpService(SignUpFragment signUpFragment, SignUpFormModel signUpForm, Context context) {
        final String TAG = Constants.TAG;

        MeeeamApiService apiService = ApiService.getMeeeamApiService();

        Call<SignUpResponse> responseCall = apiService.setSignUp(signUpForm);

        assert responseCall != null;
        try {
//            Response serviceResponse = responseCall.execute();
            responseCall.enqueue(new Callback<SignUpResponse>() {
                @Override
                public void onResponse(@NonNull Call<SignUpResponse> call,
                                       @NonNull Response<SignUpResponse> response) {

                    assert response.body() != null;
                    serviceResponse.setStatus(response.body().getStatus());
                    serviceResponse.setMessage(response.body().getMessage());
                    serviceResponse.setData(new Gson().toJson(response.body().getData()));
                    serviceResponse.setToken(response.body().getToken());

                    if (response.code() == 200) {
                        Log.d(TAG, new Gson().toJson(response.body()));
                        assert response.body() != null;
                        Log.d(TAG, response.body().getMessage());
                    }
                    signUpFragment.displayServiceResponse(serviceResponse);
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
                    signUpFragment.displayServiceResponse(serviceResponse);
                }
            });
        } catch (Exception e) {
            serviceResponse.setStatus("failed");
            serviceResponse.setMessage(Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, Objects.requireNonNull(e.getMessage()));
        }
    }
}
