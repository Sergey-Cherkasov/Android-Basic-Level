package br.svcdev.weatherapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.svcdev.weatherapp.adapters.ForecastRecyclerViewAdapter;
import br.svcdev.weatherapp.databinding.FragmentDailyForecastsBinding;

public class DailyForecastsFragment extends Fragment {

    private FragmentDailyForecastsBinding mBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentDailyForecastsBinding.inflate(inflater, container, false);

        ForecastRecyclerViewAdapter adapter = new ForecastRecyclerViewAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(mBinding.getRoot().getContext());
        DividerItemDecoration itemDecorator = new DividerItemDecoration(mBinding.getRoot()
                .getContext(), LinearLayoutManager.VERTICAL);
        itemDecorator.setDrawable(getContext().getDrawable(R.drawable.ic_separator));
        mBinding.rvForecastDayOfWeek.setHasFixedSize(true);
        mBinding.rvForecastDayOfWeek.setLayoutManager(manager);
        mBinding.rvForecastDayOfWeek.addItemDecoration(itemDecorator);
        mBinding.rvForecastDayOfWeek.setAdapter(adapter);

        return mBinding.getRoot();
    }
}