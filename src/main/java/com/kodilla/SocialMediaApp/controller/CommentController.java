package com.kodilla.SocialMediaApp.controller;

import com.kodilla.SocialMediaApp.domain.dto.CommentDto;
import com.kodilla.SocialMediaApp.facade.CommentFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/comments")
public final class CommentController {
    private final CommentFacade commentFacade;

    @GetMapping


    @GetMapping("/{id}")


    @PostMapping


    @PutMapping


    @DeleteMapping("/{id}")
}
