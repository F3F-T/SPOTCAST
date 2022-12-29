package f3f.domain.message.api;

import f3f.domain.message.application.MessageService;
import f3f.domain.message.domain.Message;
import f3f.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController(value = "/message")
@Slf4j
public class MessageController {

    private final MessageService messageService;

    @PostMapping(value = "/send")
    public void sendMessage(@RequestBody Message message){

        Long memberId = SecurityUtil.getCurrentMemberId();
        messageService.sendMessage(message,memberId);
    }

    @DeleteMapping(value = "/{messageId}")
    public void cancelMessage(@PathVariable long messageId){
        Long memberId = SecurityUtil.getCurrentMemberId();
        messageService.deleteMessage(memberId,messageId);
    }

    @PutMapping(value = "/{messageId}/update")
    public Message updateMessage(@PathVariable long messageId, @RequestBody Message message){
        Long memberId = SecurityUtil.getCurrentMemberId();
        return messageService.updateMessage(messageId,memberId,message);
    }

    @GetMapping(value = "/{messageId}")
    public Message getMessageInfo(@PathVariable long messageId){
        Long memberId = SecurityUtil.getCurrentMemberId();
        return messageService.getMessageInfo(messageId , memberId);
    }

    @GetMapping(value = "/sender")
    public List<Message> getSendMessageListByUserId(){
        Long memberId = SecurityUtil.getCurrentMemberId();
        return messageService.getSendMessageListByUserId(memberId);
    }

    @GetMapping(value = "/recipient")
    public List<Message> getRecipientMessageListByUserId(){
        Long memberId = SecurityUtil.getCurrentMemberId();
        return messageService.getRecipientMessageListByUserId(memberId);
    }
}
