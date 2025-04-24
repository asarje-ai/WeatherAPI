package com.example.weather.service;

import com.example.weather.exception.WeatherServiceException;
import com.example.weather.model.WeatherResponse;
import com.example.weather.service.WeatherProvider;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WeatherServiceImplTest {

    @Test
    void returnsWeatherFromFirstProvider() {
        WeatherProvider provider1 = city -> new WeatherResponse(25, 12);
        WeatherProvider provider2 = city -> new WeatherResponse(22, 10); // Should not be called

        WeatherServiceImpl service = new WeatherServiceImpl(List.of(provider1, provider2));
        WeatherResponse response = service.getWeather("melbourne");

        assertNotNull(response);
        assertEquals(25, response.getTemperatureDegrees());
        assertEquals(12, response.getWindSpeed());
    }

    @Test
    void fallsBackToSecondProvider() {
        WeatherProvider provider1 = city -> null;
        WeatherProvider provider2 = city -> new WeatherResponse(20, 8);

        WeatherServiceImpl service = new WeatherServiceImpl(List.of(provider1, provider2));
        WeatherResponse response = service.getWeather("melbourne");

        assertNotNull(response);
        assertEquals(20, response.getTemperatureDegrees());
        assertEquals(8, response.getWindSpeed());
    }

    @Test
    void throwsWhenAllProvidersReturnNull() {
        WeatherProvider provider1 = city -> null;
        WeatherProvider provider2 = city -> null;

        WeatherServiceImpl service = new WeatherServiceImpl(List.of(provider1, provider2));

        WeatherServiceException ex = assertThrows(
                WeatherServiceException.class,
                () -> service.getWeather("melbourne")
        );

        assertEquals("All weather providers failed", ex.getMessage());
    }
}
