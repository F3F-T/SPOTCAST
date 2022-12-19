package f3f.domain.board.domain;

import f3f.domain.board.dto.BoardDTO;
import f3f.domain.category.domain.Category;
import f3f.domain.comment.domain.Comment;
import f3f.domain.likes.domain.Likes;
import f3f.domain.model.BaseTimeEntity;
import f3f.domain.model.BoardType;
import f3f.domain.scrap.domain.ScrapBoard;
import f3f.domain.user.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String title;

    private String content;

    private long viewCount;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<ScrapBoard> scrapBoardList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY , mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY , mappedBy = "board")
    private List<Likes> likesList = new ArrayList<>();

    @Builder
    public Board(Long id, String title, String content, long viewCount, BoardType boardType, Category category,
                 Member member) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.boardType = boardType;
        this.category = category;
        this.member = member;
    }


    public BoardDTO.BoardInfoDTO toBoardInfoDTO(){
        return BoardDTO.BoardInfoDTO.builder()
                .title(this.title)
                .content(this.content)
                .viewCount(this.viewCount)
                .boardType(this.boardType)
                .category(this.category)
                .member(this.member)
                .build();
    }

    public void updateBoard(BoardDTO.SaveRequest request){
        this.title = request.getTitle();
        this.content = request.getContent();
        this.viewCount = request.getViewCount();
        this.boardType = request.getBoardType();
        this.category = request.getCategory();
    }
}
