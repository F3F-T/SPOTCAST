package f3f.domain.scrap.dao;

import f3f.domain.scrap.domain.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    List<Scrap> findByMemberId(Long memberId);

}
