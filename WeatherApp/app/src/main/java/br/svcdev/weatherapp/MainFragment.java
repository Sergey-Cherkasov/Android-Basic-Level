package br.svcdev.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import br.svcdev.weatherapp.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {

    static final String NAME_FRAGMENT = "MainFragment";
    Context mContext;
    SharedPreferences mPreferences;
    FragmentMainBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentMainBinding.inflate(inflater, container, false);
        mBinding.tvLocationCity.setText(R.string.Kemerovo);

        Toast.makeText(mContext, "onCreateView()", Toast.LENGTH_SHORT).show();
        Log.d(ExternalMethods.TAG_APP, NAME_FRAGMENT + ".onCreateView()");

        return mBinding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        Toast.makeText(context, "onAttach()", Toast.LENGTH_SHORT).show();
        Log.d(ExternalMethods.TAG_APP, NAME_FRAGMENT + ".onAttach()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(mContext, "onCreate()", Toast.LENGTH_SHORT).show();
        Log.d(ExternalMethods.TAG_APP, NAME_FRAGMENT + ".onCreate()");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Toast.makeText(mContext, "onActivityCreated()", Toast.LENGTH_SHORT).show();
        Log.d(ExternalMethods.TAG_APP, NAME_FRAGMENT + ".onActivityCreated()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Toast.makeText(mContext, "onStart()", Toast.LENGTH_SHORT).show();
        Log.d(ExternalMethods.TAG_APP, NAME_FRAGMENT + ".onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();

        mBinding.tvLocationCity.setText(SettingsApp.getmSettings().getmLocation());

        Toast.makeText(mContext, "onResume()", Toast.LENGTH_SHORT).show();
        Log.d(ExternalMethods.TAG_APP, NAME_FRAGMENT + ".onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Toast.makeText(mContext, "onPause()", Toast.LENGTH_SHORT).show();
        Log.d(ExternalMethods.TAG_APP, NAME_FRAGMENT + ".onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Toast.makeText(mContext, "onStop()", Toast.LENGTH_SHORT).show();
        Log.d(ExternalMethods.TAG_APP, NAME_FRAGMENT + ".onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
        Toast.makeText(mContext, "onDestroyView()", Toast.LENGTH_SHORT).show();
        Log.d(ExternalMethods.TAG_APP, NAME_FRAGMENT + ".onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(mContext, "onDestroy()", Toast.LENGTH_SHORT).show();
        Log.d(ExternalMethods.TAG_APP, NAME_FRAGMENT + ".onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Toast.makeText(mContext, "onDetach()", Toast.LENGTH_SHORT).show();
        Log.d(ExternalMethods.TAG_APP, NAME_FRAGMENT + ".onDetach()");
    }
}
