package br.svcdev.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import br.svcdev.weatherapp.api.autocompletesearch.AutocompleteSearch;
import br.svcdev.weatherapp.databinding.ActivitySettingsBinding;
import br.svcdev.weatherapp.host.HostRequestConstants;

public class SettingsActivity extends AppCompatActivity {

    private static final int MIN_LENGTH_INPUT_TEXT = 3;

    private ActivitySettingsBinding mBinding;

    private ArrayAdapter<List<AutocompleteSearch>> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySettingsBinding.inflate(LayoutInflater.from(this));
        setContentView(mBinding.getRoot());

        initToolbar();
        initViewElements();

    }

    private void initViewElements() {

        mAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line);

        mBinding.actvLocation.setAdapter(mAdapter);

        mBinding.actvLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String mInputCity = charSequence.toString();
                Log.d(Constants.TAG_APP, mInputCity);
                if (mInputCity.length() > MIN_LENGTH_INPUT_TEXT){
                    sendRequestGetListCities(mInputCity);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        mBinding.actvLocation.setThreshold(3);
        mBinding.actvLocation.setHint("Input city");
        mBinding.switchNightMode.setChecked(SettingsApp.getSettings().isNightMode());
        mBinding.switchTemperatureUnits.setChecked(SettingsApp.getSettings().isTemperatureUnits());
        mBinding.switchWindSpeedUnits.setChecked(SettingsApp.getSettings().isWindSpeedUnits());

        mBinding.tvCancel.setOnClickListener(view -> SettingsActivity.this.finish());
        mBinding.tvApply.setOnClickListener(new ApplyClickListener());
    }

    private void sendRequestGetListCities(String mInputCity) {
        String mUrlString = null;
        try {
            mUrlString = HostRequestConstants.ACCUWEATHER_HOST +
                    HostRequestConstants.URL_GET_LIST_CITIES + "?" +
                    "apikey=" + Constants.ACCUWEATHER_API_KEY + "&" +
                    "q=" + URLEncoder.encode(mInputCity, "UTF-8") + "&" +
                    "language=" + getResources().getString(R.string.data_request_language);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            final URL mUrl = new URL(mUrlString);

            Log.d(Constants.TAG_APP, "mUrl = " + mUrl);

            final Handler handler = new Handler();
            new Thread(() -> {
                HttpURLConnection mConnection;
                try {
                    mConnection = (HttpURLConnection) mUrl.openConnection();
                    mConnection.setRequestMethod(HostRequestConstants.REQUEST_METHOD_GET);
                    mConnection.setReadTimeout(10000);
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(mConnection.getInputStream()));
                    String response = getLines(in);

                    Log.d(Constants.TAG_APP, "Result httpQuery = " + response);

                    Gson gson = new Gson();
                    AutocompleteSearch[] autocompleteSearches = gson.fromJson(response,
                            AutocompleteSearch[].class);
                    handler.post(() -> setResponseOnViewElement(autocompleteSearches));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void setResponseOnViewElement(AutocompleteSearch[] searchRequest) {
        List<AutocompleteSearch> listSearchResult = Arrays.asList(searchRequest);
        mAdapter = new AutocompleteSearchAdapter(this,
                android.R.layout.simple_list_item_1,
                listSearchResult);
//        Log.d(Constants.TAG_APP, "searchRequest.length = " + searchRequest.length);
//        for (int i = 0; i < searchRequest.length; i++) {
//            Log.d(Constants.TAG_APP,"item = " + searchRequest[i].toString());
//        }
    }

    private String getLines(BufferedReader in) {
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

    private void initToolbar() {
        setSupportActionBar(mBinding.tlbSettings.appToolbar);
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private class ApplyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (!mBinding.actvLocation.getText().toString().equals("")) {
                SettingsApp.getSettings().setLocation(String.valueOf(mBinding.actvLocation
                        .getText()));
//                SettingsApp.getSettings().setLocationIds(mCities.indexOf(SettingsApp.getSettings()
//                        .getLocation()));
            }
            SettingsApp.getSettings().setNightMode(mBinding.switchNightMode.isChecked());
            SettingsApp.getSettings().setTemperatureUnits(mBinding.switchTemperatureUnits
                    .isChecked());
            SettingsApp.getSettings().setWindSpeedUnits(mBinding.switchWindSpeedUnits.isChecked());
            onSaveSettings();
            finish();
        }
    }

    private void onSaveSettings() {
        SharedPreferences mPreferences = getSharedPreferences(Constants.APP_PREFERENCES_FILE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mPreferences.edit();
        mEditor.putString(Constants.APP_PREFERENCES_LOCATION,
                String.valueOf(SettingsApp.getSettings().getLocation()));
        mEditor.putInt(Constants.APP_PREFERENCES_LOCATION_ID,
                SettingsApp.getSettings().getLocationIds());
        mEditor.putBoolean(Constants.APP_PREFERENCES_NIGHT_MODE,
                SettingsApp.getSettings().isNightMode());
        mEditor.putBoolean(Constants.APP_PREFERENCES_TEMPERATURE_UNITS,
                SettingsApp.getSettings().isTemperatureUnits());
        mEditor.putBoolean(Constants.APP_PREFERENCES_WIND_SPEED_UNITS,
                SettingsApp.getSettings().isWindSpeedUnits());
        mEditor.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private static class AutocompleteSearchAdapter extends ArrayAdapter<List<AutocompleteSearch>> {
        List<AutocompleteSearch> listSerachResult;

        public AutocompleteSearchAdapter(Context context, int layout, List<AutocompleteSearch> listSearchResult) {
            super(context, layout);
            this.listSerachResult = listSearchResult;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            AutocompleteSearch item = listSerachResult.get(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(android.R.layout.simple_list_item_1, null);
            }
            ((TextView) convertView.findViewById(android.R.id.text1))
                    .setText(item.getLocalizedName());

            return convertView;
        }

    }
}