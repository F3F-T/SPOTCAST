package f3f.domain.message.dto;

import f3f.domain.user.dto.MemberDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MessageDTO {

    @Getter
    @NoArgsConstructor
    public static class MessageResponseDto{
        private long id;
        private String content;
        private MemberDTO.MemberDataResponseDto sender;
        private MemberDTO.MemberDataResponseDto recipient;

        @Builder
        public MessageResponseDto(long id, String content, MemberDTO.MemberDataResponseDto sender, MemberDTO.MemberDataResponseDto recipient) {
            this.id = id;
            this.content = content;
            this.sender = sender;
            this.recipient = recipient;
        }
    }
}
