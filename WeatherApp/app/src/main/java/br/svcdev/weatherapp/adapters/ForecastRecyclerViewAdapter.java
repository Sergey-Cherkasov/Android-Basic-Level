package br.svcdev.weatherapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.svcdev.weatherapp.R;
import br.svcdev.weatherapp.api.conditions.forecast.DayForecast;
import br.svcdev.weatherapp.api.conditions.forecast.ForecastRequest;

public class ForecastRecyclerViewAdapter extends RecyclerView.Adapter<ForecastRecyclerViewAdapter
        .ForecastViewHolder>{

    private ForecastRequest mDatasource;


    public ForecastRecyclerViewAdapter(ForecastRequest dataSource) {
        if (dataSource != null) {
            this.mDatasource = dataSource;
        }
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.component_forecast_recycler_view_item, parent, false);
        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        DayForecast dayForecast = mDatasource.getDailyForecasts()[position];
        holder.setData(dayForecast.getDate(),
                dayForecast.getForecastTemperature().getMaximum().getValue(),
                dayForecast.getForecastTemperature().getMinimum().getValue());
    }

    @Override
    public int getItemCount() {
        if (mDatasource == null){
            return 0;
        }
        return mDatasource.getDailyForecasts().length;
    }

    public static class ForecastViewHolder extends RecyclerView.ViewHolder{

        private TextView mForecastDateOfWeek;
        private TextView mForecastCloudiness;
        private TextView mForecastHumidity;
        private TextView mForecastMaxTemperature;
        private TextView mForecastMinTemperature;

        public ForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            mForecastDateOfWeek = itemView.findViewById(R.id.tv_forecast_day_of_week);
            mForecastCloudiness = itemView.findViewById(R.id.tv_forecast_cloudiness);
//            mForecastHumidity = itemView.findViewById(R.id.tv_forecast_humidity);
            mForecastMaxTemperature = itemView.findViewById(R.id.tv_forecast_max_temperature);
            mForecastMinTemperature = itemView.findViewById(R.id.tv_forecast_min_temperature);
        }

        public TextView getForecastDateOfWeek() {
            return mForecastDateOfWeek;
        }

        public TextView getForecastCloudiness() {
            return mForecastCloudiness;
        }

        public TextView getForecastHumidity() {
            return mForecastHumidity;
        }

        public TextView getForecastMaxTemperature() {
            return mForecastMaxTemperature;
        }

        public TextView getForecastMinTemperature() {
            return mForecastMinTemperature;
        }

        public void setData(String date, double maxTemperature, double minTemperature) {
            getForecastDateOfWeek().setText(date);
//            getForecastCloudiness().setText(cloudiness);
            getForecastMaxTemperature().setText(Double.toString(maxTemperature));
            getForecastMinTemperature().setText(Double.toString(minTemperature));
        }
    }

}
