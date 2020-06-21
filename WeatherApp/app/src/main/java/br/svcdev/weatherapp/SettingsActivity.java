package br.svcdev.weatherapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import br.svcdev.weatherapp.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {

    ActivitySettingsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        setSupportActionBar(mBinding.tlbSettings.appToolbar);

        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }

        String[] mCities = getResources().getStringArray(R.array.cities);

        mBinding.actvLocation.setThreshold(3);
        mBinding.actvLocation.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line,
                mCities));
        Log.d(ExternalMethods.TAG_APP, "SettingsActivity.onCreate: Location = " + SettingsApp.getmSettings().getmLocation());
        mBinding.actvLocation.setText(SettingsApp.getmSettings().getmLocation());

        Log.d(ExternalMethods.TAG_APP, "SettingsActivity.onCreate: Night mode = " + SettingsApp.getmSettings().ismNightMode());
        mBinding.switchNightMode.setChecked(SettingsApp.getmSettings().ismNightMode());
        Log.d(ExternalMethods.TAG_APP, "SettingsActivity.onCreate: Temperature units = " + SettingsApp.getmSettings().ismTemperatureUnits());
        mBinding.switchTemperatureUnits.setChecked(SettingsApp.getmSettings().ismTemperatureUnits());
        Log.d(ExternalMethods.TAG_APP, "SettingsActivity.onCreate: Wind speed units = " + SettingsApp.getmSettings().ismWindSpeedUnits());
        mBinding.switchWindSpeedUnits.setChecked(SettingsApp.getmSettings().ismWindSpeedUnits());

        mBinding.tvCancel.setOnClickListener(view -> SettingsActivity.this.finish());
        mBinding.tvApply.setOnClickListener(new ApplyClickListener());

    }

    private class ApplyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            SettingsApp.getmSettings().setmLocation(String.valueOf(mBinding.actvLocation.getText()));
            SettingsApp.getmSettings().setmNightMode(mBinding.switchNightMode.isChecked());
            SettingsApp.getmSettings().setmTemperatureUnits(mBinding.switchTemperatureUnits.isChecked());
            SettingsApp.getmSettings().setmWindSpeedUnits(mBinding.switchWindSpeedUnits.isChecked());

            Log.d(ExternalMethods.TAG_APP,
                    "SettingsActivity.onCreate: Pressed APPLY button");
            Toast.makeText(SettingsActivity.this,
                    "Pressed APPLY button",
                    Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}