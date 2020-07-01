package br.svcdev.weatherapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.svcdev.weatherapp.databinding.FragmentDailyForecastsBinding;

public class DailyForecastsFragment extends Fragment {

    private FragmentDailyForecastsBinding mBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentDailyForecastsBinding.inflate(inflater, container, false);

        return mBinding.getRoot();
    }
}