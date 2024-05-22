package com.pvalentin.meeeam.data.network;

import static com.pvalentin.meeeam.util.Constants.BASE_URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {
    private static final Retrofit.Builder RETROFIT_BUILDER =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static final Retrofit RETROFIT = RETROFIT_BUILDER.build();

    private static final ApiService API_SERVICE = RETROFIT.create(ApiService.class);

    public static ApiService getApiService(){
        return API_SERVICE;
    }
}
