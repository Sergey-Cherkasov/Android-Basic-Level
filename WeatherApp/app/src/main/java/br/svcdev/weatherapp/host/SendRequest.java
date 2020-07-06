package br.svcdev.weatherapp.host;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import br.svcdev.weatherapp.Constants;
import br.svcdev.weatherapp.R;
import br.svcdev.weatherapp.SettingsApp;
import br.svcdev.weatherapp.api.conditions.current.CurrentWeather;

public class SendRequest extends AsyncTask<Void, Object[], Object[]> {

    private Context mContext;
    private FragmentManager mManager;

    public SendRequest(Context context, FragmentManager manager){
        this.mContext = context;
        this.mManager = manager;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(Constants.TAG_APP, "SendRequest.onPreExecute: Begin");
    }

    @Override
    protected Object[] doInBackground(Void... voids) {
        Log.d(Constants.TAG_APP, "SendRequest.doInBackground: Begin async method");
        CurrentWeather[] currentWeather = null;
        try {
            URL url = new URL(buildApiStringRequest(SettingsApp.getSettings().getLocationId(),
                    true));
            HttpURLConnection urlConnection = null;
            Log.i(Constants.TAG_APP, "run: response = " + url);
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod(HostRequestConstants.REQUEST_METHOD_GET);
                urlConnection.setReadTimeout(10000);
                BufferedReader input = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream()));
                String response = getStringResponse(input);
                Log.i(Constants.TAG_APP, "run: response = " + response);
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                currentWeather = gson.fromJson(response,
                        CurrentWeather[].class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d(Constants.TAG_APP, "SendRequest.doInBackground: End async method");
        return currentWeather;
    }

    private String getStringResponse(BufferedReader in) {
        String tmpString;
        StringBuilder resultString = new StringBuilder();
        try {
            while ((tmpString = in.readLine()) != null) {
                resultString.append(tmpString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultString.toString();
    }

    /**
     * 127.0.0.1/currentconditions/v1/293142?apikey=WEATHER_API_KEY&language=ru-ru
     */
    private String buildApiStringRequest(int locationId, boolean details) {
        String url = "";
        url = HostRequestConstants.ACCUWEATHER_HOST +
                HostRequestConstants.URL_GET_CURRENT_CONDITIONS +
                locationId + "?" +
                "apikey=" + Constants.ACCUWEATHER_API_KEY + "&" +
                "details=" + details + "&" +
                "language=" + mContext.getResources().getString(R.string.data_request_language);
        return url;
    }

    @Override
    protected void onPostExecute(Object[] o) {
        super.onPostExecute(o);
        setRequestResultOnGUI((CurrentWeather[]) o);
        Log.d(Constants.TAG_APP, "SendRequest.onPostExecute: End.");
    }

    private void setRequestResultOnGUI(CurrentWeather[] currentWeather) {
        if (currentWeather.length == 1) {
            Log.i(Constants.TAG_APP, String.format("MainActivity.setRequestResultOnGUI: temperature = %d",
                    (int) currentWeather[0].getTemperature().getMetric().getValue()));
            ((TextView) mManager.findFragmentById(R.id.fl_current_frame).getView()
                    .findViewById(R.id.tv_temperature_value))
                    .setText(String.format("%d", (int) currentWeather[0]
                            .getTemperature().getMetric().getValue()));
            ((TextView) mManager.findFragmentById(R.id.fl_current_frame).getView()
                    .findViewById(R.id.tv_air_humidity_value))
                    .setText(String.format("%d %%", (int) currentWeather[0]
                            .getRelativeHumidity()));
            ((TextView) mManager.findFragmentById(R.id.fl_current_frame).getView()
                    .findViewById(R.id.tv_air_pressure_value))
                    .setText(String.format("%d %s", (int) currentWeather[0]
                            .getPressure().getMetric().getValue(),
                            currentWeather[0].getPressure().getMetric().getUnit()));
            ((TextView) mManager.findFragmentById(R.id.fl_current_frame).getView()
                    .findViewById(R.id.tv_wind_value))
                    .setText(String.format("%d %s", (int) currentWeather[0]
                            .getWind().getSpeed().getMetric().getValue(),
                            currentWeather[0].getWind().getSpeed().getMetric().getUnit()));
        }
    }
}
