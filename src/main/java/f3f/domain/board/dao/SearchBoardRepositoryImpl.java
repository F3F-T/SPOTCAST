package f3f.domain.board.dao;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import f3f.domain.board.domain.Board;
import f3f.domain.board.domain.ProfitStatus;
import f3f.domain.board.dto.BoardDTO;
import f3f.domain.board.dto.BoardDTO.BoardInfoDTO;
import f3f.domain.board.dto.BoardDTO.BoardListResponse;
import f3f.domain.publicModel.BoardType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static f3f.domain.board.domain.QBoard.board;
import static f3f.domain.comment.domain.QComment.comment;
import static f3f.domain.likes.domain.QLikes.*;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

    private JPAQueryFactory jpaQueryFactory;

    public SearchBoardRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Board.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<BoardListResponse> getBoardListInfoByCategoryId(String boardType, Long categoryId, String profitStatus, Pageable pageable) {
        JPQLQuery<BoardListResponse> query = querydsl().applyPagination(pageable, jpaQueryFactory
                .select(Projections.fields(BoardListResponse.class,
                        board.id,
                        board.title,
                        board.content,
                        board.recruitType,
                        board.viewCount,
                        board.regDate,
                        board.production,
                        board.profitStatus,
                        board.boardType,
                        board.category.id.as("categoryId"),
                        board.category.name,
                        board.member.id.as("memberId"),
                        board.member.name.as("memberName")))
                .from(board)
                .where(eqBoardType(boardType), eqCategoryId(categoryId), eqProfitStatus(profitStatus)));

        long size = query.fetchCount();
        List<BoardListResponse> result = query.fetch();
        return new PageImpl<>(result, pageable, size);
    }

    @Override
    public Page<BoardListResponse> getBoardList(String boardType, String profitStatus, Pageable pageable) {
        JPQLQuery<BoardListResponse> query = querydsl().applyPagination(pageable, jpaQueryFactory
                .select(Projections.fields(BoardListResponse.class,
                        board.id,
                        board.title,
                        board.content,
                        board.recruitType,
                        board.viewCount,
                        board.regDate,
                        board.production,
                        board.profitStatus,
                        board.boardType,
                        board.category.id.as("categoryId"),
                        board.category.name,
                        board.category.name.as("categoryName"),
                        board.member.id.as("memberId"),
                        board.member.name.as("memberName")))
                .from(board)
                .where(eqBoardType(boardType),eqProfitStatus(profitStatus)));

        long size = query.fetchCount();
        List<BoardListResponse> result = query.fetch();
        return new PageImpl<>(result, pageable, size);
    }

    @Override
    public Page<BoardListResponse> getBoardListInfoByMemberId(Long memberId, String boardType, String profitStatus, Pageable pageable) {
        JPQLQuery<BoardListResponse> query = querydsl().applyPagination(pageable, jpaQueryFactory
                .select(Projections.fields(BoardListResponse.class,
                        board.id,
                        board.title,
                        board.content,
                        board.recruitType,
                        board.viewCount,
                        board.regDate,
                        board.production,
                        board.profitStatus,
                        board.boardType,
                        board.category.id.as("categoryId"),
                        board.category.name,
                        board.category.name.as("categoryName"),
                        board.member.id.as("memberId"),
                        board.member.name.as("memberName")))
                .from(board)
                .where(board.member.id.eq(memberId),eqBoardType(boardType),eqProfitStatus(profitStatus)));

        long size = query.fetchCount();
        List<BoardListResponse> result = query.fetch();
        return new PageImpl<>(result, pageable, size);
    }

    @Override
    public Page<BoardInfoDTO> findAllBySearchCondition(BoardDTO.SearchCondition condition, Pageable pageable) {
        JPQLQuery<BoardInfoDTO> query = querydsl().applyPagination(pageable, jpaQueryFactory
                .select(Projections.fields(BoardInfoDTO.class,
                        board.title,
                        board.content,
                        board.viewCount,
                        board.boardType,
                        board.category,
                        board.member))
                .from(board)
                .leftJoin(board.likesList, likes)
                .fetchJoin()
                .leftJoin(board.comments, comment)
                .fetchJoin()
                .where(containsKeyword(condition.getKeyword())));

        long size = query.fetchCount();
        List<BoardInfoDTO> result = query.fetch();
        return new PageImpl<>(result, pageable, size);
    }

    private BooleanExpression containsKeyword(String keyword) {
        return (hasText(keyword)) ? board.title.containsIgnoreCase(keyword)
                .or(board.title.containsIgnoreCase(keyword))
                : null;
    }


    private BooleanExpression eqBoardType(String boardType) {
        if(boardType == null || boardType.equals("null")){
            return null;
        }
        return board.boardType.eq(BoardType.valueOf(boardType.toUpperCase(Locale.ROOT)));
    }

    private BooleanExpression eqCategoryId(Long categoryId) {
        if(categoryId == null || categoryId == 0){
            return null;
        }
        return board.category.id.eq(categoryId);
    }

    private BooleanExpression eqProfitStatus(String profitStatus) {
        if(profitStatus == null || profitStatus.equals("null")){
            return null;
        }
        return board.profitStatus.eq(ProfitStatus.valueOf(profitStatus));
    }
    private Querydsl querydsl() {
        return Objects.requireNonNull(getQuerydsl());
    }
}
