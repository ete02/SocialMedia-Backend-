package com.kodilla.SocialMediaApp.domain.entity;

import com.kodilla.SocialMediaApp.domain.enums.RoleType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.Set;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.SEQUENCE;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "users")
@ToString
@Getter
@Entity
@Table(name = "ROLES")
public final class Role {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long id;

    @NotNull(message = "Role type cannot be Null!")
    @Enumerated(STRING)
    @Column(name = "ROLE_TYPE")
    private RoleType roleType;

    @ManyToMany
    @JoinTable(
            name = "USER_ROLES",
            joinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")}
    )
    private Set<User> users;
}