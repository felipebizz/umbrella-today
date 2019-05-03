package com.challenge.merlin.model;

import com.challenge.merlin.helper.CacheHelper;
import com.google.gson.JsonObject;

public class ForecastCache {

    private CacheHelper cache;

    public ForecastCache(CacheHelper cache) {
        this.cache = cache;
    }

    /**
     * Check if has values added for a specific latitude/longitude
     *
     * @param latitude  latitude
     * @param longitude longitude
     * @return if has values return a JsonObject
     */
    public JsonObject checkCachedValues(String latitude, String longitude) {
        String coordinate = new StringBuilder().append(latitude).append(longitude).toString();
        if (cache.getCoordinates().containsKey(coordinate))
            return cache.getCoordinates().get(coordinate);
        return new JsonObject();
    }

    /**
     * Add coordinates int the cache
     *
     * @param coordinate coordinate
     * @param value      String containing a Json
     */
    public void add(String coordinate, JsonObject value) {
        cache.getCoordinates().put(coordinate, value);
    }

    public CacheHelper getCache() {
        return cache;
    }

}
