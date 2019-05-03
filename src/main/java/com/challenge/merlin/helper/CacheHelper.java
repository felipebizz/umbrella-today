package com.challenge.merlin.helper;

import com.google.gson.JsonObject;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.stereotype.Component;

@Component
public class CacheHelper {

    private CacheManager cacheManager;
    private Cache<String, JsonObject> coordinates;


    public CacheHelper() {
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build();
        cacheManager.init();

        coordinates = cacheManager
                .createCache("storeCoordinates", CacheConfigurationBuilder
                        .newCacheConfigurationBuilder(String.class, JsonObject.class, ResourcePoolsBuilder.heap(10)));

    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public Cache<String, JsonObject> getCoordinates() {
        return cacheManager.getCache("storeCoordinates", String.class, JsonObject.class);
    }

    public void setCoordinates(Cache<String, JsonObject> coordinates) {
        this.coordinates = coordinates;
    }
}