package f3f.domain.board.dao;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import f3f.domain.board.dto.BoardDTO;
import f3f.domain.board.dto.BoardDTO.BoardInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static f3f.domain.board.domain.QBoard.board;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class SearchBoardRepositoryImpl implements SearchBoardRepository{

    private JPAQueryFactory jpaQueryFactory;

    @Override
    public List<BoardInfoDTO> getBoardListByCategoryId(long categoryId) {
        List<BoardInfoDTO> result = jpaQueryFactory
                .select(Projections.fields(BoardInfoDTO.class,
                        board.title,
                        board.content,
                        board.viewCount,
                        board.boardType,
                        board.category.id,
                        board.category.name,
                        board.member))
                .from(board)
                .where(board.category.id.eq(categoryId))
                .fetch();
        return result;
    }

    @Override
    public List<BoardInfoDTO> getBoardListByUserId(long memberId) {
        List<BoardInfoDTO> result = jpaQueryFactory
                .select(Projections.fields(BoardInfoDTO.class,
                        board.title,
                        board.content,
                        board.viewCount,
                        board.boardType,
                        board.category.id,
                        board.category.name,
                        board.member))
                .from(board)
                .where(board.category.id.eq(memberId))
                .fetch();
        return result;
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
                .join(board.member)
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

}
