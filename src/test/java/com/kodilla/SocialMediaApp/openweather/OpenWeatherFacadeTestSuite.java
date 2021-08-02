package com.kodilla.SocialMediaApp.openweather;

import com.kodilla.SocialMediaApp.openweather.dto.OpenWeatherMainDto;
import com.kodilla.SocialMediaApp.openweather.dto.OpenWeatherResponse;
import com.kodilla.SocialMediaApp.openweather.dto.OpenWeatherResponseDto;
import com.kodilla.SocialMediaApp.openweather.dto.OpenWeatherWeatherDto;
import com.kodilla.SocialMediaApp.openweather.facade.OpenWeatherFacade;
import com.kodilla.SocialMediaApp.openweather.mapper.OpenWeatherResponseMapper;
import com.kodilla.SocialMediaApp.openweather.service.OpenWeatherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static com.kodilla.SocialMediaApp.util.DomainDataFixture.createOpenWeatherResponse;
import static com.kodilla.SocialMediaApp.util.DtoDataFixture.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(MockitoExtension.class)
public class OpenWeatherFacadeTestSuite {
    @InjectMocks
    private OpenWeatherFacade openWeatherFacade;
    @Mock
    private OpenWeatherService openWeatherService;
    @Mock
    private OpenWeatherResponseMapper openWeatherResponseMapper;

    @Test
    public void shouldGetWeatherResponse() {
        //GIVEN
        String city = " Poznan";
        OpenWeatherMainDto openWeatherMainDto = createOpenWeatherMainDto();
        OpenWeatherWeatherDto openWeatherWeatherDto = createOpenWeatherWeatherDto();
        OpenWeatherResponse openWeatherResponse = createOpenWeatherResponse(openWeatherMainDto, openWeatherWeatherDto);
        OpenWeatherResponseDto openWeatherResponseDto = createOpenWeatherResponseDto();
        ResponseEntity<OpenWeatherResponseDto> openWeatherResponseDtoResponseEntity = new ResponseEntity<>(openWeatherResponseDto, OK);
        given(openWeatherResponseMapper.mapToOpenWeatherResponseDto(openWeatherResponse)).willReturn(openWeatherResponseDto);
        given(openWeatherService.getWeatherForCityResponseFromClient(city)).willReturn(openWeatherResponse);
        //WHEN
        ResponseEntity<OpenWeatherResponseDto> weatherResponse = openWeatherFacade.getWeatherResponse(city);
        //THEN
        assertEquals(openWeatherResponseDtoResponseEntity, weatherResponse);
    }
}
