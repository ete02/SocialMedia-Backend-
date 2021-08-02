package com.kodilla.SocialMediaApp.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public final class MySocialAppConf {
    @Value("${info.api.name}")
    private String instagramName;

    @Value("${info.api.address.street}")
    private String instagramAddressStreet;

    @Value("${info.api.address.town}")
    private String instagramAddressTown;

    @Value("${info.api.address.number}")
    private String instagramAddressNumber;
}
