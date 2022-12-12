package f3f.domain.user.dao;

import f3f.domain.user.domain.User;
import f3f.domain.user.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User>findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

    boolean existsByEmailAndPassword(String email, String password);

    Optional<List<UserDTO.EmailListResponse>> findByNameAndPhone(String name, String phone);
}