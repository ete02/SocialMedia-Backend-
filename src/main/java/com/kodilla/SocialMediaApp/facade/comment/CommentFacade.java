package com.kodilla.SocialMediaApp.facade.comment;

import com.kodilla.SocialMediaApp.domain.dto.CommentDto;
import com.kodilla.SocialMediaApp.domain.dto.CommentRequest;
import com.kodilla.SocialMediaApp.domain.dto.UpdateCommentRequest;
import com.kodilla.SocialMediaApp.domain.entity.Comment;
import com.kodilla.SocialMediaApp.domain.entity.Post;
import com.kodilla.SocialMediaApp.domain.entity.User;
import com.kodilla.SocialMediaApp.exceptions.custom.comment.CommentNotFoundException;
import com.kodilla.SocialMediaApp.exceptions.custom.post.PostNotFoundException;
import com.kodilla.SocialMediaApp.exceptions.custom.user.UserNotFoundException;
import com.kodilla.SocialMediaApp.mapper.CommentMapper;
import com.kodilla.SocialMediaApp.service.CommentServiceDb;
import com.kodilla.SocialMediaApp.service.PostServiceDb;
import com.kodilla.SocialMediaApp.service.UserServiceDb;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CommentFacade {
    private final UserServiceDb userServiceDb;
    private final PostServiceDb postServiceDb;
    private final CommentMapper commentMapper;
    private final CommentServiceDb commentServiceDb;
    private final CommentFacadeUtils commentFacadeUtils;

    public ResponseEntity<List<CommentDto>> getComments() {
        log.info("Get published comments!");
        List<CommentDto> comments = commentMapper.mapToCommentsDto(commentServiceDb.getAllComments());
        return new ResponseEntity<>(comments, OK);
    }

    public ResponseEntity<CommentDto> getComment(final Long id) {
        log.info("Get published comment by id: " + id);
        CommentDto commentDto = commentMapper.mapToCommentDto(commentServiceDb.getCommentById(id)
                .orElseThrow(() -> new CommentNotFoundException(id)));
        return new ResponseEntity<>(commentDto, OK);
    }

    public ResponseEntity<CommentDto> publishComment(@RequestBody final CommentRequest commentRequest) {
        log.info("Try to publish comment!");
        Post post = postServiceDb.getPostById(commentRequest.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentRequest.getPostId()));
        User user = userServiceDb.getUserByLogin(commentRequest.getLogin())
                .orElseThrow(() -> new UserNotFoundException(commentRequest.getLogin()));
        return commentFacadeUtils.createCommentIfUserIsAuthorized(commentRequest, post, user);
    }

    public ResponseEntity<CommentDto> updateComment(final UpdateCommentRequest updateCommentRequest) {
        log.info("Try to update comment!");
        Comment comment = commentServiceDb.getCommentById(updateCommentRequest.getCommentId())
                .orElseThrow(() -> new CommentNotFoundException(updateCommentRequest.getCommentId()));
        return commentFacadeUtils.updateCommentIfExists(comment, updateCommentRequest);
    }

    public ResponseEntity<String> deleteCommentById(final Long id) {
        log.info("Delete published comment by id: " + id);
        return commentFacadeUtils.deleteCommentIfExists(id);
    }
}
