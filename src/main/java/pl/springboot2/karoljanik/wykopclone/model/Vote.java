package pl.springboot2.karoljanik.wykopclone.model;

import javax.persistence.*;

@Entity
@Table(name = "votes")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;
    private VoteType voteType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    private Post post;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    public Vote(Long voteId, VoteType voteType) {
        this.voteId = voteId;
        this.voteType = voteType;
    }

    public Vote() {
    }

    public Long getVoteId() {
        return voteId;
    }

    public void setVoteId(Long voteId) {
        this.voteId = voteId;
    }

    public VoteType getVoteType() {
        return voteType;
    }

    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
