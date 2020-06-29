package br.svcdev.weatherapp.api.autocompletesearch;

import com.google.gson.annotations.SerializedName;

public class Country {
    @SerializedName("ID")
    private String id;
    @SerializedName("LocalizedName")
    private String localizedName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }
}
