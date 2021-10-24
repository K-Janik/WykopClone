package pl.springboot2.karoljanik.wykopclone.service;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.springboot2.karoljanik.wykopclone.dto.PostDto;
import pl.springboot2.karoljanik.wykopclone.exceptions.WykopCloneException;
import pl.springboot2.karoljanik.wykopclone.model.Post;
import pl.springboot2.karoljanik.wykopclone.model.Tag;
import pl.springboot2.karoljanik.wykopclone.model.User;
import pl.springboot2.karoljanik.wykopclone.repository.CommentRepository;
import pl.springboot2.karoljanik.wykopclone.repository.PostRepository;
import pl.springboot2.karoljanik.wykopclone.repository.TagRepository;
import pl.springboot2.karoljanik.wykopclone.repository.UserRepository;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class PostService {

    private final TagRepository tagRepository;
    private final AuthorizationService authorizationService;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(TagRepository tagRepository, AuthorizationService authorizationService, PostRepository postRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.tagRepository = tagRepository;
        this.authorizationService = authorizationService;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public void save(PostDto postDto) {
        Tag tag = tagRepository.findByTagName(postDto.getTagName())
                .orElseThrow(() -> new WykopCloneException("No such tag: " + postDto.getPostName()));
        User currentUser = authorizationService.getCurrentUser();

        Post post = postRepository.save(mapToPost(postDto));
        post.setTag(tag);
        post.setUser(currentUser);
    }

    @Transactional
    public PostDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new WykopCloneException("No post with id: " + id));
        return mapToPostDto(post);
    }

    @Transactional
    public List<PostDto> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(this::mapToPostDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostDto> getPostsByTag(Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new WykopCloneException(tagId.toString()));
        List<Post> posts = postRepository.findAllByTag(tag);
        return posts.stream()
                .map(this::mapToPostDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostDto> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new WykopCloneException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(this::mapToPostDto)
                .collect(Collectors.toList());
    }

    private PostDto mapToPostDto(Post post) {
        return PostDto.builder().id(post.getPostId())
                .tagName(post.getTag().getTagName())
                .postName(post.getPostName())
                .url(post.getUrl())
                .userName(post.getUser().getUsername())
                .description(post.getDescription())
                .voteCount(post.getVoteCount())
                .commentCount(commentRepository.findByPost(post).size())
                .duration(TimeAgo.using(post.getCreateDate().toEpochMilli()))
                .build();
    }

    private Post mapToPost(PostDto postDto) {
        return Post.builder().postName(postDto.getPostName())
                .url(postDto.getUrl())
                .description(postDto.getDescription())
                .createDate(Instant.now())
                .voteCount(0)
                .build();
    }
}
