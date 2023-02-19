package f3f.domain.scrapBoard.domain;

import f3f.domain.board.domain.Board;
import f3f.domain.board.dto.BoardDTO;
import f3f.domain.category.domain.Category;
import f3f.domain.category.dto.CategoryDTO;
import f3f.domain.scrap.domain.Scrap;
import f3f.domain.user.domain.Member;
import f3f.domain.user.dto.MemberDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class ScrapBoard {

    @Id
    @GeneratedValue
    @Column(name = "scrapBoard_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scrap_id")
    private Scrap scrap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;


    @Builder
    public ScrapBoard(Long id, Scrap scrap, Board board) {
        this.id = id;
        this.scrap = scrap;
        this.board = board;
    }

    public BoardDTO.BoardInfoDTO toBoardInfoByScrapBoxDTO(){
        return BoardDTO.BoardInfoDTO.builder()
                .title(this.board.getTitle())
                .content(this.board.getContent())
                .viewCount(this.board.getViewCount())
                .boardType(this.board.getBoardType())
                .category(changeCategoryBoardInfo(this.board.getCategory()))
                .member(changeMemberBoardInfoDTO(this.board.getMember()))
                .build();
    }

    private CategoryDTO.CategoryInfo changeCategoryBoardInfo(Category category) {
        return CategoryDTO.CategoryInfo.builder()
                .categoryId(category.getId())
                .name(category.getName())
                .depth(category.getDepth())
                .build();
    }

    private MemberDTO.MemberBoardInfoResponseDto changeMemberBoardInfoDTO(Member member) {
        return MemberDTO.MemberBoardInfoResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }


}
