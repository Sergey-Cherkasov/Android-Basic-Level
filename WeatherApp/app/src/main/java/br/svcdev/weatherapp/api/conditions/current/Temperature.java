package br.svcdev.weatherapp.api.conditions.current;

import com.google.gson.annotations.SerializedName;

public class Temperature {

    @SerializedName("Metric")
    private Metric metric;

    @SerializedName("Imperial")
    private Imperial imperial;


}
