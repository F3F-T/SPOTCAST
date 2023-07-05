package f3f.domain.board.dto;

import f3f.domain.board.domain.Board;
import f3f.domain.board.domain.ProfitStatus;
import f3f.domain.category.domain.Category;
import f3f.domain.category.dto.CategoryDTO;
import f3f.domain.publicModel.BoardType;
import f3f.domain.user.domain.Member;
import f3f.domain.user.dto.MemberDTO;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

public class BoardDTO {

    @Getter
    @NoArgsConstructor
    public static class SaveRequest {
        private String title;

        private String production;

        private String content;

        private long viewCount;

        //지원이메일
        private String supportEmail;

        //지원이메일
        private String phone;

        //페이
        private String pay;

        //참여기간
        private String participationPeriod;
        //참여 인원
        private int  recruitVolume;

        //모집분야
        private String recruitType;

        private ProfitStatus profitStatus;

        //작업일, 마감일
        private LocalDateTime regDate;

        @Enumerated(EnumType.STRING)
        private BoardType boardType;

        private Category category;

        private Member member;


        @Builder
        public SaveRequest(String title,String production, String content, long viewCount, String supportEmail, String phone, String pay,
                           String participationPeriod, int recruitVolume, String recruitType, ProfitStatus profitStatus,
                           LocalDateTime regDate, BoardType boardType, Category category, Member member) {
            this.title = title;
            this.content = content;
            this.viewCount = viewCount;
            this.production = production;
            this.supportEmail = supportEmail;
            this.phone = phone;
            this.pay = pay;
            this.participationPeriod = participationPeriod;
            this.recruitVolume = recruitVolume;
            this.recruitType = recruitType;
            this.profitStatus = profitStatus;
            this.regDate = regDate;
            this.boardType = boardType;
            this.category = category;
            this.member = member;
        }

        public Board toEntity() {
            return Board.builder()
                    .title(this.title)
                    .content(this.content)
                    .production(this.production)
                    .viewCount(this.viewCount)
                    .supportEmail(this.supportEmail)
                    .participationPeriod(this.participationPeriod)
                    .pay(this.pay)
                    .recruitType(this.recruitType)
                    .recruitVolume(this.recruitVolume)
                    .profitStatus(this.profitStatus)
                    .regDate(this.regDate)
                    .phone(this.phone)
                    .boardType(this.boardType)
                    .category(this.category)
                    .member(this.member)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class BoardInfoDTO {

        private long id;

        private String title;

        private String production;

        private String content;

        private long viewCount;

        private long commentCount;

        private long likeCount;

        //지원이메일
        private String supportEmail;

        //지원이메일
        private String phone;

        //페이
        private String pay;

        //참여기간
        private String participationPeriod;
        //참여 인원
        private int  recruitVolume;

        //모집분야
        private String recruitType;

        private ProfitStatus profitStatus;

        //작업일, 마감일
        private LocalDateTime regDate;

        private BoardType boardType;

        private CategoryDTO.CategoryInfo category;

        private MemberDTO.MemberBoardInfoResponseDto member;

        @Builder
        public BoardInfoDTO(long id, String title, String production,String content, long viewCount, long commentCount, long likeCount,
                            String supportEmail, String phone, String pay, String participationPeriod, int recruitVolume, String recruitType,
                            ProfitStatus profitStatus, LocalDateTime regDate, BoardType boardType, CategoryDTO.CategoryInfo category,
                            MemberDTO.MemberBoardInfoResponseDto member) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.production = production;
            this.viewCount = viewCount;
            this.commentCount = commentCount;
            this.likeCount = likeCount;
            this.supportEmail = supportEmail;
            this.phone = phone;
            this.pay = pay;
            this.participationPeriod = participationPeriod;
            this.recruitVolume = recruitVolume;
            this.recruitType = recruitType;
            this.profitStatus = profitStatus;
            this.regDate = regDate;
            this.boardType = boardType;
            this.category = category;
            this.member = member;
        }
    }
    @Getter
    public static class BoardDetailInfoDTO {

    }
    @Getter
    @Setter
    @NoArgsConstructor
    public static class BoardListResponse{

        private long id;

        private String title;

        private String content;

        private long viewCount;

        private long likeCount;

        private long commentCount;

        private String recruitType;

        private LocalDateTime regDate;

        @Enumerated(EnumType.STRING)
        private BoardType boardType;

        private CategoryDTO.CategoryInfo category;

        private String categoryName;

        private Long memberId;

        private String memberName;

        @Builder
        public BoardListResponse(long id, String title, String content, long viewCount, long likeCount, long commentCount,
                                 String recruitType, LocalDateTime regDate, BoardType boardType, CategoryDTO.CategoryInfo category, Long memberId, String memberName) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.viewCount = viewCount;
            this.likeCount = likeCount;
            this.commentCount = commentCount;
            this.recruitType = recruitType;
            this.regDate = regDate;
            this.boardType = boardType;
            this.category = category;
            this.memberId = memberId;
            this.memberName = memberName;
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchCondition {
        private String keyword;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getBoardCondition {
        private ProfitStatus profitStatus;
    }
}
