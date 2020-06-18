package br.svcdev.weatherapp;

import android.content.SharedPreferences;

import br.svcdev.weatherapp.databinding.ActivityMainBinding;

public class ExternalMethods {

    /* Preference file name */
    public static final String APP_PREFERENCES_FILE = "weather_settings";

    /* Night mode preferences key */
    public static final String APP_NIGHT_MODE = "night_mode";

    static SharedPreferences mPreferences;

}
