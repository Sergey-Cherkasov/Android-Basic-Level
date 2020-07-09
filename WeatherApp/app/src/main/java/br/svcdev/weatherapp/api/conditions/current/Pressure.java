package br.svcdev.weatherapp.api.conditions.current;

import com.google.gson.annotations.SerializedName;

import br.svcdev.weatherapp.api.conditions.Values;

public class Pressure {

    @SerializedName("Metric")
    private Values metric;

    @SerializedName("Imperial")
    private Values imperial;

    public Values getMetric() {
        return metric;
    }

    public void setMetric(Values metric) {
        this.metric = metric;
    }

    public Values getImperial() {
        return imperial;
    }

    public void setImperial(Values imperial) {
        this.imperial = imperial;
    }
}
