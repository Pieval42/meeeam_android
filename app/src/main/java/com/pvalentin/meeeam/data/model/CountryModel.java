package com.pvalentin.meeeam.data.model;

import androidx.annotation.NonNull;

public class CountryModel {
    private int id_pays;
    private String code_pays;
    private String nom_fr;
    private String nom_en;

    public CountryModel(int id_pays, String code_pays, String nom_fr, String nom_en) {
        this.id_pays = id_pays;
        this.code_pays = code_pays;
        this.nom_fr = nom_fr;
        this.nom_en = nom_en;
    }

    public int getId() {
        return id_pays;
    }

    public void setId(int id_pays) {
        this.id_pays = id_pays;
    }

    public String getCode() {
        return code_pays;
    }

    public void setCode(String code_pays) {
        this.code_pays = code_pays;
    }

    public String getNameFr() {
        return nom_fr;
    }

    public void setNameFr(String nom_fr) {
        this.nom_fr = nom_fr;
    }

    public String getNameEn() {
        return nom_en;
    }

    public void setNameEn(String nom_en) {
        this.nom_en = nom_en;
    }

    @NonNull
    public String toString() {
        return this.nom_fr;
    }
}
