package br.svcdev.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import br.svcdev.weatherapp.adapters.CityAutocompleteAdapter;
import br.svcdev.weatherapp.api.autocompletesearch.AutocompleteSearch;
import br.svcdev.weatherapp.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySettingsBinding.inflate(LayoutInflater.from(this));
        setContentView(mBinding.getRoot());

        initToolbar();
        initViewElements();

    }

    private void initViewElements() {
        mBinding.cityName.setThreshold(3);
        mBinding.cityName.setAutocompleteDelay(800);
        mBinding.cityName.setAdapter(new CityAutocompleteAdapter(this));
        mBinding.cityName.setIndicatorLoading(mBinding.progressBarCircle);
        mBinding.cityName.setOnItemClickListener((adapterView, view, position, id) -> {
            AutocompleteSearch autocompleteSearch =
                    (AutocompleteSearch) adapterView.getItemAtPosition(position);
            mBinding.cityName.setText(autocompleteSearch.getLocalizedName());
        });

        mBinding.switchNightMode.setChecked(SettingsApp.getSettings().isNightMode());
        mBinding.switchTemperatureUnits.setChecked(SettingsApp.getSettings().isTemperatureUnits());
        mBinding.switchWindSpeedUnits.setChecked(SettingsApp.getSettings().isWindSpeedUnits());

        mBinding.tvCancel.setOnClickListener(view -> SettingsActivity.this.finish());
        mBinding.tvApply.setOnClickListener(new ApplyClickListener());
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
            if (!mBinding.cityName.getText().toString().equals("")) {
                SettingsApp.getSettings().setLocation(String.valueOf(mBinding.cityName
                        .getText()));
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

}