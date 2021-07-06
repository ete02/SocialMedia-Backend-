package com.kodilla.SocialMediaApp.domain.entity;

import com.kodilla.SocialMediaApp.domain.enums.UserStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Entity
@Table(name = "USERS")
public final class User {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long id;

    @NotBlank(message = "User name is needed!")
    @Column(name = "USER_NAME")
    private String userName;

    @NotBlank(message = "Login is needed!")
    @Column(name = "LOGIN", unique = true)
    private String login;

    @NotBlank(message = "Password is needed!")
    @Column(name = "PASSWORD")
    private String password;

    @Email
    @NotEmpty(message = "Email is required !")
    @Column(name = "EMAIL")
    private String email;

    @Nullable
    @Lob
    @Column(name = "DESCRIPTION")
    private String description;

    @CreationTimestamp
    @NotNull(message = "User create date cannot be Null!")
    @Column(name = "CREATE_DATE")
    private LocalDate createDate;

    @NotNull(message = "User status cannot be Null!")
    @Enumerated(STRING)
    @Column(name = "USER_STATUS")
    private UserStatus userStatus;

    @NotNull(message = "User validation status cannot be Null!")
    @Column(name = "Enabled")
    private boolean enabled;

    @OneToMany(
            targetEntity = Post.class,
            mappedBy = "user",
            fetch = LAZY
    )
    private List<Post> posts;

    @OneToMany(
            targetEntity = Post.class,
            fetch = LAZY
    )
    @JoinColumn(name = "LIKED_POSTS")
    private List<Post> likedPosts;


}
