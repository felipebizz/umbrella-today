package com.challenge.merlin.service;

import com.challenge.merlin.model.Forecast;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;


@Service
public class ForecastServiceImpl implements ForecastService {

    private Forecast forecast = new Forecast();

    @Override
    public JsonObject forecastMapper(JSONObject jsonObject) {

        JSONObject jsonNode = new JSONObject();
        JsonParser jsonParser = new JsonParser();

        try {
            checkPrecipitationProbabilityAndPrecipType(jsonObject);
            buildResponseJson(jsonObject, jsonNode);

        } catch (Exception e) {
            throw new RuntimeJsonMappingException(e.getMessage());
        }

        JsonObject asJsonObject1 = jsonParser.parse(jsonNode.toString()).getAsJsonObject();
        return asJsonObject1;
    }


    @Override
    public void checkPrecipitationProbabilityAndPrecipType(JSONObject jsonObj) {
        for (String keyStr : jsonObj.keySet()) {

            Object keyValue = jsonObj.get(keyStr);

            if (keyStr.equals("precipProbability")) {
                double precipProbability = Double.parseDouble(keyValue.toString());
                if (precipProbability >= 0.5) {
                    forecast.setUmbrella(Boolean.TRUE);
                    forecast.setPrecipProbability(precipProbability);
                }
            }
            if (keyStr.equals("precipType")) {
                forecast.setPrecipType(keyValue.toString());
            }

            if (keyValue instanceof JSONObject)
                checkPrecipitationProbabilityAndPrecipType((JSONObject) keyValue);

            if (keyValue instanceof JSONArray) {
                for (Object object : (JSONArray) keyValue) {
                    if (object instanceof JSONObject)
                        checkPrecipitationProbabilityAndPrecipType((JSONObject) object);
                }
            }
        }
    }

    private void buildResponseJson(JSONObject jsonObject, JSONObject nodeJson) {

        Map<String, Object> forecastMap = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            forecastMap = mapper.readValue(String.valueOf(jsonObject),
                    new TypeReference<Map<String, Object>>() {
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        JSONObject weather = new JSONObject();
        weather.put("precipProbability", forecast.getPrecipProbability());
        weather.put("precipType", forecast.isUmbrella() == Boolean.TRUE ? forecast.getPrecipType() : "");


        JSONObject location = new JSONObject();
        location.put("latitude", forecastMap.get("latitude"));
        location.put("longitude", forecastMap.get("longitude"));

        nodeJson.put("umbrella", forecast.isUmbrella() == Boolean.TRUE ? "Yes" : Boolean.FALSE);
        nodeJson.put("weather", weather);
        nodeJson.put("location", location);
    }
}
