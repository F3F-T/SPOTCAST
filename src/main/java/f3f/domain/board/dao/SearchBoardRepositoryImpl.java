package f3f.domain.board.dao;

import com.querydsl.core.Query;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import f3f.domain.board.dto.BoardDTO;
import f3f.domain.board.dto.BoardDTO.BoardInfoDTO;
import f3f.domain.board.dto.BoardDTO.BoardListResponse;
import f3f.domain.publicModel.BoardType;
import f3f.domain.publicModel.SortType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static f3f.domain.BoardImage.domain.QBoardImage.boardImage;
import static f3f.domain.board.domain.QBoard.board;
import static f3f.domain.category.domain.QCategory.category;
import static f3f.domain.comment.domain.QComment.comment;
import static f3f.domain.likes.domain.QLikes.*;
import static f3f.domain.user.domain.QMember.member;
import static org.springframework.util.StringUtils.hasText;
import static org.springframework.util.StringUtils.parseLocale;

@RequiredArgsConstructor
public class SearchBoardRepositoryImpl implements SearchBoardRepository{

    private JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<BoardListResponse> getBoardListInfoByCategoryId(long categoryId, BoardType boardType, Pageable pageable) {
        QueryResults<BoardListResponse> result = jpaQueryFactory
                .select(Projections.fields(BoardListResponse.class,
                        board.title,
                        board.content,
                        board.recruitType,
                        board.viewCount,
                        board.regDate,
                        board.boardType,
                        board.category.id,
                        board.category.name,
                        board.member,
                        board.comments.size().as("commentCount"),
                        board.likesList.size().as("likesCount")))
                .from(board)
                .leftJoin(board.likesList, likes)
                .fetchJoin()
                .leftJoin(board.comments, comment)
                .fetchJoin()
                .leftJoin(board.member,member)
                .fetchJoin()
                .leftJoin(board.category,category)
                .fetchJoin()
                .leftJoin(board.boardImageList, boardImage)
                .fetchJoin()
                .where(board.category.id.eq(categoryId).and(board.boardType.eq(boardType)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<BoardListResponse> products = result.getResults();
        long total = result.getTotal();

        return new PageImpl<>(products,pageable,total);
    }

    @Override
    public Page<BoardListResponse> getBoardListInfoByUserId(long memberId, BoardType boardType, Pageable pageable) {
        QueryResults<BoardListResponse> result = jpaQueryFactory
                .select(Projections.fields(BoardListResponse.class,
                        board.title,
                        board.content,
                        board.recruitType,
                        board.viewCount,
                        board.regDate,
                        board.boardType,
                        board.category.id,
                        board.category.name,
                        board.member,
                        board.comments.size().as("commentCount"),
                        board.likesList.size().as("likesCount")))
                .from(board)
                .leftJoin(board.likesList, likes)
                .fetchJoin()
                .leftJoin(board.comments, comment)
                .fetchJoin()
                .leftJoin(board.member,member)
                .fetchJoin()
                .leftJoin(board.category,category)
                .fetchJoin()
                .leftJoin(board.boardImageList, boardImage)
                .fetchJoin()
                .where(board.member.id.eq(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<BoardListResponse> products = result.getResults();
        long total = result.getTotal();

        return new PageImpl<>(products,pageable,total);
    }

    @Override
    public Page<BoardInfoDTO> findAllBySearchCondition(BoardDTO.SearchCondition condition, Pageable pageable) {
        QueryResults<BoardInfoDTO> results = jpaQueryFactory
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
                .where(containsKeyword(condition.getKeyword()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<BoardInfoDTO> boardInfoDTOList = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(boardInfoDTOList,pageable,total);
    }

    private BooleanExpression containsKeyword(String keyword) {
        return (hasText(keyword)) ? board.title.containsIgnoreCase(keyword)
                .or(board.title.containsIgnoreCase(keyword))
                : null;
    }


    private BooleanExpression boardTypeEqual(BoardType boardType){
        return null;
    }

}
