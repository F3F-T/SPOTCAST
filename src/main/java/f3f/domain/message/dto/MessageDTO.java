package f3f.domain.message.dto;

import f3f.domain.message.domain.Message;
import f3f.domain.user.domain.Member;
import f3f.domain.user.dto.MemberDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MessageDTO {

    @Getter
    @NoArgsConstructor
    public static class MessageResponseDto{
        private Long id;
        private String content;
        private MemberDTO.MemberBoardInfoResponseDto sender;
        private MemberDTO.MemberBoardInfoResponseDto recipient;

        @Builder
        public MessageResponseDto(long id, String content, MemberDTO.MemberBoardInfoResponseDto sender, MemberDTO.MemberBoardInfoResponseDto recipient) {
            this.id = id;
            this.content = content;
            this.sender = sender;
            this.recipient = recipient;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class MessageRequestDto {
        private String content;
        private Member sender;
        private Member recipient;

        @Builder
        public MessageRequestDto(String content, Member sender, Member recipient) {
            this.content = content;
            this.sender = sender;
            this.recipient = recipient;
        }

        public Message toEntity(){
            return Message.builder()
                    .content(this.content)
                    .recipient(this.recipient)
                    .sender(this.sender)
                    .recipientDisplayStatus(true)
                    .senderDisplayStatus(true)
                    .build();

        }
    }

}
