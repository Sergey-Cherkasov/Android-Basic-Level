package br.svcdev.weatherapp.api.conditions.current;

import com.google.gson.annotations.SerializedName;

public class Imperial {

    @SerializedName("Value")
    private double value;

    @SerializedName("Unit")
    private String unit;

    public double getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
