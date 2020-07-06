package br.svcdev.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import br.svcdev.weatherapp.adapters.CityAutocompleteAdapter;
import br.svcdev.weatherapp.api.autocompletesearch.AutocompleteSearch;
import br.svcdev.weatherapp.databinding.ActivitySettingsBinding;

import static android.widget.CompoundButton.*;

public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding mBinding;
    private int key;

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
        mBinding.cityName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                AutocompleteSearch autocompleteSearch =
                        (AutocompleteSearch) adapterView.getItemAtPosition(position);
                mBinding.cityName.setText(autocompleteSearch.getLocalizedName());
                key = autocompleteSearch.getKey();
            }
        });

        mBinding.switchNightMode.setChecked(SettingsApp.getSettings().isNightMode());
        mBinding.switchNightMode.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                setDarkTheme(isChecked);
                recreate();
            }
        });
        mBinding.switchTemperatureUnits.setChecked(SettingsApp.getSettings().isTemperatureUnits());
        mBinding.switchWindSpeedUnits.setChecked(SettingsApp.getSettings().isWindSpeedUnits());

        mBinding.tvCancel.setOnClickListener(view -> SettingsActivity.this.finish());
        mBinding.tvApply.setOnClickListener(new ApplyClickListener());
    }

    private void setDarkTheme(boolean isDarkTheme){
        SettingsApp.getSettings().setNightMode(isDarkTheme);
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
            SettingsApp.getSettings().setLocationId(key);
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
                SettingsApp.getSettings().getLocationId());
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