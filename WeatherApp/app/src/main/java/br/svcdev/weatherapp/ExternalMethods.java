package br.svcdev.weatherapp;

import android.content.SharedPreferences;

import br.svcdev.weatherapp.databinding.ActivityMainBinding;

public final class ExternalMethods {

    public static final String TAG_APP = "Weather_App";

    /* Preference file name */
    public static final String APP_PREFERENCES_FILE = "weather_settings";

    /* Preferences keys: Location, night mode, temperature units, wind speed units*/
    public static final String APP_PREFERENCES_LOCATION = "Location";
    public static final String APP_PREFERENCES_NIGHT_MODE = "Night_mode";
    public static final String APP_PREFERENCES_TEMPERATURE_UNITS = "Temperature_units";
    public static final String APP_PREFERENCES_WIND_SPEED_UNITS = "Wind_speed_units";

}
