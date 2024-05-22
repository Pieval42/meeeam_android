package com.pvalentin.meeeam.data.network;

import com.pvalentin.meeeam.data.network.request.SignUpRequest;
import com.pvalentin.meeeam.data.network.request.LoginRequest;
import com.pvalentin.meeeam.data.network.response.CountryResponse;
import com.pvalentin.meeeam.data.network.response.LoginResponse;
import com.pvalentin.meeeam.data.network.response.SignUpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("pays/")
    Call<CountryResponse> getCountries();

    @POST("inscription/")
    Call<SignUpResponse> sendSignUpForm(@Body SignUpRequest signUpRequest);

    @POST("connexion/")
    Call<LoginResponse> sendLoginForm(@Body LoginRequest loginRequest);
}
