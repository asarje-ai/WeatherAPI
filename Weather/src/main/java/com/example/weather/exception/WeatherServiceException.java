package com.example.weather.exception;

@SuppressWarnings("serial")
public class WeatherServiceException extends RuntimeException {
    public WeatherServiceException(String message) {
        super(message);
    }
}
