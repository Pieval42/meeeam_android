package com.pvalentin.meeeam.data.network;

import com.pvalentin.meeeam.data.network.request.AuthTokenRequest;
import com.pvalentin.meeeam.data.network.request.LoginRequest;
import com.pvalentin.meeeam.data.network.request.SignUpRequest;
import com.pvalentin.meeeam.data.network.response.AuthTokenResponse;
import com.pvalentin.meeeam.data.network.response.CountryResponse;
import com.pvalentin.meeeam.data.network.response.LoginResponse;
import com.pvalentin.meeeam.data.network.response.MessagesResponse;
import com.pvalentin.meeeam.data.network.response.NewPostResponse;
import com.pvalentin.meeeam.data.network.response.PostResponse;
import com.pvalentin.meeeam.data.network.response.SignUpResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {
  @GET("pays/")
  Call<CountryResponse> getCountries();
  
  @POST("inscription/")
  Call<SignUpResponse> sendSignUpForm(@Body SignUpRequest signUpRequest);
  
  @POST("connexion/")
  Call<LoginResponse> sendLoginForm(@Body LoginRequest loginRequest);
  
  @POST("authentification/")
  Call<AuthTokenResponse> getAuthToken(
      @HeaderMap Map<String, String> headers, @Body AuthTokenRequest authTokenRequest
  );
  
  @GET("messages/get")
  Call<MessagesResponse> getMessages(
      @Header("Authorization") String accessToken,
      @Query("id_utilisateur") int idUtilisateur,
      @Query("id_utilisateur_2") int idUtilisateur2
  );
  
  @GET("profil/getPublications")
  Call<PostResponse> getPosts(
      @Header("Authorization") String accessToken,
      @Query("id_utilisateur") int idUtilisateur,
      @Query("id_page_profil") int idProfilePage
  );
  
  @Multipart
  @POST("profil/newPublication")
  Call<NewPostResponse> sendNewPost(
      @Header("Authorization") String accessToken,
      @Part MultipartBody.Part image, @Part("contenu_publication")RequestBody textContent,
      @Part("id_utilisateur") int idUtilisateur, @Part("id_page_profil") int idProfilePage
      );
}
