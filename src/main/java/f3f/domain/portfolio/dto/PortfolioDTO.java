package f3f.domain.portfolio.dto;

import f3f.domain.board.dto.BoardDTO.BoardInfoDTO;
import f3f.domain.user.dto.MemberDTO.OtherMemberInfoResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PortfolioDTO {


    @Getter
    @NoArgsConstructor
    public static class SaveRequest{

        private Long id;
        private Long boardId;
        private Long memberId;
        private int order;

        @Builder
        public SaveRequest(Long id, Long boardId, Long memberId, int order) {
            this.id = id;
            this.boardId = boardId;
            this.memberId = memberId;
            this.order = order;
        }
    }


    @Getter
    @NoArgsConstructor
    public static class PortfolioInfo{

        private Long id;
        private BoardInfoDTO boardInfoDTO;
        private OtherMemberInfoResponseDto member;
        private int order;

        @Builder
        public PortfolioInfo(Long id, BoardInfoDTO boardInfoDTO,
                             OtherMemberInfoResponseDto member, int order) {
            this.id = id;
            this.boardInfoDTO = boardInfoDTO;
            this.member = member;
            this.order = order;
        }
    }

}
