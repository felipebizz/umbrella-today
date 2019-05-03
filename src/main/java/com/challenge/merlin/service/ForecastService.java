package com.challenge.merlin.service;

import com.google.gson.JsonObject;
import org.json.JSONObject;

public interface ForecastService {

    /**
     * @param jsonObject jsonObject
     * @return JsonObject
     */
    JsonObject forecastMapper(JSONObject jsonObject);

    /**
     * @param jsonObj JSONObject
     */
    void checkPrecipitationProbabilityAndPrecipType(JSONObject jsonObj);
}
