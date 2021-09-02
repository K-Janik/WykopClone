package pl.springboot2.karoljanik.wykopclone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.springboot2.karoljanik.wykopclone.dto.VoteDto;
import pl.springboot2.karoljanik.wykopclone.exceptions.WykopCloneException;
import pl.springboot2.karoljanik.wykopclone.model.Post;
import pl.springboot2.karoljanik.wykopclone.model.Vote;
import pl.springboot2.karoljanik.wykopclone.repository.PostRepository;
import pl.springboot2.karoljanik.wykopclone.repository.VoteRepository;

import java.util.Optional;

import static pl.springboot2.karoljanik.wykopclone.model.VoteType.UPVOTE;

@Transactional
@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthorizationService authorizationService;

    @Autowired
    public VoteService(VoteRepository voteRepository, PostRepository postRepository, AuthorizationService authorizationService) {
        this.voteRepository = voteRepository;
        this.postRepository = postRepository;
        this.authorizationService = authorizationService;
    }

    public void save(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new WykopCloneException("Post with ID - " + voteDto.getPostId() + " not found"));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authorizationService.getCurrentUser());
        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType()
                        .equals(voteDto.getVoteType())) {
            throw new WykopCloneException("You have already " + voteDto.getVoteType() + " for this post");
        }
        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteDto));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .user(authorizationService.getCurrentUser())
                .build();
    }
}
