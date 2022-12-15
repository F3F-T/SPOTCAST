package f3f.domain.user.dao;

import f3f.domain.user.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member>findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

    boolean existsByEmailAndPassword(String email, String password);

    void deleteByEmail(String email);
}