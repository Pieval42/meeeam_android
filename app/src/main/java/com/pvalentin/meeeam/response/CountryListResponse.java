package com.pvalentin.meeeam.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pvalentin.meeeam.models.CountryModel;

import java.util.List;

/**
 * Récupère la liste de tous les pays
 */
public class CountryListResponse {
    @SerializedName("data")
    @Expose()
    private List<CountryModel> countries;
    public List<CountryModel> getCountries(){
        return countries;
    }

}
