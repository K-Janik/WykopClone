package pl.springboot2.karoljanik.wykopclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.springboot2.karoljanik.wykopclone.model.RefreshToken;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository <RefreshToken, Long>{
    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
}
