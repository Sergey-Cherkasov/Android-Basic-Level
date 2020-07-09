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
 * API open weather:
 */

package br.svcdev.weatherapp;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import br.svcdev.weatherapp.api.conditions.current.CurrentWeather;
import br.svcdev.weatherapp.api.conditions.forecast.ForecastRequest;
import br.svcdev.weatherapp.databinding.ActivityMainBinding;
import br.svcdev.weatherapp.host.HostRequestConstants;
import br.svcdev.weatherapp.host.SendRequest;

public class MainActivity extends AppCompatActivity {

    private static final String NAME_ACTIVITY = "MainActivity";
    private static final int PERMISSION_FINE_LOCATION_OK = PackageManager.PERMISSION_GRANTED;
    private static final int PERMISSION_COARSE_LOCATION_OK = PackageManager.PERMISSION_GRANTED;
    private static final int REQUEST_CODE_PERMISSIONS = 100;

    private ActivityMainBinding mBinding;
    private Toolbar mToolbar;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSharedPreferences(Constants.APP_PREFERENCES_FILE, Context.MODE_PRIVATE)
                .getBoolean(Constants.APP_PREFERENCES_NIGHT_MODE, false)){
            setTheme(R.style.AppDarkTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
        mBinding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(mBinding.getRoot());

        onLoadSettings();
        initApp();
        initToolbar();
        initMainFragment();
        initForecastFragment();

    }

    private void initToolbar() {
        mToolbar = mBinding.toolbar.appToolbar;
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    /**
     * Метод инициализации приложения. Проверяет на наличие разрешений к геолокации,
     * запрашивает соответствующие разрешения и запускает сервис геолокации
     */
    private void initApp() {
        Log.d(Constants.TAG_APP, "MainActivity.initApp: Start init application");
        int permissionStatusAccessFineLocation = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionStatusAccessCoarseLocation = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permissionStatusAccessFineLocation != PERMISSION_FINE_LOCATION_OK
                && permissionStatusAccessCoarseLocation != PERMISSION_COARSE_LOCATION_OK) {
            Log.d(Constants.TAG_APP, "Permissions are missing");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_CODE_PERMISSIONS);
        } else {
            Log.d(Constants.TAG_APP, "Permissions are available");
            Log.d(Constants.TAG_APP, "MainActivity.initApp(): Start service");
            initUI();
        }
        Log.d(Constants.TAG_APP, "MainActivity.initApp(): End init application.");
    }

    /**
     * Метод обратного вызова (коллбек) после запроса установления разрешений к геолокации,
     * запускает сервис геолокации
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PERMISSION_FINE_LOCATION_OK &&
                    grantResults[1] == PERMISSION_COARSE_LOCATION_OK) {
                initUI();
            } else {
                finish();
            }
        }
    }

    /**
     * Метод инициализации графического интерфейса
     */
    private void initUI() {
        mBinding.tvLocationCity.setText("");
        mBinding.tvLocationCity.setOnClickListener((View view) -> {
            String url = getResources().getString(R.string.url_wiki_search_city);
            Uri uri = Uri.parse(url + mBinding.tvLocationCity.getText());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            Intent chooser = Intent.createChooser(intent, "Choose browser:");
            ComponentName componentName = intent.resolveActivity(this.getPackageManager());
            if (componentName != null) {
                startActivity(chooser);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    /**
     * Метод загружает данные из файла настроек и текущую погоду с погодного сервиса
     */
    private void onLoadSettings() {
        Log.d(Constants.TAG_APP, "MainActivity.onLoadSettings(): Started to load settings.");
        Map<String, ?> mapSettings = getSharedPreferences(Constants.APP_PREFERENCES_FILE,
                Context.MODE_PRIVATE).getAll();

        for (Map.Entry<String, ?> mapSetting : mapSettings.entrySet()) {
            switch (mapSetting.getKey()) {
                case Constants.APP_PREFERENCES_LOCATION: {
                    SettingsApp.getSettings().setLocation((String) mapSetting.getValue());
                    break;
                }
                case Constants.APP_PREFERENCES_LOCATION_ID: {
                    SettingsApp.getSettings().setLocationId((Integer) mapSetting.getValue());
                    break;
                }
                case Constants.APP_PREFERENCES_NIGHT_MODE: {
                    SettingsApp.getSettings().setNightMode((Boolean) mapSetting.getValue());
                    break;
                }
                case Constants.APP_PREFERENCES_TEMPERATURE_UNITS: {
                    SettingsApp.getSettings().setTemperatureUnits((Boolean) mapSetting.getValue());
                    break;
                }
                case Constants.APP_PREFERENCES_WIND_SPEED_UNITS: {
                    SettingsApp.getSettings().setWindSpeedUnits((Boolean) mapSetting.getValue());
                }
            }
        }
        Log.d(Constants.TAG_APP, "MainActivity.onLoadSettings(): Ended to load settings.");
        if (mBinding.tvLocationCity.getText().toString().equals("") &&
                !mBinding.tvLocationCity.getText().toString()
                        .equals(SettingsApp.getSettings().getLocation())) {

            // Отправляем запрос на погодный сервис для получения информации о текущей погоде
            // Метод работает в отдельном потоке
//            SendRequest sr = new SendRequest(this, getSupportFragmentManager());
//            sr.execute();

//            onSendRequestCurrentConditions();
//            onSendRequestForecastConditions();

        }
    }

/*
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

        SendRequest sendRequest = new SendRequest(this, mHost, mController, mCityId,
                mRequestParameters, mRequestMethod, HostRequestConstants.URL_GET_CURRENT_CONDITIONS);
        sendRequest.execute();
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

        SendRequest sendRequest = new SendRequest(this, mHost, mController, mCityId,
                mRequestParameters, mRequestMethod,
                HostRequestConstants.URL_GET_FORECASTS_CONDITIONS);
        sendRequest.execute();
    }
*/

/*
    public void onServerResponse(Map<String, String> response){
        Iterator<String> key = response.keySet().iterator();
        String requestId = key.next();
        String responseString = response.get(requestId);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        if (requestId.equals(HostRequestConstants.URL_GET_CURRENT_CONDITIONS)) {
            CurrentWeather[] currentWeathers = gson.fromJson(responseString,
                    CurrentWeather[].class);
            onSetCurrentConditions(currentWeathers);
        }
        if (requestId.equals(HostRequestConstants.URL_GET_FORECASTS_CONDITIONS)) {
            ForecastRequest forecastRequest = gson.fromJson(responseString,
                    ForecastRequest.class);
            onSetForecastConditions(forecastRequest);
        }
        Log.d(Constants.TAG_APP, "onServerResponse: ");
    }

    private void onSetForecastConditions(ForecastRequest forecastRequest) {
        DailyForecastsFragment.setForecastRequest(forecastRequest);
    }
*/

/*
    private void onSetCurrentConditions(CurrentWeather[] currentWeathers) {
        FragmentManager manager = getSupportFragmentManager();
        Log.i(Constants.TAG_APP, String.format("MainActivity.setRequestResultOnGUI: temperature = %d",
                (int) currentWeathers[0].getTemperature().getMetric().getValue()));
        ((TextView) manager.findFragmentById(R.id.fl_current_frame).getView()
                .findViewById(R.id.tv_temperature_value))
                .setText(String.format("%d", (int) currentWeathers[0]
                        .getTemperature().getMetric().getValue()));
        ((TextView) manager.findFragmentById(R.id.fl_current_frame).getView()
                .findViewById(R.id.tv_air_humidity_value))
                .setText(String.format("%d %%", (int) currentWeathers[0]
                        .getRelativeHumidity()));
        ((TextView) manager.findFragmentById(R.id.fl_current_frame).getView()
                .findViewById(R.id.tv_air_pressure_value))
                .setText(String.format("%d %s", (int) currentWeathers[0]
                                .getPressure().getMetric().getValue(),
                        currentWeathers[0].getPressure().getMetric().getUnit()));
        ((TextView) manager.findFragmentById(R.id.fl_current_frame).getView()
                .findViewById(R.id.tv_wind_value))
                .setText(String.format("%d %s", (int) currentWeathers[0]
                                .getWind().getSpeed().getMetric().getValue(),
                        currentWeathers[0].getWind().getSpeed().getMetric().getUnit()));
    }
*/

    /**
     * Метод загружает фрагмент с текущей погодой
     */
    private void initMainFragment() {
        Fragment mFragment = new CurrentConditionsFragment();
        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.fl_current_frame, mFragment);
        mFragmentTransaction.commit();
    }

    /**
     * Метод загружает фрагмент с прогнозом погоды на несколько дней
     */
    private void initForecastFragment() {
        Fragment mFragment = new DailyForecastsFragment();
        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.fl_forecast_frame, mFragment);
        mFragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBinding.tvLocationCity.setText(SettingsApp.getSettings().getLocation());
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
