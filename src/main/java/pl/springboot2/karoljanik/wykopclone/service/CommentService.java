package pl.springboot2.karoljanik.wykopclone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.springboot2.karoljanik.wykopclone.dto.CommentDto;
import pl.springboot2.karoljanik.wykopclone.exceptions.WykopCloneException;
import pl.springboot2.karoljanik.wykopclone.model.AuthorizationMail;
import pl.springboot2.karoljanik.wykopclone.model.Comment;
import pl.springboot2.karoljanik.wykopclone.model.Post;
import pl.springboot2.karoljanik.wykopclone.model.User;
import pl.springboot2.karoljanik.wykopclone.repository.CommentRepository;
import pl.springboot2.karoljanik.wykopclone.repository.PostRepository;
import pl.springboot2.karoljanik.wykopclone.repository.UserRepository;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthorizationService authorizationService;
    private final CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    @Autowired
    public CommentService(PostRepository postRepository, UserRepository userRepository, AuthorizationService authorizationService, CommentRepository commentRepository, MailContentBuilder mailContentBuilder, MailService mailService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.authorizationService = authorizationService;
        this.commentRepository = commentRepository;
        this.mailContentBuilder = mailContentBuilder;
        this.mailService = mailService;
    }

    public void save(CommentDto commentDto) {
        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new WykopCloneException("No such post: " + commentDto.getPostId().toString()));
        User currentUser= authorizationService.getCurrentUser();
//TO DO make commenting user as logged-in user
        Comment comment = commentRepository.save(mapToComment(commentDto));
        comment.setPost(post);
        comment.setUser(currentUser);

        String message = mailContentBuilder.build(comment.getUser().getUsername() + " posted a comment on your post.");
        sendCommentNotification(message, comment.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new AuthorizationMail(user.getEmail(),
                user.getUsername() + " Commented on your post", message));
    }

    private CommentDto mapToCommentDto(Comment comment) {
        return CommentDto.builder().id(comment.getId())
                .postId(comment.getPost().getPostId())
                .createdDate(comment.getCreatedDate())
                .text(comment.getText())
                .userName(comment.getUser().getUsername())
                .build();
    }

    private Comment mapToComment(CommentDto commentDto) {
        return Comment.builder().text(commentDto.getText())
                .createdDate(Instant.now())
                .build();
    }

    public List<CommentDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new WykopCloneException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(this::mapToCommentDto)
                .collect(Collectors.toList());

    }

    public List<CommentDto> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new WykopCloneException(userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(this::mapToCommentDto)
                .collect(Collectors.toList());
    }
}
