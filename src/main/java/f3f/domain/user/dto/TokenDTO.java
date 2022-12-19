package f3f.domain.user.dto;

import f3f.domain.user.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenDTO {


    @Getter
    @NoArgsConstructor
    public static class TokenSaveDTO {
        private String grantType;
        private String accessToken;

        private String refreshToken;

        private Long accessTokenExpiresIn;

        @Builder
        public TokenSaveDTO(String grantType, String accessToken, String refreshToken, Long accessTokenExpiresIn) {
            this.grantType = grantType;
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
            this.accessTokenExpiresIn = accessTokenExpiresIn;
        }

        public TokenResponseDTO toEntity(){
            return TokenResponseDTO.builder()
                    .grantType(this.grantType)
                    .accessToken(this.accessToken)
                    .accessTokenExpiresIn(this.accessTokenExpiresIn)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class TokenResponseDTO {

        private String grantType;
        private String accessToken;

        private Long accessTokenExpiresIn;

        @Builder
        public TokenResponseDTO(String grantType, String accessToken, Long accessTokenExpiresIn) {
            this.grantType = grantType;
            this.accessToken = accessToken;
            this.accessTokenExpiresIn = accessTokenExpiresIn;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class TokenRequestDTO {

        private String accessToken;

        @Builder
        public TokenRequestDTO(String accessToken) {
            this.accessToken = accessToken;
        }
    }

}