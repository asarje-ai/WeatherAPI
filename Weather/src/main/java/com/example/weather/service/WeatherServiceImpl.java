package com.example.weather.service;

import com.example.weather.exception.WeatherServiceException;
import com.example.weather.model.WeatherResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final List<WeatherProvider> providers;

    public WeatherServiceImpl(List<WeatherProvider> providers) {
        this.providers = providers;
    }

    @Override
    @Cacheable(value = "weatherCache", key = "#city", unless = "#result == null")
    public WeatherResponse getWeather(String city) {
        for (WeatherProvider provider : providers) {
            WeatherResponse response = provider.fetchWeather(city);
            if (response != null) {
                return response;
            }
        }
        throw new WeatherServiceException("All weather providers failed");
    }
}
