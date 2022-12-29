package f3f.domain.user.dto;

import f3f.domain.model.LoginType;
import f3f.domain.model.LoginMemberType;
import f3f.domain.model.Authority;
import f3f.domain.user.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Builder
public class MemberDTO {

    @Getter
    @NoArgsConstructor
    public static class MemberSaveRequestDto {

        @Email
        @NotBlank
        private String email;

        @Length(min = 8, max = 20)
        @NotBlank
        private String password;

        @NotBlank
        private String name;

        @NotBlank
        @Length(min = 3, max = 20)
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{3,20}$")
        private String nickname;

        @Enumerated(value = EnumType.STRING)
        private LoginMemberType loginMemberType;

        @Enumerated(value = EnumType.STRING)
        private LoginType loginType;

        @Enumerated(value = EnumType.STRING)
        private Authority authority;

        @NotBlank
        private String information;

        @NotBlank
        @Length(min = 10, max = 11)
        private String phone;


        public void passwordEncryption(PasswordEncoder passwordEncoder) {
            this.password = passwordEncoder.encode(password);
        }

        @Builder
        public MemberSaveRequestDto(String email, String password, String name, String nickname,
                                    LoginMemberType loginMemberType, LoginType loginType, Authority authority, String information, String phone) {
            this.email = email;
            this.password = password;
            this.name = name;
            this.nickname = nickname;
            this.loginMemberType = loginMemberType;
            this.loginType = loginType;
            this.authority = authority;
            this.information = information;
            this.phone = phone;
        }

        public Member toEntity(){
                return Member.builder()
                        .email(this.email)
                        .password(this.password)
                        .nickname(this.nickname)
                        .name(this.name)
                        .loginMemberType(this.loginMemberType)
                        .loginType(this.loginType)
                        .authority(this.authority)
                        .phone(this.phone)
                        .information(this.information)
                        .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class MemberInfoResponseDto {

        private String email;

        private String name;

        private String nickname;
        @Enumerated(value = EnumType.STRING)
        private LoginMemberType loginMemberType;

        @Enumerated(value = EnumType.STRING)
        private LoginType loginType;

        @Enumerated(value = EnumType.STRING)
        private Authority authority;

        private String information;

        private String phone;

        @Builder
        public MemberInfoResponseDto(String email, String name, String nickname, LoginMemberType loginMemberType,
                                     LoginType loginType, Authority authority, String information, String phone) {
            this.email = email;
            this.name = name;
            this.nickname = nickname;
            this.loginMemberType = loginMemberType;
            this.loginType = loginType;
            this.authority = authority;
            this.information = information;
            this.phone = phone;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class MemberLoginRequestDto {

        private String email;
        private String password;

        @Builder
        public MemberLoginRequestDto(String email, String password) {
            this.email = email;
            this.password = password;
        }


        public UsernamePasswordAuthenticationToken toAuthentication(){
            return new UsernamePasswordAuthenticationToken(email,password);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class MemberLoginServiceResponseDto {

        private String email;

        private Authority authority;

        private String name;

        private String nickname;

        private LoginMemberType loginMemberType;

        private String grantType;

        private String accessToken;

        private String refreshToken;

        private Long accessTokenExpiresIn;

        @Builder
        public MemberLoginServiceResponseDto(String email, Authority authority, String name, String nickname, LoginMemberType loginMemberType, String grantType, String accessToken, String refreshToken, Long accessTokenExpiresIn) {
            this.email = email;
            this.authority = authority;
            this.name = name;
            this.nickname = nickname;
            this.loginMemberType = loginMemberType;
            this.grantType = grantType;
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
            this.accessTokenExpiresIn = accessTokenExpiresIn;
        }

        public MemberLoginResponseDto toEntity(){
            return MemberLoginResponseDto.builder()
                    .email(this.email)
                    .authority(this.authority)
                    .nickname(this.nickname)
                    .name(this.name)
                    .loginMemberType(this.loginMemberType)
                    .grantType(this.grantType)
                    .accessToken(this.accessToken)
                    .accessTokenExpiresIn(this.accessTokenExpiresIn)
                    .build();
        }


    }



    @Getter
    @NoArgsConstructor
    public static class MemberLoginResponseDto {

        private String Email;

        private Authority authority;

        private String name;

        private String nickname;

        private LoginMemberType loginMemberType;

        private String grantType;

        private String accessToken;

        private Long accessTokenExpiresIn;

        @Builder

        public MemberLoginResponseDto(String email, Authority authority, String name, String nickname, LoginMemberType loginMemberType, String grantType, String accessToken, Long accessTokenExpiresIn) {
            Email = email;
            this.authority = authority;
            this.name = name;
            this.nickname = nickname;
            this.loginMemberType = loginMemberType;
            this.grantType = grantType;
            this.accessToken = accessToken;
            this.accessTokenExpiresIn = accessTokenExpiresIn;
        }
    }
    @Getter
    @NoArgsConstructor
    public static class MemberDeleteRequestDto {

        private String email;
        private String password;

        @Builder
        public MemberDeleteRequestDto(String email, String password) {
            this.email = email;
            this.password = password;
        }

    }


    @Getter
    @NoArgsConstructor
    public static class MemberUpdateLoginPasswordRequestDto {

        private String email;
        private String beforePassword;
        private String afterPassword;

        @Builder
        public MemberUpdateLoginPasswordRequestDto(String email, String beforePassword, String afterPassword) {
            this.email = email;
            this.beforePassword = beforePassword;
            this.afterPassword = afterPassword;
        }

    }

    @Getter
    @NoArgsConstructor
    public static class MemberUpdateForgotPasswordRequestDto {

        private String email;
        private String afterPassword;

        @Builder
        public MemberUpdateForgotPasswordRequestDto(String email, String afterPassword) {
            this.email = email;
            this.afterPassword = afterPassword;
        }

        public void passwordEncryption(PasswordEncoder passwordEncoder) {
            this.afterPassword = passwordEncoder.encode(afterPassword);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class MemberUpdateNicknameRequestDto {

        @NotBlank
        @Length(min = 3, max = 20)
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{3,20}$")
        private String nickname;

        @Builder
        public MemberUpdateNicknameRequestDto(String nickname) {
            this.nickname = nickname;
        }
    }


    @Getter
    @NoArgsConstructor
    public static class MemberUpdateInformationRequestDto {

        @NotBlank
        private String information;

        @Builder
        public MemberUpdateInformationRequestDto(String information) {
            this.information = information;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class MemberUpdatePhoneRequestDto {

        @NotBlank
        @Length(min = 10, max = 11)
        private String phone;
        @Builder

        public MemberUpdatePhoneRequestDto(String phone) {
            this.phone = phone;
        }
    }


    @Getter
    @NoArgsConstructor
    public static class EmailCertificationRequest{
        private String email;
        private String certificationNumber;

        @Builder
        public EmailCertificationRequest(String email, String certificationNumber) {
            this.email = email;
            this.certificationNumber = certificationNumber;
        }
    }
}
