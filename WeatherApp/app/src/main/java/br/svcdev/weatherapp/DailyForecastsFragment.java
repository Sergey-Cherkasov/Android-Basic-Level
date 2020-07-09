package br.svcdev.weatherapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import br.svcdev.weatherapp.adapters.ForecastRecyclerViewAdapter;
import br.svcdev.weatherapp.api.conditions.current.CurrentWeather;
import br.svcdev.weatherapp.api.conditions.forecast.ForecastRequest;
import br.svcdev.weatherapp.databinding.FragmentDailyForecastsBinding;
import br.svcdev.weatherapp.host.HostRequestConstants;
import br.svcdev.weatherapp.host.SendRequest;

public class DailyForecastsFragment extends Fragment implements ServerResponse {

    private FragmentDailyForecastsBinding mBinding;

    private static ForecastRequest mForecastRequest;
    private ForecastRecyclerViewAdapter mAdapter;

    public static void setForecastRequest(ForecastRequest forecastRequest) {
        mForecastRequest = forecastRequest;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentDailyForecastsBinding.inflate(inflater, container, false);

        onSendRequestForecastConditions();


        LinearLayoutManager manager = new LinearLayoutManager(mBinding.getRoot().getContext());
        DividerItemDecoration itemDecorator = new DividerItemDecoration(mBinding.getRoot()
                .getContext(), LinearLayoutManager.VERTICAL);
        itemDecorator.setDrawable(getContext().getDrawable(R.drawable.ic_separator));
        mBinding.rvForecastDayOfWeek.setHasFixedSize(true);
        mBinding.rvForecastDayOfWeek.setLayoutManager(manager);
        mBinding.rvForecastDayOfWeek.addItemDecoration(itemDecorator);

        mAdapter = new ForecastRecyclerViewAdapter(mForecastRequest);

        mBinding.rvForecastDayOfWeek.setAdapter(mAdapter);

        return mBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void onSendRequestForecastConditions(){
        String mHost = HostRequestConstants.ACCUWEATHER_HOST;
        String mController = HostRequestConstants.URL_GET_FORECASTS_CONDITIONS;
        int mCityId = SettingsApp.getSettings().getLocationId();
        boolean mDetails = true;
        String mLanguage = getResources().getString(R.string.data_request_language);
        boolean mMetric = true;
        String mRequestMethod = HostRequestConstants.REQUEST_METHOD_GET;
        Map<String, Object> mRequestParameters = new HashMap<>();

        mRequestParameters.put("details", mDetails);
        mRequestParameters.put("metric", mMetric);
        mRequestParameters.put("language", mLanguage);

        SendRequest sendRequest = new SendRequest(getFragmentManager(), mHost, mController, mCityId,
                mRequestParameters, mRequestMethod,
                HostRequestConstants.URL_GET_FORECASTS_CONDITIONS);
        sendRequest.execute();
    }

    @Override
    public void onServerResponse(Map<String, String> response) {
        Iterator<String> key = response.keySet().iterator();
        String requestId = key.next();
        String responseString = response.get(requestId);
        if (responseString.contains("ServiceUnavailable")){
            Toast.makeText(mBinding.getRoot().getContext(), "Service unavailable",
                    Toast.LENGTH_LONG).show();
            return;
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        if (requestId.equals(HostRequestConstants.URL_GET_FORECASTS_CONDITIONS)) {
            ForecastRequest forecastRequest = gson.fromJson(responseString,
                    ForecastRequest.class);
            setForecastRequest(forecastRequest);
            mAdapter.notifyDataSetChanged();
        }
    }
}