package pl.springboot2.karoljanik.wykopclone.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.springboot2.karoljanik.wykopclone.dto.PostDto;
import pl.springboot2.karoljanik.wykopclone.model.Post;
import pl.springboot2.karoljanik.wykopclone.model.Tag;
import pl.springboot2.karoljanik.wykopclone.model.User;
import pl.springboot2.karoljanik.wykopclone.repository.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@SpringBootTest
class PostServiceTest {

    private PostRepository postRepository = mock(PostRepository.class);
    private TagRepository tagRepository = mock(TagRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);
    private AuthorizationService authorizationService;
    private CommentRepository commentRepository;
    private VoteRepository voteRepository;

    private PostService postService = new PostService(tagRepository,authorizationService,postRepository,commentRepository,
            userRepository,voteRepository);

    @BeforeEach
    private void setUpRepository() {
        //given
        Tag tag1 = new Tag();
        tag1.setId(1L);
        tag1.setTagName("tag 1");

        Tag tag2 = new Tag();
        tag2.setId(2L);
        tag2.setTagName("tag 2");


        User user1 = new User();
        user1.setUsername("user 1");

        User user2 = new User();
        user2.setUsername("user 2");

        Post post1 = new Post();
        post1.setPostName("post 1");
        post1.setPostId(1L);
        post1.setTag(tag1);
        post1.setUser(user1);

        Post post2 = new Post();
        post2.setPostName("post 2");
        post2.setPostId(2L);
        post2.setTag(tag2);
        post2.setUser(user2);

        Post post3 = new Post();
        post3.setPostName("post 3");
        post3.setPostId(3L);
        post3.setTag(tag1);
        post3.setUser(user1);

        List<Post> all = Arrays.asList(post1, post2, post3);
        Optional<Tag> tag = Optional.ofNullable(tag1);

//        doReturn(all).when(postRepository).findAll();
        doReturn(all).when(postRepository).findAllByTag(tag1);
        doReturn(tag).when(tagRepository).findById(1L);


    }

    @Test
    @DisplayName("shouldReturnPostsByGivenTag")
    void getPostsByTag() {

        //when
        List<PostDto> actual = postService.getPostsByTag(1L);


        //then
        Assertions.assertEquals("tag 1", actual.get(0).getTagName());
    }

    @Test
    @DisplayName("shouldReturnPostByGivenId")
    void getPostsByUsername() {
    }
}
