package br.svcdev.weatherapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import br.svcdev.weatherapp.databinding.FragmentCurrentConditionsBinding;

public class CurrentConditionsFragment extends Fragment {

    private static final String NAME_FRAGMENT = "MainFragment";
    private Context mContext;
    private SharedPreferences mPreferences;
    private FragmentCurrentConditionsBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentCurrentConditionsBinding.inflate(inflater, container, false);
        mBinding.tvLocationCity.setText(R.string.Kemerovo);
        mBinding.tvLocationCity.setOnClickListener((View view) -> {
            String url = getResources().getString(R.string.url_wiki_search_city);
            Uri uri = Uri.parse(url + mBinding.tvLocationCity.getText());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            Intent chooser = Intent.createChooser(intent, "Choose browser:");
            ComponentName componentName = intent.resolveActivity(Objects
                    .requireNonNull(getActivity()).getPackageManager());
            if (componentName != null) {
                startActivity(chooser);
            }
        });
        return mBinding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        List<String> listCities = Arrays.asList(getResources().getStringArray(R.array.cities));
        mBinding.tvLocationCity.setText(listCities.get(SettingsApp.getSettings().getLocationIds()));
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}