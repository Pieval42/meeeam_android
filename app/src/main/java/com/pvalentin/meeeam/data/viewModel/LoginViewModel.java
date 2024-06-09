package com.pvalentin.meeeam.data.viewModel;

import static com.pvalentin.meeeam.util.Constants.AUTH_PREFS_FILE;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.pvalentin.meeeam.data.network.request.LoginRequest;
import com.pvalentin.meeeam.data.network.response.LoginResponse;
import com.pvalentin.meeeam.data.repository.LoginRepository;
import com.pvalentin.meeeam.ui.LoginFragment;

public class LoginViewModel extends ViewModel {
  private final MutableLiveData<Boolean> isAuthenticated = new MutableLiveData<>();
  private SharedPreferences authPrefs;
  
  public LoginViewModel() {
  
  }
  
  public void login(LoginFragment loginFragment, LoginRequest loginRequest, Context context) {
    this.authPrefs = context.getSharedPreferences(
        AUTH_PREFS_FILE, Context.MODE_PRIVATE
    );
    LoginRepository.callLoginService(loginRequest, context, new LoginRepository.LoginCallback() {
      
      @Override
      public void onSuccess(LoginResponse response) {
        LoginResponse.LoginData loginData = response.getData();
        isAuthenticated.setValue(true);
        loginFragment.displayServiceResponse(response);
        SharedPreferences.Editor editor = authPrefs.edit();
        editor.putString("meeeam_access_token", response.getAccess_token());
        editor.putString("meeeam_refresh_token", response.getRefresh_token());
        editor.putInt("meeeam_user_id", loginData.getId_utilisateur());
        editor.putInt("meeeam_user_profile_id", loginData.getId_page_profil());
        editor.putString("meeeam_user_details", new Gson().toJson(loginData.getDetails()));
        editor.apply();
        
        loginFragment.navigateToMainLoggedInActivity();
      }
      
      @Override
      public void onError(LoginResponse response) {
        loginFragment.displayServiceResponse(response);
      }
    });
  }
  
  // Méthode pour vérifier si l'utilisateur est authentifié
  public LiveData<Boolean> isAuthenticated() {
    return isAuthenticated;
  }
}
