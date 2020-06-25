package br.svcdev.weatherapp;

public final class SettingsApp {

    private static SettingsApp sSettings;

    private String mLocation;
    private int mLocationIds;
    private boolean mNightMode;
    private boolean mTemperatureUnits;
    private boolean mWindSpeedUnits;

    public static SettingsApp getSettings() {
        if (sSettings == null) sSettings = new SettingsApp();
        return sSettings;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public boolean isTemperatureUnits() {
        return mTemperatureUnits;
    }

    public void setTemperatureUnits(boolean mTemperatureUnits) {
        this.mTemperatureUnits = mTemperatureUnits;
    }

    public boolean isWindSpeedUnits() {
        return mWindSpeedUnits;
    }

    public void setWindSpeedUnits(boolean mWindSpeedUnits) {
        this.mWindSpeedUnits = mWindSpeedUnits;
    }

    public boolean isNightMode() {
        return mNightMode;
    }

    public void setNightMode(boolean mNightMode) {
        this.mNightMode = mNightMode;
    }

    public int getLocationIds() {
        return mLocationIds;
    }

    public void setLocationIds(int mLocationIds) {
        this.mLocationIds = mLocationIds;
    }
}
