package br.svcdev.weatherapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.svcdev.weatherapp.Constants;
import br.svcdev.weatherapp.R;
import br.svcdev.weatherapp.api.autocompletesearch.AutocompleteSearch;
import br.svcdev.weatherapp.host.HostRequestConstants;

public class CityAutocompleteAdapter extends BaseAdapter implements Filterable {

    /**
     * Максимальное количество элементов в выпадающем списке
     */
    private final static int MAX_RESULT = 10;

    private final Context mContext;
    private List<AutocompleteSearch> mResults;

    public CityAutocompleteAdapter(Context mContext) {
        this.mContext = mContext;
        this.mResults = new ArrayList<AutocompleteSearch>();
    }

    @Override
    public int getCount() {
        return mResults.size();
    }

    @Override
    public AutocompleteSearch getItem(int index) {
        return mResults.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
        }
        AutocompleteSearch item = getItem(position);
        String cityAndCountry = String.format("%s, %s, %s", item.getLocalizedName(),
                item.getAdministrativeArea().getLocalizedName(), item.getCountry().getId());
        ((TextView) convertView.findViewById(android.R.id.text1)).setText(cityAndCountry);

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            /**
             * Invoked in a worker thread to filter the data according to the constraint.
             * Метод вызывается в отдельном потоке для фильтрации данных в соответствии
             * с ограничением.
             * @param charSequence Данные, которые необходимо отфильтровать
             * @return Простой объект с двумя переменными: int count, Object values
             */
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if (charSequence != null) {
                    List<AutocompleteSearch> autocompleteSearches =
                            findCities(charSequence.toString());
                    filterResults.values = autocompleteSearches;
                    filterResults.count = autocompleteSearches.size();
                }
                return filterResults;
            }

            /**
             * Callback method publishResults()
             * Invoked in the UI thread to publish the filtering results in the user interface.
             *
             * Метод обратного вызова publishResults()
             * Вызывается в потоке пользовательского интерфейса для публикации результатов
             * фильтрации в пользовательском интерфейсе.
             * @param charSequence  Данные, которые необходимо отфильтровать
             * @param filterResults Результат выполнения метода performFiltering()
             */
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filterResults != null && filterResults.count > 0) {
                    mResults = (List<AutocompleteSearch>) filterResults.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }

    /**
     * Метод вызывается и выполняется в отдельном потоке. Осуществляет отправку запроса на удаленный
     * ресурс погодного сервиса.
     *
     * @param partNameCity Данные, по которым осуществляется поиск наименований населенных пунктов.
     * @return Список населенных пунктов
     */
    private List<AutocompleteSearch> findCities(String partNameCity) {
        String mUrlString = "";
        List<AutocompleteSearch> result;
        mUrlString = buildStringRequest(partNameCity);
        result = sendRequest(mUrlString);
        return result;
    }

    /**
     * Метод отправляет запрос на удаленный ресурс погодного сервиса
     *
     * @param mUrlString Строка запроса
     * @return Список объектов List<AutocompleteSearch>
     */
    private List<AutocompleteSearch> sendRequest(String mUrlString) {
        List<AutocompleteSearch> result = new ArrayList<>();
        try {
            final URL mUrl = new URL(mUrlString);
            Log.d(Constants.TAG_APP, "mUrl = " + mUrl);
            HttpURLConnection mConnection;
            try {
                mConnection = (HttpURLConnection) mUrl.openConnection();
                mConnection.setRequestMethod(HostRequestConstants.REQUEST_METHOD_GET);
                mConnection.setReadTimeout(10000);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(mConnection.getInputStream()));
                String response = getStringResponse(in);
                Log.d(Constants.TAG_APP, "Result httpQuery = " + response);
                Gson gson = new Gson();
                AutocompleteSearch[] autocompleteSearches = gson.fromJson(response,
                        AutocompleteSearch[].class);
                result = Arrays.asList(autocompleteSearches);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Метод строит строку запроса к удаленному ресурсу погодного сервиса
     *
     * @param partNameCity Строка с частью/полным наименования/наименованием населенного пункта
     */
    private String buildStringRequest(String partNameCity) {
        String mUrlString = "";
        try {
            mUrlString = HostRequestConstants.ACCUWEATHER_HOST +
                    HostRequestConstants.URL_GET_LIST_CITIES + "?" +
                    "apikey=" + Constants.ACCUWEATHER_API_KEY + "&" +
                    "q=" + URLEncoder.encode(partNameCity, "UTF-8") + "&" +
                    "language=" + mContext.getResources().getString(R.string.data_request_language);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return mUrlString;
    }

    /**
     * Метод получает ответ от удаленного ресурса и формирует строку ответа
     *
     * @param in Буфер входящего потока
     * @return Json в строковом представлении
     */
    private String getStringResponse(BufferedReader in) {
        String tmpString;
        StringBuilder resultString = new StringBuilder();
        try {
            while ((tmpString = in.readLine()) != null) {
                resultString.append(tmpString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultString.toString();
    }

}
