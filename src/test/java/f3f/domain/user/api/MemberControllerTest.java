package f3f.domain.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import f3f.domain.publicModel.Authority;
import f3f.domain.publicModel.LoginType;
import f3f.domain.user.application.EmailCertificationService;
import f3f.domain.user.application.MemberService;
import f3f.domain.user.dao.MemberRepository;
import f3f.domain.user.dto.MemberDTO;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static f3f.domain.publicModel.LoginMemberType.GENERAL_USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
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
    @Test
    @DisplayName("회원 가입 성공 테스트")
    public void success_signUp() throws Exception{
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
    public void fail_signUp() throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto saveRequestDto = createSignUpRequest();


        // when
        doThrow(new GeneralException(ErrorCode.DUPLICATION_EMAIL,"이미 가입되어 있는 이메일입니다."))
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
    public void success_login() throws Exception{
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
    public void fail_login_wrongEmail() throws Exception{
        //given
        MemberDTO.MemberLoginRequestDto loginRequest = createLoginRequest();

        // when
        doThrow(new GeneralException(ErrorCode.NOTFOUND_MEMBER,"이미 가입되어 있는 이메일입니다."))
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
    public void fail_login_wrongPassword() throws Exception{
        //given
        MemberDTO.MemberLoginRequestDto loginRequest = createLoginRequest();

        // when
        doThrow(new GeneralException(ErrorCode.NOTFOUND_MEMBER,"이미 가입되어 있는 이메일입니다."))
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

}
