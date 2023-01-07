package f3f.domain.portfolio.application;

import f3f.domain.board.dao.BoardRepository;
import f3f.domain.board.domain.Board;
import f3f.domain.portfolio.dao.PortfolioRepository;
import f3f.domain.portfolio.domain.Portfolio;
import f3f.domain.portfolio.dto.PortfolioDTO.SaveRequest;
import f3f.domain.user.dao.MemberRepository;
import f3f.domain.user.domain.Member;
import f3f.global.response.ErrorCode;
import f3f.global.response.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PortfolioService {


    private final PortfolioRepository portfolioRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;


    public SaveRequest savePortfolio(SaveRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new GeneralException(ErrorCode.NOTFOUND_MEMBER, "유효하지 않은 유저입니다."));
        Board board = boardRepository.findById(request.getBoardId())
                .orElseThrow(() -> new GeneralException(ErrorCode.NOTFOUND_BOARD, "유효하지 않는 게시글 입니다."));

        if (member.getId() != board.getMember().getId()) {
            throw new GeneralException(ErrorCode.VALIDATION_ERROR, "본인 게시글만 등록가능합니다.");
        }

        Portfolio portfolio = Portfolio.builder()
                .board(board)
                .member(member)
                .order(request.getOrder())
                .build();

        portfolioRepository.save(portfolio);

        return request;
    }
//    public SaveRequest updatePortfolio(long memberId, SaveRequest request) {
//
//        List<Portfolio> portfolioListByMemberId = portfolioRepository.findByMemberId(memberId);
//        for (Portfolio portfolio : portfolioListByMemberId) {
//            portfolioRepository.deleteById(portfolio.getId());
//        }
//        Member member = memberRepository.findById(request.getMemberId())
//                .orElseThrow(() -> new GeneralException(ErrorCode.NOTFOUND_MEMBER, "유효하지 않은 유저입니다."));
//        Board board = boardRepository.findById(request.getBoardId())
//                .orElseThrow(() -> new GeneralException(ErrorCode.NOTFOUND_BOARD, "유효하지 않는 게시글 입니다."));
//
//        if (member.getId() != board.getMember().getId()) {
//            throw new GeneralException(ErrorCode.VALIDATION_ERROR, "본인 게시글만 등록가능합니다.");
//        }
//
//        Portfolio portfolio = Portfolio.builder()
//                .board(board)
//                .member(member)
//                .order(request.getOrder())
//                .build();
//
//        portfolioRepository.save(portfolio);
//        return request;
//    }


    public long deletePortfolio(long portfolioId, long memberId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND, "존재하지 않는 포트폴리오입니다."));

        if (portfolio.getMember().getId() != memberId) {
            throw new GeneralException(ErrorCode.VALIDATION_ERROR, "본인 게시글만 포트폴리오에서 삭제가 가능합니다.");
        }
        portfolioRepository.deleteById(portfolio.getId());
        return portfolioId;
    }

    public long getPortfolioListByMemberId(long memberId) {
        return 0;
    }
}
