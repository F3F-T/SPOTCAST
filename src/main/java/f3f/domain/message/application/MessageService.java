package f3f.domain.message.application;


import f3f.domain.message.dao.MessageRepository;
import f3f.domain.message.dao.SearchMessageRepository;
import f3f.domain.message.domain.Message;
import f3f.domain.message.dto.MessageDTO;
import f3f.domain.user.dao.MemberRepository;
import f3f.domain.user.domain.Member;
import f3f.global.response.ErrorCode;
import f3f.global.response.GeneralException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Message sendMessage(MessageDTO.MessageRequestDto requestDto, long memberId) {
        //발신자 검증
        Member sender = memberRepository.findById(requestDto.getSender().getId())
                .orElseThrow(() -> new GeneralException(ErrorCode.NOTFOUND_MEMBER, "존재하지 않는 사용자입니다."));

        //로그인된 정보에서 넘어온 맴버 아이디와 다를 경우 예외
        if (!sender.getId().equals(memberId)) {
            throw new GeneralException(ErrorCode.MISMATCH_SENDER, "잘못된 발신 요청입니다.");
        }

        //수신자 검증
        memberRepository.findById(requestDto.getRecipient().getId())
                .orElseThrow(() -> new GeneralException(ErrorCode.NOTFOUND_MEMBER, "존재하지 않는 사용자입니다."));

        Message message = requestDto.toEntity();
        Message savedMessage = messageRepository.save(message);
        return savedMessage;
    }

    @Transactional
    public void updateDisplayStatus(long messageId, long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new GeneralException(ErrorCode.NOTFOUND_MEMBER, "존재하지 않는 사용자입니다.");
        }

        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOTFOUND_MESSAGE, "존재하지 않는 메세지입니다."));


        message.updateDisplayStatus(memberId);

    }

    @Transactional
    public void updateReadStatus(long messageId, long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new GeneralException(ErrorCode.NOTFOUND_MEMBER, "존재하지 않는 사용자입니다.");
        }

        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOTFOUND_MESSAGE, "존재하지 않는 메세지입니다."));


        message.updateReadStatus(memberId);

    }


    @Transactional(readOnly = true)
    public MessageDTO.MessageResponseDto getMessageInfo(long messageId, Long memberId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOTFOUND_MESSAGE, "존재하지 않는 메세지입니다."));

        if (!(message.getSender().getId().equals(memberId)
                ||message.getRecipient().getId().equals(memberId))) {
            throw new GeneralException(ErrorCode.MISMATCH_SENDER, "잘못된 조회 요청입니다.");
        }

        return message.toMessageDto();
    }

    @Transactional(readOnly = true)
    public Page<MessageDTO.MessageListResponseDto> getSendMessageListByUserId(long memberId, Pageable pageable) {
        if (!memberRepository.existsById(memberId)) {
            throw new GeneralException(ErrorCode.NOTFOUND_MEMBER, "존재하지 않는 사용자입니다.");
        }
        Page<MessageDTO.MessageListResponseDto> sendListByUserId = searchMessageRepository.getSendListByUserId(memberId, pageable);
        return sendListByUserId;
    }

    @Transactional(readOnly = true)
    public Page<MessageDTO.MessageListResponseDto> getRecipientMessageListByUserId(long memberId, Pageable pageable) {
        if(!memberRepository.existsById(memberId)){
            throw new GeneralException(ErrorCode.NOTFOUND_MEMBER,"존재하지 않는 사용자입니다.");
        }
        Page<MessageDTO.MessageListResponseDto> recipientListByUserId = searchMessageRepository.getRecipientListByUserId(memberId,pageable);


        return recipientListByUserId;
    }

    @Transactional(readOnly = true)
    public Page<MessageDTO.MessageListResponseDto> getRecipientUnReadMessageListByUserId(long memberId, Pageable pageable) {
        if(!memberRepository.existsById(memberId)){
            throw new GeneralException(ErrorCode.NOTFOUND_MEMBER,"존재하지 않는 사용자입니다.");
        }
        Page<MessageDTO.MessageListResponseDto> recipientListByUserId = searchMessageRepository.getRecipientUnReadListByUserId(memberId,pageable);


        return recipientListByUserId;

    }
}
