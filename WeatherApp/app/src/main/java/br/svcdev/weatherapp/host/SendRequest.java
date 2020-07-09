package br.svcdev.weatherapp.host;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import br.svcdev.weatherapp.Constants;
import br.svcdev.weatherapp.DailyForecastsFragment;
import br.svcdev.weatherapp.R;
import br.svcdev.weatherapp.ServerResponse;

public class SendRequest extends AsyncTask<Void, Map<String, String>, Map<String, String>> {

    private Context mContext;
    private String mHost;
    private String mController;
    private int mCityId;
    private String mRequestMethod;
    private String mRequestId;
    private Map<String, Object> mRequestParameters;

    private FragmentManager mManager;

    private String mRequestParametersString;

    public SendRequest(FragmentManager fragmentManager, String host, String controller, int cityId,
                       Map<String, Object> requestParameters, String requestMethod,
                       String requestId) {
        this.mManager = fragmentManager;
        this.mHost = host;
        this.mController = controller;
        this.mCityId = cityId;
        this.mRequestParameters = requestParameters;
        this.mRequestMethod = requestMethod;
        this.mRequestId = requestId;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mRequestParametersString = buildRequestParametersString(mRequestParameters);
        Log.d(Constants.TAG_APP, "SendRequest.onPreExecute: Begin");
    }

    /**
     * 127.0.0.1/currentconditions/v1/293142?apikey=WEATHER_API_KEY&language=ru-ru
     */
    private String buildRequestParametersString(Map<String, Object> requestParameters) {
        Uri.Builder builder = new Uri.Builder();
        builder.appendQueryParameter("apikey", Constants.ACCUWEATHER_API_KEY);
        for (Map.Entry<String, Object> entry : requestParameters.entrySet()) {
            builder.appendQueryParameter(entry.getKey(), entry.getValue().toString());
        }
        builder.build().getEncodedQuery();
        Log.d(Constants.TAG_APP, builder.build().getEncodedQuery());
        return builder.build().getEncodedQuery();
    }

    @Override
    protected Map<String, String> doInBackground(Void... voids) {
        Log.d(Constants.TAG_APP, "SendRequest.doInBackground: Begin async method");
        try {
            URL url = new URL(mHost + mController + mCityId + "?" + mRequestParametersString);
            HttpURLConnection urlConnection = null;
            Log.i(Constants.TAG_APP, "run: url = " + url);
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod(mRequestMethod);
                urlConnection.setReadTimeout(10000);

                InputStream inputStream;
                if (urlConnection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                    inputStream = urlConnection.getInputStream();
                } else {
                    inputStream = urlConnection.getErrorStream();
                }
                BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
                String response = getStringResponse(input);
                Log.d(Constants.TAG_APP, "run: response = " + response);
                Map<String, String> responseMap = new HashMap<>();
                responseMap.put(mRequestId, response);
                return responseMap;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d(Constants.TAG_APP, "SendRequest.doInBackground: End async method");
        return null;
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

    @Override
    protected void onPostExecute(Map<String, String> responseMap) {
        super.onPostExecute(responseMap);
        switch (responseMap.keySet().iterator().next()){
            case HostRequestConstants.URL_GET_CURRENT_CONDITIONS:
                ((ServerResponse) mManager.findFragmentById(R.id.fl_current_frame))
                        .onServerResponse(responseMap);
                break;
            case HostRequestConstants.URL_GET_FORECASTS_CONDITIONS:
                ((ServerResponse) mManager.findFragmentById(R.id.fl_forecast_frame))
                        .onServerResponse(responseMap);
        }
        Log.d(Constants.TAG_APP, "SendRequest.onPostExecute: End.");
    }

}
