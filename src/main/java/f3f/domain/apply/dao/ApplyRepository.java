package f3f.domain.apply.dao;

import f3f.domain.apply.domain.Apply;
import f3f.domain.apply.dto.ApplyDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplyRepository extends JpaRepository<Apply,Long> {
    List<ApplyDTO.ApplyInfo> findByRecruiterId(long recruiterId);
    List<ApplyDTO.ApplyInfo> findByVolunteerId(long volunteerId);
}
