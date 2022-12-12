package f3f.domain.user.application;

import f3f.domain.model.LoginType;
import f3f.domain.model.LoginUserType;
import f3f.domain.model.UserType;
import f3f.domain.user.dao.UserRepository;
import f3f.domain.user.domain.User;
import f3f.domain.user.dto.UserDTO;
import f3f.domain.user.exception.DuplicateEmailException;
import f3f.domain.user.exception.NotGeneralLoginType;
import f3f.domain.user.exception.UnauthenticatedUserException;
import f3f.domain.user.exception.UserNotFoundException;
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
                .name("lim")
                .nickname("dong")
                .build();
        return saveRequest;
    }
    private UserDTO.SaveRequest createGoogleUserDto() {
        UserDTO.SaveRequest saveRequest = UserDTO.SaveRequest.builder()
                .email("test123@test.com")
                .password("test1234")
                .phone("01011112222")
                .userType(UserType.USER)
                .loginUserType(LoginUserType.GENERAL_USER)
                .loginType(LoginType.GOOGLE_LOGIN)
                .information("test")
                .name("lim")
                .nickname("dong")
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
                .name("lim")
                .nickname("dong")
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
                .name("lim")
                .nickname("dong")
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
                .name("lim")
                .nickname("dong")
                .build();
        return saveRequest;
    }
    private UserDTO.SaveRequest createFailByNameUserDto() {
        UserDTO.SaveRequest saveRequest = UserDTO.SaveRequest.builder()
                .email("test123@test.com")
                .password("test1234")
                .phone("01011112222")
                .userType(UserType.USER)
                .loginUserType(LoginUserType.GENERAL_USER)
                .loginType(LoginType.GENERAL_LOGIN)
                .information("")
                .nickname("dong")
                .build();
        return saveRequest;
    }
    private UserDTO.SaveRequest createFailByNicknameUserDto() {
        UserDTO.SaveRequest saveRequest = UserDTO.SaveRequest.builder()
                .email("test123@test.com")
                .password("test1234")
                .phone("01011112222")
                .userType(UserType.USER)
                .loginUserType(LoginUserType.GENERAL_USER)
                .loginType(LoginType.GENERAL_LOGIN)
                .information("")
                .name("lim")
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
                .name("lim")
                .nickname("dong")
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
    @DisplayName("이름 오류로 회원가입 실패")
    void fail_SaveUser_ByName()throws Exception{
        //given
        UserDTO.SaveRequest saveRequest = createFailByNameUserDto();

        //when
        Set<ConstraintViolation<UserDTO.SaveRequest>> violations = validator.validate(saveRequest);

        //then
        assertThat(violations.size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("정보 오류로 회원가입 실패")
    void fail_SaveUser_ByNickname()throws Exception{
        //given
        UserDTO.SaveRequest saveRequest = createFailByNicknameUserDto();

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
    @DisplayName("회원정보조회_성공")
    void success_findMyPageInfo()throws Exception{
        //given
        Long userId = userService.saveUser(createUserDto());
        Optional<User> byId = userRepository.findById(userId);
        String email = byId.get().getEmail();

        //when
        String findEmail = userService.findMyPageInfo(email).getEmail();

        //then
        Assertions.assertThat(email).isEqualTo(findEmail);
    }

    @Test
    @DisplayName("회원정보조회_실패")
    void fail_findMyPageInfo()throws Exception{
        //given
        String email = "test@te.com";

        //when

        //then
        assertThrows(UserNotFoundException.class, ()->
                userService.findMyPageInfo(email));
    }
    @Test
    @DisplayName("비밀번호변경_성공 로그인 O - 이전 비밀번호가 같고 비밀번호 변경 후 유저의 비밀번호와 request 비밀번호가 같은 경우")
    void success_UpdatePassword()throws Exception{
        //given
        UserDTO.SaveRequest saveRequest = createUserDto();
        Long userId = userService.saveUser(saveRequest);
        User user = userRepository.findById(userId).get();

        //when
        UserDTO.UpdatePasswordRequest passwordRequest = UserDTO.UpdatePasswordRequest.builder()
                .email(user.getEmail())
                .beforePassword("test1234")
                .afterPassword("asdfqwer")
                .build();


        userService.updatePassword(passwordRequest);
        String afterPassword = passwordRequest.getAfterPassword();

        //then
        assertThat(user.getPassword()).isEqualTo(afterPassword);
    }

    @Test
    @DisplayName("비밀번호변경_실패 로그인 O - 이전 비밀번호가 다른 경우 비밀번호 변경 실패")
    void fail_UpdatePassword()throws Exception{
        //given
        UserDTO.SaveRequest saveRequest = createUserDto();
        Long userId = userService.saveUser(saveRequest);
        User user = userRepository.findById(userId).get();

        //when
        UserDTO.UpdatePasswordRequest passwordRequest = UserDTO.UpdatePasswordRequest.builder()
                .email(user.getEmail())
                .beforePassword("qwer1234")
                .afterPassword("asdfqwer")
                .build();

        //then
        assertThrows(UnauthenticatedUserException.class,() -> userService.updatePassword(passwordRequest));
    }

    @Test
    @DisplayName("비밀번호변경_실패 로그인 O - 일반 회원가입 유저가 아닌 경우")
    void fail_UpdatePassword_NotGeneralType()throws Exception{
        //given
        UserDTO.SaveRequest saveRequest = createGoogleUserDto();
        Long userId = userService.saveUser(saveRequest);
        User user = userRepository.findById(userId).get();

        //when
        UserDTO.UpdatePasswordRequest passwordRequest = UserDTO.UpdatePasswordRequest.builder()
                .email(user.getEmail())
                .beforePassword("qwer1234")
                .afterPassword("asdfqwer")
                .build();

        //then
        assertThrows(NotGeneralLoginType.class,() -> userService.updatePassword(passwordRequest));
    }

    @Test
    @DisplayName("비밀번호변경_성공 로그인 X - 이메일이 존재하는 경우 성공")
    void success_UpdatePasswordByForgot()throws Exception{
        //given
        UserDTO.SaveRequest saveRequest = createUserDto();
        Long userId = userService.saveUser(saveRequest);
        User user = userRepository.findById(userId).get();

        //when
        UserDTO.UpdatePasswordRequest passwordRequest = UserDTO.UpdatePasswordRequest.builder()
                .email(user.getEmail())
                .afterPassword("asdfqwer")
                .build();


        userService.updatePasswordByForgot(passwordRequest);
        String afterPassword = passwordRequest.getAfterPassword();

        //then
        assertThat(user.getPassword()).isEqualTo(afterPassword);
    }

    @Test
    @DisplayName("비밀번호변경_실패 로그인 X - 이메일이 존재하지 않는 경우 비밀번호 변경 실패")
    void fail_UpdatePasswordByForgot()throws Exception{
        //given

        //when
        UserDTO.UpdatePasswordRequest passwordRequest = UserDTO.UpdatePasswordRequest.builder()
                .email("test")
                .afterPassword("asdfqwer")
                .build();

        //then
        assertThrows(UserNotFoundException.class,() -> userService.updatePasswordByForgot(passwordRequest));
    }
    @Test
    @DisplayName("비밀번호변경_실패 로그인 X - 일반 회원가입 유저가 아닌 경우")
    void fail_UpdatePasswordByForgot_NotGeneralType()throws Exception{
        //given
        UserDTO.SaveRequest saveRequest = createGoogleUserDto();
        Long userId = userService.saveUser(saveRequest);
        User user = userRepository.findById(userId).get();

        //when
        UserDTO.UpdatePasswordRequest passwordRequest = UserDTO.UpdatePasswordRequest.builder()
                .email(user.getEmail())
                .afterPassword("asdfqwer")
                .build();


        //then
        assertThrows(NotGeneralLoginType.class,() -> userService.updatePasswordByForgot(passwordRequest));
    }

    @Test
    @DisplayName("이메일찾기 성공 - 이름과 휴대폰 번호로 조회했을 때 정보가 존재하는 경우")
    public  void success_FindEmailByForgot() throws Exception{
        //given
        UserDTO.SaveRequest saveRequest = createUserDto();
        Long userId = userService.saveUser(saveRequest);
        User user = userRepository.findById(userId).get();

        //when

        //then
    }
}