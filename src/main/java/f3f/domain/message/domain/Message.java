package f3f.domain.message.domain;


import f3f.domain.message.dto.MessageDTO;
import f3f.domain.publicModel.BaseTimeEntity;
import f3f.domain.user.domain.Member;
import f3f.domain.user.dto.MemberDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Message extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "message_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", insertable = false, updatable = false, referencedColumnName = "member_id")
    private Member sender;

    private Boolean senderDisplayStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id", insertable = false, updatable = false, referencedColumnName = "member_id")
    private Member recipient;

    private Boolean recipientDisplayStatus;

    @Builder
    public Message(Long id, String content, Member sender, Boolean senderDisplayStatus, Member recipient, Boolean recipientDisplayStatus) {
        this.id = id;
        this.content = content;
        this.sender = sender;
        this.senderDisplayStatus = senderDisplayStatus;
        this.recipient = recipient;
        this.recipientDisplayStatus = recipientDisplayStatus;
    }


    public void updateDisplayStatus(Long memberId) {
        if (memberId == sender.getId()) {
            this.senderDisplayStatus = false;
        } else if (memberId == recipient.getId()) {
            this.recipientDisplayStatus = false;
        }

    }

    public MessageDTO.MessageResponseDto toMessageDto() {
        return MessageDTO.MessageResponseDto.builder()
                .id(this.getId())
                .content(this.content)
                .recipient(this.recipient.toMessageMemberDTO())
                .sender(this.sender.toMessageMemberDTO())
                .build();

    }
}
