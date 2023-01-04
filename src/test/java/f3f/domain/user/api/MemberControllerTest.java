package f3f.domain.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import f3f.domain.publicModel.Authority;
import f3f.domain.publicModel.LoginType;
import f3f.domain.user.application.MemberService;
import f3f.domain.user.dao.MemberRepository;
import f3f.domain.user.dto.MemberDTO;
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
                .nickname("nickname")
                .phone("01012345678")
                .email("userEmail@email.com")
                .information("info")
                .password("password")
                .loginMemberType(GENERAL_USER)
                .loginType(LoginType.GENERAL_LOGIN)
                .authority(Authority.ROLE_USER)
                .build();
    }

    @Test
    @DisplayName("회원 가입 성공 테스트")
    public void success_signUp() throws Exception{
        //given
        MemberDTO.MemberSaveRequestDto saveRequestDto = createSignUpRequest();
        memberService.saveMember(saveRequestDto);
//        Member member = memberRepository.findByEmail(signUpRequest.getEmail()).get();

        // then
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saveRequestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("auth/signup/successful", requestFields(
                        fieldWithPath("name").description("The user's name"),
                        fieldWithPath("nickname").description("The user's nickname"),
                        fieldWithPath("phone").description("The user's phoneNumber"),
                        fieldWithPath("email").description("The user's email"),
                        fieldWithPath("password").description("The user's password"),
                        fieldWithPath("information").description("The user's information"),
                        fieldWithPath("loginMemberType").description("The user's loginMemberType"),
                        fieldWithPath("loginType").description("The user's loginType"),
                        fieldWithPath("authority").description("The user's authority")

                )));

        // TODO 컨트롤러 테스트 코드 ㅌ작성 및 에러 코드 대응
    }

}
