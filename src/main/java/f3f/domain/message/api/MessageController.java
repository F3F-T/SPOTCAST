package f3f.domain.message.api;

import f3f.domain.message.application.MessageService;
import f3f.domain.message.domain.Message;
import f3f.domain.message.dto.MessageDTO;
import f3f.global.response.ResultDataResponseDTO;
import f3f.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
@Slf4j
/**
 * 구현기능
 * 1. 메세지 전송
 * 2. 메세지 삭제(보이는 상태만 바꾸는 것)
 * 3. 메세지 내용 조회
 * 4. 메세지 보낸 목록 조회
 * 5. 메세지 받은 목록 조회
 */
public class MessageController {

    private final MessageService messageService;

    @PostMapping(value = "/send")
    public ResultDataResponseDTO sendMessage(@RequestBody MessageDTO.MessageRequestDto requestDto){

        log.info(requestDto.toString());
        Long memberId = SecurityUtil.getCurrentMemberId();
        messageService.sendMessage(requestDto,memberId);

        return ResultDataResponseDTO.empty();
    }

    @PatchMapping(value = "/{messageId}")
    public ResultDataResponseDTO concealMessageDisplay(@PathVariable long messageId){
        Long memberId = SecurityUtil.getCurrentMemberId();
        messageService.updateDisplayStatus(messageId,memberId);

        return ResultDataResponseDTO.empty();
    }

    @GetMapping(value = "/{messageId}")
    public ResultDataResponseDTO<MessageDTO.MessageResponseDto> getMessageInfo(@PathVariable long messageId){
        Long memberId = SecurityUtil.getCurrentMemberId();
        MessageDTO.MessageResponseDto messageInfo = messageService.getMessageInfo(messageId, memberId);
        return ResultDataResponseDTO.of(messageInfo);
    }

    @GetMapping(value = "/sender")
    public ResultDataResponseDTO<Page<MessageDTO.MessageListResponseDto>> getSendMessageListByUserId(Pageable pageable){
        Long memberId = SecurityUtil.getCurrentMemberId();
        Page<MessageDTO.MessageListResponseDto> sendMessageList = messageService.getSendMessageListByUserId(memberId,pageable);
        return ResultDataResponseDTO.of(sendMessageList);
    }


    @GetMapping(value = "/recipient")
    public ResultDataResponseDTO<Page<MessageDTO.MessageListResponseDto>> getRecipientMessageListByUserId(Pageable pageable){
        Long memberId = SecurityUtil.getCurrentMemberId();
        Page<MessageDTO.MessageListResponseDto> recipientMessageList = messageService.getRecipientMessageListByUserId(memberId,pageable);
        return ResultDataResponseDTO.of(recipientMessageList);
    }
}
