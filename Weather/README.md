Weather Service API

Overview
An HTTP service built with Java Spring Boot to fetch Melbourne weather, with automatic failover between WeatherStack and OpenWeatherMap.

Features
- Failover between providers
- Unified response: `{ "windSpeed": 20, "temperatureDegrees": 29 }`
- Caching with Caffeine (3 seconds)
- SOLID principle design
- Global Exception Handling
- Unit Tested

How to Run
- `./mvnw spring-boot:run`
- Or build JAR and run.

 API Call
```bash
curl "http://localhost:8080/v1/weather?city=melbourne"

