package com.example.weather.service;

import com.example.weather.model.WeatherResponse;

public interface WeatherProvider {
    WeatherResponse fetchWeather(String city);
}