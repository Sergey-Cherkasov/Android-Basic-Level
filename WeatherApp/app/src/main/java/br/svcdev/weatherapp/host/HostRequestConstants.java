package br.svcdev.weatherapp.host;

public abstract class HostRequestConstants {

    public static final String REQUEST_METHOD_GET = "GET";
    public static final String ACCUWEATHER_HOST = "http://dataservice.accuweather.com";

    /**
     * Идентификатор url получения текущего прогноза погоды
     * @example http://127.0.0.1/currentconditions/v1/{locationId}?apikey=&details=true&language=
     */
    public static final String URL_GET_CURRENT_CONDITIONS = "/currentconditions/v1/";

    /**
     * Идентификатор url получения прогноза погоды на 5 дней
     * GET /forecasts/v1/daily/5day/293142?apikey=U9NAEdJP9XaeAeu9JXweo7vGTGRJfiQq&details=true&metric=true
     * @example http://127.0.0.1/forecasts/v1/daily/5day/{locationId}?apikey=&details=true&metric=true&language=
     */
    public static final String URL_GET_FORECASTS_CONDITIONS = "/forecasts/v1/daily/5day/";

    /**
     * Идентификатор url получения информации о местоположении города
     * @example http://127.0.0.1/locations/v1/cities/search?apikey=&q=&language=
     */
    public static final String URL_GET_LOCATIONS_CITIES_SEARCH = "/locations/v1/cities/search";

    /**
     * Идентификатор url получения списка городов для AutoCompleteTextView
     * @example http://127.0.0.1/locations/v1/cities/search?apikey=&q=&language=
     */
    public static final String URL_GET_LIST_CITIES = "/locations/v1/cities/autocomplete";

}
