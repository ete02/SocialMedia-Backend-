package com.kodilla.SocialMediaApp.mailboxlayer.validator;

import com.kodilla.SocialMediaApp.mailboxlayer.dto.ValidateMailResponseDto;
import com.kodilla.SocialMediaApp.mailboxlayer.service.MailBoxLayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class EmailValidator {
    private final MailBoxLayerService mailBoxLayerService;

    public boolean validateUserEmail(final String email) {
        log.info("Start to validate user email!");
        ValidateMailResponseDto validateMailDto = mailBoxLayerService.getValidateMailResponseFromClient(email);
        boolean result = isEmailValid(validateMailDto);
        log.info((result) ? "User email validated successfully!" : "User email is not valid!");
        return result;
    }

    private boolean isEmailValid(final ValidateMailResponseDto validateMailDto) {
        return validateMailDto.isSmtpValid() &&
                validateMailDto.isEmailFormatValid() &&
                validateMailDto.isMxRecordsFound();
    }
}
