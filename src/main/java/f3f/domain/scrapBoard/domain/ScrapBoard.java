package f3f.domain.scrapBoard.domain;

import f3f.domain.board.domain.Board;
import f3f.domain.board.dto.BoardDTO;
import f3f.domain.scrap.domain.Scrap;
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
    @Column(name = "scrapPost_id")
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

    public BoardDTO.BoardInfoDTO toBoardInfoDTO(){
        return BoardDTO.BoardInfoDTO.builder()
                .title(this.board.getTitle())
                .content(this.board.getContent())
                .viewCount(this.board.getViewCount())
                .boardType(this.board.getBoardType())
                .category(this.board.getCategory())
                .member(this.board.getMember())
                .build();
    }


}
