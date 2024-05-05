package com.pvalentin.meeeam.network;

import com.pvalentin.meeeam.models.SignInFormModel;
import com.pvalentin.meeeam.response.SignInResponse;
import com.pvalentin.meeeam.response.SignUpResponse;
import com.pvalentin.meeeam.models.SignUpFormModel;
import com.pvalentin.meeeam.response.CountryListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MeeeamApiService {
    @GET("pays/")
    Call<CountryListResponse> getCountries();

    @POST("inscription/")
    Call<SignUpResponse> setSignUp(@Body SignUpFormModel signUpFormModel);

    @POST("connexion/")
    Call<SignInResponse> setSignIn(@Body SignInFormModel signInFormModel);
}
