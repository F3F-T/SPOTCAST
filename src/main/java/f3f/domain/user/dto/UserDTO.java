package f3f.domain.user.dto;

import com.sun.istack.NotNull;
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
        public SaveRequest(String email, String password, LoginUserType loginUserType,
                           LoginType loginType,UserType userType, String information, String phone) {
            this.email = email;
            this.password = password;
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
                        .loginUserType(this.loginUserType)
                        .loginType(this.loginType)
                        .userType(this.userType)
                        .phone(this.phone)
                        .information(this.information)
                        .build();
        }
    }

    @Getter
    public static class FindUserDTO {

        private String email;

        @Enumerated(value = EnumType.STRING)
        private LoginUserType loginUserType;

        @Enumerated(value = EnumType.STRING)
        private LoginType loginType;

        @Enumerated(value = EnumType.STRING)
        private UserType userType;

        private String information;

        private String phone;

        @Builder
        public FindUserDTO(String email, LoginUserType loginUserType, LoginType loginType,
                           UserType userType, String information, String phone) {
            this.email = email;
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
}
