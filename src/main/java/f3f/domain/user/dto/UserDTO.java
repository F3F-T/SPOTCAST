package f3f.domain.user.dto;

import f3f.domain.model.LoginBase;
import f3f.domain.model.UserType;
import f3f.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class UserDTO {

    @Getter
    public static class SaveRequest {

        @Embedded
        private LoginBase loginBase;

        @Enumerated(value = EnumType.STRING)
        private UserType userType;

        private String information;

        private String phone;

        @Builder
        public SaveRequest(LoginBase loginBase, UserType userType, String information, String phone) {
            this.loginBase = loginBase;
            this.userType = userType;
            this.information = information;
            this.phone = phone;
        }

        public User toEntity(){
                return User.builder()
                        .loginBase(this.loginBase)
                        .userType(this.userType)
                        .phone(this.phone)
                        .information(this.information)
                        .build();
        }
    }

    @Getter
    public static class UserInfoDTO {

        @Embedded
        private LoginBase loginBase;

        @Enumerated(value = EnumType.STRING)
        private UserType userType;

        private String information;

        private String phone;

        @Builder
        public UserInfoDTO(LoginBase loginBase, UserType userType, String information, String phone) {
            this.loginBase = loginBase;
            this.userType = userType;
            this.information = information;
            this.phone = phone;
        }
    }
}
