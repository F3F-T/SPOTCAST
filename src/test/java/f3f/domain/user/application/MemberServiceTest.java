package f3f.domain.user.application;

import f3f.domain.publicModel.LoginType;
import f3f.domain.publicModel.LoginMemberType;
import f3f.domain.publicModel.Authority;
import f3f.domain.user.dao.MemberRepository;
import f3f.domain.user.domain.Member;
import f3f.domain.user.dto.MemberDTO;
import f3f.domain.user.dto.TokenDTO;
import f3f.domain.user.exception.*;
import f3f.global.encrypt.EncryptionService;
import f3f.global.response.GeneralException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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

    @Autowired
    HttpSession httpSession;

    private Validator validator = null;

    @BeforeEach
    public void setupValidator() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }



    private MemberDTO.MemberSaveRequestDto createMemberDto() {
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

    private MemberDTO.MemberSaveRequestDto createGoogleMemberDto() {
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = MemberDTO.MemberSaveRequestDto.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .authority(Authority.ROLE_USER)
                .loginMemberType(LoginMemberType.GENERAL_USER)
                .loginType(LoginType.GOOGLE_LOGIN)
                .name("lim")
                .build();
        return memberSaveRequestDto;
    }

    private MemberDTO.MemberSaveRequestDto createFailByPasswordMemberDto() {
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = MemberDTO.MemberSaveRequestDto.builder()
                .email("test123@test.com")
                .password("te234")
                .authority(Authority.ROLE_USER)
                .loginMemberType(LoginMemberType.GENERAL_USER)
                .loginType(LoginType.GENERAL_LOGIN)
                .name("lim")
                .build();
        return memberSaveRequestDto;
    }

    private MemberDTO.MemberSaveRequestDto createFailByPhoneMemberDto() {
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = MemberDTO.MemberSaveRequestDto.builder()
                .email("test123@test.com")
                .password("test1234")
                .authority(Authority.ROLE_USER)
                .loginMemberType(LoginMemberType.GENERAL_USER)
                .loginType(LoginType.GENERAL_LOGIN)
                .name("lim")
                .build();
        return memberSaveRequestDto;
    }

    private MemberDTO.MemberSaveRequestDto createFailByInformationMemberDto() {
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = MemberDTO.MemberSaveRequestDto.builder()
                .email("test123@test.com")
                .password("test1234")
                .authority(Authority.ROLE_USER)
                .loginMemberType(LoginMemberType.GENERAL_USER)
                .loginType(LoginType.GENERAL_LOGIN)
                .name("lim")
                .build();
        return memberSaveRequestDto;
    }

    private MemberDTO.MemberSaveRequestDto createFailByNameMemberDto() {
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = MemberDTO.MemberSaveRequestDto.builder()
                .email("test123@test.com")
                .password("test1234")
                .authority(Authority.ROLE_USER)
                .loginMemberType(LoginMemberType.GENERAL_USER)
                .loginType(LoginType.GENERAL_LOGIN)
                .build();
        return memberSaveRequestDto;
    }

    private MemberDTO.MemberSaveRequestDto createFailByNicknameMemberDto() {
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = MemberDTO.MemberSaveRequestDto.builder()
                .email("test123@test.com")
                .password("test1234")
                .authority(Authority.ROLE_USER)
                .loginMemberType(LoginMemberType.GENERAL_USER)
                .loginType(LoginType.GENERAL_LOGIN)
                .name("lim")
                .build();
        return memberSaveRequestDto;
    }

    private MemberDTO.MemberSaveRequestDto createFailByEmailMemberDto() {
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = MemberDTO.MemberSaveRequestDto.builder()
                .email("test123")
                .password("test1234")
                .authority(Authority.ROLE_USER)
                .loginMemberType(LoginMemberType.GENERAL_USER)
                .loginType(LoginType.GENERAL_LOGIN)
                .name("lim")
                .build();
        return memberSaveRequestDto;
    }

    @Test
    @DisplayName("회원가입 성공")
    void success_SaveMember() throws Exception {
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createMemberDto();
        //when
        Long memberId = memberService.saveMember(memberSaveRequestDto);
        //then
        Optional<Member> byId = memberRepository.findById(memberId);
        assertThat(byId.get().getId()).isEqualTo(memberId);

    }

    @Test
    @DisplayName("이메일 중복으로 회원가입 실패")
    void fail_SaveMember() throws Exception {
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto1 = createMemberDto();
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto2 = createMemberDto();
        //when
        memberService.saveMember(memberSaveRequestDto1);

        //then
        assertThrows(GeneralException.class, () ->
                memberService.saveMember(memberSaveRequestDto2));

    }

    @Test
    @DisplayName("이메일 오류로 회원가입 실패")
    void fail_SaveMember_ByEmail() throws Exception {
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createFailByEmailMemberDto();

        //when
        Set<ConstraintViolation<MemberDTO.MemberSaveRequestDto>> violations = validator.validate(memberSaveRequestDto);


        //then
        assertThat(violations.size()).isGreaterThan(0);

    }

    @Test
    @DisplayName("비밀번호 오류로 회원가입 실패")
    void fail_SaveMember_ByPassword() throws Exception {
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createFailByPasswordMemberDto();

        //when
        Set<ConstraintViolation<MemberDTO.MemberSaveRequestDto>> violations = validator.validate(memberSaveRequestDto);

        //then
        assertThat(violations.size()).isGreaterThan(0);

    }

 
    @DisplayName("이름 오류로 회원가입 실패")
    void fail_SaveMember_ByName() throws Exception {
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createFailByNameMemberDto();

        //when
        Set<ConstraintViolation<MemberDTO.MemberSaveRequestDto>> violations = validator.validate(memberSaveRequestDto);

        //then
        assertThat(violations.size()).isGreaterThan(0);
    }




    @Test
    @DisplayName("회원정보조회_성공")
    void success_findMyPageInfo() throws Exception {
        //given
        Long memberId = memberService.saveMember(createMemberDto());
        Member member = memberService.findMemberByMemberId(memberId);
        MemberDTO.MemberInfoResponseDto memberDTO = member.toFindMemberDto();
        //when
        MemberDTO.MemberInfoResponseDto findMemberDTO = memberService.findMemberInfoByMemberId(memberId);

        //then
        assertThat(memberDTO).isEqualToComparingFieldByField(findMemberDTO);
    }

    @Test
    @DisplayName("회원정보조회_실패")
    void fail_findMyPageInfo() throws Exception {
        //given
        Long memberId = 112343L;

        //when

        //then
        assertThrows(GeneralException.class, () ->
                memberService.findMemberInfoByMemberId(memberId));
    }

    @Test
    @DisplayName("비밀번호변경_성공 로그인 O - 이전 비밀번호가 같고 비밀번호 변경 후 유저의 비밀번호와 request 비밀번호가 같은 경우")
    void success_UpdatePassword() throws Exception {
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createMemberDto();
        Long memberId = memberService.saveMember(memberSaveRequestDto);
        Optional<Member> byId = memberRepository.findById(memberId);
        Member member = byId.get();

        //when
        MemberDTO.MemberUpdateLoginPasswordRequestDto passwordRequest = MemberDTO.MemberUpdateLoginPasswordRequestDto.builder()
                .email(EMAIL)
                .beforePassword(PASSWORD)
                .afterPassword("as1234wer")
                .build();
        System.out.println("memberId = " + memberId);
        System.out.println("member.getPassword() = " + member.getPassword());


        memberService.updatePassword(passwordRequest, memberId);
        String afterPassword = passwordRequest.getAfterPassword();

        //then
        assertThat(member.getPassword()).isEqualTo(afterPassword);
    }

    @Test
    @DisplayName("비밀번호변경_실패 로그인 O - 이전 비밀번호가 다른 경우 비밀번호 변경 실패")
    void fail_UpdatePassword() throws Exception {
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createMemberDto();
        Long memberId = memberService.saveMember(memberSaveRequestDto);

        Member member = memberRepository.findById(memberId).get();

        //when
        MemberDTO.MemberUpdateLoginPasswordRequestDto passwordRequest = MemberDTO.MemberUpdateLoginPasswordRequestDto.builder()
                .email(member.getEmail())
                .beforePassword("qwer1234")
                .afterPassword("asdfqwer")
                .build();

        //then
        assertThrows(GeneralException.class, () -> memberService.updatePassword(passwordRequest, memberId));
    }

    @Test
    @DisplayName("비밀번호변경_실패 로그인 O - 일반 회원가입 유저가 아닌 경우")
    void fail_UpdatePassword_NotGeneralType() throws Exception {
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createGoogleMemberDto();

        Long memberId = memberService.saveMember(memberSaveRequestDto);

        //when
        MemberDTO.MemberUpdateLoginPasswordRequestDto passwordRequest = MemberDTO.MemberUpdateLoginPasswordRequestDto.builder()
                .email(EMAIL)
                .beforePassword(PASSWORD)
                .afterPassword("asdfqwer")
                .build();

        //then
        assertThrows(GeneralException.class, () -> memberService.updatePassword(passwordRequest, memberId));
    }

    @Test
    @DisplayName("비밀번호변경_성공 로그인 X - 이메일이 존재하는 경우 성공")
    void success_UpdatePasswordByForgot() throws Exception {
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createMemberDto();
        Long memberId = memberService.saveMember(memberSaveRequestDto);
        Optional<Member> byId = memberRepository.findById(memberId);
        Member member = byId.get();

        //when
        MemberDTO.MemberUpdateForgotPasswordRequestDto passwordRequest = MemberDTO.MemberUpdateForgotPasswordRequestDto.builder()
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
    void fail_UpdatePasswordByForgot() throws Exception {
        //given

        //when
        MemberDTO.MemberUpdateForgotPasswordRequestDto passwordRequest = MemberDTO.MemberUpdateForgotPasswordRequestDto.builder()
                .email("test")
                .afterPassword("asdfqwer")
                .build();

        //then
        assertThrows(GeneralException.class, () -> memberService.updatePasswordByForgot(passwordRequest));
    }

    @Test
    @DisplayName("비밀번호변경_실패 로그인 X - 일반 회원가입 유저가 아닌 경우")
    void fail_UpdatePasswordByForgot_NotGeneralType() throws Exception {
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createGoogleMemberDto();
        Long memberId = memberService.saveMember(memberSaveRequestDto);
        Member member = memberRepository.findById(memberId).get();

        //when
        MemberDTO.MemberUpdateForgotPasswordRequestDto passwordRequest = MemberDTO.MemberUpdateForgotPasswordRequestDto.builder()
                .email(member.getEmail())
                .afterPassword("asdfqwer")
                .build();

        //then
        assertThrows(GeneralException.class, () -> memberService.updatePasswordByForgot(passwordRequest));
    }



    @Test
    @DisplayName("정보 변경 성공")
    public void success_UpdateInformation() throws Exception {
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createMemberDto();
        Long memberId = memberService.saveMember(memberSaveRequestDto);
        Member member = memberRepository.findById(memberId).get();

        String changeInformation = "test";

        MemberDTO.MemberUpdateInformationRequestDto informationRequest = MemberDTO.MemberUpdateInformationRequestDto.builder()
                .information(changeInformation)
                .build();

        //when
        memberService.updateInformation(informationRequest, memberId);

        //then
        Assertions.assertThat(member.getInformation()).isEqualTo(changeInformation);
    }

    @Test
    @DisplayName("정보 변경 실패 - 유저 찾지 못한 경우")
    public void fail_UpdateInformation_NotFoundMember() throws Exception {
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createMemberDto();
        Long memberId = 1213123L;
        memberService.saveMember(memberSaveRequestDto);

        //when
        String changeInformation = "test";

        MemberDTO.MemberUpdateInformationRequestDto informationRequest = MemberDTO.MemberUpdateInformationRequestDto.builder()
                .information(changeInformation)
                .build();


        //then
        assertThrows(GeneralException.class, () -> memberService.updateInformation(informationRequest, memberId));
    }

    @Test
    @DisplayName("회원 삭제 성공")
    public void success_DeleteMember() throws Exception {
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createMemberDto();
        Long memberId = memberService.saveMember(memberSaveRequestDto);
        Optional<Member> byId = memberRepository.findById(memberId);
        Member member = byId.get();
        //when
        String email = EMAIL;
        String password = PASSWORD;

        MemberDTO.MemberDeleteRequestDto deleteRequestDto = MemberDTO.MemberDeleteRequestDto.builder()
                .email(email)
                .password(password)
                .build();

        System.out.println("memberId = " + memberId);
        System.out.println("member.getPassword() = " + member.getPassword());

        memberService.deleteMember(deleteRequestDto, memberId);

        //then
        assertThat(memberRepository.findByEmail(email)).isEqualTo(null);
    }

    @Test
    @DisplayName("회원 삭제 실패 - 유저가 존재하지 않는 경우")
    public void fail_DeleteMember_NotFountMember() throws Exception {
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createMemberDto();
        Long memberId = 1213123L;
        memberService.saveMember(memberSaveRequestDto);

        //when
        String email = "EMAIL";
        String password = PASSWORD;
        MemberDTO.MemberDeleteRequestDto deleteRequestDto = MemberDTO.MemberDeleteRequestDto.builder()
                .email(email)
                .password(password)
                .build();

        //then
        assertThrows(GeneralException.class, () -> memberService.deleteMember(deleteRequestDto, memberId));

    }

    @Test
    @DisplayName("회원 삭제 실패 - 삭제 요청 이메일이 일치하지 않는 경우")
    public void fail_DeleteMember_InvalidEmail() throws Exception {
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createMemberDto();
        Long memberId = memberService.saveMember(memberSaveRequestDto);

        //when
        String email = "EMAIL";
        String password = PASSWORD;
        MemberDTO.MemberDeleteRequestDto deleteRequestDto = MemberDTO.MemberDeleteRequestDto.builder()
                .email(email)
                .password(password)
                .build();

        //then
        assertThrows(GeneralException.class, () -> memberService.deleteMember(deleteRequestDto, memberId));

    }

    @Test
    @DisplayName("회원 삭제 실패 - 비밀번호가 잘못된 경우")
    public void fail_DeleteMember_IncorrectPassword() throws Exception {
        //given
        MemberDTO.MemberSaveRequestDto memberSaveRequestDto = createMemberDto();
        Long memberId = memberService.saveMember(memberSaveRequestDto);

        //when
        String email = EMAIL;
        String password = "PASSWORD";

        MemberDTO.MemberDeleteRequestDto deleteRequestDto = MemberDTO.MemberDeleteRequestDto.builder()
                .email(email)
                .password(password)
                .build();
        //then
        assertThrows(GeneralException.class, () -> memberService.deleteMember(deleteRequestDto, memberId));
    }

    @Test
    @DisplayName("회원 가입 성공")
    public void success_saveMember() throws Exception {
        //given
        MemberDTO.MemberSaveRequestDto saveRequestDto = createMemberDto();

        //when
        Long memberId = memberService.saveMember(saveRequestDto);

        //then
        assertThat(memberId).isEqualTo(memberRepository.findById(memberId).get().getId());
    }




    @Test
    @DisplayName("로그인 성공")
    public void success_Login() throws Exception {
        //given
        Long memberId = memberService.saveMember(createMemberDto());
        Member findMember = memberService.findMemberByMemberId(memberId);

        //when
        MemberDTO.MemberLoginRequestDto memberLoginRequest = MemberDTO.MemberLoginRequestDto.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .build();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MemberDTO.MemberLoginServiceResponseDto loginServiceResponseDto = memberService.login(memberLoginRequest,response,request);

        //then
        assertThat(loginServiceResponseDto.getEmail()).isEqualTo(findMember.getEmail());
    }

    @Test
    @DisplayName("로그인 실패 - 이메일이 잘못된 경우")
    public void fail_Login_WrongEmail() throws Exception {
        //given
        Long memberId = memberService.saveMember(createMemberDto());

        //when
        MemberDTO.MemberLoginRequestDto memberLoginRequest = MemberDTO.MemberLoginRequestDto.builder()
                .email("EMAIL")
                .password(PASSWORD)
                .build();

        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest();
        //then
        assertThrows(GeneralException.class, () -> memberService.login(memberLoginRequest,response,request));
    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호가 일치하지 않는 경우")
    public void fail_Login_WrongPassword() throws Exception {
        //given
        Long memberId = memberService.saveMember(createMemberDto());

        //when
        MemberDTO.MemberLoginRequestDto memberLoginRequest = MemberDTO.MemberLoginRequestDto.builder()
                .email(EMAIL)
                .password("PASSWORD")
                .build();

        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest();
        //then
        assertThrows(GeneralException.class, () -> memberService.login(memberLoginRequest,response,request));
    }

    @Test
    @DisplayName("로그인 실패 - 데이터가 안넘어온경우")
    public void fail_Login_NoData() throws Exception {
        //given
        Long memberId = memberService.saveMember(createMemberDto());

        //when
        MemberDTO.MemberLoginRequestDto memberLoginRequest = MemberDTO.MemberLoginRequestDto.builder()
                .email(null)
                .password(null)
                .build();

        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest();
        //then
        assertThrows(GeneralException.class, () -> memberService.login(memberLoginRequest,response,request));
    }



    @Test
    @DisplayName("이메일 중복 체크 성공")
    public void success_CheckDuplicateEmail_TRUE() throws Exception{
        //given
        memberService.saveMember(createMemberDto());

        //when
        boolean duplicateCheck = memberService.emailDuplicateCheck(EMAIL);

        //then
        assertThat(duplicateCheck).isTrue();
    }

    @Test
    @DisplayName("이메일 중복 체크 성공")
    public void success_CheckDuplicateEmail_FALSE() throws Exception{
        //given
        memberService.saveMember(createMemberDto());

        //when
        boolean duplicateCheck = memberService.emailDuplicateCheck("test@test.net");

        //then
        assertThat(duplicateCheck).isFalse();
    }


}