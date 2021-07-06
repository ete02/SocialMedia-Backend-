package com.kodilla.SocialMediaApp.domain.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Entity
@Table(name = "POSTS")
public final class Post {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long id;

    @NotBlank(message = "Post name is required !")
    @Column(name = "POST_NAME")
    private String postName;

    @Lob
    @Nullable
    @Column(name = "HEADLINE")
    private String headline;

    @Nullable
    @Column(name = "URL")
    private String url;

    @Column(name = "IMAGE_ID")
    private Long imageId;

    @Column(name = "LIKES_COUNT")
    private Long likesCount;

    @CreationTimestamp
    @NotNull(message = "Post creation date can not be Null!")
    @Column(name = "POST_DATE")
    private LocalDate postDate;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(
            cascade = CascadeType.REMOVE,
            targetEntity = Comment.class,
            mappedBy = "post",
            fetch = LAZY
    )
    private List<Comment> comments;
}
