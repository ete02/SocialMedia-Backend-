package com.kodilla.SocialMediaApp.mailboxlayer;

import com.kodilla.SocialMediaApp.mailboxlayer.client.MailBoxLayerClient;
import com.kodilla.SocialMediaApp.mailboxlayer.dto.ValidateMailResponseDto;
import com.kodilla.SocialMediaApp.mailboxlayer.exceptions.MailBoxLayerApiException;
import com.kodilla.SocialMediaApp.mailboxlayer.service.MailBoxLayerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;

import static com.kodilla.SocialMediaApp.util.DtoDataFixture.createValidateResponseDto;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class MailBoxLayerServiceTestSuite {
    @InjectMocks
    private MailBoxLayerService mailBoxLayerService;
    @Mock
    private MailBoxLayerClient mailBoxLayerClient;

    @Test
    public void shouldGetValidateMailResponseFromClient() {
        //GIVEN
        String email = "test@gmail.com";
        ValidateMailResponseDto validateResponseDto = createValidateResponseDto(true, true, true);
        CompletableFuture<ValidateMailResponseDto> completableFuture = CompletableFuture.completedFuture(validateResponseDto);
        given(mailBoxLayerClient.getValidatedEmailFromUrl(email)).willReturn(completableFuture);
        //WHEN
        ValidateMailResponseDto validateMailResponseFromClient = mailBoxLayerService.getValidateMailResponseFromClient("test@gmail.com");
        //THEN
        assertEquals(validateResponseDto, validateMailResponseFromClient);
    }

    @Test
    public void shouldNotValidateMailResponseFromClientAndThrowMailBoxLayerApiException() {
        //GIVEN
        String email = "test@gmail.com";
        given(mailBoxLayerClient.getValidatedEmailFromUrl(email)).willThrow(MailBoxLayerApiException.class);
        //WHEN & THEN
        assertThatThrownBy(() -> mailBoxLayerService.getValidateMailResponseFromClient("test@gmail.com"))
                .isInstanceOf(MailBoxLayerApiException.class)
                .hasMessage("MailBoxLayerApi error for validation of email: test@gmail.com");
    }
}
