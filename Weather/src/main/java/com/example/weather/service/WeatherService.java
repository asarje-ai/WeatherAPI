package com.example.weather.service;

import com.example.weather.model.WeatherResponse;

public interface WeatherService {
    WeatherResponse getWeather(String city);
}
