package f3f.domain.message.application;


import f3f.domain.board.dao.BoardRepository;
import f3f.domain.message.dao.MessageRepository;
import f3f.domain.message.domain.Message;
import f3f.domain.message.exception.MessageNotFoundException;
import f3f.domain.message.exception.RecipientMissMatchException;
import f3f.domain.message.exception.SenderMissMatchException;
import f3f.domain.user.dao.MemberRepository;
import f3f.domain.user.domain.Member;
import f3f.domain.user.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {

    private final MemberRepository memberRepository;
    private final MessageRepository messageRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void sendMessage(Message message,long memberId){
        //발신자 검증
        Member sender = memberRepository.findById(message.getSender().getId())
                .orElseThrow(MemberNotFoundException::new);

        //로그인된 정보에서 넘어온 맴버 아이디와 다를 경우 예외
        if (sender.getId() == memberId){
            throw new SenderMissMatchException();
        }

        //수신자 검증
        memberRepository.findById(message.getRecipient().getId())
                .orElseThrow(MemberNotFoundException::new);

        messageRepository.save(message);
    }

//    @Transactional
//    public void deleteMessage(long memberId , long messageId){
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new MemberNotFoundException());
//
//        List<Message> sendMessageList = member.getSendMessageList();
//
//        for (Message message : sendMessageList) {
//            if (message.getId() == messageId){
//                messageRepository.deleteById(messageId);
//                return;
//            }
//        }
//    }

    @Transactional
    public Message updateDisplayStatus(long messageId, long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException());

        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new MessageNotFoundException());


        message.updateDisplayStatus(memberId);

        return message;
    }


//    @Transactional
//    public Message updateMessage(long messageId,long memberId ,Message message){
//        Message targetMessage = messageRepository.findById(messageId)
//                .orElseThrow();
//
//        if(targetMessage.getSender().getId() != messageId){
//            throw new SenderMissMatchException();
//        }
//
//        targetMessage.updateMessage(message);
//
//        return targetMessage;
//    }

    @Transactional(readOnly = true)
    public Message getMessageInfo(long messageId, Long memberId){
        Message message = messageRepository.findById(messageId).orElseThrow();

        if(message.getSender().getId() == messageId){
            throw new SenderMissMatchException();
        }

        return message;
    }

    @Transactional(readOnly = true)
    public List<Message> getSendMessageListByUserId(long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow();
        return member.getSendMessageList();
    }

    @Transactional(readOnly = true)
    public List<Message> getRecipientMessageListByUserId(long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow();
        return member.getReceptionMessageList();
    }
}
