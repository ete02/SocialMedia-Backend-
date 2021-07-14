package com.kodilla.SocialMediaApp.domain.dto;

import lombok.*;

@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
@Getter
@RequiredArgsConstructor
public class RoleDto {
    private final Long id;
    private final RoleType roleType;
}
