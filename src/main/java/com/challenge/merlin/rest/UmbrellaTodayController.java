package com.challenge.merlin.rest;

import com.challenge.merlin.helper.CacheHelper;
import com.challenge.merlin.model.ForecastCache;
import com.challenge.merlin.service.ForecastService;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.concurrent.Callable;

@RestController
class UmbrellaTodayController {

    @Autowired
    private
    ForecastService forecastService;

    @Autowired
    private
    CacheHelper cacheHelper;

    @Value("${umbrella-today-api.url}")
    private String umbrellaTodayApiUrl;

    @Value("${umbrella-today-api.key}")
    private String secretKey;

    @RequestMapping(method = RequestMethod.GET, value = "/umbrella", produces = "application/json; charset=UTF-8")
    public Callable<ResponseEntity<String>> needUmbrellaToday(@RequestParam("latitude") String latitude,
                                                              @RequestParam("longitude") String longitude) {
        return () -> {

            ForecastCache forecastCache = new ForecastCache(cacheHelper);
            JsonObject responseJson = null;
            JsonObject cachedValue = forecastCache.checkCachedValues(latitude, longitude);

            if (cachedValue.size() == 0) {
                HttpRequest getRequest = Unirest.get(umbrellaTodayApiUrl + secretKey + "/" + latitude + "," + longitude);
                JSONObject responseJSONObject = getRequest.asJson().getBody().getObject();
                if (responseJSONObject.has("code") && !responseJSONObject.get("code").equals("200")) {
                    return new ResponseEntity<>(errorDefault(), HttpStatus.OK);
                }

                responseJson = forecastService.forecastMapper(responseJSONObject);

                forecastCache.add(latitude + longitude, responseJson);
            }

            return new ResponseEntity<>(cachedValue.size() > 0 ? cachedValue.toString() : Objects.requireNonNull(responseJson).toString(),
                    HttpStatus.OK);
        };
    }

    /**
     * Error Default
     *
     * @return String equals false
     */
    private String errorDefault() {
        return Boolean.FALSE.toString();
    }
}
