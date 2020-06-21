/*
 * Field naming:
 *
 * Non-public, non-static field names start with m.
 * Static field names start with s.
 * Other fields start with a lower case letter.
 * Public static final fields (constants) are ALL_CAPS_WITH_UNDERSCORES.
 *
 * Sample field naming:
 *
 * private static final int CONSTANT_VALUE = 42;
 * private static int sStaticIntValue = 42;
 * static int sStaticIntValue = 42;
 * public int publicIntValue = 42;
 * int mIntValue = 42;
 * private int mPrivateIntValue = 42;
 * protected int mIntValue = mProtectedIntValue = 42;
 *
 */

package br.svcdev.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import br.svcdev.weatherapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mBinding;
    Toolbar mToolbar;
    ActionBar mActionBar;

    static final String NAME_ACTIVITY = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(mBinding.getRoot());

        initApp();

        Toast.makeText(this, "onCreate()", Toast.LENGTH_SHORT).show();
        Log.d(ExternalMethods.TAG_APP, NAME_ACTIVITY + ".onCreate()");

        mToolbar = mBinding.toolbar.appToolbar;
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(false);
        }

        Fragment mFragment = new MainFragment();
        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.fl_content_frame, mFragment);
        mFragmentTransaction.commit();
    }

    private void initApp() {
        Log.d(ExternalMethods.TAG_APP, "MainActivity.initApp(): Method started.");
        SharedPreferences mPreferences = getSharedPreferences(ExternalMethods.APP_PREFERENCES_FILE,
                Context.MODE_PRIVATE);
        if (mPreferences.contains(ExternalMethods.APP_PREFERENCES_LOCATION)) {
            SettingsApp.getmSettings().setmLocation(mPreferences
                    .getString(ExternalMethods.APP_PREFERENCES_LOCATION, ""));
        }
        if (mPreferences.contains(ExternalMethods.APP_PREFERENCES_NIGHT_MODE)) {
            SettingsApp.getmSettings().setmNightMode(mPreferences
                    .getBoolean(ExternalMethods.APP_PREFERENCES_NIGHT_MODE, false));
        }
        if (mPreferences.contains(ExternalMethods.APP_PREFERENCES_TEMPERATURE_UNITS)) {
            SettingsApp.getmSettings().setmTemperatureUnits(mPreferences
                    .getBoolean(ExternalMethods.APP_PREFERENCES_TEMPERATURE_UNITS, false));
        }
        if (mPreferences.contains(ExternalMethods.APP_PREFERENCES_WIND_SPEED_UNITS)) {
            SettingsApp.getmSettings().setmWindSpeedUnits(mPreferences
                    .getBoolean(ExternalMethods.APP_PREFERENCES_WIND_SPEED_UNITS, false));
        }
        Log.d(ExternalMethods.TAG_APP, "MainActivity.initApp(): Method ended.");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Toast.makeText(this, "onStart()", Toast.LENGTH_SHORT).show();
        Log.d(ExternalMethods.TAG_APP, NAME_ACTIVITY + ".onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume()", Toast.LENGTH_SHORT).show();
        Log.d(ExternalMethods.TAG_APP, NAME_ACTIVITY + ".onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "onPause()", Toast.LENGTH_SHORT).show();
        Log.d(ExternalMethods.TAG_APP, NAME_ACTIVITY + ".onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop()", Toast.LENGTH_SHORT).show();
        Log.d(ExternalMethods.TAG_APP, NAME_ACTIVITY + ".onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences mPreferences = getSharedPreferences(ExternalMethods.APP_PREFERENCES_FILE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mPreferences.edit();
        mEditor.putString(ExternalMethods.APP_PREFERENCES_LOCATION,
                String.valueOf(SettingsApp.getmSettings().getmLocation()));
        mEditor.putBoolean(ExternalMethods.APP_PREFERENCES_NIGHT_MODE,
                SettingsApp.getmSettings().ismNightMode());
        mEditor.putBoolean(ExternalMethods.APP_PREFERENCES_TEMPERATURE_UNITS,
                SettingsApp.getmSettings().ismTemperatureUnits());
        mEditor.putBoolean(ExternalMethods.APP_PREFERENCES_WIND_SPEED_UNITS,
                SettingsApp.getmSettings().ismWindSpeedUnits());
        mEditor.apply();

        Toast.makeText(this, "onDestroy()", Toast.LENGTH_SHORT).show();
        Log.d(ExternalMethods.TAG_APP, NAME_ACTIVITY + ".onDestroy()");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
