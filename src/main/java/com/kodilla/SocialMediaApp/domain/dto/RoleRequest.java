package com.kodilla.SocialMediaApp.domain.dto;

import com.kodilla.SocialMediaApp.domain.enums.RoleType;
import lombok.*;

@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {
    private String login;
    private RoleType roleType;
}
