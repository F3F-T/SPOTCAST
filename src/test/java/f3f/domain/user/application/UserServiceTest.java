package f3f.domain.user.application;

import f3f.domain.model.LoginType;
import f3f.domain.model.LoginUserType;
import f3f.domain.model.UserType;
import f3f.domain.user.dao.UserRepository;
import f3f.domain.user.domain.User;
import f3f.domain.user.dto.UserDTO;
import f3f.domain.user.exception.DuplicateEmailException;
import f3f.global.encrypt.EncryptionService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EncryptionService encryptionService;

    @Autowired
    UserService userService;

    private Validator validator = null;

    @BeforeEach
    public void setupValidator() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public User createUser(UserDTO.SaveRequest saveRequest){
        return saveRequest.toEntity();
    }

    private UserDTO.SaveRequest createUserDto() {
        UserDTO.SaveRequest saveRequest = UserDTO.SaveRequest.builder()
                .email("test123@test.com")
                .password("test1234")
                .phone("01011112222")
                .userType(UserType.USER)
                .loginUserType(LoginUserType.GENERAL_USER)
                .loginType(LoginType.GENERAL_LOGIN)
                .information("test")
                .build();
        return saveRequest;
    }
    private UserDTO.SaveRequest createFailByPasswordUserDto() {
        UserDTO.SaveRequest saveRequest = UserDTO.SaveRequest.builder()
                .email("test123@test.com")
                .password("te234")
                .phone("01011112222")
                .userType(UserType.USER)
                .loginUserType(LoginUserType.GENERAL_USER)
                .loginType(LoginType.GENERAL_LOGIN)
                .information("test")
                .build();
        return saveRequest;
    }

    private UserDTO.SaveRequest createFailByPhoneUserDto() {
        UserDTO.SaveRequest saveRequest = UserDTO.SaveRequest.builder()
                .email("test123@test.com")
                .password("test1234")
                .phone("01012222")
                .userType(UserType.USER)
                .loginUserType(LoginUserType.GENERAL_USER)
                .loginType(LoginType.GENERAL_LOGIN)
                .information("test")
                .build();
        return saveRequest;
    }

    private UserDTO.SaveRequest createFailByInformationUserDto() {
        UserDTO.SaveRequest saveRequest = UserDTO.SaveRequest.builder()
                .email("test123@test.com")
                .password("test1234")
                .phone("01011112222")
                .userType(UserType.USER)
                .loginUserType(LoginUserType.GENERAL_USER)
                .loginType(LoginType.GENERAL_LOGIN)
                .information("")
                .build();
        return saveRequest;
    }

    private UserDTO.SaveRequest createFailByEmailUserDto() {
        UserDTO.SaveRequest saveRequest = UserDTO.SaveRequest.builder()
                .email("test123")
                .password("test1234")
                .phone("01011112222")
                .userType(UserType.USER)
                .loginUserType(LoginUserType.GENERAL_USER)
                .loginType(LoginType.GENERAL_LOGIN)
                .information("test")
                .build();
        return saveRequest;
    }
    @Test
    @DisplayName("회원가입 성공")
    void success_SaveUser()throws Exception{
        //given
        UserDTO.SaveRequest saveRequest = createUserDto();
        //when
        Long userId = userService.saveUser(saveRequest);
        //then
        Optional<User> byId = userRepository.findById(userId);
        assertThat(byId.get().getId()).isEqualTo(userId);

    }

    @Test
    @DisplayName("이메일 중복으로 회원가입 실패")
    void fail_SaveUser()throws Exception{
        //given
        UserDTO.SaveRequest saveRequest1 = createUserDto();
        UserDTO.SaveRequest saveRequest2 = createUserDto();
        //when
        userService.saveUser(saveRequest1);

        //then
        assertThrows(DuplicateEmailException.class, ()->
                userService.saveUser(saveRequest2));

    }
    @Test
    @DisplayName("이메일 오류로 회원가입 실패")
    void fail_SaveUser_ByEmail()throws Exception{
        //given
        UserDTO.SaveRequest saveRequest = createFailByEmailUserDto();

        //when
        Set<ConstraintViolation<UserDTO.SaveRequest>> violations = validator.validate(saveRequest);


        //then
        assertThat(violations.size()).isGreaterThan(0);

    }

    @Test
    @DisplayName("비밀번호 오류로 회원가입 실패")
    void fail_SaveUser_ByPassword()throws Exception{
        //given
        UserDTO.SaveRequest saveRequest = createFailByPasswordUserDto();

        //when
        Set<ConstraintViolation<UserDTO.SaveRequest>> violations = validator.validate(saveRequest);

        //then
        assertThat(violations.size()).isGreaterThan(0);

    }

    @Test
    @DisplayName("전화번호 오류로 회원가입 실패")
    void fail_SaveUser_ByPhone()throws Exception{
        //given
        UserDTO.SaveRequest saveRequest = createFailByPhoneUserDto();

        //when
        Set<ConstraintViolation<UserDTO.SaveRequest>> violations = validator.validate(saveRequest);

        //then
        assertThat(violations.size()).isGreaterThan(0);

    }

    @Test
    @DisplayName("정보 오류로 회원가입 실패")
    void fail_SaveUser_ByInformation()throws Exception{
        //given
        UserDTO.SaveRequest saveRequest = createFailByInformationUserDto();

        //when
        Set<ConstraintViolation<UserDTO.SaveRequest>> violations = validator.validate(saveRequest);

        //then
        assertThat(violations.size()).isGreaterThan(0);

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