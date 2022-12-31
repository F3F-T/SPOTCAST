package f3f.domain.scrap.dto;

import f3f.domain.scrap.domain.Scrap;
import f3f.domain.user.domain.Member;
import lombok.Builder;
import lombok.Getter;

public class ScrapDTO {


    @Getter
    public static class SaveRequest {

        private String name;


        @Builder
        public SaveRequest(String name) {
            this.name = name;
        }



        public Scrap toEntity(Member member) {
            return Scrap.builder()
                    .name(this.name)
                    .member(member)
                    .build();
        }
    }

    @Getter
    public static class ScrapInfoDTO{
        private Long scrapId;
        private String name;

        @Builder
        public ScrapInfoDTO(Long scrapId, String name) {
            this.scrapId = scrapId;
            this.name = name;
        }
    }

}
