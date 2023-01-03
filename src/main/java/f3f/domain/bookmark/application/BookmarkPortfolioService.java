package f3f.domain.bookmark.application;

import f3f.domain.board.domain.Board;
import f3f.domain.board.exception.NotFoundBoardException;
import f3f.domain.bookmark.dao.BookmarkRepository;
import f3f.domain.bookmark.domain.Bookmark;
import f3f.domain.portfolio.dao.PortfolioBookmarkRepository;
import f3f.domain.portfolio.dao.PortfolioRepository;
import f3f.domain.portfolio.domain.Portfolio;
import f3f.domain.portfolio.domain.PortfolioBookmark;
import f3f.domain.portfolio.exception.PortfolioNotFoundException;
import f3f.domain.scrap.domain.Scrap;
import f3f.domain.scrap.domain.ScrapBoard;
import f3f.domain.scrap.exception.ScrapBoardMissMatchMemberException;
import f3f.domain.scrap.exception.ScrapNotFoundException;
import f3f.domain.user.dao.MemberRepository;
import f3f.domain.user.domain.Member;
import f3f.domain.user.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkPortfolioService {

    private final BookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;
    private final PortfolioBookmarkRepository portfolioBookmarkRepository;
    private final PortfolioRepository portfolioRepository;

    /**
     * 스크랩 추가
     *
     * @param memberId
     * @param bookmarkId
     * @param portfolioId
     */
    @Transactional
    public void saveBookmark(Long memberId, Long bookmarkId, Long portfolioId){

        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));

        Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
                .orElseThrow(() -> new NotFoundBoardException("존재하지 않는 게시글입니다."));

        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new PortfolioNotFoundException("존재하지 않는 포트폴리오입니다."));

        PortfolioBookmark portfolioBookmark = PortfolioBookmark.builder()
                .bookmark(bookmark)
                .portfolio(portfolio)
                .build();

        PortfolioBookmark save = portfolioBookmarkRepository.save(portfolioBookmark);

    }

    /**
     * 북마크 삭제
     *
     @param memberId
     @param bookmarkId
     @param portfolioId

     */
    @Transactional
    public void deleteScrap(Long memberId, Long bookmarkId, Long portfolioId) {

               /* 1. jparepository findbybookmarkIdandPortfolioId -> 인자값으로 아이디넣어주면 찾아줌
                2. 걔를 관계 테이블에서 지워준다
                * */

        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));

        Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
                .orElseThrow(() -> new NotFoundBoardException("존재하지 않는 게시글입니다."));

        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new PortfolioNotFoundException("존재하지 않는 포트폴리오입니다."));

        PortfolioBookmark portfolioBookmark = portfolioBookmarkRepository.findByBookmarkIdAndPortfolioId(bookmarkId, portfolioId)
                .orElseThrow(() -> new IllegalArgumentException());

        portfolioBookmarkRepository.delete(portfolioBookmark);


    }


    /**
     * 북마크 목록 조회
     * @param bookmarkId
     * @param memberId
     * @return
     */
    //멤버 아이디로 즐겨찾기를 누른 포트폴리오를 가지고와야함
    @Transactional(readOnly = true)
    public List<Portfolio> getBookmarkList(Long bookmarkId, Long memberId){
        // 멤버를 찾고
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));
        Bookmark findBookmark;
        for (Bookmark bookmark : findMember.getBookmarkList()) {
            if (bookmark.getId() == bookmarkId){
                findBookmark = bookmarkRepository.findById(bookmarkId)
                        .orElseThrow(() -> new NotFoundBoardException("존재하지 않는 게시글입니다."));
                break;
            }
        }
        List<Portfolio> portfolioList = portfolioBookmarkRepository.findByBookmarkId(bookmarkId);

        return portfolioList;
    }

}
