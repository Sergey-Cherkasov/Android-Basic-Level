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
    public static final String ACCUWEATHER_API_KEY = "U9NAEdJP9XaeAeu9JXweo7vGTGRJfiQq";
    protected static final String OPEN_WEATHER_API_KEY = "2774380a7fbc3859b8bbe154984844b5";
//    public static final String YANDEX_WEATHER_API_KEY = "db04b1bb-4d7c-485f-8e02-9252032416d6";

    /* Формат запроса к Yandex.Weather: GET https://api.weather.yandex.ru/v2/forecast?
    * Header request: X-Yandex-API-Key: db04b1bb-4d7c-485f-8e02-9252032416d6*/

}
