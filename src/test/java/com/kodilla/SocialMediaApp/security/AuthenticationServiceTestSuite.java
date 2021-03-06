package com.kodilla.SocialMediaApp.security;

import com.kodilla.SocialMediaApp.domain.au.AuthenticationResponse;
import com.kodilla.SocialMediaApp.domain.au.LoginRequest;
import com.kodilla.SocialMediaApp.domain.au.RefreshTokenRequest;
import com.kodilla.SocialMediaApp.domain.au.RegisterRequest;
import com.kodilla.SocialMediaApp.domain.entity.RefreshToken;
import com.kodilla.SocialMediaApp.domain.entity.User;
import com.kodilla.SocialMediaApp.domain.entity.VerificationToken;
import com.kodilla.SocialMediaApp.exceptions.custom.register.RegisterRequestException;
import com.kodilla.SocialMediaApp.exceptions.custom.security.VerificationTokenNotFoundException;
import com.kodilla.SocialMediaApp.exceptions.custom.user.UserRegistrationException;
import com.kodilla.SocialMediaApp.mailboxlayer.validator.EmailValidator;
import com.kodilla.SocialMediaApp.security.jwt.JwtProvider;
import com.kodilla.SocialMediaApp.security.service.AuthenticationService;
import com.kodilla.SocialMediaApp.security.service.AuthenticationServiceUtils;
import com.kodilla.SocialMediaApp.service.*;
import com.kodilla.SocialMediaApp.validator.ValidateRegisterRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static com.kodilla.SocialMediaApp.domain.enums.RoleType.NO_ROLE;
import static com.kodilla.SocialMediaApp.domain.util.Constants.DEFAULT_PICTURE_SAVED;
import static com.kodilla.SocialMediaApp.util.DomainDataFixture.createAuthenticationResponse;
import static com.kodilla.SocialMediaApp.util.EntityDataFixture.*;
import static com.kodilla.SocialMediaApp.util.RequestDataFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTestSuite {
    @InjectMocks
    private AuthenticationService authenticationService;
    @Mock
    private JwtProvider jwtProvider;
    @Mock
    private ImageService imageService;
    @Mock
    private UserServiceDb userServiceDb;
    @Mock
    private EmailValidator emailValidator;
    @Mock
    private MailSenderService mailSenderService;
    @Mock
    private RefreshTokenService refreshTokenService;
    @Mock
    private MailCreationService mailCreationService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private ValidateRegisterRequest validateRegisterRequest;
    @Mock
    private VerificationTokenServiceDb verificationTokenServiceDb;
    @Mock
    private AuthenticationServiceUtils authenticationServiceUtils;

    @Test
    public void shouldRegister() {
        //GIVEN
        User user = createUser("login", "test@gmail.com");
        RegisterRequest registerRequest = createRegisterRequest("login",
                "Poznan", "test@gmail.com", "Password");
        given(validateRegisterRequest.isRegisterRequestValid(any())).willReturn(true);
        given(userServiceDb.getAllUsersByLoginContaining(anyString())).willReturn(new ArrayList<>());
        given(emailValidator.validateUserEmail(anyString())).willReturn(true);
        given(authenticationServiceUtils.assignUserWithRole(registerRequest, NO_ROLE)).willReturn(user);
        given(authenticationServiceUtils.generateVerificationToken(user)).willReturn("token");
        given(imageService.loadDefaultUserImage(user)).willReturn(DEFAULT_PICTURE_SAVED);
        verify(mailSenderService, times(0)).sendPersonalizedEmail(anyString(), anyString(), anyString());
        //WHEN
        boolean isRegistered = authenticationService.register(registerRequest);
        //THEN
        assertTrue(isRegistered);
    }

    @Test
    public void shouldThrowUserRegistrationExceptionBecauseOfInvalidEmailDuringRegister() {
        //GIVEN
        RegisterRequest registerRequest = createRegisterRequest("login",
                "Poznan", "test@gmail.com", "Password");
        given(validateRegisterRequest.isRegisterRequestValid(any())).willReturn(true);
        given(userServiceDb.getAllUsersByLoginContaining(anyString())).willReturn(new ArrayList<>());
        given(emailValidator.validateUserEmail(anyString())).willReturn(false);
        //WHEN && THEN
        assertThatThrownBy(() -> authenticationService.register(registerRequest))
                .isInstanceOf(UserRegistrationException.class)
                .hasMessage("User exists and can not be created with login: login or User email: test@gmail.com is not valid!");
    }

    @Test
    public void shouldThrowUserRegistrationExceptionBecauseUsereAlreadyExistDuringRegister() {
        //GIVEN
        User user = createUser("login", "test@gmail.com");
        RegisterRequest registerRequest = createRegisterRequest("login",
                "Poznan", "test@gmail.com", "Password");
        given(validateRegisterRequest.isRegisterRequestValid(any())).willReturn(true);
        given(userServiceDb.getAllUsersByLoginContaining(anyString())).willReturn(new ArrayList<>(List.of(user)));
        //WHEN && THEN
        assertThatThrownBy(() -> authenticationService.register(registerRequest))
                .isInstanceOf(UserRegistrationException.class)
                .hasMessage("User exists and can not be created with login: login or User email: test@gmail.com is not valid!");
    }

    @Test
    public void shouldThrowRegisterRequestExceptionBecauseOfInvalidRegistrationRequestDuringRegister() {
        //GIVEN
        RegisterRequest registerRequest = createRegisterRequest("login",
                "Poznan", "test@gmail.com", "Password");
        given(validateRegisterRequest.isRegisterRequestValid(any())).willReturn(false);
        //WHEN && THEN
        assertThatThrownBy(() -> authenticationService.register(registerRequest))
                .isInstanceOf(RegisterRequestException.class)
                .hasMessage("RegisterRequest(login=login, password=Password, email=test@gmail.com, city=Poznan) is not valid!");
    }

    @Test
    public void shouldVerifyToken() {
        //GIVEN
        User user = createUser("login", "test@gmail.com");
        VerificationToken verificationToken = createVerificationToken(user, "token");
        Optional<VerificationToken> optionalVerificationToken = Optional.of(verificationToken);
        given(verificationTokenServiceDb.getVerificationTokenByToken("token")).willReturn(optionalVerificationToken);
        given(authenticationServiceUtils.fetchUserAndEnable(verificationToken)).willReturn(true);
        //WHEN
        boolean isTokenVerified = authenticationService.verifyToken("token");
        //THEN
        assertTrue(isTokenVerified);
    }

    @Test
    public void shouldNotVerifyToken() {
        //WHEN && THEN
        assertThatThrownBy(() -> authenticationService.verifyToken("token"))
                .isInstanceOf(VerificationTokenNotFoundException.class)
                .hasMessage("Could not find verification token by token: token");
    }

    @Test
    public void shouldLogin() {
        //GIVEN
        LoginRequest loginRequest = createLoginRequest();
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword());
        AuthenticationResponse authenticationResponse = createAuthenticationResponse();
        RefreshToken refreshToken = createRefreshToken("token");
        given(authenticationManager.authenticate(any())).willReturn(authentication);
        given(jwtProvider.generateToken(any())).willReturn("token");
        given(refreshTokenService.generateRefreshToken()).willReturn(refreshToken);
        given(authenticationServiceUtils.createAuthenticationResponse(
                anyString(), anyString(), anyString())).willReturn(authenticationResponse);
        //WHEN
        AuthenticationResponse authenticationResponseFromService = authenticationService.login(loginRequest);
        //THEN
        assertThat(authenticationResponseFromService).isNotNull();
        assertEquals("login", authenticationResponseFromService.getLogin());
        assertEquals("authenticationToken", authenticationResponseFromService.getAuthenticationToken());
        assertEquals("refreshToken", authenticationResponseFromService.getRefreshToken());
        assertEquals(authenticationResponse.getExpiresAt(), authenticationResponseFromService.getExpiresAt());
    }

    @Test
    public void shouldRefreshToken() {
        //GIVEN
        RefreshToken refreshToken = createRefreshToken("token");
        RefreshTokenRequest refreshTokenRequest = createRefreshTokenRequest();
        AuthenticationResponse authenticationResponse = createAuthenticationResponse();
        given(refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken())).willReturn(refreshToken);
        given(jwtProvider.generateTokenWithLogin(refreshTokenRequest.getLogin())).willReturn("login");
        given(authenticationServiceUtils.createAuthenticationResponse(
                anyString(), anyString(), anyString())).willReturn(authenticationResponse);
        //WHEN
        AuthenticationResponse authenticationResponseFromService = authenticationService.refreshToken(refreshTokenRequest);
        //THEN
        assertThat(authenticationResponseFromService).isNotNull();
        assertEquals("login", authenticationResponseFromService.getLogin());
        assertEquals("authenticationToken", authenticationResponseFromService.getAuthenticationToken());
        assertEquals("refreshToken", authenticationResponseFromService.getRefreshToken());
        assertEquals(authenticationResponse.getExpiresAt(), authenticationResponseFromService.getExpiresAt());
    }
}
