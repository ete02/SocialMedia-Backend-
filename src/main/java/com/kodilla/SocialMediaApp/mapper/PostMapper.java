package com.kodilla.SocialMediaApp.mapper;

import com.kodilla.SocialMediaApp.domain.dto.PostDto;
import com.kodilla.SocialMediaApp.domain.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "comments", ignore = true)
    Post mapToPost(PostDto postDto);

    @Mapping(target = "userId", expression = "java(post.getUser().getId())")
    @Mapping(target = "login", expression = "java(post.getUser().getLogin())")
    PostDto mapToPostDto(Post post);

    @Mapping(target = "userId", expression = "java(post.getUser().getId())")
    @Mapping(target = "login", expression = "java(post.getUser().getLogin())")
    List<PostDto> mapToPostsDto(List<Post> posts);
}
