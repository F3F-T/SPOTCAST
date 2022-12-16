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
        private LoginMemberType loginMemberType;

        @Builder
        public MemberLoginRequestDto(String email, String password, LoginMemberType loginMemberType) {
            this.email = email;
            this.password = password;
            this.loginMemberType = loginMemberType;
        }

        public void passwordEncryption(PasswordEncoder passwordEncoder) {
            this.password = passwordEncoder.encode(password);
        }

        public UsernamePasswordAuthenticationToken toAuthentication(){
            return new UsernamePasswordAuthenticationToken(email,password);
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

        public String passwordEncryption(PasswordEncoder passwordEncoder) {
            return passwordEncoder.encode(password);
        }
    }


    @Getter
    @NoArgsConstructor
    public static class MemberUpdatePasswordRequestDto {

        private String email;
        private String beforePassword;
        private String afterPassword;

        @Builder
        public MemberUpdatePasswordRequestDto(String email, String beforePassword, String afterPassword) {
            this.email = email;
            this.beforePassword = beforePassword;
            this.afterPassword = afterPassword;
        }

        public void passwordEncryption(PasswordEncoder passwordEncoder) {
            this.beforePassword = passwordEncoder.encode(beforePassword);
            this.afterPassword = passwordEncoder.encode(afterPassword);
        }
    }


    @Getter
    @NoArgsConstructor
    public static class MemberUpdateNicknameRequestDto {

        private String email;

        @NotBlank
        @Length(min = 3, max = 20)
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{3,20}$")
        private String nickname;

        @Builder

        public MemberUpdateNicknameRequestDto(String email, String nickname) {
            this.email = email;
            this.nickname = nickname;
        }
    }


    @Getter
    @NoArgsConstructor
    public static class MemberUpdateInformationRequestDto {

        private String email;

        @NotBlank
        private String information;

        @Builder

        public MemberUpdateInformationRequestDto(String email, String information) {
            this.email = email;
            this.information = information;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class MemberUpdatePhoneRequestDto {

        private String email;

        @NotBlank
        @Length(min = 10, max = 11)
        private String phone;
        @Builder

        public MemberUpdatePhoneRequestDto(String email, String phone) {
            this.email = email;
            this.phone = phone;
        }
    }


}
