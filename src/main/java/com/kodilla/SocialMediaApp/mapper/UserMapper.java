package com.kodilla.SocialMediaApp.mapper;

import com.kodilla.SocialMediaApp.domain.dto.UserDto;
import com.kodilla.SocialMediaApp.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "likedPosts", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User mapToUser(UserDto userDto);

    UserDto mapToUserDto(User user);

    List<UserDto> mapToUsersDto(List<User> users);
}