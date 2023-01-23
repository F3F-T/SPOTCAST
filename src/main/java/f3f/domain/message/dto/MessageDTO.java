package f3f.domain.message.dto;

import com.querydsl.core.annotations.QueryProjection;
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

        @QueryProjection
        public MessageResponseDto(long id, String content, Member sender, Member recipient) {
            this.id = id;
            this.content = content;
            this.sender = sender.toMessageMemberDTO();
            this.recipient = recipient.toMessageMemberDTO();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class MessageListResponseDto{
        private Long id;

        private String title;
        private String content;

        private Long memberId;
        private String memberEmail;

        private String memberName;

        @Builder
        public MessageListResponseDto(Long id, String title, String content, Long memberId, String memberEmail, String memberName) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.memberId = memberId;
            this.memberEmail = memberEmail;
            this.memberName = memberName;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class MessageRequestDto {

        private String title;

        private String content;
        private Member sender;
        private Member recipient;

        @Builder
        public MessageRequestDto(String title, String content, Member sender, Member recipient) {
            this.title = title;
            this.content = content;
            this.sender = sender;
            this.recipient = recipient;
        }





        public Message toEntity(){
            return Message.builder()
                    .title(this.title)
                    .content(this.content)
                    .recipient(this.recipient)
                    .sender(this.sender)
                    .recipientDisplayStatus(true)
                    .senderDisplayStatus(true)
                    .build();

        }
    }

}
