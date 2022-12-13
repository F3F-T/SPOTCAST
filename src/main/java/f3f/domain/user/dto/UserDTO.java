package f3f.domain.user.dto;

import f3f.domain.model.LoginType;
import f3f.domain.model.LoginUserType;
import f3f.domain.model.UserType;
import f3f.domain.user.domain.User;
import f3f.global.encrypt.EncryptionService;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Builder
public class UserDTO {

    @Getter
    @NoArgsConstructor
    public static class SaveRequest {



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
        private LoginUserType loginUserType;

        @Enumerated(value = EnumType.STRING)
        private LoginType loginType;

        @Enumerated(value = EnumType.STRING)
        private UserType userType;

        @NotBlank
        private String information;

        @NotBlank
        @Length(min = 10, max = 11)
        private String phone;


        public void passwordEncryption(EncryptionService encryptionService) {
            this.password = encryptionService.encrypt(password);
        }

        @Builder
        public SaveRequest(String email, String password, String name, String nickname,
                           LoginUserType loginUserType, LoginType loginType, UserType userType, String information, String phone) {
            this.email = email;
            this.password = password;
            this.name = name;
            this.nickname = nickname;
            this.loginUserType = loginUserType;
            this.loginType = loginType;
            this.userType = userType;
            this.information = information;
            this.phone = phone;
        }

        public User toEntity(){
                return User.builder()
                        .email(this.email)
                        .password(this.password)
                        .nickname(this.nickname)
                        .name(this.name)
                        .loginUserType(this.loginUserType)
                        .loginType(this.loginType)
                        .userType(this.userType)
                        .phone(this.phone)
                        .information(this.information)
                        .build();
        }
    }

    @Getter
    public static class UserInfoDTO {

        private String email;

        private String name;

        private String nickname;
        @Enumerated(value = EnumType.STRING)
        private LoginUserType loginUserType;

        @Enumerated(value = EnumType.STRING)
        private LoginType loginType;

        @Enumerated(value = EnumType.STRING)
        private UserType userType;

        private String information;

        private String phone;

        @Builder
        public UserInfoDTO(String email, String name, String nickname, LoginUserType loginUserType,
                           LoginType loginType, UserType userType, String information, String phone) {
            this.email = email;
            this.name = name;
            this.nickname = nickname;
            this.loginUserType = loginUserType;
            this.loginType = loginType;
            this.userType = userType;
            this.information = information;
            this.phone = phone;
        }
    }

    @Getter
    public static class LoginRequest{

        private String email;
        private String password;
        private LoginUserType loginUserType;

        @Builder
        public LoginRequest(String email, String password, LoginUserType loginUserType) {
            this.email = email;
            this.password = password;
            this.loginUserType = loginUserType;
        }

        public void passwordEncryption(EncryptionService encryptionService) {
            this.password = encryptionService.encrypt(password);
        }
    }

    @Getter
    public static class UpdatePasswordRequest{

        private String email;
        private String beforePassword;
        private String afterPassword;

        @Builder
        public UpdatePasswordRequest(String email, String beforePassword, String afterPassword) {
            this.email = email;
            this.beforePassword = beforePassword;
            this.afterPassword = afterPassword;
        }

        public void passwordEncryption(EncryptionService encryptionService) {
            this.beforePassword = encryptionService.encrypt(beforePassword);
            this.afterPassword = encryptionService.encrypt(afterPassword);
        }
    }


    @Getter
    public static class UpdateNicknameRequest{

        private String email;

        @NotBlank
        @Length(min = 3, max = 20)
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{3,20}$")
        private String nickname;

        @Builder

        public UpdateNicknameRequest(String email, String nickname) {
            this.email = email;
            this.nickname = nickname;
        }
    }


    @Getter
    public static class UpdateInformationRequest {

        private String email;

        @NotBlank
        private String information;

        @Builder

        public UpdateInformationRequest(String email, String information) {
            this.email = email;
            this.information = information;
        }
    }

}
