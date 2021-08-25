package pl.springboot2.karoljanik.wykopclone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.springboot2.karoljanik.wykopclone.dto.PostDto;
import pl.springboot2.karoljanik.wykopclone.exceptions.WykopCloneException;
import pl.springboot2.karoljanik.wykopclone.model.Post;
import pl.springboot2.karoljanik.wykopclone.model.Tag;
import pl.springboot2.karoljanik.wykopclone.model.User;
import pl.springboot2.karoljanik.wykopclone.repository.PostRepository;
import pl.springboot2.karoljanik.wykopclone.repository.TagRepository;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class PostService {

    private final TagRepository tagRepository;
    private final AuthorizationService authorizationService;
    private final PostRepository postRepository;

    @Autowired
    public PostService(TagRepository tagRepository, AuthorizationService authorizationService, PostRepository postRepository) {
        this.tagRepository = tagRepository;
        this.authorizationService = authorizationService;
        this.postRepository = postRepository;
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

    private PostDto mapToPostDto(Post post) {
        return PostDto.builder().id(post.getPostId())
                .tagName(post.getTag().getTagName())
                .postName(post.getPostName())
                .url(post.getUrl())
                .description(post.getDescription())
                .build();
    }

    private Post mapToPost(PostDto postDto) {
        return Post.builder().postName(postDto.getPostName())
                .url(postDto.getUrl())
                .description(postDto.getDescription())
                .createDate(Instant.now())
                .build();
    }
}
