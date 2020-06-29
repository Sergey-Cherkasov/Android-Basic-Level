package br.svcdev.weatherapp.host.request;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.*;
import java.util.HashMap;
import java.util.Map;

import br.svcdev.weatherapp.host.HostRequestConstants;

public class WeatherHostRequest extends AsyncTask<Map<String, Object>, Void, Map<String, String>> {

    /**
     * Идентификатор url запроса
     */
    private String identifierUrlRequest;
    /**
     * Метод запроса (GET)
     */
    private String requestMethod;
    /**
     * Параметры запроса
     */
    private final Map<String, String> requestParameters;
    /**
     * Время ожидания ответа сервера (в секундах)
     */
    private final int timeDelay;
    /**
     * Флаг о необходимости повторно отправить запрос на удаленный сервер
     */
    private final boolean flagRestartRequest;

    private HttpURLConnection urlConnection;
    private Handler handler;

    /**
     * Конструктор класса WeatherServerRequest.
     *
     * @param identifierUrlRequest Идентификатор url запроса
     * @param requestMethod        Метод запроса (GET)
     * @param requestParameters    Параметры запроса
     * @param timeDelay            Время ожидания ответа сервера (в секундах)
     * @param flagRestartRequest   Флаг о необходимости повторно отправить запрос на удаленный сервер
     */
    public WeatherHostRequest(String identifierUrlRequest, String requestMethod, Map<String, String> requestParameters, int timeDelay,
                              boolean flagRestartRequest) {
        this.identifierUrlRequest = identifierUrlRequest;
        this.requestMethod = requestMethod;
        this.requestParameters = requestParameters;
        this.timeDelay = timeDelay;
        this.flagRestartRequest = flagRestartRequest;
    }

    public void onExecuteRequest() {
        handler = new Handler();
        String url = "";
        url = HostRequestConstants.ACCUWEATHER_HOST + identifierUrlRequest;
        Map<String, Object> urlRequestParameters = new HashMap<>();
        urlRequestParameters.put("url", url);
        urlRequestParameters.put("method", requestMethod);
        urlRequestParameters.put("parameters", requestParameters);

        this.execute(urlRequestParameters);

    }

    @SafeVarargs
    @Override
    protected final Map<String, String> doInBackground(Map<String, Object>... params) {
        String requestUrl = (String) params[0].get("url");
        String requestMethod = (String) params[0].get("method");
        Map<String, String> requestParams = (Map<String, String>) params[0].get("parameters");
        String requestBody = buildRequestBody(requestParams);
        try {
            urlConnection = createUrlConnection(requestUrl, requestMethod, requestBody);
            return onServerResponse(urlConnection);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    private Map<String, String> onServerResponse(HttpURLConnection urlConnection) {
        InputStream inputStream;
        try {
            if (urlConnection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                inputStream = urlConnection.getInputStream();
            } else {
                // TODO: Реализовать получения ошибки
                inputStream = urlConnection.getErrorStream();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringResponse = new StringBuilder();
            String stringTemp;
            while ((stringTemp = reader.readLine()) != null){
                stringResponse.append(stringTemp);
            }
            Map<String, String> serverResponseMap = new HashMap<>();
            serverResponseMap.put("response", stringResponse.toString());
            return serverResponseMap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private HttpURLConnection createUrlConnection(String requestUrl, String requestMethod,
                                                  String requestBody) throws IOException {
        // Инициализируем URL адрес
        URL url = new URL(requestUrl);
        // Создаем подключение
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        // Устанавливаем разреншение на получение ответа от сервера
        httpURLConnection.setDoInput(true);
        // Устанавливаем разрешение на отправку параметров запроса
        httpURLConnection.setDoOutput(true);
        // Устанавливаем метод запроса: GET
        httpURLConnection.setRequestMethod(requestMethod);

        if (requestBody != null) {
            OutputStream outputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream,
                    StandardCharsets.UTF_8));
            writer.write(requestBody);
            writer.flush();
            writer.close();
            outputStream.close();
        }
        urlConnection.connect();
        return urlConnection;
    }

    /**
     * Метод строит строку параметров запроса к серверу
     *
     * @param param Параметры запроса
     * @return Строка с параметрами запроса
     */
    private String buildRequestBody(Map<String, String> param) {
        String stringOutput = null;
        Uri.Builder uriBuilder = new Uri.Builder();
        if (param != null) {
            for (Map.Entry entry : param.entrySet()) {
                uriBuilder.appendQueryParameter(entry.getKey().toString(),
                        entry.getValue().toString());
            }
            stringOutput = uriBuilder.build().getEncodedQuery();
        }
        return stringOutput;
    }

    @Override
    protected void onPostExecute(Map<String, String> stringStringMap) {
        super.onPostExecute(stringStringMap);
        handler.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
