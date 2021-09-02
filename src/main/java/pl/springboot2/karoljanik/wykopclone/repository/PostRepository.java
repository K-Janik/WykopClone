package pl.springboot2.karoljanik.wykopclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.springboot2.karoljanik.wykopclone.model.Post;
import pl.springboot2.karoljanik.wykopclone.model.Tag;
import pl.springboot2.karoljanik.wykopclone.model.User;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByTag(Tag tag);

    List<Post> findByUser(User user);
}
