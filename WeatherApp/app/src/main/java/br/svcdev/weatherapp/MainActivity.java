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
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Map;

import br.svcdev.weatherapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String NAME_ACTIVITY = "MainActivity";
    private static final int PERMISSION_FINE_LOCATION_OK = PackageManager.PERMISSION_GRANTED;
    private static final int PERMISSION_COARSE_LOCATION_OK = PackageManager.PERMISSION_GRANTED;
    private static final int REQUEST_CODE_PERMISSIONS = 100;

    private ActivityMainBinding mBinding;
    private Toolbar mToolbar;
    private ActionBar mActionBar;

    private Intent intentService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(mBinding.getRoot());

        initApp();
        initToolbar();
        initMainFragment();

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
            Log.d(Constants.TAG_APP,"Permissions are available");
            Log.d(Constants.TAG_APP, "MainActivity.initApp(): Start service");
            intentService = new Intent(this, WeatherLocationService.class);
            startService(intentService);
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
                intentService = new Intent(this, WeatherLocationService.class);
                startService(intentService);
            } else {
                finish();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        onLoadSettings();
    }

    /**
     * Метод загружает данные из файла настроект
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
                    SettingsApp.getSettings().setLocationIds((Integer) mapSetting.getValue());
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
    }

    /**
     * Метод загружает главный фрагмент
     */
    private void initMainFragment() {
        Fragment mFragment = new MainFragment();
        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.fl_content_frame, mFragment);
        mFragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        stopService(intentService);
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
