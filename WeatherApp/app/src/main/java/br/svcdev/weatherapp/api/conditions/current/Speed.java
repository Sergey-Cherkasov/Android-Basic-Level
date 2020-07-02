package br.svcdev.weatherapp.api.conditions.current;

import com.google.gson.annotations.SerializedName;

public class Speed {

    @SerializedName("Metric")
    private Metric metric;

    @SerializedName("Imperial")
    private Imperial imperial;

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    public Imperial getImperial() {
        return imperial;
    }

    public void setImperial(Imperial imperial) {
        this.imperial = imperial;
    }
}
