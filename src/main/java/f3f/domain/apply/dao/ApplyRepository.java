package f3f.domain.apply.dao;

import f3f.domain.apply.domain.Apply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplyRepository extends JpaRepository<Apply,Long> {
    List<Apply> findByRecruiterId(Long recruiterId);
    List<Apply> findByVolunteerId(Long volunteerId);
}
