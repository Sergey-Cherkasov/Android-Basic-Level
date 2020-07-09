package br.svcdev.weatherapp.api.conditions.forecast;

import com.google.gson.annotations.SerializedName;

import br.svcdev.weatherapp.api.conditions.Values;

public class ForecastTemperature {

    @SerializedName("Minimum")
    private Values minimum;

    @SerializedName("Maximum")
    private Values maximum;

    public Values getMinimum() {
        return minimum;
    }

    public void setMinimum(Values minimum) {
        this.minimum = minimum;
    }

    public Values getMaximum() {
        return maximum;
    }

    public void setMaximum(Values maximum) {
        this.maximum = maximum;
    }
}
