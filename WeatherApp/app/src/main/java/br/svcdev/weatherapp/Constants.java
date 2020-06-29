package br.svcdev.weatherapp;

public final class Constants {

    public static final String TAG_APP = "Weather_App";

    /* Preference file name */
    public static final String APP_PREFERENCES_FILE = "weather_settings";

    /* Preferences keys: Location, night mode, temperature units, wind speed units*/
    public static final String APP_PREFERENCES_LOCATION = "Location";
    public static final String APP_PREFERENCES_LOCATION_ID = "Location_id";
    public static final String APP_PREFERENCES_NIGHT_MODE = "Night_mode";
    public static final String APP_PREFERENCES_TEMPERATURE_UNITS = "Temperature_units";
    public static final String APP_PREFERENCES_WIND_SPEED_UNITS = "Wind_speed_units";


    /* API KEYs for weather sites */
    protected static final String ACCUWEATHER_API_KEY = "U9NAEdJP9XaeAeu9JXweo7vGTGRJfiQq";
    protected static final String OPEN_WEATHER_API_KEY = "2774380a7fbc3859b8bbe154984844b5";

//    protected static final String URL_CURRENT_CONDITIONS =
//            ACCUWEATHER_HOST + URL_GET_CURRENT_CONDITIONS + "/293142?apikey=" + ACCUWEATHER_API_KEY
//                    + "&language=" + getResources().getString(R.string.data_request_language);
    protected static final String URL_LOCATIONS_CITIES_SEARCH =
            "ACCUWEATHER_HOST/currentconditions/v1/293142?apikey=WEATHER_API_KEY&language=ru-ru";

}
