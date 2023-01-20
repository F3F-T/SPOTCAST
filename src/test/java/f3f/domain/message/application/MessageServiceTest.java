package f3f.domain.message.application;

import f3f.domain.message.dao.MessageRepository;
import f3f.domain.message.dao.SearchMessageRepository;
import f3f.domain.message.domain.Message;
import f3f.domain.message.dto.MessageDTO;
import f3f.domain.publicModel.Authority;
import f3f.domain.publicModel.LoginMemberType;
import f3f.domain.publicModel.LoginType;
import f3f.domain.user.application.MemberService;
import f3f.domain.user.dao.MemberRepository;
import f3f.domain.user.domain.Member;
import f3f.domain.user.dto.MemberDTO;
import f3f.global.response.GeneralException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@WebAppConfiguration
@RunWith(SpringRunner.class)
class MessageServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    SearchMessageRepository searchMessageRepository;
    @Autowired
    MemberService memberService;

    @Autowired
    MessageService messageService;
    public static final String EMAIL = "test123@test.com";
    public static final String PASSWORD = "test1234";
    public static final String INFORMATION = "test";
    public static final String NAME = "lim";

    private MemberDTO.MemberSaveRequestDto createSenderDto() {
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = MemberDTO.MemberSaveRequestDto.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .authority(Authority.ROLE_USER)
                .loginMemberType(LoginMemberType.GENERAL_USER)
                .loginType(LoginType.GENERAL_LOGIN)
                .name(NAME)
                .build();
        return memberSaveRequestDto;
    }


    private MemberDTO.MemberSaveRequestDto createRecipientDto() {
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = MemberDTO.MemberSaveRequestDto.builder()
                .email("id1007@naver.com")
                .password(PASSWORD)
                .authority(Authority.ROLE_USER)
                .loginMemberType(LoginMemberType.GENERAL_USER)
                .loginType(LoginType.GENERAL_LOGIN)
                .name("NAME")
                .build();
        return memberSaveRequestDto;
    }


    private Member createMember(){
        return Member.builder().email("id2007@naver.com")
                .id(1234677889L)
                .password(PASSWORD)
                .authority(Authority.ROLE_USER)
                .loginMemberType(LoginMemberType.GENERAL_USER)
                .loginType(LoginType.GENERAL_LOGIN)
                .name("NAME")
                .build();

    }
    private MessageDTO.MessageRequestDto createMessageRequestDTO(Member sender, Member recipient){
        return MessageDTO.MessageRequestDto.builder()
                .content("test Content")
                .recipient(recipient)
                .sender(sender)
                .build();
    }
    @Test
    @DisplayName("메시지 전송_성공")
    void success_sendMessage()throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto senderDto = createSenderDto();
        MemberDTO.MemberSaveRequestDto recipientDto = createRecipientDto();
        Long senderId = memberService.saveMember(senderDto);
        Long recipientId = memberService.saveMember(recipientDto);

        Member sender = memberRepository.findById(senderId).get();
        Member recipient = memberRepository.findById(recipientId).get();

        //when
        MessageDTO.MessageRequestDto messageRequestDTO = createMessageRequestDTO(sender, recipient);
        Message message = messageService.sendMessage(messageRequestDTO, sender.getId());

        //then
        Assertions.assertThat(message.getSender().getId()).isEqualTo(sender.getId());
        Assertions.assertThat(message.getRecipient().getId()).isEqualTo(recipient.getId());
    }


    @Test
    @DisplayName("메세지 전송_실패 - 발신자 없음")
    void fail_sendMessage_senderNotFound()throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto senderDto = createSenderDto();
        MemberDTO.MemberSaveRequestDto recipientDto = createRecipientDto();
        Long senderId = memberService.saveMember(senderDto);
        Long recipientId = memberService.saveMember(recipientDto);

        Member sender = memberRepository.findById(senderId).get();
        Member recipient = memberRepository.findById(recipientId).get();

        //when
        Member member = createMember();
        MessageDTO.MessageRequestDto messageRequestDTO = createMessageRequestDTO(member, recipient);

        //then
        assertThrows(GeneralException.class, () ->
                messageService.sendMessage(messageRequestDTO, sender.getId()));
    }

    @Test
    @DisplayName("메세지 전송_실패 - 발신자 불일치")
    void fail_sendMessage_MisMatchFound()throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto senderDto = createSenderDto();
        MemberDTO.MemberSaveRequestDto recipientDto = createRecipientDto();
        Long senderId = memberService.saveMember(senderDto);
        Long recipientId = memberService.saveMember(recipientDto);

        Member sender = memberRepository.findById(senderId).get();
        Member recipient = memberRepository.findById(recipientId).get();

        //when
        MessageDTO.MessageRequestDto messageRequestDTO = createMessageRequestDTO(sender, recipient);

        //then
        assertThrows(GeneralException.class, () ->
                messageService.sendMessage(messageRequestDTO, 123456789L));
    }

    @Test
    @DisplayName("메세지 전송_실패 - 수신자 없음 ")
    void fail_sendMessage_recipientNotFound()throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto senderDto = createSenderDto();
        MemberDTO.MemberSaveRequestDto recipientDto = createRecipientDto();
        Long senderId = memberService.saveMember(senderDto);
        Long recipientId = memberService.saveMember(recipientDto);

        Member sender = memberRepository.findById(senderId).get();
        Member recipient = memberRepository.findById(recipientId).get();

        //when
        Member member = createMember();
        MessageDTO.MessageRequestDto messageRequestDTO = createMessageRequestDTO(sender, member);


        //then
        assertThrows(GeneralException.class, () ->
                messageService.sendMessage(messageRequestDTO, senderId));
    }

    @Test
    @DisplayName("메시지 숨기기_성공 - sender 측 요청")
    void success_updateDisplayStatusSender()throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto senderDto = createSenderDto();
        MemberDTO.MemberSaveRequestDto recipientDto = createRecipientDto();
        Long senderId = memberService.saveMember(senderDto);
        Long recipientId = memberService.saveMember(recipientDto);

        Member sender = memberRepository.findById(senderId).get();
        Member recipient = memberRepository.findById(recipientId).get();

        MessageDTO.MessageRequestDto messageRequestDTO = createMessageRequestDTO(sender, recipient);
        Message message = messageService.sendMessage(messageRequestDTO, sender.getId());
        boolean senderStatus = message.getSenderDisplayStatus();
        //when
        messageService.updateDisplayStatus(message.getId(),senderId);
        //then
        Assertions.assertThat(message.getSenderDisplayStatus()).isEqualTo(!senderStatus);
    }

    @Test
    @DisplayName("메시지 숨기기_성공 - recipient 측 요청")
    void success_updateDisplayStatusRecipient()throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto senderDto = createSenderDto();
        MemberDTO.MemberSaveRequestDto recipientDto = createRecipientDto();
        Long senderId = memberService.saveMember(senderDto);
        Long recipientId = memberService.saveMember(recipientDto);

        Member sender = memberRepository.findById(senderId).get();
        Member recipient = memberRepository.findById(recipientId).get();

        MessageDTO.MessageRequestDto messageRequestDTO = createMessageRequestDTO(sender, recipient);
        Message message = messageService.sendMessage(messageRequestDTO, sender.getId());
        boolean recipientStatus = message.getRecipientDisplayStatus();
        //when
        messageService.updateDisplayStatus(message.getId(),recipientId);
        //then
        Assertions.assertThat(message.getRecipientDisplayStatus()).isEqualTo(!recipientStatus);
    }


    @Test
    @DisplayName("메시지 숨기기_실패 - 멤버 존재 X")
    void fail_updateDisplayStatusRecipient_memberNotFound()throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto senderDto = createSenderDto();
        MemberDTO.MemberSaveRequestDto recipientDto = createRecipientDto();
        Long senderId = memberService.saveMember(senderDto);
        Long recipientId = memberService.saveMember(recipientDto);

        Member sender = memberRepository.findById(senderId).get();
        Member recipient = memberRepository.findById(recipientId).get();

        MessageDTO.MessageRequestDto messageRequestDTO = createMessageRequestDTO(sender, recipient);
        Message message = messageService.sendMessage(messageRequestDTO, sender.getId());
        //when

        //then

        assertThrows(GeneralException.class, () ->
                messageService.updateDisplayStatus(message.getId(),11231241L));
    }

    @Test
    @DisplayName("메시지 숨기기_실패 - 메세지 존재 X")
    void fail_updateDisplayStatusRecipient_messageNotFound()throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto senderDto = createSenderDto();
        MemberDTO.MemberSaveRequestDto recipientDto = createRecipientDto();
        Long senderId = memberService.saveMember(senderDto);
        Long recipientId = memberService.saveMember(recipientDto);

        Member sender = memberRepository.findById(senderId).get();
        Member recipient = memberRepository.findById(recipientId).get();

        MessageDTO.MessageRequestDto messageRequestDTO = createMessageRequestDTO(sender, recipient);
        Message message = messageService.sendMessage(messageRequestDTO, sender.getId());
        //when

        //then

        assertThrows(GeneralException.class, () ->
                messageService.updateDisplayStatus(11231241L,senderId));
    }


    @Test
    @DisplayName("메시지 정보 조회 성공 - sender 로 조회")
    void success_getMessageInfo_BySender()throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto senderDto = createSenderDto();
        MemberDTO.MemberSaveRequestDto recipientDto = createRecipientDto();
        Long senderId = memberService.saveMember(senderDto);
        Long recipientId = memberService.saveMember(recipientDto);

        Member sender = memberRepository.findById(senderId).get();
        Member recipient = memberRepository.findById(recipientId).get();

        MessageDTO.MessageRequestDto messageRequestDTO = createMessageRequestDTO(sender, recipient);
        Message message = messageService.sendMessage(messageRequestDTO, sender.getId());
        //when
        MessageDTO.MessageResponseDto messageInfo = messageService.getMessageInfo(message.getId(), senderId);
        //then
        Assertions.assertThat(messageInfo.getId()).isEqualTo(message.getId());
    }

    @Test
    @DisplayName("메시지 정보 조회 성공 - recipient 로 조회")
    void success_getMessageInfo_ByRecipient()throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto senderDto = createSenderDto();
        MemberDTO.MemberSaveRequestDto recipientDto = createRecipientDto();
        Long senderId = memberService.saveMember(senderDto);
        Long recipientId = memberService.saveMember(recipientDto);

        Member sender = memberRepository.findById(senderId).get();
        Member recipient = memberRepository.findById(recipientId).get();

        MessageDTO.MessageRequestDto messageRequestDTO = createMessageRequestDTO(sender, recipient);
        Message message = messageService.sendMessage(messageRequestDTO, sender.getId());
        //when
        MessageDTO.MessageResponseDto messageInfo = messageService.getMessageInfo(message.getId(), recipientId);
        //then
        Assertions.assertThat(messageInfo.getId()).isEqualTo(message.getId());
    }

    @Test
    @DisplayName("메시지 정보 조회 실패 - 존재하지 않는 message")
    void fail_getMessageInfo_NotFoundMessage()throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto senderDto = createSenderDto();
        MemberDTO.MemberSaveRequestDto recipientDto = createRecipientDto();
        Long senderId = memberService.saveMember(senderDto);
        Long recipientId = memberService.saveMember(recipientDto);

        Member sender = memberRepository.findById(senderId).get();
        Member recipient = memberRepository.findById(recipientId).get();

        MessageDTO.MessageRequestDto messageRequestDTO = createMessageRequestDTO(sender, recipient);
        Message message = messageService.sendMessage(messageRequestDTO, sender.getId());
        //when

        //then
                assertThrows(GeneralException.class, () ->
                messageService.getMessageInfo(1234142L, recipientId));
    }

    @Test
    @DisplayName("메시지 정보 조회 실패 - sender 나 recipient 가 조회하려는 것 이 아닐 경우")
    void fail_getMessageInfo_MisMatchMember()throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto senderDto = createSenderDto();
        MemberDTO.MemberSaveRequestDto recipientDto = createRecipientDto();
        Long senderId = memberService.saveMember(senderDto);
        Long recipientId = memberService.saveMember(recipientDto);

        Member sender = memberRepository.findById(senderId).get();
        Member recipient = memberRepository.findById(recipientId).get();

        MessageDTO.MessageRequestDto messageRequestDTO = createMessageRequestDTO(sender, recipient);
        Message message = messageService.sendMessage(messageRequestDTO, sender.getId());
        //when

        //then
        assertThrows(GeneralException.class, () ->
                messageService.getMessageInfo(message.getId(), 123123123L));
    }

    @Test
    @DisplayName("메시지 보낸 내역 조회 성공")
    void success_getSendMessageListByUserId()throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto senderDto = createSenderDto();
        MemberDTO.MemberSaveRequestDto recipientDto = createRecipientDto();
        Long senderId = memberService.saveMember(senderDto);
        Long recipientId = memberService.saveMember(recipientDto);

        Member sender = memberRepository.findById(senderId).get();
        Member recipient = memberRepository.findById(recipientId).get();

        MessageDTO.MessageRequestDto messageRequestDTO = createMessageRequestDTO(sender, recipient);
        Message message = messageService.sendMessage(messageRequestDTO, sender.getId());

        //when
        PageRequest pagable = PageRequest.of(0, 2);
        Page<MessageDTO.MessageListResponseDto> sendMessageListByUserId = messageService.getSendMessageListByUserId(senderId,pagable);

        //then
        Assertions.assertThat(sendMessageListByUserId.getTotalElements()).isEqualTo(1L);
    }

    @Test
    @DisplayName("메시지 보낸 내역 조회 실패")
    void fail_getSendMessageListByUserId()throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto senderDto = createSenderDto();
        MemberDTO.MemberSaveRequestDto recipientDto = createRecipientDto();
        Long senderId = memberService.saveMember(senderDto);
        Long recipientId = memberService.saveMember(recipientDto);

        Member sender = memberRepository.findById(senderId).get();
        Member recipient = memberRepository.findById(recipientId).get();

        MessageDTO.MessageRequestDto messageRequestDTO = createMessageRequestDTO(sender, recipient);
        Message message = messageService.sendMessage(messageRequestDTO, sender.getId());

        //when

        PageRequest pagable = PageRequest.of(0, 2);
        //then
        assertThrows(GeneralException.class, () ->
                messageService.getSendMessageListByUserId(123141241L,pagable));
    }


    @Test
    @DisplayName("메시지 받은 내역 조회 성공")
    void success_getRecipientMessageListByUserId()throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto senderDto = createSenderDto();
        MemberDTO.MemberSaveRequestDto recipientDto = createRecipientDto();
        Long senderId = memberService.saveMember(senderDto);
        Long recipientId = memberService.saveMember(recipientDto);

        Member sender = memberRepository.findById(senderId).get();
        Member recipient = memberRepository.findById(recipientId).get();

        MessageDTO.MessageRequestDto messageRequestDTO = createMessageRequestDTO(sender, recipient);
        Message message = messageService.sendMessage(messageRequestDTO, sender.getId());

        //when
        PageRequest pagable = PageRequest.of(0, 2);
        Page<MessageDTO.MessageListResponseDto> sendMessageListByUserId = messageService.getRecipientMessageListByUserId(recipientId,pagable);

        //then
        Assertions.assertThat(sendMessageListByUserId.getTotalElements()).isEqualTo(1L);
    }

    @Test
    @DisplayName("메시지 보낸 내역 조회 실패")
    void fail_getRecipientMessageListByUserId()throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto senderDto = createSenderDto();
        MemberDTO.MemberSaveRequestDto recipientDto = createRecipientDto();
        Long senderId = memberService.saveMember(senderDto);
        Long recipientId = memberService.saveMember(recipientDto);

        Member sender = memberRepository.findById(senderId).get();
        Member recipient = memberRepository.findById(recipientId).get();

        MessageDTO.MessageRequestDto messageRequestDTO = createMessageRequestDTO(sender, recipient);
        Message message = messageService.sendMessage(messageRequestDTO, sender.getId());

        //when

        PageRequest pagable = PageRequest.of(0, 2);

        //then
        assertThrows(GeneralException.class, () ->
                messageService.getRecipientMessageListByUserId(123141241L,pagable));
    }

}