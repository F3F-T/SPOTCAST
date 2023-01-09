package f3f.domain.message.application;


import com.sun.xml.bind.v2.TODO;
import f3f.domain.board.dao.BoardRepository;
import f3f.domain.board.dto.BoardDTO;
import f3f.domain.message.dao.MessageRepository;
import f3f.domain.message.dao.SearchMessageRepository;
import f3f.domain.message.domain.Message;
import f3f.domain.message.dto.MessageDTO;
import f3f.domain.message.exception.MessageNotFoundException;
import f3f.domain.message.exception.RecipientMissMatchException;
import f3f.domain.message.exception.SenderMissMatchException;
import f3f.domain.scrapBoard.domain.ScrapBoard;
import f3f.domain.user.dao.MemberRepository;
import f3f.domain.user.domain.Member;
import f3f.domain.user.exception.MemberNotFoundException;
import f3f.global.response.ErrorCode;
import f3f.global.response.GeneralException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {

    private final MemberRepository memberRepository;
    private final MessageRepository messageRepository;

    private final SearchMessageRepository searchMessageRepository;

    @Transactional
    public void sendMessage(Message message,long memberId){
        //발신자 검증
        Member sender = memberRepository.findById(message.getSender().getId())
                .orElseThrow(() ->new GeneralException(ErrorCode.NOTFOUND_MEMBER,"존재하지 않는 사용자입니다."));

        //로그인된 정보에서 넘어온 맴버 아이디와 다를 경우 예외
        if (!sender.getId().equals(memberId)){
            throw new GeneralException(ErrorCode.MISMATCH_SENDER,"잘못된 발신 요청입니다.");
        }

        //수신자 검증
        memberRepository.findById(message.getRecipient().getId())
                .orElseThrow(() ->new GeneralException(ErrorCode.NOTFOUND_MEMBER,"존재하지 않는 사용자입니다."));

        messageRepository.save(message);
    }

    @Transactional
    public Message updateDisplayStatus(long messageId, long memberId){
        memberRepository.findById(memberId)
                .orElseThrow(() ->new GeneralException(ErrorCode.NOTFOUND_MEMBER,"존재하지 않는 사용자입니다."));

        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOTFOUND_MESSAGE,"존재하지 않는 메세지입니다."));


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
    public MessageDTO.MessageResponseDto getMessageInfo(long messageId, Long memberId){
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() ->new GeneralException(ErrorCode.NOTFOUND_MEMBER,"존재하지 않는 사용자입니다."));

        if(!message.getSender().getId().equals(memberId)){
            throw new GeneralException(ErrorCode.MISMATCH_SENDER,"잘못된 발신 요청입니다.");
        }

        return message.toMessageDto();
    }

    //TODO 리스트 가져오는 쿼리 작성
    @Transactional(readOnly = true)
    public List<MessageDTO.MessageResponseDto> getSendMessageListByUserId(long memberId){
        if(!memberRepository.existsById(memberId)){
            throw new GeneralException(ErrorCode.NOTFOUND_MEMBER,"존재하지 않는 사용자입니다.");
        }
        List<MessageDTO.MessageResponseDto> sendListByCategoryId = searchMessageRepository.getSendListByCategoryId(memberId);

        return sendListByCategoryId;
    }

    @Transactional(readOnly = true)
    public List<MessageDTO.MessageResponseDto> getRecipientMessageListByUserId(long memberId){
        if(!memberRepository.existsById(memberId)){
            throw new GeneralException(ErrorCode.NOTFOUND_MEMBER,"존재하지 않는 사용자입니다.");
        }
        List<MessageDTO.MessageResponseDto> recipientListByUserId = searchMessageRepository.getRecipientListByUserId(memberId);
        return recipientListByUserId;
    }
}
