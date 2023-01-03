package f3f.domain.message.domain;


import f3f.domain.model.BaseTimeEntity;
import f3f.domain.user.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Message  extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "message_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id",insertable = false,updatable = false)
    private Member sender;

    private Boolean senderDisplayStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id",insertable = false,updatable = false)
    private Member recipient;

    private Boolean recipientDisplayStatus;

    @Builder
    public Message(Long id, String content, Member sender, Member recipient) {
        this.id = id;
        this.content = content;
        this.sender = sender;
        this.recipient = recipient;
    }

    public void updateMessage(Message message){
        this.content = message.getContent();
    }
    public void updateSenderDisplayStatus(Boolean senderDisplayStatus){
        this.senderDisplayStatus = senderDisplayStatus;
    }

    public void updateRecipientDisplayStatus(Boolean recipientDisplayStatus){
        this.recipientDisplayStatus  = recipientDisplayStatus;
    }
}
