package com.pvalentin.meeeam.data.viewModel;
import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.pvalentin.meeeam.data.model.CountryModel;
import com.pvalentin.meeeam.data.network.request.SignUpRequest;
import com.pvalentin.meeeam.data.network.response.CountryResponse;
import com.pvalentin.meeeam.data.network.response.SignUpResponse;
import com.pvalentin.meeeam.data.repository.CountryRepository;
import com.pvalentin.meeeam.data.repository.SignUpRepository;
import com.pvalentin.meeeam.ui.home.SignUpFragment;

import java.util.ArrayList;

public class SignUpViewModel extends ViewModel {
  private static final ArrayList<String> countriesName = new ArrayList<>();
  public void signUp(SignUpFragment signUpFragment, SignUpRequest signUpRequest, Context context) {
    SignUpRepository.callSignUpService(signUpRequest, context, new SignUpRepository.SignUpCallback() {
      
      @Override
      public void onSuccess(SignUpResponse response) {
        signUpFragment.displayServiceResponse(response);
      }
      @Override
      public void onError(SignUpResponse response) {
        signUpFragment.displayServiceResponse(response);
      }
    });
  }
  public void getCountries(SignUpFragment signUpFragment, Context context) {
    CountryRepository.callCountryService(context, new CountryRepository.CountryCallback() {
      @Override
      public void onSuccess(CountryResponse response) {
        countriesName.add("");
        for (CountryModel country : response.getCountries()) {
          countriesName.add(country.getNameFr());
        }
        signUpFragment.setCountriesDropdown(countriesName);
      }
      @Override
      public void onError(CountryResponse response) {
        signUpFragment.displayCountriesError(response.getMessage());
      }
    });
  }
}
