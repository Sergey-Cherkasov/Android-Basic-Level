package br.svcdev.weatherapp;

public final class SettingsApp {

    private static SettingsApp mSettings;

    private String mLocation;
    private boolean mNightMode;
    private boolean mTemperatureUnits;
    private boolean mWindSpeedUnits;

    public static SettingsApp getmSettings() {
        if (mSettings == null) mSettings = new SettingsApp();
        return mSettings;
    }

    public String getmLocation() {
        return mLocation;
    }

    public void setmLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public boolean ismTemperatureUnits() {
        return mTemperatureUnits;
    }

    public void setmTemperatureUnits(boolean mTemperatureUnits) {
        this.mTemperatureUnits = mTemperatureUnits;
    }

    public boolean ismWindSpeedUnits() {
        return mWindSpeedUnits;
    }

    public void setmWindSpeedUnits(boolean mWindSpeedUnits) {
        this.mWindSpeedUnits = mWindSpeedUnits;
    }

    public boolean ismNightMode() {
        return mNightMode;
    }

    public void setmNightMode(boolean mNightMode) {
        this.mNightMode = mNightMode;
    }
}
