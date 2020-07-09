package br.svcdev.weatherapp;

import java.util.Map;

public interface ServerResponse {

    void onServerResponse(Map<String, String> response);

}
