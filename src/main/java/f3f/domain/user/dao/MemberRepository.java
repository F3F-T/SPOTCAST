package f3f.domain.user.dao;

import f3f.domain.user.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email);

    Optional<Member>findByIdAndPassword(Long id, String password);

    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

    boolean existsByPhone(String phone);

    boolean existsByEmailAndPassword(String email, String password);

    boolean existsByIdAndPassword(Long Id, String password);


    void deleteById(Long Id);

}