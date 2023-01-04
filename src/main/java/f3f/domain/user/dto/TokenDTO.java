package f3f.domain.user.dto;

import f3f.domain.user.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenDTO {

    private String grantType;
    private String accessToken;

    private String refreshToken;

    private Long accessTokenExpiresIn;

    @Builder
    public TokenDTO(String grantType, String accessToken, String refreshToken, Long accessTokenExpiresIn) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpiresIn = accessTokenExpiresIn;
    }

    public MemberDTO.MemberLoginServiceResponseDto toLoginEntity(Member member){
        return MemberDTO.MemberLoginServiceResponseDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .loginMemberType(member.getLoginMemberType())
                .authority(member.getAuthority())
                .grantType(this.grantType)
                .accessToken(this.accessToken)
                .accessTokenExpiresIn(this.accessTokenExpiresIn)
                .build();
    }
    public TokenResponseDTO toEntity(){
        return TokenResponseDTO.builder()
                .grantType(this.grantType)
                .accessToken(this.accessToken)
                .accessTokenExpiresIn(this.accessTokenExpiresIn)
                .build();
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