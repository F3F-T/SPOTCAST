package f3f.domain.teamApply.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ApplyServiceTest {


    @Test
    @DisplayName("지원하기_성공")
    void saveApply_success()throws Exception{
        //given

        //when

        //then
    }


    @Test
    @DisplayName("지원하기_실패_구인글 아님")
    void saveApply_fail_not_recruit()throws Exception{
        //given

        //when

        //then
    }


    @Test
    @DisplayName("지원하기_실패_유저정보 없음")
    void saveApply_fail_unknown_userInfo()throws Exception{
        //given

        //when

        //then
    }

    @Test
    @DisplayName("지원취소_실패_본인 지원글 아님")
    void cancelApply_fail_missMatchUser()throws Exception{
        //given

        //when

        //then
    }

    @Test
    @DisplayName("지원취소_성공_본인 지원글 아님")
    void cancelApply_success()throws Exception{
        //given

        //when

        //then
    }


    @Test
    @DisplayName("지원 조회_성공_본인이 지원한 구인글 조회")
    void getApplyInfo_success()throws Exception{
        //given

        //when

        //then
    }

}