package f3f.domain.user.dao;

import f3f.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User>findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
}
