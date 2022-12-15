package f3f.domain.message.application;


import f3f.domain.message.dao.MessageRepository;
import f3f.domain.user.dao.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {

    private final MemberRepository memberRepository;
    private final MessageRepository messageRepository;


    public void sendMessage(){

    }

    public void deleteMessage(){

    }

    public void updateMessage(){

    }

    public void getMessageInfo(){

    }
}
