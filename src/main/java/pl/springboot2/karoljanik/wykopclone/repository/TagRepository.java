package pl.springboot2.karoljanik.wykopclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.springboot2.karoljanik.wykopclone.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
