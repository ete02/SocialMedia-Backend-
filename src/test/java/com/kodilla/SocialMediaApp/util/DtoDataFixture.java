package com.kodilla.SocialMediaApp.util;

import com.kodilla.SocialMediaApp.domain.dto.CommentDto;
import com.kodilla.SocialMediaApp.domain.dto.PostDto;
import com.kodilla.SocialMediaApp.domain.dto.RoleDto;
import com.kodilla.SocialMediaApp.domain.dto.UserDto;
import com.kodilla.SocialMediaApp.domain.enums.RoleType;
import com.kodilla.SocialMediaApp.mailboxlayer.dto.ValidateMailResponseDto;
import com.kodilla.SocialMediaApp.openweather.dto.OpenWeatherMainDto;
import com.kodilla.SocialMediaApp.openweather.dto.OpenWeatherResponseDto;
import com.kodilla.SocialMediaApp.openweather.dto.OpenWeatherWeatherDto;

import java.time.Instant;

import static com.kodilla.SocialMediaApp.domain.enums.UserStatus.ACTIVE;
import static java.time.temporal.ChronoUnit.SECONDS;

public final class DtoDataFixture {
    private DtoDataFixture() {
    }

    public static ValidateMailResponseDto createValidateResponseDto(final boolean emailFormatValid,
                                                                    final boolean mxRecordsFound,
                                                                    final boolean smtpValid) {
        return ValidateMailResponseDto.builder()
                .validatedEmail("test@gmail.com")
                .emailFormatValid(emailFormatValid)
                .mxRecordsFound(mxRecordsFound)
                .smtpValid(smtpValid)
                .build();
    }


    public static OpenWeatherMainDto createOpenWeatherMainDto() {
        return OpenWeatherMainDto.builder()
                .temperature(12.1)
                .feltTemperature(12.3)
                .humidity(60)
                .pressure(999)
                .build();
    }

    public static OpenWeatherWeatherDto createOpenWeatherWeatherDto() {
        return OpenWeatherWeatherDto.builder()
                .mainWeather("Cloud")
                .weatherDescription("Cloudy weather")
                .build();
    }

    public static OpenWeatherResponseDto createOpenWeatherResponseDto() {
        return OpenWeatherResponseDto.builder()
                .city("Paris")
                .temperature(16)
                .feltTemperature(12.2)
                .pressure(1000)
                .humidity(12)
                .mainWeather("Test Weather")
                .weatherDescription("Test Description")
                .build();
    }


    public static UserDto createUserDto(final String login, final String mail) {
        return UserDto.builder()
                .id(1L)
                .userName("User")
                .login(login)
                .password("Password")
                .email(mail)
                .description("Description")
                .createDate(Instant.now().truncatedTo(SECONDS))
                .updateDate(Instant.now().truncatedTo(SECONDS))
                .userStatus(ACTIVE)
                .enabled(true)
                .build();
    }

    public static PostDto createPostDto(final UserDto user, final Instant postDate) {
        return PostDto.builder()
                .id(1L)
                .postName("Post")
                .caption("Sign")
                .url("URL")
                .imageSerialNumber(0L)
                .likesCount(0L)
                .postDate(postDate)
                .updateDate(Instant.now().truncatedTo(SECONDS))
                .userId(1L)
                .login("login")
                .build();
    }

    public static CommentDto createCommentDto(final PostDto post) {
        return CommentDto.builder()
                .id(1L)
                .commentName("Comment")
                .content("Content")
                .commentDate(Instant.now().truncatedTo(SECONDS))
                .updateDate(Instant.now().truncatedTo(SECONDS))
                .postId(1L)
                .postName("Post")
                .userId(1L)
                .build();
    }

    public static RoleDto createRoleDto(final RoleType roleType) {
        return RoleDto.builder()
                .id(1L)
                .roleType(roleType)
                .build();
    }
}
