package f3f.domain.message.model;


import f3f.domain.model.BaseTimeEntity;
import f3f.domain.user.model.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Message  extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "message_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    private User recipient;
}
