package com.pvalentin.meeeam.data.network.response;

import com.google.gson.annotations.SerializedName;
import com.pvalentin.meeeam.data.model.CountryModel;

import java.util.List;

/**
 * Récupère la liste de tous les pays
 */
public class CountryResponse extends ApiResponse {
  
  @SerializedName("data")
  private List<CountryModel> countries;
  
  public CountryResponse(String status, String message, String access_token, String refresh_token) {
    super(status, message, access_token, refresh_token);
  }
  
  
  public List<CountryModel> getCountries() {
    return countries;
  }
  
  public void setCountries(List<CountryModel> countries) {
    this.countries = countries;
  }
}
