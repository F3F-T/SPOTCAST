package f3f.domain.apply.dto;

import f3f.domain.board.dto.BoardDTO;
import f3f.domain.user.dto.MemberDTO;
import lombok.Builder;
import lombok.Getter;

public class ApplyDTO {

    @Getter
    public static class ApplyInfo{
        private long applyId;
        private MemberDTO.MemberBoardInfoResponseDto recruiter;
        private MemberDTO.MemberBoardInfoResponseDto volunteer;
        private BoardDTO.BoardInfoDTO board;

        @Builder
        public ApplyInfo(long applyId, MemberDTO.MemberBoardInfoResponseDto recruiter, MemberDTO.MemberBoardInfoResponseDto volunteer, BoardDTO.BoardInfoDTO board) {
            this.applyId = applyId;
            this.recruiter = recruiter;
            this.volunteer = volunteer;
            this.board = board;
        }
    }

    @Getter
    public static class SaveRequest{
        private String application;
        private long volunteerId;
        private long recruiterId;
        private long boardId;

        @Builder
        public SaveRequest(String application, long volunteerId, long recruiterId, long boardId) {
            this.application = application;
            this.volunteerId = volunteerId;
            this.recruiterId = recruiterId;
            this.boardId = boardId;
        }
    }

}
