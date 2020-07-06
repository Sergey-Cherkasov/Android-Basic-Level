package br.svcdev.weatherapp.api.conditions.current;

import com.google.gson.annotations.SerializedName;

public class Wind {

    @SerializedName("Speed")
    private Speed speed;

    public Speed getSpeed() {
        return speed;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }
}
