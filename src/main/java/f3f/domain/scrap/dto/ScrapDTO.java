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
            Scrap scrap = Scrap.builder()
                    .name(this.name)
                    .member(member)
                    .build();
            member.addScrapList(scrap);
            return scrap;
        }
    }
    @Getter
    public static class UpdateRequest {

        private String name;
        private Long scrapId;

        @Builder

        public UpdateRequest(String name, Long scrapId) {
            this.name = name;
            this.scrapId = scrapId;
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

    @Getter
    public static class ScrapDeleteRequestDTO {
        private Long scrapId;

        @Builder
        public ScrapDeleteRequestDTO(Long scrapId) {
            this.scrapId = scrapId;
        }
    }

}
