package br.svcdev.weatherapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import br.svcdev.weatherapp.api.conditions.current.CurrentWeather;
import br.svcdev.weatherapp.databinding.FragmentCurrentConditionsBinding;
import br.svcdev.weatherapp.host.HostRequestConstants;
import br.svcdev.weatherapp.host.SendRequest;

public class CurrentConditionsFragment extends Fragment implements ServerResponse{

    private static final String NAME_FRAGMENT = "MainFragment";
    private Context mContext;
    private SharedPreferences mPreferences;
    private FragmentCurrentConditionsBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentCurrentConditionsBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        onSendRequestCurrentConditions();
    }

    private void onSendRequestCurrentConditions(){
        String mHost = HostRequestConstants.ACCUWEATHER_HOST;
        String mController = HostRequestConstants.URL_GET_CURRENT_CONDITIONS;
        int mCityId = SettingsApp.getSettings().getLocationId();
        boolean mDetails = true;
        String mLanguage = getResources().getString(R.string.data_request_language);
        String mRequestMethod = HostRequestConstants.REQUEST_METHOD_GET;
        Map<String, Object> mRequestParameters = new HashMap<>();

        mRequestParameters.put("details", mDetails);
        mRequestParameters.put("language", mLanguage);

        SendRequest sendRequest = new SendRequest(getFragmentManager(), mHost, mController, mCityId,
                mRequestParameters, mRequestMethod, HostRequestConstants.URL_GET_CURRENT_CONDITIONS);
        sendRequest.execute();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    @Override
    public void onServerResponse(Map<String, String> response) {
        Iterator<String> key = response.keySet().iterator();
        String requestId = key.next();
        String responseString = response.get(requestId);
        if (responseString.contains("ServiceUnavailable")){
            Log.d(Constants.TAG_APP, "Service unavailable");
            Toast.makeText(mBinding.getRoot().getContext(), "Service unavailable",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        if (requestId.equals(HostRequestConstants.URL_GET_CURRENT_CONDITIONS)) {
            CurrentWeather[] currentWeathers = gson.fromJson(responseString,
                    CurrentWeather[].class);
            onSetCurrentConditions(currentWeathers);
        }
    }

    private void onSetCurrentConditions(CurrentWeather[] currentWeathers) {
        mBinding.componentTemperature.tvTemperatureValue
                .setText(String.format("%d", (int) currentWeathers[0]
                        .getTemperature().getMetric().getValue()));
        mBinding.atmosphericIndicators.tvAirHumidityValue
                .setText(String.format("%d %%", (int) currentWeathers[0]
                        .getRelativeHumidity()));
        mBinding.atmosphericIndicators.tvAirPressureValue
                .setText(String.format("%d %s", (int) currentWeathers[0]
                                .getPressure().getMetric().getValue(),
                        currentWeathers[0].getPressure().getMetric().getUnit()));
        mBinding.atmosphericIndicators.tvWindValue
                .setText(String.format("%d %s", (int) currentWeathers[0]
                                .getWind().getSpeed().getMetric().getValue(),
                        currentWeathers[0].getWind().getSpeed().getMetric().getUnit()));
    }

}
