package com.kodilla.SocialMediaApp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
