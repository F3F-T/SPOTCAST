package f3f.domain.board.domain;

import f3f.domain.BoardImage.domain.BoardImage;
import f3f.domain.board.dto.BoardDTO;
import f3f.domain.category.domain.Category;
import f3f.domain.category.dto.CategoryDTO;
import f3f.domain.comment.domain.Comment;
import f3f.domain.likes.domain.Likes;
import f3f.domain.publicModel.BaseTimeEntity;
import f3f.domain.publicModel.BoardType;
import f3f.domain.scrapBoard.domain.ScrapBoard;
import f3f.domain.user.domain.Member;
import f3f.domain.user.dto.MemberDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    //지원이메일
    private String supportEmail;

    //지원이메일
    private String phone;

    //페이
    private long pay;

    //참여기간
    private int participationPeriod;
    //참여 인원
    private int  recruitVolume;

    //모집분야
    private String recruitType;

    @Enumerated(EnumType.STRING)
    private ProfitStatus profitStatus;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;
    //작업일, 마감일
    private LocalDateTime regDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;


    @OneToMany( fetch = FetchType.LAZY , mappedBy = "board")
    private List<BoardImage> boardImageList = new ArrayList<>();

    @OneToMany( fetch = FetchType.LAZY, mappedBy = "board")
    private List<ScrapBoard> scrapBoardList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY , mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY , mappedBy = "board")
    private List<Likes> likesList = new ArrayList<>();

    @Builder
    public Board(Long id, String title, String content, long viewCount, String supportEmail, String phone,
                 long pay, int participationPeriod, int recruitVolume, String recruitType, ProfitStatus profitStatus,
                 BoardType boardType, LocalDateTime regDate, Category category, Member member) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.supportEmail = supportEmail;
        this.phone = phone;
        this.pay = pay;
        this.participationPeriod = participationPeriod;
        this.recruitVolume = recruitVolume;
        this.recruitType = recruitType;
        this.profitStatus = profitStatus;
        this.boardType = boardType;
        this.regDate = regDate;
        this.category = category;
        this.member = member;
    }

    public BoardDTO.BoardListResponse toBoardListResponseInfo(){
        return BoardDTO.BoardListResponse.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .viewCount(this.viewCount)
                .boardType(this.boardType)
                .recruitType(this.recruitType)
                .regDate(this.regDate)
                .likeCount(this.likesList.size())
                .commentCount(this.comments.size())
                .category(changeCategoryBoardInfo(this.category))
                .member(changeMemberBoardInfoDTO(this.member))
                .build();
    }

    public BoardDTO.BoardInfoDTO toBoardInfoDTO(){
        return BoardDTO.BoardInfoDTO.builder()
                .title(this.title)
                .content(this.content)
                .viewCount(this.viewCount)
                .boardType(this.boardType)
                .supportEmail(this.supportEmail)
                .phone(this.phone)
                .participationPeriod(this.participationPeriod)
                .recruitType(this.recruitType)
                .recruitVolume(this.recruitVolume)
                .regDate(this.regDate)
                .category(this.category)
                .likeCount(this.likesList.size())
                .commentCount(this.comments.size())
                .member(changeMemberBoardInfoDTO(this.member))
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

    public void updateBoard(BoardDTO.SaveRequest request){
        this.title = request.getTitle();
        this.content = request.getContent();
        this.viewCount = request.getViewCount();
        this.boardType = request.getBoardType();
        this.pay = request.getPay();
        this.phone = request.getPhone();
        this.regDate = request.getRegDate();
        this.participationPeriod = request.getParticipationPeriod();
        this.supportEmail = request.getSupportEmail();
        this.profitStatus = request.getProfitStatus();
        this.recruitVolume = request.getRecruitVolume();
    }

    public void updateViewCount(Board board){
        board.viewCount += 1;
    }
}
