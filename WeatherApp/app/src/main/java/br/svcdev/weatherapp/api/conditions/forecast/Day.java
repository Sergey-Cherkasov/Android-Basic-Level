package br.svcdev.weatherapp.api.conditions.forecast;

import com.google.gson.annotations.SerializedName;

import br.svcdev.weatherapp.api.conditions.current.Wind;

public class Day {

    @SerializedName("Wind")
    private Wind wind;

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }
}
