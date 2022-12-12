package f3f.domain.user.application;

import f3f.domain.user.dao.UserRepository;
import f3f.global.encrypt.EncryptionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    EncryptionService encryptionService;

    @InjectMocks
    UserService userService;


    @Test
    @DisplayName("회원가입 성공")
    void success_SaveUser()throws Exception{
        //given

        //when

        //then

    }

    @Test
    @DisplayName("회원가입 실패")
    void fail_SaveUser()throws Exception{
        //given

        //when

        //then

    }

    @Test
    @DisplayName("회원정보수정_성공")
    void success_UpdateUser()throws Exception{
        //given

        //when

        //then

    }

    @Test
    @DisplayName("회원정보수정_실패")
    void fail_UpdateUser()throws Exception{
        //given

        //when

        //then

    }

    @Test
    @DisplayName("회원탈퇴_성공")
    void success_DeleteUser()throws Exception{
        //given

        //when

        //then

    }

    @Test
    @DisplayName("회원탈퇴_실패")
    void fail_DeleteUser()throws Exception{
        //given

        //when

        //then

    }
}