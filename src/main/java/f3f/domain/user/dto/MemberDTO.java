package f3f.domain.user.dto;

import f3f.domain.publicModel.LoginType;
import f3f.domain.publicModel.LoginMemberType;
import f3f.domain.publicModel.Authority;
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


        @Enumerated(value = EnumType.STRING)
        private LoginMemberType loginMemberType;

        @Enumerated(value = EnumType.STRING)
        private LoginType loginType;

        @Enumerated(value = EnumType.STRING)
        private Authority authority;

        private String field;



        public void passwordEncryption(PasswordEncoder passwordEncoder) {
            this.password = passwordEncoder.encode(password);
        }

        @Builder
        public MemberSaveRequestDto(String email, String password, String name,
                                    LoginMemberType loginMemberType, LoginType loginType, Authority authority, String field) {
            this.email = email;
            this.password = password;
            this.name = name;
            this.loginMemberType = loginMemberType;
            this.loginType = loginType;
            this.authority = authority;
            this.field = field;
        }

        public Member toEntity(){
                return Member.builder()
                        .email(this.email)
                        .password(this.password)
                        .name(this.name)
                        .loginMemberType(this.loginMemberType)
                        .loginType(this.loginType)
                        .authority(this.authority)
                        .field(this.field)
                        .profile("https://shopping-phinf.pstatic.net/main_2343561/23435610490.20211228162539.jpg?type=f640")
                        .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class MemberInfoResponseDto {

        private Long id;
        private String email;

        private String name;

        private String twitter;

        private String instagram;

        private String otherSns;

        private String profile;
        private String egName;

        private String field;

        @Enumerated(value = EnumType.STRING)
        private LoginMemberType loginMemberType;

        @Enumerated(value = EnumType.STRING)
        private LoginType loginType;

        @Enumerated(value = EnumType.STRING)
        private Authority authority;

        private String information;


        @Builder
        public MemberInfoResponseDto(Long id, String email, String name, String twitter, String instagram, String otherSns, String egName, String field, LoginMemberType loginMemberType, LoginType loginType, Authority authority, String information, String profile) {
            this.id = id;
            this.email = email;
            this.name = name;
            this.twitter = twitter;
            this.instagram = instagram;
            this.otherSns = otherSns;
            this.egName = egName;
            this.field = field;
            this.loginMemberType = loginMemberType;
            this.loginType = loginType;
            this.authority = authority;
            this.information = information;
            this.profile = profile;
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

        private Long id;
        private String email;

        private Authority authority;

        private String name;

        private LoginMemberType loginMemberType;


        @Builder
        public MemberLoginServiceResponseDto(Long id, String email, Authority authority, String name, LoginMemberType loginMemberType) {
            this.id = id;
            this.email = email;
            this.authority = authority;
            this.name = name;
            this.loginMemberType = loginMemberType;
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
    public static class MemberUpdateInformationRequestDto {

        private String information;

        private String twitter;

        private String instagram;

        private String otherSns;

        private String field;

        private String egName;

        @Builder
        public MemberUpdateInformationRequestDto(String information, String twitter, String instagram, String otherSns, String field, String egName) {
            this.information = information;
            this.twitter = twitter;
            this.instagram = instagram;
            this.otherSns = otherSns;
            this.field = field;
            this.egName = egName;
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

    @Getter
    @NoArgsConstructor
    public static class MemberBoardInfoResponseDto {

        private Long id;
        private String email;

        private String name;

        private String nickname;



        @Builder
        public MemberBoardInfoResponseDto(Long id, String email, String name, String nickname) {
            this.id = id;
            this.email = email;
            this.name = name;
            this.nickname = nickname;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class OtherMemberInfoResponseDto {

        private Long id;
        private String email;

        private String name;

        private String information;

        private String twitter;

        private String instagram;

        private String otherSns;
        @Builder
        public OtherMemberInfoResponseDto(Long id, String email, String name, String information, String twitter, String instagram, String otherSns) {
            this.id = id;
            this.email = email;
            this.name = name;
            this.information = information;
            this.twitter = twitter;
            this.instagram = instagram;
            this.otherSns = otherSns;
        }
    }




}
