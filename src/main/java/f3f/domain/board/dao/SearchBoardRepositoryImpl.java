package f3f.domain.board.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import f3f.domain.board.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class SearchBoardRepositoryImpl implements SearchBoardRepository{

    private JPAQueryFactory jpaQueryFactory;

    @Override
    public List<BoardDTO.BoardInfoDTO> getBoardListByCategoryId(long categoryId) {
        return null;
    }

    @Override
    public List<BoardDTO.BoardInfoDTO> getBoardListByUserId(long userId) {
        return null;
    }

    @Override
    public Page<BoardDTO.BoardInfoDTO> findAllBySearchCondition(BoardDTO.SearchCondition condition, Pageable pageable) {
        return null;
    }
}
