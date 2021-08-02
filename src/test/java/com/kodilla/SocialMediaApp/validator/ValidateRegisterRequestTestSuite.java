package com.kodilla.SocialMediaApp.validator;

import com.kodilla.SocialMediaApp.domain.au.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.kodilla.SocialMediaApp.util.RequestDataFixture.createRegisterRequest;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ValidateRegisterRequestTestSuite {
    @Autowired
    private ValidateRegisterRequest validateRegisterRequest;

    @Test
    public void isRegisterRequestValid() {
        //GIVEN
        RegisterRequest registerRequest = createRegisterRequest("login",
                "password", "test@gmail.com", "Poznan");
        //WHEN
        boolean result = validateRegisterRequest.isRegisterRequestValid(registerRequest);
        //THEN
        assertTrue(result);
    }

    @Test
    public void isRegisterRequestNotValid() {
        //GIVEN
        RegisterRequest blankRegisterRequest = createRegisterRequest("",
                "password", "test@gmail.com", "Poznan");
        RegisterRequest nullRegisterRequest = createRegisterRequest(null,
                "password", "test@gmail.com", "Poznan");
        //WHEN
        boolean resultForBlankField = validateRegisterRequest.isRegisterRequestValid(blankRegisterRequest);
        boolean resultForNullField = validateRegisterRequest.isRegisterRequestValid(nullRegisterRequest);
        //THEN
        assertFalse(resultForBlankField);
        assertFalse(resultForNullField);
    }
}
