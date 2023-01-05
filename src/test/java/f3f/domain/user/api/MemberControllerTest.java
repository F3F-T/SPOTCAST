package f3f.domain.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import f3f.domain.publicModel.Authority;
import f3f.domain.publicModel.LoginMemberType;
import f3f.domain.publicModel.LoginType;
import f3f.domain.user.application.EmailCertificationService;
import f3f.domain.user.application.MemberService;
import f3f.domain.user.dao.MemberRepository;
import f3f.domain.user.dto.MemberDTO;
import f3f.domain.user.dto.TokenDTO;
import f3f.global.annotation.WithMockCustomUser;
import f3f.global.response.ErrorCode;
import f3f.global.response.GeneralException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import static f3f.domain.publicModel.LoginMemberType.GENERAL_USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer.sharedHttpSession;

@WebMvcTest({MemberController.class, MemberAuthController.class})
@MockBean(JpaMetamodelMappingContext.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class MemberControllerTest {
    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private EmailCertificationService emailCertificationService;


    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(sharedHttpSession())
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .build();
    }

    // 회원가입 DTO 생성 메소드
    public MemberDTO.MemberSaveRequestDto createSignUpRequest() {
        return MemberDTO.MemberSaveRequestDto.builder()
                .name("username")
                .email("userEmail@email.com")
                .information("info")
                .password("password")
                .loginMemberType(GENERAL_USER)
                .loginType(LoginType.GENERAL_LOGIN)
                .authority(Authority.ROLE_USER)
                .build();
    }

    // 로그인 DTO 생성 메소드
    public MemberDTO.MemberLoginRequestDto createLoginRequest() {
        return MemberDTO.MemberLoginRequestDto.builder()
                .email("userEmail@email.com")
                .password("password")
                .build();
    }

    public MemberDTO.MemberInfoResponseDto createMemberInfo() {
        return MemberDTO.MemberInfoResponseDto.builder()
                .id(1L)
                .email("userEmail@email.com")
                .name("userName")
                .twitter("twitter Info")
                .instagram("instagram Info")
                .otherSns("other sns Info")
                .loginMemberType(GENERAL_USER)
                .loginType(LoginType.GENERAL_LOGIN)
                .authority(Authority.ROLE_USER)
                .information("information")
                .build();
    }

    @Test
    @DisplayName("회원 가입 성공 테스트")
    public void success_signUp() throws Exception {
        //given
        MemberDTO.MemberSaveRequestDto saveRequestDto = createSignUpRequest();

        // then
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(saveRequestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("auth/signup/success", requestFields(
                        fieldWithPath("email").description("로그인 시 사용되는 사용자 이메일"),
                        fieldWithPath("name").description("사용자의 이름"),
                        fieldWithPath("password").description("비밀번호"),
                        fieldWithPath("loginMemberType").description("일반인인지 회사 직원인지 회사 인지 판단"),
                        fieldWithPath("loginType").description("로그인 타입(일반로그인 or 간편로그인)"),
                        fieldWithPath("authority").description("admin 인지 일반 user 인지 판단"),
                        fieldWithPath("information").description("한줄소개")

                )));

    }

    @Test
    @DisplayName("회원 가입 실패 테스트")
    public void fail_signUp() throws Exception {
        //given
        MemberDTO.MemberSaveRequestDto saveRequestDto = createSignUpRequest();


        // when
        doThrow(new GeneralException(ErrorCode.DUPLICATION_EMAIL, "이미 가입되어 있는 이메일입니다."))
                .when(memberService).saveMember(any());

        // then
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(saveRequestDto)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andDo(document("auth/signup/fail/duplicateEmail", requestFields(
                        fieldWithPath("email").description("로그인 시 사용되는 사용자 이메일"),
                        fieldWithPath("name").description("사용자의 이름"),
                        fieldWithPath("password").description("비밀번호"),
                        fieldWithPath("loginMemberType").description("일반인인지 회사 직원인지 회사 인지 판단"),
                        fieldWithPath("loginType").description("로그인 타입(일반로그인 or 간편로그인)"),
                        fieldWithPath("authority").description("admin 인지 일반 user 인지 판단"),
                        fieldWithPath("information").description("한줄소개")

                )));
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void success_login() throws Exception {
        //given
        MemberDTO.MemberLoginRequestDto loginRequest = createLoginRequest();

        //then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("auth/login/success", requestFields(
                        fieldWithPath("email").description("로그인 시 사용되는 사용자 이메일"),
                        fieldWithPath("password").description("비밀번호")
                )));
    }

    @Test
    @DisplayName("로그인 실패 테스트 - 잘못된 이메일")
    public void fail_login_wrongEmail() throws Exception {
        //given
        MemberDTO.MemberLoginRequestDto loginRequest = createLoginRequest();

        // when
        doThrow(new GeneralException(ErrorCode.INVALID_EMAIL_AND_PASSWORD_REQUEST, "아이디 또는 비밀번호가 일치하지 않습니다."))
                .when(memberService).login(any());

        //then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(document("auth/login/fail/wrongEmail", requestFields(
                        fieldWithPath("email").description("로그인 시 사용되는 사용자 이메일"),
                        fieldWithPath("password").description("비밀번호")
                )));
    }

    @Test
    @DisplayName("로그인 실패 테스트 - 잘못된 비밀번호")
    public void fail_login_wrongPassword() throws Exception {
        //given
        MemberDTO.MemberLoginRequestDto loginRequest = createLoginRequest();

        // when
        doThrow(new GeneralException(ErrorCode.INVALID_EMAIL_AND_PASSWORD_REQUEST, "아이디 또는 비밀번호가 일치하지 않습니다."))
                .when(memberService).login(any());

        //then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(document("auth/login/fail/wrongPassword", requestFields(
                        fieldWithPath("email").description("로그인 시 사용되는 사용자 이메일"),
                        fieldWithPath("password").description("비밀번호")
                )));
    }

    @Test
    @DisplayName("토큰 재발급 성공")
    public void success_reissue() throws Exception {
        //given
        TokenDTO.TokenRequestDTO tokenRequestDTO =
                TokenDTO.TokenRequestDTO
                        .builder()
                        .accessToken("testToken").build();

        //when

        //then
        mockMvc.perform(post("/auth/reissue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(tokenRequestDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("auth/reissue/success", requestFields(
                        fieldWithPath("accessToken").description("Access Token 값")
                )));
    }

    @Test
    @DisplayName("토큰 재발급 실패 - 사용자 조회 실패")
    public void fail_reissue_NORFOUND_MEMBER() throws Exception {
        //given
        TokenDTO.TokenRequestDTO tokenRequestDTO = TokenDTO.TokenRequestDTO.builder().accessToken("testToken").build();

        //when
        doThrow(new GeneralException(ErrorCode.NOTFOUND_MEMBER, "존재하지 않는 사용자입니다."))
                .when(memberService).reissue(any());

        //then
        mockMvc.perform(post("/auth/reissue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(tokenRequestDTO)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(document("auth/reissue/fail/NotFoundMember", requestFields(
                        fieldWithPath("accessToken").description("Access Token 값")
                )));
    }

    @Test
    @DisplayName("토큰 재발급 실패 - Refresh Token 조회 실패")
    public void fail_reissue_NORFOUND_REFRESHTOKEN() throws Exception {
        //given
        TokenDTO.TokenRequestDTO tokenRequestDTO = TokenDTO.TokenRequestDTO.builder().accessToken("testToken").build();

        //when
        doThrow(new GeneralException(ErrorCode.NOTFOUND_REFRESHTOKEN, "로그아웃 된 사용자입니다."))
                .when(memberService).reissue(any());

        //then
        mockMvc.perform(post("/auth/reissue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(tokenRequestDTO)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(document("auth/reissue/fail/NotFoundRefreshToken", requestFields(
                        fieldWithPath("accessToken").description("Access Token 값")
                )));
    }

    @Test
    @DisplayName("토큰 재발급 실패 - 유효하지 않은 Refresh Token")
    public void fail_reissue_Invalid_REFRESHTOKEN() throws Exception {
        //given
        TokenDTO.TokenRequestDTO tokenRequestDTO = TokenDTO.TokenRequestDTO.builder().accessToken("testToken").build();

        //when
        doThrow(new GeneralException(ErrorCode.INVALID_REFRESHTOKEN, "유효하지 않은 refresh token 입니다."))
                .when(memberService).reissue(any());

        //then
        mockMvc.perform(post("/auth/reissue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(tokenRequestDTO)))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andDo(document("auth/reissue/fail/InvalidRefreshToken", requestFields(
                        fieldWithPath("accessToken").description("Access Token 값")
                )));
    }

    @Test
    @DisplayName("로그아웃 성공")
    @WithMockCustomUser
    public void success_logout() throws Exception {

        mockMvc.perform(post("/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("auth/logout"));
    }

    @Test
    @DisplayName("소셜 로그인 시 정보 조회 성공")
    @WithMockCustomUser
    public void success_findMemberInfoById() throws Exception {
        //given
        MemberDTO.MemberInfoResponseDto memberInfo = createMemberInfo();

        //when
        given(memberService.findMyInfo(any())).willReturn(memberInfo);

        //then
        mockMvc.perform(get("/auth/myInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("auth/myInfo/success",
                        responseFields(
                                fieldWithPath("success").description("성공 여부"),
                                fieldWithPath("code").description("http 상태 코드"),
                                fieldWithPath("message").description("실패 시 실패 메세지"),
                                fieldWithPath("data").description("member info"),
                                fieldWithPath("data.id").description("member id"),
                                fieldWithPath("data.email").description("member email"),
                                fieldWithPath("data.name").description("member name"),
                                fieldWithPath("data.twitter").description("member twitter"),
                                fieldWithPath("data.instagram").description("member instagram"),
                                fieldWithPath("data.otherSns").description("member otherSns"),
                                fieldWithPath("data.loginMemberType").description("member loginMemberType"),
                                fieldWithPath("data.loginType").description("member loginType"),
                                fieldWithPath("data.authority").description("member authority"),
                                fieldWithPath("data.information").description("member information")

                        )));
    }


    @Test
    @DisplayName("소셜 로그인 시 정보 조회 실패")
    @WithMockCustomUser
    public void fail_findMemberInfoById_NotFoundMember() throws Exception {
        //given
        MemberDTO.MemberInfoResponseDto memberInfo = createMemberInfo();

        //when
        doThrow(new GeneralException(ErrorCode.NOTFOUND_MEMBER, "존재하지 않는 사용자입니다."))
                .when(memberService).findMyInfo(any());
        //then
        mockMvc.perform(get("/auth/myInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(document("auth/myInfo/fail/NotFoundMember"));
    }

    @Test
    @DisplayName("이메일 인증 번호 전송 성공")
    @WithMockCustomUser
    public void success_sendEmailCertification() throws Exception {
        //given
        MemberDTO.EmailCertificationRequest emailCertificationRequest = MemberDTO.EmailCertificationRequest.builder().email("test@naver.com").build();

        //when

        //then
        mockMvc.perform(post("/auth/email-certification/sends")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(emailCertificationRequest))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("auth/email-certification/sends/success",
                        requestFields(
                                fieldWithPath("email").description("인증번호 전송할 이메일"),
                                fieldWithPath("certificationNumber").description("null")
                        )));
    }

    @Test
    @DisplayName("이메일 인증 번호 확인 성공")
    @WithMockCustomUser
    public void success_confirmEmailCertification() throws Exception {
        //given
        MemberDTO.EmailCertificationRequest emailCertificationRequest
                = MemberDTO.EmailCertificationRequest
                .builder()
                .email("test@naver.com")
                .certificationNumber("123456")
                .build();

        //when

        //then
        mockMvc.perform(post("/auth/email-certification/confirms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(emailCertificationRequest))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("auth/email-certification/confirms/success",
                        requestFields(
                                fieldWithPath("email").description("인증번호 전송할 이메일"),
                                fieldWithPath("certificationNumber").description("인증 번호")
                        )));
    }

    @Test
    @DisplayName("이메일 인증 번호 확인 실패")
    @WithMockCustomUser
    public void fail_confirmEmailCertification_InvalidCertificationNumber() throws Exception {
        //given
        MemberDTO.EmailCertificationRequest emailCertificationRequest
                = MemberDTO.EmailCertificationRequest
                .builder()
                .email("test@naver.com")
                .certificationNumber("123456")
                .build();

        //when
        doThrow(new GeneralException(ErrorCode.EMAIL_CERTIFICATION_MISMATCH, "인증번호가 일치하지 않습니다."))
                .when(emailCertificationService).verifyEmail(any());

        //then
        mockMvc.perform(post("/auth/email-certification/confirms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(emailCertificationRequest))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(document("auth/email-certification/confirms/fail",
                        requestFields(
                                fieldWithPath("email").description("인증번호 전송할 이메일"),
                                fieldWithPath("certificationNumber").description("인증 번호")
                        )));
    }

    @Test
    @DisplayName("이메일 중복검사 성공")
    @WithMockCustomUser
    public void success_duplicateCheckEmail() throws Exception {
        //given
        MemberDTO.MemberInfoResponseDto memberInfo = createMemberInfo();
        String email = memberInfo.getEmail();

        //when

        //then
        mockMvc.perform(get("/auth/member-emails/{email}/exists", email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("auth/member-emails/exists/success",
                        pathParameters(
                                parameterWithName("email").description("중복 검사할 email")
                        )));
    }

}
