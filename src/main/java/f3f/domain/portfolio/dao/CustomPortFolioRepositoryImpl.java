package f3f.domain.portfolio.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import f3f.domain.portfolio.dto.PortfolioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomPortFolioRepositoryImpl implements CustomPortfolioRepository{

    private JPAQueryFactory jpaQueryFactory;

    //memberId로 포폴 리스트 구성
    @Override
    public List<PortfolioDTO.PortfolioInfo> getPortfolioListByMemberId(long memberId) {
        return null;
    }
}
