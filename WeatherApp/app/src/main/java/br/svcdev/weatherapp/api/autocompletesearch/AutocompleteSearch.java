package br.svcdev.weatherapp.api.autocompletesearch;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class AutocompleteSearch {

    @SerializedName("Key")
    private int key;
    @SerializedName("LocalizedName")
    private String localizedName;
    @SerializedName("Country")
    private Country country;
    @SerializedName("AdministrativeArea")
    private AdministrativeArea administrativeArea;

    public int getKey() {
        return key;
    }

    public void setId(int key) {
        this.key = key;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public AdministrativeArea getAdministrativeArea() {
        return administrativeArea;
    }

    public void setAdministrativeArea(AdministrativeArea administrativeArea) {
        this.administrativeArea = administrativeArea;
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public String toString() {
        return String.format("Key: %d, CityName: %s", getKey(), getLocalizedName());
    }
}
