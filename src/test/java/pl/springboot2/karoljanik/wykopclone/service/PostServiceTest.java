package pl.springboot2.karoljanik.wykopclone.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.springboot2.karoljanik.wykopclone.dto.PostDto;
import pl.springboot2.karoljanik.wykopclone.model.*;
import pl.springboot2.karoljanik.wykopclone.repository.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("prod")
class PostServiceTest {

    private PostRepository postRepository = mock(PostRepository.class);
    private TagRepository tagRepository = mock(TagRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);
    private AuthorizationService authorizationService = mock(AuthorizationService.class);
    private CommentRepository commentRepository = mock(CommentRepository.class);
    private VoteRepository voteRepository = mock(VoteRepository.class);

    private PostService postService = new PostService(tagRepository,authorizationService,postRepository,commentRepository,
            userRepository,voteRepository);

    @BeforeEach
    private void setUpRepository() {
        //given

    //Tags
        Tag tag1 = new Tag();
        tag1.setId(1L);
        tag1.setTagName("tag 1");

        Tag tag2 = new Tag();
        tag2.setId(2L);
        tag2.setTagName("tag 2");

    //Users
        User user1 = new User();
        user1.setUsername("user 1");
        user1.setUserId(1L);

        User user2 = new User();
        user2.setUsername("user 2");
        user2.setUserId(2L);

    //Posts
        Post post1 = new Post();
        post1.setPostName("post 1");
        post1.setPostId(1L);
        post1.setTag(tag1);
        post1.setUser(user1);
        post1.setCreateDate(Instant.now());

        Post post2 = new Post();
        post2.setPostName("post 2");
        post2.setPostId(2L);
        post2.setTag(tag2);
        post2.setUser(user2);
        post2.setCreateDate(Instant.now());

        Post post3 = new Post();
        post3.setPostName("post 3");
        post3.setPostId(3L);
        post3.setTag(tag1);
        post3.setUser(user1);
        post3.setCreateDate(Instant.now());

    //Comments
        Comment comment1 = new Comment();
        comment1.setText("cmt 1");
        comment1.setPost(post1);
        comment1.setUser(user1);

        Comment comment2 = new Comment();
        comment2.setText("cmt 2");
        comment2.setPost(post2);
        comment2.setUser(user1);

        Comment comment3 = new Comment();
        comment3.setText("cmt 3");
        comment3.setPost(post3);
        comment3.setUser(user1);

    //Votes
        Vote vote1 = new Vote();
        vote1.setVoteType(VoteType.UPVOTE);
        vote1.setPost(post1);
        vote1.setUser(user1);

        Vote vote2 = new Vote();
        vote2.setVoteType(VoteType.UPVOTE);
        vote2.setPost(post2);
        vote2.setUser(user1);

        Vote vote3 = new Vote();
        vote3.setVoteType(VoteType.UPVOTE);
        vote3.setPost(post3);
        vote3.setUser(user1);

        authorizationService.isLoggedIn();

        List<Post> all = Arrays.asList(post1, post2, post3);
        Optional<Tag> tag = Optional.ofNullable(tag1);
        Optional<User> user = Optional.ofNullable(user1);

        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag1));
        when(postRepository.findAllByTag(tag1)).thenReturn(all);

        when(userRepository.findByUsername("user 1")).thenReturn(Optional.of(user1));
        when(postRepository.findByUser(user1)).thenReturn(all);

    }

    @Test
    @DisplayName("shouldReturnPostsByGivenTag")
    void getPostsByTag() {

        //when
        List<PostDto> actual = postService.getPostsByTag(1L);

        //then
        assertEquals("tag 1", actual.get(0).getTagName());
        assertEquals("post 1", actual.get(0).getPostName());
        assertEquals(0, actual.get(0).getVoteCount());
        assertEquals("user 1", actual.get(0).getUserName());
        assertEquals(1, actual.get(0).getId());

    }

    @Test
    @DisplayName("shouldReturnPostByGivenUsername")
    void getPostsByUsername() {

        //when
        List<PostDto> actual = postService.getPostsByUsername("user 1");

        //then
        assertEquals("post 3", actual.get(2).getPostName());
        assertEquals("tag 1", actual.get(2).getTagName());
        assertEquals(0, actual.get(2).getVoteCount());
        assertEquals("user 1", actual.get(2).getUserName());
        assertEquals(3, actual.get(2).getId());

    }
}
