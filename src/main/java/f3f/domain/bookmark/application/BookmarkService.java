package f3f.domain.bookmark.application;

import f3f.domain.bookmark.dao.BookmarkRepository;
import f3f.domain.model.BaseTimeEntity;
import f3f.domain.portfolio.dao.PortfolioRepository;
import f3f.domain.user.dao.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookmarkService extends BaseTimeEntity {

    private final BookmarkRepository bookmarkRepository;
    private final PortfolioRepository portfolioRepository;
    private final MemberRepository memberRepository;




}
