package pl.springboot2.karoljanik.wykopclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.springboot2.karoljanik.wykopclone.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
}
