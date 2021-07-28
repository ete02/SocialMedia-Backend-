package com.kodilla.SocialMediaApp.openweather.facade;

import com.kodilla.SocialMediaApp.openweather.dto.OpenWeatherResponseDto;
import com.kodilla.SocialMediaApp.openweather.mapper.OpenWeatherResponseMapper;
import com.kodilla.SocialMediaApp.openweather.service.OpenWeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RequiredArgsConstructor
@Service
public class OpenWeatherFacade {
    private final OpenWeatherService openWeatherService;
    private final OpenWeatherResponseMapper openWeatherResponseMapper;

    public ResponseEntity<OpenWeatherResponseDto> getWeatherResponse(final String city) {
        log.info("Weather response correctly retrieved for city: " + city);
        OpenWeatherResponseDto openWeatherResponseDto = openWeatherResponseMapper.mapToOpenWeatherResponseDto(
                openWeatherService.getWeatherForCityResponseFromClient(city));
        return new ResponseEntity<>(openWeatherResponseDto, OK);
    }
}
