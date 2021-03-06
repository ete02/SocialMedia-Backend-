package com.kodilla.SocialMediaApp.service;

import com.kodilla.SocialMediaApp.configuration.MySocialAppConf;
import com.kodilla.SocialMediaApp.domain.entity.User;
import com.kodilla.SocialMediaApp.domain.mail.Mail;
import com.kodilla.SocialMediaApp.openweather.dto.OpenWeatherResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.InternetAddress;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class MailCreationService {
    private final Environment environment;
    private final TemplateEngine templateEngine;
    private final MySocialAppConf myInstagramConfig;

    public String createNewUserEmail(final User user, final String password) {
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("password", password);
        context.setVariable("instagram_config", myInstagramConfig);
        return templateEngine.process("newUserEmailTemplate", context);
    }

    public String createResetPasswordEmail(final User user, final String password) {
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("password", password);
        context.setVariable("instagram_config", myInstagramConfig);
        return templateEngine.process("resetPasswordEmailTemplate", context);
    }

    public String createUpdateUserProfileEmail(final User user) {
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("instagram_config", myInstagramConfig);
        return templateEngine.process("updateUserProfileEmailTemplate", context);
    }

    public String createWeatherEmail(final User user, final OpenWeatherResponseDto openWeatherResponseDto) {
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("weather", openWeatherResponseDto);
        context.setVariable("instagram_config", myInstagramConfig);
        return templateEngine.process("whetherEmailTemplate", context);
    }

    public MimeMessagePreparator createMimeMessage(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setPriority(1);
            mimeMessageHelper.setTo(mail.getMailTo());
            mimeMessageHelper.setSubject(mail.getSubject());
            mimeMessageHelper.setText(mail.getText(), true);
            mimeMessageHelper.setFrom(new InternetAddress(Objects.requireNonNull(environment.getProperty("support.mail"))));
        };
    }

    public Mail createMail(final String email, final String subject, final String text) {
        return new Mail.MailBuilder()
                .mailTo(email)
                .subject(subject)
                .text(text)
                .build();
    }
}
