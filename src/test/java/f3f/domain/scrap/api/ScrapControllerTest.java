package f3f.domain.scrap.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import f3f.domain.scrap.application.ScrapService;
import f3f.domain.scrap.dto.ScrapDTO;
import f3f.domain.scrapBoard.dto.ScrapBoardDTO;
import f3f.domain.user.api.MemberAuthController;
import f3f.domain.user.api.MemberController;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer.sharedHttpSession;

@WebMvcTest({ScrapController.class})
@MockBean(JpaMetamodelMappingContext.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class ScrapControllerTest {

    @MockBean
    private ScrapService scrapService;

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

    public ScrapDTO.SaveRequest createSaveRequest() {
        return ScrapDTO.SaveRequest.builder()
                .name("testscrap")
                .build();
    }

    public ScrapDTO.ScrapInfoDTO createResponseDTO() {
        return ScrapDTO.ScrapInfoDTO.builder()
                .scrapId(1L)
                .name("responseName")
                .build();
    }

    public ScrapDTO.UpdateRequest createUpdateRequest() {
        return ScrapDTO.UpdateRequest.builder()
                .scrapId(1L)
                .name("updateName")
                .build();
    }

    public ScrapDTO.ScrapDeleteRequestDTO createDeleteRequest() {
        return ScrapDTO.ScrapDeleteRequestDTO.builder()
                .scrapId(1L)
                .build();
    }


    @Test
    @DisplayName("스크랩 박스 생성 성공")
    public void success_saveScrapBox() throws Exception {
        //given
        ScrapDTO.SaveRequest saveRequest = createSaveRequest();
        Long memberId = 1L;
        //when

        //then
        mockMvc.perform(post("/member/{memberId}/scrap", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(saveRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("member/scrap/save/success", requestFields(
                        fieldWithPath("name").description("스크랩 박스 이름")

                )));
    }


    @Test
    @DisplayName("스크랩 박스 생성 실패")
    public void fail_saveScrapBox_NotFoundMember() throws Exception {
        //given
        ScrapDTO.SaveRequest saveRequest = createSaveRequest();
        Long memberId = 1L;
        //when
        doThrow(new GeneralException(ErrorCode.NOTFOUND_MEMBER, "존재하지 않는 사용자입니다."))
                .when(scrapService).saveScrapBox(any(), any());
        //then
        mockMvc.perform(post("/member/{memberId}/scrap", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(saveRequest)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(document("member/scrap/save/fail", requestFields(
                        fieldWithPath("name").description("스크랩 박스 이름")

                )));
    }

    @Test
    @DisplayName("스크랩 박스 삭제 성공")
    public void success_deleteScrapBox() throws Exception {
        //given
        ScrapDTO.ScrapDeleteRequestDTO deleteRequest = createDeleteRequest();
        Long memberId = 1L;
        //when

        //then
        mockMvc.perform(delete("/member/{memberId}/scrap", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(deleteRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("member/scrap/delete/success", requestFields(
                        fieldWithPath("scrapId").description("스크랩 박스 Id")

                )));
    }

    @Test
    @DisplayName("스크랩 박스 삭제 실패 - 존재하지 않는 사용자")
    public void success_deleteScrapBox_NotFoundMember() throws Exception {
        //given
        ScrapDTO.ScrapDeleteRequestDTO deleteRequest = createDeleteRequest();
        Long memberId = 1L;
        //when
        doThrow(new GeneralException(ErrorCode.NOTFOUND_MEMBER, "존재하지 않는 사용자입니다."))
                .when(scrapService).deleteScrapBox(any(), any());
        //then
        mockMvc.perform(delete("/member/{memberId}/scrap", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(deleteRequest)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(document("member/scrap/delete/fail/NotFoundMember", requestFields(
                        fieldWithPath("scrapId").description("스크랩 박스 Id")

                )));
    }

    @Test
    @DisplayName("스크랩 박스 삭제 실패 - 존재하지 않는 스크랩박스")
    public void success_deleteScrapBox_NotFoundScrapBox() throws Exception {
        //given
        ScrapDTO.ScrapDeleteRequestDTO deleteRequest = createDeleteRequest();
        Long memberId = 1L;
        //when
        doThrow(new GeneralException(ErrorCode.NOTFOUND_SCRAPBOX, "존재하지 않는 스크랩박스입니다."))
                .when(scrapService).deleteScrapBox(any(), any());
        //then
        mockMvc.perform(delete("/member/{memberId}/scrap", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(deleteRequest)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(document("member/scrap/delete/fail/NotFoundScrapBox", requestFields(
                        fieldWithPath("scrapId").description("스크랩 박스 Id")

                )));
    }

    @Test
    @DisplayName("스크랩 박스 삭제 실패 - 일치하지 않는 사용자")
    public void success_deleteScrapBox_NOTCURRENT_MEMBER() throws Exception {
        //given
        ScrapDTO.ScrapDeleteRequestDTO deleteRequest = createDeleteRequest();
        Long memberId = 1L;
        //when
        doThrow(new GeneralException(ErrorCode.NOTCURRENT_MEMBER, "스크랩과 유저 정보가 일치하지 않습니다."))
                .when(scrapService).deleteScrapBox(any(), any());
        //then
        mockMvc.perform(delete("/member/{memberId}/scrap", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(deleteRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(document("member/scrap/delete/fail/NotCurrentMember", requestFields(
                        fieldWithPath("scrapId").description("스크랩 박스 Id")

                )));
    }

    @Test
    @DisplayName("스크랩 박스 리스트 조회 성공")
    public void success_getScrapBoxList() throws Exception {
        //given
        ScrapDTO.ScrapInfoDTO scrapInfoDTO1 = createResponseDTO();
        ScrapDTO.ScrapInfoDTO scrapInfoDTO2 = createResponseDTO();
        List<ScrapDTO.ScrapInfoDTO> scrapInfoDTOList = new ArrayList<>();

        scrapInfoDTOList.add(scrapInfoDTO1);
        scrapInfoDTOList.add(scrapInfoDTO2);
        Long memberId = 1L;
        //when
        given(scrapService.getScrapBoxList(any())).willReturn(scrapInfoDTOList);
        //then
        mockMvc.perform(get("/member/{memberId}/scrap", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("member/scrap/getScrapBoxList/success",
                        pathParameters(
                                parameterWithName("memberId").description("조회할 member Id")
                        )
                ));
    }

    @Test
    @DisplayName("스크랩 박스 리스트 조회 실패")
    public void fail_getScrapBoxList() throws Exception {
        //given
        ScrapDTO.ScrapInfoDTO scrapInfoDTO1 = createResponseDTO();
        ScrapDTO.ScrapInfoDTO scrapInfoDTO2 = createResponseDTO();
        List<ScrapDTO.ScrapInfoDTO> scrapInfoDTOList = new ArrayList<>();

        scrapInfoDTOList.add(scrapInfoDTO1);
        scrapInfoDTOList.add(scrapInfoDTO2);
        Long memberId = 1L;
        //when
        doThrow(new GeneralException(ErrorCode.NOTFOUND_MEMBER, "존재하지 않는 사용자입니다."))
                .when(scrapService).getScrapBoxList(any());
        //then
        mockMvc.perform(get("/member/{memberId}/scrap", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(document("member/scrap/getScrapBoxList/fail",
                        pathParameters(
                                parameterWithName("memberId").description("조회할 member Id")
                        )
                ));
    }

    @Test
    @DisplayName("스크랩 박스 이름 변경 성공")
    public void success_updateScrapBox() throws Exception {
        //given
        ScrapDTO.UpdateRequest updateRequest = createUpdateRequest();
        Long memberId = 1L;
        //when

        //then
        mockMvc.perform(patch("/member/{memberId}/scrap", memberId)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(updateRequest)
                ))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("member/scrap/updateScrapBox/success",
                        pathParameters(
                                parameterWithName("memberId").description("조회할 member Id")
                        ), requestFields(
                                fieldWithPath("name").description("변경할 스크랩 박스 이름"),
                                fieldWithPath("scrapId").description("스크랩 박스 Id")
                        )
                ));
    }

    @Test
    @DisplayName("스크랩 박스 이름 변경 실패")
    public void fail_updateScrapBox() throws Exception {
        //given
        ScrapDTO.UpdateRequest updateRequest = createUpdateRequest();
        Long memberId = 1L;
        //when
        doThrow(new GeneralException(ErrorCode.NOTFOUND_MEMBER, "존재하지 않는 사용자입니다."))
                .when(scrapService).updateScrapBox(any(), any());
        //then
        mockMvc.perform(patch("/member/{memberId}/scrap", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(updateRequest)
                        ))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(document("member/scrap/updateScrapBox/fail/NotFoundMember",
                        requestFields(
                                fieldWithPath("name").description("변경할 스크랩 박스 이름"),
                                fieldWithPath("scrapId").description("스크랩 박스 Id")
                        ),pathParameters(
                                parameterWithName("memberId").description("조회할 member Id")
                        )
                        ));
    }

    @Test
    @DisplayName("스크랩 박스 이름 변경 실패")
    public void fail_updateScrapBox_NotFoundScrapBox() throws Exception {
        //given
        ScrapDTO.UpdateRequest updateRequest = createUpdateRequest();
        Long memberId = 1L;
        //when
        doThrow(new GeneralException(ErrorCode.NOTFOUND_SCRAPBOX,"존재하지 않는 스크랩박스입니다."))
                .when(scrapService).updateScrapBox(any(), any());
        //then
        mockMvc.perform(patch("/member/{memberId}/scrap", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(updateRequest)
                        ))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(document("member/scrap/updateScrapBox/fail/NotFoundScrapBox",
                        pathParameters(
                                parameterWithName("memberId").description("조회할 member Id")
                        ), requestFields(
                                fieldWithPath("name").description("변경할 스크랩 박스 이름"),
                                fieldWithPath("scrapId").description("스크랩 박스 Id")
                        )
                ));
    }

}
