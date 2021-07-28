package com.kodilla.SocialMediaApp.openweather.service;

import com.kodilla.SocialMediaApp.openweather.client.OpenWeatherClient;
import com.kodilla.SocialMediaApp.openweather.dto.OpenWeatherResponse;
import com.kodilla.SocialMediaApp.openweather.exceptions.OpenWeatherApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Slf4j
@RequiredArgsConstructor
@Service
public class OpenWeatherService {
    private final OpenWeatherClient openWeatherClient;

    public OpenWeatherResponse getWeatherForCityResponseFromClient(final String city) {
        log.info("Weather response from OpenWeather API sent for city: " + city);
        try {
            return openWeatherClient.getWeatherForCityFromUrl(city).get();
        } catch (ExecutionException | InterruptedException | OpenWeatherApiException e) {
            throw new OpenWeatherApiException(city);
        }
    }
}
