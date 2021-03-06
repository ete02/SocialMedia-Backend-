package com.kodilla.SocialMediaApp.controller;

import com.kodilla.SocialMediaApp.domain.dto.PasswordRequest;
import com.kodilla.SocialMediaApp.domain.dto.UserDto;
import com.kodilla.SocialMediaApp.domain.dto.UserRequest;
import com.kodilla.SocialMediaApp.facade.user.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/users")
public final class UserController {
    private final UserFacade userFacade;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        return userFacade.getUsers();
    }

    @GetMapping("/{login}")
    public ResponseEntity<List<UserDto>> getUsersByLogin(@PathVariable final String login) {
        return userFacade.getUsersByLogin(login);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable final Long id) {
        return userFacade.getUserById(id);
    }

    @GetMapping("/login/{login}")
    public ResponseEntity<UserDto> getUserByLogin(@PathVariable final String login) {
        return userFacade.getUserByLogin(login);
    }

    @GetMapping("/mail/{mail}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable final String mail) {
        return userFacade.getUserByMail(mail);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadUserImage(@RequestParam("image") final MultipartFile image) {
        return userFacade.uploadUserImage(image);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDto> updateProfile(@RequestBody final UserRequest userRequest) {
        return userFacade.updateProfile(userRequest);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody final PasswordRequest passwordRequest) {
        return userFacade.changePassword(passwordRequest);
    }

    @PutMapping("/resetPassword/{mail}")
    public ResponseEntity<UserDto> resetPassword(@PathVariable final String mail) {
        return userFacade.resetPassword(mail);
    }

    @DeleteMapping("/{login}")
    public ResponseEntity<String> deleteUser(@PathVariable final String login) {
        return userFacade.deleteUser(login);
    }
}
