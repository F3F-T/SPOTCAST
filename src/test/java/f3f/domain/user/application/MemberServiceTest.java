package f3f.domain.user.application;

import f3f.domain.model.LoginType;
import f3f.domain.model.LoginMemberType;
import f3f.domain.model.Authority;
import f3f.domain.user.dao.MemberRepository;
import f3f.domain.user.domain.Member;
import f3f.domain.user.dto.MemberDTO;
import f3f.domain.user.exception.*;
import f3f.global.encrypt.EncryptionService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
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
class MemberServiceTest {

    public static final String EMAIL = "test123@test.com";
    public static final String PASSWORD = "test1234";
    public static final String PHONE = "01011112222";
    public static final String INFORMATION = "test";
    public static final String NAME = "lim";
    public static final String NICKNAME = "dong";
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EncryptionService encryptionService;

    @Autowired
    MemberService memberService;

    private Validator validator = null;

    @BeforeEach
    public void setupValidator() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public Member createMember(MemberDTO.MemberSaveRequestDto memberSaveRequestDto){
        return memberSaveRequestDto.toEntity();
    }

    private MemberDTO.MemberSaveRequestDto createMemberDto() {
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = MemberDTO.MemberSaveRequestDto.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .phone(PHONE)
                .authority(Authority.ROLE_USER)
                .loginMemberType(LoginMemberType.GENERAL_USER)
                .loginType(LoginType.GENERAL_LOGIN)
                .information(INFORMATION)
                .name(NAME)
                .nickname(NICKNAME)
                .build();
        return memberSaveRequestDto;
    }
    private MemberDTO.MemberSaveRequestDto createGoogleMemberDto() {
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = MemberDTO.MemberSaveRequestDto.builder()
                .email("test123@test.com")
                .password("test1234")
                .phone("01011112222")
                .authority(Authority.ROLE_USER)
                .loginMemberType(LoginMemberType.GENERAL_USER)
                .loginType(LoginType.GOOGLE_LOGIN)
                .information("test")
                .name("lim")
                .nickname("dong")
                .build();
        return memberSaveRequestDto;
    }

    private MemberDTO.MemberSaveRequestDto createFailByPasswordMemberDto() {
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = MemberDTO.MemberSaveRequestDto.builder()
                .email("test123@test.com")
                .password("te234")
                .phone("01011112222")
                .authority(Authority.ROLE_USER)
                .loginMemberType(LoginMemberType.GENERAL_USER)
                .loginType(LoginType.GENERAL_LOGIN)
                .information("test")
                .name("lim")
                .nickname("dong")
                .build();
        return memberSaveRequestDto;
    }

    private MemberDTO.MemberSaveRequestDto createFailByPhoneMemberDto() {
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = MemberDTO.MemberSaveRequestDto.builder()
                .email("test123@test.com")
                .password("test1234")
                .phone("01012222")
                .authority(Authority.ROLE_USER)
                .loginMemberType(LoginMemberType.GENERAL_USER)
                .loginType(LoginType.GENERAL_LOGIN)
                .information("test")
                .name("lim")
                .nickname("dong")
                .build();
        return memberSaveRequestDto;
    }

    private MemberDTO.MemberSaveRequestDto createFailByInformationMemberDto() {
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = MemberDTO.MemberSaveRequestDto.builder()
                .email("test123@test.com")
                .password("test1234")
                .phone("01011112222")
                .authority(Authority.ROLE_USER)
                .loginMemberType(LoginMemberType.GENERAL_USER)
                .loginType(LoginType.GENERAL_LOGIN)
                .information("")
                .name("lim")
                .nickname("dong")
                .build();
        return memberSaveRequestDto;
    }
    private MemberDTO.MemberSaveRequestDto createFailByNameMemberDto() {
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = MemberDTO.MemberSaveRequestDto.builder()
                .email("test123@test.com")
                .password("test1234")
                .phone("01011112222")
                .authority(Authority.ROLE_USER)
                .loginMemberType(LoginMemberType.GENERAL_USER)
                .loginType(LoginType.GENERAL_LOGIN)
                .information("")
                .nickname("dong")
                .build();
        return memberSaveRequestDto;
    }
    private MemberDTO.MemberSaveRequestDto createFailByNicknameMemberDto() {
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = MemberDTO.MemberSaveRequestDto.builder()
                .email("test123@test.com")
                .password("test1234")
                .phone("01011112222")
                .authority(Authority.ROLE_USER)
                .loginMemberType(LoginMemberType.GENERAL_USER)
                .loginType(LoginType.GENERAL_LOGIN)
                .information("")
                .name("lim")
                .build();
        return memberSaveRequestDto;
    }
    private MemberDTO.MemberSaveRequestDto createFailByEmailMemberDto() {
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = MemberDTO.MemberSaveRequestDto.builder()
                .email("test123")
                .password("test1234")
                .phone("01011112222")
                .authority(Authority.ROLE_USER)
                .loginMemberType(LoginMemberType.GENERAL_USER)
                .loginType(LoginType.GENERAL_LOGIN)
                .information("test")
                .name("lim")
                .nickname("dong")
                .build();
        return memberSaveRequestDto;
    }

