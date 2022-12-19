package f3f.domain.message.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {


    @Test
    @DisplayName("메시지 전송_성공")
    void sendMessage_success()throws Exception{
        //given

        //when

        //then
    }


    @Test
    @DisplayName("메세지 전송_실패")
    void sendMessage_fail()throws Exception{
        //given

        //when

        //then
    }

    @Test
    @DisplayName("메세지 전송_실패_발신자 정보없음")
    void sendMessage_fail_unknown_sender()throws Exception{
        //given

        //when

        //then
    }


    @Test
    @DisplayName("메세지 전송_실패_수신자 정보 없음")
    void sendMessage_fail_unknown_recipient()throws Exception{
        //given

        //when

        //then
    }
}