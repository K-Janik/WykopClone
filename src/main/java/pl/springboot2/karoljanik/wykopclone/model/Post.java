package pl.springboot2.karoljanik.wykopclone.model;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    @NotBlank
    private String postName;
    @Nullable
    private String url;
    @Nullable
    @Lob
    private String description;
    private Integer voteCount = 0;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
    private Instant createDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tagId", referencedColumnName = "id")
    private Tag tag;

    public Post(Long postId, String postName, @Nullable String url, @Nullable String description, Integer voteCount, Instant createDate) {
        this.postId = postId;
        this.postName = postName;
        this.url = url;
        this.description = description;
        this.voteCount = voteCount;
        this.createDate = createDate;
    }

    public Post() {
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    @Nullable
    public String getUrl() {
        return url;
    }

    public void setUrl(@Nullable String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
