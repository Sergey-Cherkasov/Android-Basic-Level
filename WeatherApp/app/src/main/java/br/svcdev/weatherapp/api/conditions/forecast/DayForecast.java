package br.svcdev.weatherapp.api.conditions.forecast;

import com.google.gson.annotations.SerializedName;

public class DayForecast {

    @SerializedName("Date")
    private String date;

    @SerializedName("Temperature")
    private ForecastTemperature forecastTemperature;

    @SerializedName("Day")
    private Day day;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ForecastTemperature getForecastTemperature() {
        return forecastTemperature;
    }

    public void setForecastTemperature(ForecastTemperature forecastTemperature) {
        this.forecastTemperature = forecastTemperature;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }
}
