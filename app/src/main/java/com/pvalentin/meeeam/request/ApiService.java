package com.pvalentin.meeeam.request;

import static com.pvalentin.meeeam.config.Constants.BASE_URL;

import com.pvalentin.meeeam.network.MeeeamApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    private static final Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static final Retrofit retrofit = retrofitBuilder.build();

    private static final MeeeamApiService meeeamApiService = retrofit.create(MeeeamApiService.class);

    public static MeeeamApiService getMeeeamApiService(){
        return meeeamApiService;
    }
}
