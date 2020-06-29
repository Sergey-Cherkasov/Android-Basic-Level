package br.svcdev.weatherapp.api.locations;

import br.svcdev.weatherapp.api.autocompletesearch.Country;

public class LocationRequest {

    private long key;
    private String localizedName;
    private Country country;

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
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
}
