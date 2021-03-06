package br.svcdev.weatherapp.api.conditions.current;

import com.google.gson.annotations.SerializedName;

public final class CurrentWeather {

    @SerializedName("WeatherIcon")
    private int weatherIcon;

    @SerializedName("Temperature")
    private Temperature temperature;

    @SerializedName("RelativeHumidity")
    private int relativeHumidity;

    @SerializedName("Wind")
    private Wind wind;

    @SerializedName("Pressure")
    private Pressure pressure;

    public int getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(int weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public int getRelativeHumidity() {
        return relativeHumidity;
    }

    public void setRelativeHumidity(int relativeHumidity) {
        this.relativeHumidity = relativeHumidity;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Pressure getPressure() {
        return pressure;
    }

    public void setPressure(Pressure pressure) {
        this.pressure = pressure;
    }

}
