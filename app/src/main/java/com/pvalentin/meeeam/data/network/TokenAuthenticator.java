package com.pvalentin.meeeam.data.network;

import static com.pvalentin.meeeam.util.Constants.AUTH_PREFS_FILE;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.pvalentin.meeeam.data.network.request.AuthTokenRequest;
import com.pvalentin.meeeam.data.network.response.AuthTokenResponse;
import com.pvalentin.meeeam.ui.MainActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;

public class TokenAuthenticator extends MainActivity implements Authenticator {
  private final SharedPreferences authPrefs;
  private final AuthTokenRequest authTokenRequest;
  private AuthTokenResponse authTokenResponse;
  private String accessToken;
  
  public TokenAuthenticator (Context context, int id_utilisateur, int id_page_profil,
                             String pseudo_utilisateur, String meeeam_refresh_token)
  {
    this.authPrefs = context.getSharedPreferences(AUTH_PREFS_FILE, Context.MODE_PRIVATE);
    this.accessToken = "";
    this.authTokenRequest = new AuthTokenRequest(
        id_utilisateur, id_page_profil, pseudo_utilisateur, meeeam_refresh_token
    );
    
  }
  
  public TokenAuthenticator(Context context) {
    
    this.authPrefs = context.getSharedPreferences(AUTH_PREFS_FILE, Context.MODE_PRIVATE);;
    this.accessToken = "";
    this.authTokenRequest = new AuthTokenRequest(
        -1, -1, "", ""
    );
    this.authTokenResponse = new AuthTokenResponse(
        "", "", "", ""
    );
  }
  
  @Override
  public Request authenticate(Route route, @NonNull Response response) throws IOException {
    String updatedToken = getUpdatedToken();
    if (updatedToken == null) {
      return null;
    }
    return response.request().newBuilder().header(
        "Authorization", "Bearer " + updatedToken
    ).build();
  }
  
  private String getUpdatedToken() throws IOException {
    this.authTokenRequest.setMeeeam_refresh_token("Refresh " + authPrefs.getString(
        "meeeam_refresh_token", null
    ));
    this.accessToken = "Bearer " + authPrefs.getString("meeeam_access_token", null);
    
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", this.accessToken);
    
    Call<AuthTokenResponse> call = NetworkClient.getApiService().getAuthToken(headers, authTokenRequest);
    retrofit2.Response<AuthTokenResponse> response = call.execute();
    if(response.isSuccessful()) {
      authTokenResponse = response.body();
    }
    
    if (authTokenResponse != null){
      String newAccessToken = authTokenResponse.getAccess_token();
      String newRefreshToken = authTokenResponse.getRefresh_token();
      SharedPreferences.Editor editor = authPrefs.edit();
      editor.putString("meeeam_access_token", newAccessToken);
      editor.putString("meeeam_refresh_token", newRefreshToken);
      editor.apply();
      return newAccessToken;
    }
    return null;
  }
}