    @Test
    @DisplayName("회원가입 성공")
    void success_SaveMember()throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createMemberDto();
        //when
        Long userId = memberService.saveMember(memberSaveRequestDto);
        //then
        Optional<Member> byId = memberRepository.findById(userId);
        assertThat(byId.get().getId()).isEqualTo(userId);

    }

    @Test
    @DisplayName("이메일 중복으로 회원가입 실패")
    void fail_SaveMember()throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto1 = createMemberDto();
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto2 = createMemberDto();
        //when
        memberService.saveMember(memberSaveRequestDto1);

        //then
        assertThrows(DuplicateEmailException.class, ()->
                memberService.saveMember(memberSaveRequestDto2));

    }
    @Test
    @DisplayName("이메일 오류로 회원가입 실패")
    void fail_SaveMember_ByEmail()throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createFailByEmailMemberDto();

        //when
        Set<ConstraintViolation<MemberDTO.MemberSaveRequestDto>> violations = validator.validate(memberSaveRequestDto);


        //then
        assertThat(violations.size()).isGreaterThan(0);

    }

    @Test
    @DisplayName("비밀번호 오류로 회원가입 실패")
    void fail_SaveMember_ByPassword()throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createFailByPasswordMemberDto();

        //when
        Set<ConstraintViolation<MemberDTO.MemberSaveRequestDto>> violations = validator.validate(memberSaveRequestDto);

        //then
        assertThat(violations.size()).isGreaterThan(0);

    }

    @Test
    @DisplayName("전화번호 오류로 회원가입 실패")
    void fail_SaveMember_ByPhone()throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createFailByPhoneMemberDto();

        //when
        Set<ConstraintViolation<MemberDTO.MemberSaveRequestDto>> violations = validator.validate(memberSaveRequestDto);

        //then
        assertThat(violations.size()).isGreaterThan(0);

    }

    @Test
    @DisplayName("이름 오류로 회원가입 실패")
    void fail_SaveMember_ByName()throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createFailByNameMemberDto();

        //when
        Set<ConstraintViolation<MemberDTO.MemberSaveRequestDto>> violations = validator.validate(memberSaveRequestDto);

        //then
        assertThat(violations.size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("정보 오류로 회원가입 실패")
    void fail_SaveMember_ByNickname()throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createFailByNicknameMemberDto();

        //when
        Set<ConstraintViolation<MemberDTO.MemberSaveRequestDto>> violations = validator.validate(memberSaveRequestDto);

        //then
        assertThat(violations.size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("정보 오류로 회원가입 실패")
    void fail_SaveMember_ByInformation()throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createFailByInformationMemberDto();

        //when
        Set<ConstraintViolation<MemberDTO.MemberSaveRequestDto>> violations = validator.validate(memberSaveRequestDto);

        //then
        assertThat(violations.size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("회원정보조회_성공")
    void success_findMyPageInfo()throws Exception{
        //given
        Long userId = memberService.saveMember(createMemberDto());
        Optional<Member> byId = memberRepository.findById(userId);
        Long memberId = byId.get().getId();

        //when
        String findEmail = memberService.findMyPageInfo(memberId).getEmail();

        //then
        Assertions.assertThat(memberId).isEqualTo(findEmail);
    }

    @Test
    @DisplayName("회원정보조회_실패")
    void fail_findMyPageInfo()throws Exception{
        //given
        Long memberId = 1L;

        //when

        //then
        assertThrows(MemberNotFoundException.class, ()->
                memberService.findMyPageInfo(memberId));
    }
    @Test
    @DisplayName("비밀번호변경_성공 로그인 O - 이전 비밀번호가 같고 비밀번호 변경 후 유저의 비밀번호와 request 비밀번호가 같은 경우")
    void success_UpdatePassword()throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createMemberDto();
        Long userId = memberService.saveMember(memberSaveRequestDto);
        Member member = memberRepository.findById(userId).get();

        //when
        MemberDTO.MemberUpdatePasswordRequestDto passwordRequest = MemberDTO.MemberUpdatePasswordRequestDto.builder()
                .email(member.getEmail())
                .beforePassword("test1234")
                .afterPassword("asdfqwer")
                .build();


        memberService.updatePassword(passwordRequest);
        String afterPassword = passwordRequest.getAfterPassword();

        //then
        assertThat(member.getPassword()).isEqualTo(afterPassword);
    }

    @Test
    @DisplayName("비밀번호변경_실패 로그인 O - 이전 비밀번호가 다른 경우 비밀번호 변경 실패")
    void fail_UpdatePassword()throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createMemberDto();
        Long userId = memberService.saveMember(memberSaveRequestDto);
        Member member = memberRepository.findById(userId).get();

        //when
        MemberDTO.MemberUpdatePasswordRequestDto passwordRequest = MemberDTO.MemberUpdatePasswordRequestDto.builder()
                .email(member.getEmail())
                .beforePassword("qwer1234")
                .afterPassword("asdfqwer")
                .build();

        //then
        assertThrows(UnauthenticatedMemberException.class,() -> memberService.updatePassword(passwordRequest));
    }

    @Test
    @DisplayName("비밀번호변경_실패 로그인 O - 일반 회원가입 유저가 아닌 경우")
    void fail_UpdatePassword_NotGeneralType()throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createGoogleMemberDto();
        Long userId = memberService.saveMember(memberSaveRequestDto);
        Member member = memberRepository.findById(userId).get();

        //when
        MemberDTO.MemberUpdatePasswordRequestDto passwordRequest = MemberDTO.MemberUpdatePasswordRequestDto.builder()
                .email(member.getEmail())
                .beforePassword("qwer1234")
                .afterPassword("asdfqwer")
                .build();

        //then
        assertThrows(NotGeneralLoginType.class,() -> memberService.updatePassword(passwordRequest));
    }

    @Test
    @DisplayName("비밀번호변경_성공 로그인 X - 이메일이 존재하는 경우 성공")
    void success_UpdatePasswordByForgot()throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createMemberDto();
        Long userId = memberService.saveMember(memberSaveRequestDto);
        Member member = memberRepository.findById(userId).get();

        //when
        MemberDTO.MemberUpdatePasswordRequestDto passwordRequest = MemberDTO.MemberUpdatePasswordRequestDto.builder()
                .email(member.getEmail())
                .afterPassword("asdfqwer")
                .build();


        memberService.updatePasswordByForgot(passwordRequest);
        String afterPassword = passwordRequest.getAfterPassword();

        //then
        assertThat(member.getPassword()).isEqualTo(afterPassword);
    }

    @Test
    @DisplayName("비밀번호변경_실패 로그인 X - 이메일이 존재하지 않는 경우 비밀번호 변경 실패")
    void fail_UpdatePasswordByForgot()throws Exception{
        //given

        //when
        MemberDTO.MemberUpdatePasswordRequestDto passwordRequest = MemberDTO.MemberUpdatePasswordRequestDto.builder()
                .email("test")
                .afterPassword("asdfqwer")
                .build();

        //then
        assertThrows(MemberNotFoundException.class,() -> memberService.updatePasswordByForgot(passwordRequest));
    }
    @Test
    @DisplayName("비밀번호변경_실패 로그인 X - 일반 회원가입 유저가 아닌 경우")
    void fail_UpdatePasswordByForgot_NotGeneralType()throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createGoogleMemberDto();
        Long userId = memberService.saveMember(memberSaveRequestDto);
        Member member = memberRepository.findById(userId).get();

        //when
        MemberDTO.MemberUpdatePasswordRequestDto passwordRequest = MemberDTO.MemberUpdatePasswordRequestDto.builder()
                .email(member.getEmail())
                .afterPassword("asdfqwer")
                .build();


        //then
        assertThrows(NotGeneralLoginType.class,() -> memberService.updatePasswordByForgot(passwordRequest));
    }

    @Test
    @DisplayName("닉네임 변경 성공")
    public void success_UpdateNickname() throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createMemberDto();
        Long userId = memberService.saveMember(memberSaveRequestDto);
        Member member = memberRepository.findById(userId).get();

        String changeNickname = "test";

        MemberDTO.MemberUpdateNicknameRequestDto nicknameRequest = MemberDTO.MemberUpdateNicknameRequestDto.builder()
                .nickname(changeNickname)
                .email(EMAIL)
                .build();

        //when
        memberService.updateNickname(nicknameRequest);

        //then
        Assertions.assertThat(member.getNickname()).isEqualTo(changeNickname);
    }

    @Test
    @DisplayName("닉네임 변경 실패 - 유저 찾지 못한 경우")
    public void fail_UpdateNickname_NotFoundMember() throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createMemberDto();
        memberService.saveMember(memberSaveRequestDto);

        //when
        String changeNickname = "test";

        MemberDTO.MemberUpdateNicknameRequestDto nicknameRequest = MemberDTO.MemberUpdateNicknameRequestDto.builder()
                .nickname(changeNickname)
                .email("EMAIL")
                .build();

        //then
        assertThrows(MemberNotFoundException.class,() -> memberService.updateNickname(nicknameRequest));
    }

    @Test
    @DisplayName("닉네임 변경 실패 - 중복된 닉네임")
    public void fail_UpdateNickname_DuplicatedNickname() throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createMemberDto();
        memberService.saveMember(memberSaveRequestDto);

        //when
        String changeNickname = NICKNAME;

        MemberDTO.MemberUpdateNicknameRequestDto nicknameRequest = MemberDTO.MemberUpdateNicknameRequestDto.builder()
                .nickname(changeNickname)
                .email(EMAIL)
                .build();

        //then
        assertThrows(DuplicateNicknameException.class,() -> memberService.updateNickname(nicknameRequest));

    }

    @Test
    @DisplayName("정보 변경 성공")
    public void success_UpdateInformation() throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createMemberDto();
        Long userId = memberService.saveMember(memberSaveRequestDto);
        Member member = memberRepository.findById(userId).get();

        String changeInformation = "test";

        MemberDTO.MemberUpdateInformationRequestDto informationRequest = MemberDTO.MemberUpdateInformationRequestDto.builder()
                .information(changeInformation)
                .email(EMAIL)
                .build();

        //when
        memberService.updateInformation(informationRequest);

        //then
        Assertions.assertThat(member.getInformation()).isEqualTo(changeInformation);
    }

    @Test
    @DisplayName("정보 변경 실패 - 유저 찾지 못한 경우")
    public void fail_UpdateInformation_NotFoundMember() throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createMemberDto();
        memberService.saveMember(memberSaveRequestDto);

        //when
        String changeInformation = "test";

        MemberDTO.MemberUpdateInformationRequestDto informationRequest = MemberDTO.MemberUpdateInformationRequestDto.builder()
                .information(changeInformation)
                .email("EMAIL")
                .build();


        //then
        assertThrows(MemberNotFoundException.class,() -> memberService.updateInformation(informationRequest));
    }

    @Test
    @DisplayName("회원 삭제 성공")
    public void success_DeleteMember() throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createMemberDto();
        memberService.saveMember(memberSaveRequestDto);

        //when
        String email = EMAIL;
        String password = PASSWORD;
        memberService.deleteMember(email, password);

        //then
        assertThat(memberRepository.findByEmail(email)).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("회원 삭제 실패 - 유저가 존재하지 않는 경우")
    public void fail_DeleteMember_NotFountMember() throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createMemberDto();
        memberService.saveMember(memberSaveRequestDto);

        //when
        String email = "EMAIL";
        String password = PASSWORD;


        //then
        assertThrows(MemberNotFoundException.class,() -> memberService.deleteMember(email, password));

    }

    @Test
    @DisplayName("회원 삭제 실패 - 비밀번호가 잘못된 경우")
    public void fail_DeleteMember_IncorrectPassword() throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createMemberDto();
        memberService.saveMember(memberSaveRequestDto);

        //when
        String email = EMAIL;
        String password = "PASSWORD";

        //then
        assertThrows(IncorrectPasswordException.class,() -> memberService.deleteMember(email, password));
    }
}