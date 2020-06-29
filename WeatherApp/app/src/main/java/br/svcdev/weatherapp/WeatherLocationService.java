package br.svcdev.weatherapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class WeatherLocationService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(Constants.TAG_APP, "WeatherLocationService.onDestroy(): Stop service");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onStartTask(startId);
        return super.onStartCommand(intent, flags, startId);
    }

    private void onStartTask(int startId) {
        new Thread(() -> {
            Log.d(Constants.TAG_APP, "WeatherLocationService.onStartTask(): Start service on new thread");
        }).start();
    }



    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
