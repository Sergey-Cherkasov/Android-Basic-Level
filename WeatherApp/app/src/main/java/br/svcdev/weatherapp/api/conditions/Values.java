package br.svcdev.weatherapp.api.conditions;

import com.google.gson.annotations.SerializedName;

public class Values {

    @SerializedName("Value")
    private double value;

    @SerializedName("Unit")
    private String unit;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
