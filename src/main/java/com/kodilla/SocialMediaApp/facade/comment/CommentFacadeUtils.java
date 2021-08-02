package com.kodilla.SocialMediaApp.facade.comment;

import com.kodilla.SocialMediaApp.domain.dto.CommentDto;
import com.kodilla.SocialMediaApp.domain.dto.CommentRequest;
import com.kodilla.SocialMediaApp.domain.dto.UpdateCommentRequest;
import com.kodilla.SocialMediaApp.domain.entity.Comment;
import com.kodilla.SocialMediaApp.domain.entity.Post;
import com.kodilla.SocialMediaApp.domain.entity.User;
import com.kodilla.SocialMediaApp.exceptions.custom.comment.CommentNotFoundException;
import com.kodilla.SocialMediaApp.exceptions.custom.user.UserValidationException;
import com.kodilla.SocialMediaApp.mapper.CommentMapper;
import com.kodilla.SocialMediaApp.service.CommentService;
import com.kodilla.SocialMediaApp.service.CommentServiceDb;
import com.kodilla.SocialMediaApp.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.kodilla.SocialMediaApp.domain.enums.ValidationStatus.AUTHORIZED;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Component
public class CommentFacadeUtils {
    private final UserValidator userValidator;
    private final CommentMapper commentMapper;
    private final CommentService commentService;
    private final CommentServiceDb commentServiceDb;

    public ResponseEntity<CommentDto> createCommentIfUserIsAuthorized(final CommentRequest commentRequest, final Post post, final User user) {
        if (userValidator.isUserValidated(user)) {
            CommentDto commentDto = commentMapper.mapToCommentDto(
                    commentService.createComment(post, commentRequest));
            log.info("Comment created successfully!");
            return new ResponseEntity<>(commentDto, OK);
        } else {
            throw new UserValidationException(commentRequest.getLogin(), AUTHORIZED);
        }
    }

    public ResponseEntity<CommentDto> updateCommentIfExists(final Comment comment, final UpdateCommentRequest updateCommentRequest) {
        CommentDto commentDto = commentMapper.mapToCommentDto(
                commentService.updateComment(comment, updateCommentRequest));
        log.info("Comment updated successfully!");
        return new ResponseEntity<>(commentDto, OK);
    }

    public ResponseEntity<String> deleteCommentIfExists(final Long id) {
        try {
            commentServiceDb.deleteCommentById(id);
            log.info("Comment deleted successfully!");
        } catch (EmptyResultDataAccessException e) {
            throw new CommentNotFoundException(id);
        }
        return new ResponseEntity<>("Comment Deleted Successfully!", OK);
    }
}
