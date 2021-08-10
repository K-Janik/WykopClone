package pl.springboot2.karoljanik.wykopclone.model;

public enum VoteType {
    UPVOTE(1), DOWNVOTE(-1),
    ;

    VoteType(int direction) {
    }
}
