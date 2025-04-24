package com.example.weather.service;

import com.example.weather.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class WeatherStackProvider implements WeatherProvider {

    private static final Logger logger = LoggerFactory.getLogger(WeatherStackProvider.class);

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${weatherstack.api.key}")
    private String apiKey;

    @Override
    public WeatherResponse fetchWeather(String city) {
        try {
            logger.info("Fetching weather from WeatherStack...");
            String url = String.format("http://api.weatherstack.com/current?access_key=%s&query=%s", apiKey, city);
            JsonNode node = restTemplate.getForObject(url, JsonNode.class);

            if (node != null && node.has("current")) {
                double temp = node.get("current").get("temperature").asDouble();
                double wind = node.get("current").get("wind_speed").asDouble();
                return new WeatherResponse(wind, temp);
            }
        } catch (Exception e) {
            logger.error("Failed to fetch from WeatherStack: {}", e.getMessage());
        }
        return null;
    }
}
