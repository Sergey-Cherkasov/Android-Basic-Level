package br.svcdev.weatherapp.api.conditions.forecast;

import com.google.gson.annotations.SerializedName;

public class ForecastRequest {

    @SerializedName("DailyForecasts")
    private DayForecast[] dailyForecasts;

    public DayForecast[] getDailyForecasts() {
        return dailyForecasts;
    }

    public void setDailyForecasts(DayForecast[] dailyForecasts) {
        this.dailyForecasts = dailyForecasts;
    }
}
