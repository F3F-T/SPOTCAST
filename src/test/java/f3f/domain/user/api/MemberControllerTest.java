//package f3f.domain.user.api;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import f3f.domain.publicModel.Authority;
//import f3f.domain.publicModel.LoginMemberType;
//import f3f.domain.publicModel.LoginType;
//import f3f.domain.user.application.EmailCertificationService;
//import f3f.domain.user.application.MemberService;
//import f3f.domain.user.dao.MemberRepository;
//import f3f.domain.user.dto.MemberDTO;
//import f3f.domain.user.dto.TokenDTO;
//import f3f.global.annotation.WithMockCustomUser;
//import f3f.global.response.ErrorCode;
//import f3f.global.response.GeneralException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
//import org.springframework.http.MediaType;
//import org.springframework.restdocs.RestDocumentationContextProvider;
//import org.springframework.restdocs.RestDocumentationExtension;
//import org.springframework.restdocs.payload.JsonFieldType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//
//import static f3f.domain.publicModel.LoginMemberType.GENERAL_USER;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.when;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
//import static org.springframework.restdocs.payload.PayloadDocumentation.*;
//import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
//import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer.sharedHttpSession;
//
//@WebMvcTest({MemberController.class, MemberAuthController.class})
//@MockBean(JpaMetamodelMappingContext.class)
//@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
//public class MemberControllerTest {
//    @MockBean
//    private MemberService memberService;
//
//    @MockBean
//    private MemberRepository memberRepository;
//
//    @MockBean
//    private EmailCertificationService emailCertificationService;
//
//
//    private MockMvc mockMvc;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//
//    @BeforeEach
//    public void setup(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentationContextProvider) {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//                .apply(sharedHttpSession())
//                .apply(documentationConfiguration(restDocumentationContextProvider))
//                .build();
//    }
//
//    // 회원가입 DTO 생성 메소드
//    public MemberDTO.MemberSaveRequestDto createSignUpRequest() {
//        return MemberDTO.MemberSaveRequestDto.builder()
//                .name("username")
//                .email("userEmail@email.com")
//                .password("password")
//                .loginMemberType(GENERAL_USER)
//                .loginType(LoginType.GENERAL_LOGIN)
//                .authority(Authority.ROLE_USER)
//                .build();
//    }
//
//    // 로그인 DTO 생성 메소드
//    public MemberDTO.MemberLoginRequestDto createLoginRequest() {
//        return MemberDTO.MemberLoginRequestDto.builder()
//                .email("userEmail@email.com")
//                .password("password")
//                .build();
//    }
//
//    public MemberDTO.MemberInfoResponseDto createMemberInfo() {
//        return MemberDTO.MemberInfoResponseDto.builder()
//                .id(1L)
//                .email("userEmail@email.com")
//                .name("userName")
//                .twitter("twitter Info")
//                .instagram("instagram Info")
//                .otherSns("other sns Info")
//                .loginMemberType(GENERAL_USER)
//                .loginType(LoginType.GENERAL_LOGIN)
//                .authority(Authority.ROLE_USER)
//                .information("information")
//                .build();
//    }
//
//    public MemberDTO.MemberLoginServiceResponseDto createLoginResponse() {
//        return MemberDTO.MemberLoginServiceResponseDto.builder()
//                .id(1L)
//                .email("userEmail@email.com")
//                .name("userName")
//                .loginMemberType(GENERAL_USER)
//                .authority(Authority.ROLE_USER)
//                .build();
//    }
//
//    @Test
//    @DisplayName("회원 가입 성공 테스트")
//    public void success_signUp() throws Exception {
//        //given
//        MemberDTO.MemberSaveRequestDto saveRequestDto = createSignUpRequest();
//
//        // then
//        mockMvc.perform(post("/auth/signup")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .content(objectMapper.writeValueAsString(saveRequestDto)))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("auth/signup/success", requestFields(
//                        fieldWithPath("email").description("로그인 시 사용되는 사용자 이메일"),
//                        fieldWithPath("name").description("사용자의 이름"),
//                        fieldWithPath("password").description("비밀번호"),
//                        fieldWithPath("loginMemberType").description("일반인인지 회사 직원인지 회사 인지 판단"),
//                        fieldWithPath("loginType").description("로그인 타입(일반로그인 or 간편로그인)"),
//                        fieldWithPath("authority").description("admin 인지 일반 user 인지 판단"),
//                        fieldWithPath("information").description("한줄소개")
//
//                )));
//
//    }
//
//    @Test
//    @DisplayName("회원 가입 실패 테스트")
//    public void fail_signUp() throws Exception {
//        //given
//        MemberDTO.MemberSaveRequestDto saveRequestDto = createSignUpRequest();
//
//
//        // when
//        doThrow(new GeneralException(ErrorCode.DUPLICATION_EMAIL, "이미 가입되어 있는 이메일입니다."))
//                .when(memberService).saveMember(any());
//
//        // then
//        mockMvc.perform(post("/auth/signup")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .content(objectMapper.writeValueAsString(saveRequestDto)))
//                .andDo(print())
//                .andExpect(status().isConflict())
//                .andDo(document("auth/signup/fail/duplicateEmail", requestFields(
//                        fieldWithPath("email").description("로그인 시 사용되는 사용자 이메일"),
//                        fieldWithPath("name").description("사용자의 이름"),
//                        fieldWithPath("password").description("비밀번호"),
//                        fieldWithPath("loginMemberType").description("일반인인지 회사 직원인지 회사 인지 판단"),
//                        fieldWithPath("loginType").description("로그인 타입(일반로그인 or 간편로그인)"),
//                        fieldWithPath("authority").description("admin 인지 일반 user 인지 판단"),
//                        fieldWithPath("information").description("한줄소개")
//                )));
//    }
//
//    @Test
//    @DisplayName("로그인 성공 테스트")
//    public void success_login() throws Exception {
//        //given
//        MemberDTO.MemberLoginRequestDto loginRequest = createLoginRequest();
//        MemberDTO.MemberLoginServiceResponseDto loginResponse = createLoginResponse();
//
//        //when
//        given(memberService.login(any(),any())).willReturn(loginResponse);
//
//        //then
//        mockMvc.perform(post("/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .content(objectMapper.writeValueAsString(loginRequest)))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("auth/login/success", requestFields(
//                                fieldWithPath("email").description("로그인 시 사용되는 사용자 이메일"),
//                                fieldWithPath("password").description("비밀번호")),
//                        responseFields(
//                                fieldWithPath("success").description("성공 여부"),
//                                fieldWithPath("code").description("http 상태 코드"),
//                                fieldWithPath("message").description("실패 시 실패 메세지"),
//                                fieldWithPath("data").description("member info"),
//                                fieldWithPath("data.id").description("member id"),
//                                fieldWithPath("data.email").description("member email"),
//                                fieldWithPath("data.name").description("member name"),
//                                fieldWithPath("data.loginMemberType").description("member loginMemberType"),
//                                fieldWithPath("data.authority").description("member authority"),
//                                fieldWithPath("data.grantType").description("token grantType"),
//                                fieldWithPath("data.accessToken").description("accessToken value"),
//                                fieldWithPath("data.accessTokenExpiresIn").description("accessToken expire time")
//                        )));
//    }
//
//    @Test
//    @DisplayName("로그인 실패 테스트 - 잘못된 이메일")
//    public void fail_login_wrongEmail() throws Exception {
//        //given
//        MemberDTO.MemberLoginRequestDto loginRequest = createLoginRequest();
//
//        // when
//        doThrow(new GeneralException(ErrorCode.INVALID_EMAIL_AND_PASSWORD_REQUEST, "아이디 또는 비밀번호가 일치하지 않습니다."))
//                .when(memberService).login(any(),any());
//
//        //then
//        mockMvc.perform(post("/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .content(objectMapper.writeValueAsString(loginRequest)))
//                .andDo(print())
//                .andExpect(status().isNotFound())
//                .andDo(document("auth/login/fail/wrongEmail", requestFields(
//                        fieldWithPath("email").description("로그인 시 사용되는 사용자 이메일"),
//                        fieldWithPath("password").description("비밀번호")
//                )));
//    }
//
//    @Test
//    @DisplayName("로그인 실패 테스트 - 잘못된 비밀번호")
//    public void fail_login_wrongPassword() throws Exception {
//        //given
//        MemberDTO.MemberLoginRequestDto loginRequest = createLoginRequest();
//
//        // when
//        doThrow(new GeneralException(ErrorCode.INVALID_EMAIL_AND_PASSWORD_REQUEST, "아이디 또는 비밀번호가 일치하지 않습니다."))
//                .when(memberService).login(any(),any());
//
//        //then
//        mockMvc.perform(post("/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .content(objectMapper.writeValueAsString(loginRequest)))
//                .andDo(print())
//                .andExpect(status().isNotFound())
//                .andDo(document("auth/login/fail/wrongPassword", requestFields(
//                        fieldWithPath("email").description("로그인 시 사용되는 사용자 이메일"),
//                        fieldWithPath("password").description("비밀번호")
//                )));
//    }
//
//    @Test
//    @DisplayName("토큰 재발급 성공")
//    public void success_reissue() throws Exception {
//        //given
//        TokenDTO.TokenRequestDTO tokenRequestDTO =
//                TokenDTO.TokenRequestDTO
//                        .builder()
//                        .accessToken("testToken").build();
//
//        TokenDTO.TokenResponseDTO tokenResponseDTO = TokenDTO.TokenResponseDTO.builder()
//                .accessToken("accesstoken value")
//                .accessTokenExpiresIn(1234L)
//                .grantType("grantType")
//                .build();
//        //when
//        given(memberService.reissue(any())).willReturn(tokenResponseDTO);
//        //then
//        mockMvc.perform(post("/auth/reissue")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .content(objectMapper.writeValueAsString(tokenRequestDTO)))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("auth/reissue/success", requestFields(
//                                fieldWithPath("accessToken").description("Access Token 값")
//                        ),
//                        responseFields(
//                                fieldWithPath("success").description("성공 여부"),
//                                fieldWithPath("code").description("http 상태 코드"),
//                                fieldWithPath("message").description("실패 시 실패 메세지"),
//                                fieldWithPath("data").description("token info"),
//                                fieldWithPath("data.accessToken").description("Access Token 값"),
//                                fieldWithPath("data.grantType").description("Access Token 타입"),
//                                fieldWithPath("data.accessTokenExpiresIn").description("Access Token 만료시간")
//                        )
//                ));
//    }
//
//    @Test
//    @DisplayName("토큰 재발급 실패 - 사용자 조회 실패")
//    public void fail_reissue_NORFOUND_MEMBER() throws Exception {
//        //given
//        TokenDTO.TokenRequestDTO tokenRequestDTO = TokenDTO.TokenRequestDTO.builder().accessToken("testToken").build();
//
//        //when
//        doThrow(new GeneralException(ErrorCode.NOTFOUND_MEMBER, "존재하지 않는 사용자입니다."))
//                .when(memberService).reissue(any());
//
//        //then
//        mockMvc.perform(post("/auth/reissue")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .content(objectMapper.writeValueAsString(tokenRequestDTO)))
//                .andDo(print())
//                .andExpect(status().isNotFound())
//                .andDo(document("auth/reissue/fail/NotFoundMember", requestFields(
//                        fieldWithPath("accessToken").description("Access Token 값")
//                )));
//    }
//
//    @Test
//    @DisplayName("토큰 재발급 실패 - Refresh Token 조회 실패")
//    public void fail_reissue_NORFOUND_REFRESHTOKEN() throws Exception {
//        //given
//        TokenDTO.TokenRequestDTO tokenRequestDTO = TokenDTO.TokenRequestDTO.builder().accessToken("testToken").build();
//
//        //when
//        doThrow(new GeneralException(ErrorCode.NOTFOUND_REFRESHTOKEN, "로그아웃 된 사용자입니다."))
//                .when(memberService).reissue(any());
//
//        //then
//        mockMvc.perform(post("/auth/reissue")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .content(objectMapper.writeValueAsString(tokenRequestDTO)))
//                .andDo(print())
//                .andExpect(status().isNotFound())
//                .andDo(document("auth/reissue/fail/NotFoundRefreshToken", requestFields(
//                        fieldWithPath("accessToken").description("Access Token 값")
//                )));
//    }
//
//    @Test
//    @DisplayName("토큰 재발급 실패 - 유효하지 않은 Refresh Token")
//    public void fail_reissue_Invalid_REFRESHTOKEN() throws Exception {
//        //given
//        TokenDTO.TokenRequestDTO tokenRequestDTO = TokenDTO.TokenRequestDTO.builder().accessToken("testToken").build();
//
//        //when
//        doThrow(new GeneralException(ErrorCode.INVALID_REFRESHTOKEN, "유효하지 않은 refresh token 입니다."))
//                .when(memberService).reissue(any());
//
//        //then
//        mockMvc.perform(post("/auth/reissue")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .content(objectMapper.writeValueAsString(tokenRequestDTO)))
//                .andDo(print())
//                .andExpect(status().isUnauthorized())
//                .andDo(document("auth/reissue/fail/InvalidRefreshToken", requestFields(
//                        fieldWithPath("accessToken").description("Access Token 값")
//                )));
//    }
//
//    @Test
//    @DisplayName("로그아웃 성공")
//    @WithMockCustomUser
//    public void success_logout() throws Exception {
//
//        mockMvc.perform(post("/auth/logout")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("auth/logout"));
//    }
//
//    @Test
//    @DisplayName("소셜 로그인 시 정보 조회 성공")
//    @WithMockCustomUser
//    public void success_findAuthInfoById() throws Exception {
//        //given
//        MemberDTO.MemberInfoResponseDto memberInfo = createMemberInfo();
//
//        //when
//        given(memberService.findMyInfo(any())).willReturn(memberInfo);
//
//        //then
//        mockMvc.perform(get("/auth/myInfo")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("auth/myInfo/success",
//                        responseFields(
//                                fieldWithPath("success").description("성공 여부"),
//                                fieldWithPath("code").description("http 상태 코드"),
//                                fieldWithPath("message").description("실패 시 실패 메세지"),
//                                fieldWithPath("data").description("member info"),
//                                fieldWithPath("data.id").description("member id"),
//                                fieldWithPath("data.email").description("member email"),
//                                fieldWithPath("data.name").description("member name"),
//                                fieldWithPath("data.twitter").description("member twitter"),
//                                fieldWithPath("data.instagram").description("member instagram"),
//                                fieldWithPath("data.otherSns").description("member otherSns"),
//                                fieldWithPath("data.loginMemberType").description("member loginMemberType"),
//                                fieldWithPath("data.loginType").description("member loginType"),
//                                fieldWithPath("data.authority").description("member authority"),
//                                fieldWithPath("data.information").description("member information")
//
//                        )));
//    }
//
//
//    @Test
//    @DisplayName("소셜 로그인 시 정보 조회 실패")
//    @WithMockCustomUser
//    public void fail_findAuthInfoById_NotFoundMember() throws Exception {
//        //given
//        MemberDTO.MemberInfoResponseDto memberInfo = createMemberInfo();
//
//        //when
//        doThrow(new GeneralException(ErrorCode.NOTFOUND_MEMBER, "존재하지 않는 사용자입니다."))
//                .when(memberService).findMyInfo(any());
//        //then
//        mockMvc.perform(get("/auth/myInfo")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                )
//                .andDo(print())
//                .andExpect(status().isNotFound())
//                .andDo(document("auth/myInfo/fail/NotFoundMember"));
//    }
//
//    @Test
//    @DisplayName("이메일 인증 번호 전송 성공")
//    @WithMockCustomUser
//    public void success_sendEmailCertification() throws Exception {
//        //given
//        MemberDTO.EmailCertificationRequest emailCertificationRequest = MemberDTO.EmailCertificationRequest.builder().email("test@naver.com").build();
//
//        //when
//
//        //then
//        mockMvc.perform(post("/auth/email-certification/sends")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .content(objectMapper.writeValueAsString(emailCertificationRequest))
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("auth/email-certification/sends/success",
//                        requestFields(
//                                fieldWithPath("email").description("인증번호 전송할 이메일"),
//                                fieldWithPath("certificationNumber").description("null")
//                        )));
//    }
//
//    @Test
//    @DisplayName("이메일 인증 번호 확인 성공")
//    @WithMockCustomUser
//    public void success_confirmEmailCertification() throws Exception {
//        //given
//        MemberDTO.EmailCertificationRequest emailCertificationRequest
//                = MemberDTO.EmailCertificationRequest
//                .builder()
//                .email("test@naver.com")
//                .certificationNumber("123456")
//                .build();
//
//        //when
//
//        //then
//        mockMvc.perform(post("/auth/email-certification/confirms")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .content(objectMapper.writeValueAsString(emailCertificationRequest))
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("auth/email-certification/confirms/success",
//                        requestFields(
//                                fieldWithPath("email").description("인증번호 전송할 이메일"),
//                                fieldWithPath("certificationNumber").description("인증 번호")
//                        )));
//    }
//
//    @Test
//    @DisplayName("이메일 인증 번호 확인 실패")
//    @WithMockCustomUser
//    public void fail_confirmEmailCertification_InvalidCertificationNumber() throws Exception {
//        //given
//        MemberDTO.EmailCertificationRequest emailCertificationRequest
//                = MemberDTO.EmailCertificationRequest
//                .builder()
//                .email("test@naver.com")
//                .certificationNumber("123456")
//                .build();
//
//        //when
//        doThrow(new GeneralException(ErrorCode.EMAIL_CERTIFICATION_MISMATCH, "인증번호가 일치하지 않습니다."))
//                .when(emailCertificationService).verifyEmail(any());
//
//        //then
//        mockMvc.perform(post("/auth/email-certification/confirms")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .content(objectMapper.writeValueAsString(emailCertificationRequest))
//                )
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//                .andDo(document("auth/email-certification/confirms/fail",
//                        requestFields(
//                                fieldWithPath("email").description("인증번호 전송할 이메일"),
//                                fieldWithPath("certificationNumber").description("인증 번호")
//                        )));
//    }
//
//    @Test
//    @DisplayName("이메일 중복검사 성공")
//    @WithMockCustomUser
//    public void success_duplicateCheckEmail() throws Exception {
//        //given
//        MemberDTO.MemberInfoResponseDto memberInfo = createMemberInfo();
//        String email = memberInfo.getEmail();
//
//        //when
//
//        //then
//        mockMvc.perform(get("/auth/member-emails/{email}/exists", email)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("auth/member-emails/exists/success",
//                        pathParameters(
//                                parameterWithName("email").description("중복 검사할 email")
//                        )));
//    }
//
//    @Test
//    @DisplayName("회원 탈퇴 성공")
//    @WithMockCustomUser
//    public void success_deleteMember() throws Exception {
//        //given
//        MemberDTO.MemberSaveRequestDto signUpRequest = createSignUpRequest();
//        MemberDTO.MemberDeleteRequestDto deleteRequestDto = MemberDTO.MemberDeleteRequestDto.builder()
//                .email(signUpRequest.getEmail())
//                .password(signUpRequest.getPassword())
//                .build();
//        Long memberId = 1L;
//        //when
//
//        //then
//        mockMvc.perform(delete("/member/{memberId}", memberId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .content(objectMapper.writeValueAsString(deleteRequestDto))
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("member/deleteMember/success",
//                        pathParameters(
//                                parameterWithName("memberId").description("Member 의 ID")
//                        ),
//                        requestFields(
//                                fieldWithPath("email").description("사용자 email"),
//                                fieldWithPath("password").description("사용자 password")
//                        )));
//    }
//
//    @Test
//    @DisplayName("회원 탈퇴 실패 - 현재 사용자가 아님")
//    @WithMockCustomUser
//    public void fail_deleteMember_NotCurrentMember() throws Exception {
//        //given
//        MemberDTO.MemberSaveRequestDto signUpRequest = createSignUpRequest();
//        MemberDTO.MemberDeleteRequestDto deleteRequestDto = MemberDTO.MemberDeleteRequestDto.builder()
//                .email(signUpRequest.getEmail())
//                .password(signUpRequest.getPassword())
//                .build();
//        Long memberId = 1L;
//
//        //when
//        doThrow(new GeneralException(ErrorCode.NOTCURRENT_MEMBER, "사용자 정보가 일치하지 않습니다."))
//                .when(memberService).deleteMember(any(), any());
//
//        //then
//        mockMvc.perform(delete("/member/{memberId}", memberId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .content(objectMapper.writeValueAsString(deleteRequestDto))
//                )
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//                .andDo(document("member/deleteMember/fail/NotCurrentMember",
//                        pathParameters(
//                                parameterWithName("memberId").description("Member 의 ID")
//                        ),
//                        requestFields(
//                                fieldWithPath("email").description("사용자 email"),
//                                fieldWithPath("password").description("사용자 password")
//                        )));
//    }
//
//    @Test
//    @DisplayName("회원 탈퇴 실패 - 이메일 또는 비밀번호가 일치하지 않을 경우")
//    @WithMockCustomUser
//    public void fail_deleteMember_InvalidRequest() throws Exception {
//        //given
//        MemberDTO.MemberSaveRequestDto signUpRequest = createSignUpRequest();
//        MemberDTO.MemberDeleteRequestDto deleteRequestDto = MemberDTO.MemberDeleteRequestDto.builder()
//                .email(signUpRequest.getEmail())
//                .password(signUpRequest.getPassword())
//                .build();
//        Long memberId = 1L;
//
//        //when
//        doThrow(new GeneralException(ErrorCode.INVALID_EMAIL_AND_PASSWORD_REQUEST, "아이디 또는 비밀번호가 일치하지 않습니다."))
//                .when(memberService).deleteMember(any(), any());
//
//        //then
//        mockMvc.perform(delete("/member/{memberId}", memberId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .content(objectMapper.writeValueAsString(deleteRequestDto))
//                )
//                .andDo(print())
//                .andExpect(status().isNotFound())
//                .andDo(document("member/deleteMember/fail/InvalidRequest",
//                        pathParameters(
//                                parameterWithName("memberId").description("Member 의 ID")
//                        ),
//                        requestFields(
//                                fieldWithPath("email").description("사용자 email"),
//                                fieldWithPath("password").description("사용자 password")
//                        )));
//    }
//
//
//    @Test
//    @DisplayName("회원 조회 성공")
//    @WithMockCustomUser
//    public void success_findMemberInfoById() throws Exception {
//        //given
//        MemberDTO.MemberInfoResponseDto memberInfo = createMemberInfo();
//        Long memberId = 1L;
//
//        //when
//        given(memberService.findMemberInfoByMemberId(any())).willReturn(memberInfo);
//        //then
//        mockMvc.perform(get("/member/{memberId}", memberId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("member/findMember/success",
//                        pathParameters(
//                                parameterWithName("memberId").description("Member 의 ID")
//                        ),
//                        responseFields(
//                                fieldWithPath("success").description("성공 여부"),
//                                fieldWithPath("code").description("http 상태 코드"),
//                                fieldWithPath("message").description("실패 시 실패 메세지"),
//                                fieldWithPath("data").description("member info"),
//                                fieldWithPath("data.id").description("member id"),
//                                fieldWithPath("data.email").description("member email"),
//                                fieldWithPath("data.name").description("member name"),
//                                fieldWithPath("data.twitter").description("member twitter"),
//                                fieldWithPath("data.instagram").description("member instagram"),
//                                fieldWithPath("data.otherSns").description("member otherSns"),
//                                fieldWithPath("data.loginMemberType").description("member loginMemberType"),
//                                fieldWithPath("data.loginType").description("member loginType"),
//                                fieldWithPath("data.authority").description("member authority"),
//                                fieldWithPath("data.information").description("member information")
//
//                        )));
//    }
//
//    @Test
//    @DisplayName("회원 조회 실패")
//    @WithMockCustomUser
//    public void fail_findMemberInfoById_NotFoundMember() throws Exception {
//        //given
//        Long memberId = 1L;
//
//        //when
//        doThrow(new GeneralException(ErrorCode.NOTFOUND_MEMBER, "존재하지 않는 사용자입니다."))
//                .when(memberService).findMemberInfoByMemberId(any());
//        //then
//        mockMvc.perform(get("/member/{memberId}", memberId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                )
//                .andDo(print())
//                .andExpect(status().isNotFound())
//                .andDo(document("member/findMember/fail",
//                        pathParameters(
//                                parameterWithName("memberId").description("Member 의 ID")
//                        )));
//    }
//
//
//    @Test
//    @DisplayName("내 정보 조회 성공")
//    @WithMockCustomUser
//    public void success_findMyInfoById() throws Exception {
//        //given
//        MemberDTO.MemberInfoResponseDto memberInfo = createMemberInfo();
//        Long memberId = 1L;
//
//        //when
//        given(memberService.findMyInfo(any())).willReturn(memberInfo);
//        //then
//        mockMvc.perform(get("/member/{memberId}/myInfo", memberId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("member/myInfo/success",
//                        pathParameters(
//                                parameterWithName("memberId").description("Member 의 ID")
//                        ),
//                        responseFields(
//                                fieldWithPath("success").description("성공 여부"),
//                                fieldWithPath("code").description("http 상태 코드"),
//                                fieldWithPath("message").description("실패 시 실패 메세지"),
//                                fieldWithPath("data").description("member info"),
//                                fieldWithPath("data.id").description("member id"),
//                                fieldWithPath("data.email").description("member email"),
//                                fieldWithPath("data.name").description("member name"),
//                                fieldWithPath("data.twitter").description("member twitter"),
//                                fieldWithPath("data.instagram").description("member instagram"),
//                                fieldWithPath("data.otherSns").description("member otherSns"),
//                                fieldWithPath("data.loginMemberType").description("member loginMemberType"),
//                                fieldWithPath("data.loginType").description("member loginType"),
//                                fieldWithPath("data.authority").description("member authority"),
//                                fieldWithPath("data.information").description("member information")
//                        )));
//    }
//
//
//    @Test
//    @DisplayName("내 정보 조회 실패 - 요청한 사용자가 잘못된 경우")
//    @WithMockCustomUser
//    public void fail_findMyInfoById_NotCurrentMember() throws Exception {
//        //given
//        MemberDTO.MemberInfoResponseDto memberInfo = createMemberInfo();
//        Long memberId = 1L;
//
//        //when
//        doThrow(new GeneralException(ErrorCode.NOTCURRENT_MEMBER, "사용자 정보가 일치하지 않습니다."))
//                .when(memberService).findMyInfo(any());
//
//        //then
//        mockMvc.perform(get("/member/{memberId}/myInfo", memberId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                )
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//                .andDo(document("member/myInfo/fail/NotCurrentUser",
//                        pathParameters(
//                                parameterWithName("memberId").description("Member 의 ID")
//                        ),
//                        responseFields(
//                                fieldWithPath("success").description("성공 여부"),
//                                fieldWithPath("code").description("http 상태 코드"),
//                                fieldWithPath("message").description("실패 시 실패 메세지")
//                        )));
//    }
//
//    @Test
//    @DisplayName("내 정보 조회 실패 - 사용자가 존재하지 않는 경우")
//    @WithMockCustomUser
//    public void fail_findMyInfoById_NotFoundMember() throws Exception {
//        //given
//        MemberDTO.MemberInfoResponseDto memberInfo = createMemberInfo();
//        Long memberId = 1L;
//
//        //when
//        doThrow(new GeneralException(ErrorCode.NOTFOUND_MEMBER, "존재하지 않는 사용자입니다."))
//                .when(memberService).findMyInfo(any());
//
//        //then
//        mockMvc.perform(get("/member/{memberId}/myInfo", memberId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                )
//                .andDo(print())
//                .andExpect(status().isNotFound())
//                .andDo(document("member/myInfo/fail/NotFoundMember",
//                        pathParameters(
//                                parameterWithName("memberId").description("Member 의 ID")
//                        ),
//                        responseFields(
//                                fieldWithPath("success").description("성공 여부"),
//                                fieldWithPath("code").description("http 상태 코드"),
//                                fieldWithPath("message").description("실패 시 실패 메세지")
//                        )));
//    }
//
//    @Test
//    @DisplayName("비밀번호 변경 성공 - 로그인 X ")
//    @WithMockCustomUser
//    public void success_changePasswordByForgot() throws Exception {
//        //given
//        MemberDTO.MemberUpdateForgotPasswordRequestDto forgotPasswordRequestDto = MemberDTO.MemberUpdateForgotPasswordRequestDto.builder()
//                .email("userEmail@email.com")
//                .afterPassword("aftepassword")
//                .build();
//        Long memberId = 1L;
//
//        //when
//
//        //then
//        mockMvc.perform(post("/member/find/password")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .content(objectMapper.writeValueAsString(forgotPasswordRequestDto))
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("member/password/find/success",
//                        requestFields(
//                                fieldWithPath("email").description("이메일"),
//                                fieldWithPath("afterPassword").description("변경할 비밀번호")
//                        ),
//                        responseFields(
//                                fieldWithPath("success").description("성공 여부"),
//                                fieldWithPath("code").description("http 상태 코드"),
//                                fieldWithPath("message").description("실패 시 실패 메세지"),
//                                fieldWithPath("data").description("null")
//                        )));
//    }
//
//    @Test
//    @DisplayName("비밀번호 변경 실패- 로그인 X - member 조회 실패")
//    @WithMockCustomUser
//    public void fail_changePasswordByForgot_NotFoundMember() throws Exception {
//        //given
//        MemberDTO.MemberUpdateForgotPasswordRequestDto forgotPasswordRequestDto = MemberDTO.MemberUpdateForgotPasswordRequestDto.builder()
//                .email("userEmail@email.com")
//                .afterPassword("aftepassword")
//                .build();
//        Long memberId = 1L;
//
//        //when
//        doThrow(new GeneralException(ErrorCode.NOTFOUND_MEMBER, "존재하지 않는 사용자입니다."))
//                .when(memberService).updatePasswordByForgot(any());
//        //then
//        mockMvc.perform(post("/member/find/password")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .content(objectMapper.writeValueAsString(forgotPasswordRequestDto))
//                )
//                .andDo(print())
//                .andExpect(status().isNotFound())
//                .andDo(document("member/password/find/fail/NotFoundMember",
//                        requestFields(
//                                fieldWithPath("email").description("이메일"),
//                                fieldWithPath("afterPassword").description("변경할 비밀번호")
//                        ),
//                        responseFields(
//                                fieldWithPath("success").description("성공 여부"),
//                                fieldWithPath("code").description("http 상태 코드"),
//                                fieldWithPath("message").description("실패 시 실패 메세지")
//                        )));
//    }
//
//
//    @Test
//    @DisplayName("비밀번호 변경 실패- 로그인 X - 일반 로그인 유저 아님")
//    @WithMockCustomUser
//    public void fail_changePasswordByForgot_NotGeneralLoginType() throws Exception {
//        //given
//        MemberDTO.MemberUpdateForgotPasswordRequestDto forgotPasswordRequestDto = MemberDTO.MemberUpdateForgotPasswordRequestDto.builder()
//                .email("userEmail@email.com")
//                .afterPassword("aftepassword")
//                .build();
//        Long memberId = 1L;
//
//        //when
//        doThrow(new GeneralException(ErrorCode.NOTGENERAL_LOGIN, "일반 로그인만 변경 가능합니다."))
//                .when(memberService).updatePasswordByForgot(any());
//        //then
//        mockMvc.perform(post("/member/find/password")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .content(objectMapper.writeValueAsString(forgotPasswordRequestDto))
//                )
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//                .andDo(document("member/password/find/fail/NotGeneralLogin",
//                        requestFields(
//                                fieldWithPath("email").description("이메일"),
//                                fieldWithPath("afterPassword").description("변경할 비밀번호")
//                        ),
//                        responseFields(
//                                fieldWithPath("success").description("성공 여부"),
//                                fieldWithPath("code").description("http 상태 코드"),
//                                fieldWithPath("message").description("실패 시 실패 메세지")
//                        )));
//    }
//
//    @Test
//    @DisplayName("비밀번호 변경 성공 - 로그인 O ")
//    @WithMockCustomUser
//    public void success_changePasswordByLogin() throws Exception {
//        //given
//        MemberDTO.MemberUpdateLoginPasswordRequestDto forgotPasswordRequestDto = MemberDTO.MemberUpdateLoginPasswordRequestDto.builder()
//                .email("userEmail@email.com")
//                .beforePassword("beforepassword")
//                .afterPassword("aftepassword")
//                .build();
//        Long memberId = 1L;
//
//        //when
//
//        //then
//        mockMvc.perform(post("/member/{memberId}/change/password",memberId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .content(objectMapper.writeValueAsString(forgotPasswordRequestDto))
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("member/password/change/success",
//                        requestFields(
//                                fieldWithPath("email").description("이메일"),
//                                fieldWithPath("beforePassword").description("현재 비밀번호"),
//                                fieldWithPath("afterPassword").description("변경할 비밀번호")
//                        ),
//                        responseFields(
//                                fieldWithPath("success").description("성공 여부"),
//                                fieldWithPath("code").description("http 상태 코드"),
//                                fieldWithPath("message").description("실패 시 실패 메세지"),
//                                fieldWithPath("data").description("null")
//                        )));
//    }
//
//    @Test
//    @DisplayName("비밀번호 변경 실패 - 로그인 O - 현재 사용자 아님")
//    @WithMockCustomUser
//    public void fail_changePasswordByLogin_NotCurrentUser() throws Exception {
//        //given
//        MemberDTO.MemberUpdateLoginPasswordRequestDto forgotPasswordRequestDto = MemberDTO.MemberUpdateLoginPasswordRequestDto.builder()
//                .email("userEmail@email.com")
//                .beforePassword("beforepassword")
//                .afterPassword("aftepassword")
//                .build();
//        Long memberId = 1L;
//
//        //when
//        doThrow(new GeneralException(ErrorCode.NOTCURRENT_MEMBER, "사용자 정보가 일치하지 않습니다."))
//                .when(memberService).updatePassword(any(),any());
//
//        //then
//        mockMvc.perform(post("/member/{memberId}/change/password",memberId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .content(objectMapper.writeValueAsString(forgotPasswordRequestDto))
//                )
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//                .andDo(document("member/password/change/fail/NotCurrentMember",
//                        requestFields(
//                                fieldWithPath("email").description("이메일"),
//                                fieldWithPath("beforePassword").description("현재 비밀번호"),
//                                fieldWithPath("afterPassword").description("변경할 비밀번호")
//                        )));
//    }
//
//    @Test
//    @DisplayName("비밀번호 변경 실패 - 로그인 O - 비밀번호 불일치")
//    @WithMockCustomUser
//    public void fail_changePasswordByLogin_MissMatchPassword() throws Exception {
//        //given
//        MemberDTO.MemberUpdateLoginPasswordRequestDto forgotPasswordRequestDto = MemberDTO.MemberUpdateLoginPasswordRequestDto.builder()
//                .email("userEmail@email.com")
//                .beforePassword("beforepassword")
//                .afterPassword("aftepassword")
//                .build();
//        Long memberId = 1L;
//
//        //when
//        doThrow(new GeneralException(ErrorCode.INVALID_EMAIL_AND_PASSWORD_REQUEST, "아이디 또는 비밀번호가 일치하지 않습니다."))
//                .when(memberService).updatePassword(any(),any());
//
//        //then
//        mockMvc.perform(post("/member/{memberId}/change/password",memberId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .content(objectMapper.writeValueAsString(forgotPasswordRequestDto))
//                )
//                .andDo(print())
//                .andExpect(status().isNotFound())
//                .andDo(document("member/password/change/fail/MissMatchPassword",
//                        requestFields(
//                                fieldWithPath("email").description("이메일"),
//                                fieldWithPath("beforePassword").description("현재 비밀번호"),
//                                fieldWithPath("afterPassword").description("변경할 비밀번호")
//                        )));
//    }
//
//    @Test
//    @DisplayName("비밀번호 변경 실패 - 로그인 O - member 못찾음")
//    @WithMockCustomUser
//    public void fail_changePasswordByLogin_NotFoundMember() throws Exception {
//        //given
//        MemberDTO.MemberUpdateLoginPasswordRequestDto forgotPasswordRequestDto = MemberDTO.MemberUpdateLoginPasswordRequestDto.builder()
//                .email("userEmail@email.com")
//                .beforePassword("beforepassword")
//                .afterPassword("aftepassword")
//                .build();
//        Long memberId = 1L;
//
//        //when
//        doThrow(new GeneralException(ErrorCode.NOTFOUND_MEMBER, "존재하지 않는 사용자입니다."))
//                .when(memberService).updatePassword(any(),any());
//
//        //then
//        mockMvc.perform(post("/member/{memberId}/change/password",memberId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .content(objectMapper.writeValueAsString(forgotPasswordRequestDto))
//                )
//                .andDo(print())
//                .andExpect(status().isNotFound())
//                .andDo(document("member/password/change/fail/MemberNotFound",
//                        requestFields(
//                                fieldWithPath("email").description("이메일"),
//                                fieldWithPath("beforePassword").description("현재 비밀번호"),
//                                fieldWithPath("afterPassword").description("변경할 비밀번호")
//                        )));
//    }
//
//
//    @Test
//    @DisplayName("비밀번호 변경 실패 - 로그인 O - member 못찾음")
//    @WithMockCustomUser
//    public void fail_changePasswordByLogin_NotGeneralLogin() throws Exception {
//        //given
//        MemberDTO.MemberUpdateLoginPasswordRequestDto forgotPasswordRequestDto = MemberDTO.MemberUpdateLoginPasswordRequestDto.builder()
//                .email("userEmail@email.com")
//                .beforePassword("beforepassword")
//                .afterPassword("aftepassword")
//                .build();
//        Long memberId = 1L;
//
//        //when
//        doThrow(new GeneralException(ErrorCode.NOTGENERAL_LOGIN, "일반 로그인 회원만 가능합니다."))
//                .when(memberService).updatePassword(any(),any());
//
//        //then
//        mockMvc.perform(post("/member/{memberId}/change/password",memberId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .content(objectMapper.writeValueAsString(forgotPasswordRequestDto))
//                )
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//                .andDo(document("member/password/change/fail/NotGeneralLogin",
//                        requestFields(
//                                fieldWithPath("email").description("이메일"),
//                                fieldWithPath("beforePassword").description("현재 비밀번호"),
//                                fieldWithPath("afterPassword").description("변경할 비밀번호")
//                        )));
//    }
//
//
//    @Test
//    @DisplayName("information 변경 성공")
//    @WithMockCustomUser
//    public void success_updateInformation() throws Exception {
//        //given
//        MemberDTO.MemberUpdateInformationRequestDto updateInfo = MemberDTO.MemberUpdateInformationRequestDto.builder()
//                .information("updateInfo")
//                .build();
//        Long memberId = 1L;
//
//        //when
//
//        //then
//        mockMvc.perform(post("/member/{memberId}/change/information",memberId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .content(objectMapper.writeValueAsString(updateInfo))
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("member/information/change/success",
//                        requestFields(
//                                fieldWithPath("information").description("수정할 정보")
//                        ),
//                        responseFields(
//                                fieldWithPath("success").description("성공 여부"),
//                                fieldWithPath("code").description("http 상태 코드"),
//                                fieldWithPath("message").description("실패 시 실패 메세지"),
//                                fieldWithPath("data").description("null")
//                        )));
//    }
//
//    @Test
//    @DisplayName("information 변경 실패 - 현재 유저 아님")
//    @WithMockCustomUser
//    public void fail_updateInformation_NotCurrentMember() throws Exception {
//        //given
//        MemberDTO.MemberUpdateInformationRequestDto updateInfo = MemberDTO.MemberUpdateInformationRequestDto.builder()
//                .information("updateInfo")
//                .build();
//        Long memberId = 1L;
//
//        //when
//        doThrow(new GeneralException(ErrorCode.NOTCURRENT_MEMBER, "사용자 정보가 일치하지 않습니다."))
//                .when(memberService).updateInformation(any(),any());
//
//        //then
//        mockMvc.perform(post("/member/{memberId}/change/information",memberId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .content(objectMapper.writeValueAsString(updateInfo))
//                )
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//                .andDo(document("member/information/change/fail/NotCurrentUser",
//                        requestFields(
//                                fieldWithPath("information").description("수정할 정보")
//                        )
//                       ));
//    }
//
//
//    @Test
//    @DisplayName("information 변경 실패 - 멤버 못찾음")
//    @WithMockCustomUser
//    public void fail_updateInformation_NotFoundMember() throws Exception {
//        //given
//        MemberDTO.MemberUpdateInformationRequestDto updateInfo = MemberDTO.MemberUpdateInformationRequestDto.builder()
//                .information("updateInfo")
//                .build();
//        Long memberId = 1L;
//
//        //when
//        doThrow(new GeneralException(ErrorCode.NOTFOUND_MEMBER, "존재하지 않는 사용자입니다."))
//                .when(memberService).updateInformation(any(),any());
//
//        //then
//        mockMvc.perform(post("/member/{memberId}/change/information",memberId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .content(objectMapper.writeValueAsString(updateInfo))
//                )
//                .andDo(print())
//                .andExpect(status().isNotFound())
//                .andDo(document("member/information/change/fail/NotFoundMember",
//                        requestFields(
//                                fieldWithPath("information").description("수정할 정보")
//                        )
//                ));
//    }
//
//}
