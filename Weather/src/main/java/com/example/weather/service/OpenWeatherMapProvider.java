package com.example.weather.service;

import com.example.weather.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class OpenWeatherMapProvider implements WeatherProvider {

    private static final Logger logger = LoggerFactory.getLogger(OpenWeatherMapProvider.class);

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${openweathermap.api.key}")
    private String apiKey;

    @Override
    public WeatherResponse fetchWeather(String city) {
        try {
            logger.info("Fetching weather from OpenWeatherMap...");
            String url = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s,AU&appid=%s&units=metric", city, apiKey);
            JsonNode node = restTemplate.getForObject(url, JsonNode.class);

            if (node != null && node.has("main") && node.has("wind")) {
                double temp = node.get("main").get("temp").asDouble();
                double wind = node.get("wind").get("speed").asDouble();
                return new WeatherResponse(wind, temp);
            }
        } catch (Exception e) {
            logger.error("Failed to fetch from OpenWeatherMap: {}", e.getMessage());
        }
        return null;
    }
}
