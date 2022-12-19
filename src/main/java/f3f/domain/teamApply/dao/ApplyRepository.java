package f3f.domain.teamApply.dao;

import f3f.domain.teamApply.domain.Apply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyRepository extends JpaRepository<Apply,Long> {
}
