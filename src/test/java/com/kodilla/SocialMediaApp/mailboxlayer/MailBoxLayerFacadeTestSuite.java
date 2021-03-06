package com.kodilla.SocialMediaApp.mailboxlayer;

import com.kodilla.SocialMediaApp.mailboxlayer.dto.ValidateMailResponseDto;
import com.kodilla.SocialMediaApp.mailboxlayer.facade.MailBoxLayerFacade;
import com.kodilla.SocialMediaApp.mailboxlayer.service.MailBoxLayerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static com.kodilla.SocialMediaApp.util.DtoDataFixture.createValidateResponseDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(MockitoExtension.class)
public class MailBoxLayerFacadeTestSuite {
    @InjectMocks
    private MailBoxLayerFacade mailBoxLayerFacade;
    @Mock
    MailBoxLayerService mailBoxLayerService;

    @Test
    public void shouldGetValidateMailResponse() {
        //GIVEN
        String email = "test@gmail.com";
        ValidateMailResponseDto validateResponseDto = createValidateResponseDto(true, true, true);
        ResponseEntity<ValidateMailResponseDto> validateMailResponseDtoResponseEntity = new ResponseEntity<>(validateResponseDto, OK);
        given(mailBoxLayerService.getValidateMailResponseFromClient(email)).willReturn(validateResponseDto);
        //WHEN
        ResponseEntity<ValidateMailResponseDto> validateMailResponse = mailBoxLayerFacade.getValidateMailResponse("test@gmail.com");
        //THEN
        assertEquals(validateMailResponseDtoResponseEntity, validateMailResponse);
    }
}
