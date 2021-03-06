package com.kodilla.SocialMediaApp.controller;

import com.kodilla.SocialMediaApp.mailboxlayer.dto.ValidateMailResponseDto;
import com.kodilla.SocialMediaApp.mailboxlayer.facade.MailBoxLayerFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/validates")
public final class MailBoxLayerController {
    private final MailBoxLayerFacade mailBoxLayerFacade;

    @GetMapping("/{email}")
    public ResponseEntity<ValidateMailResponseDto> getValidationEmail(@PathVariable final String email) {
        return mailBoxLayerFacade.getValidateMailResponse(email);
    }
}
