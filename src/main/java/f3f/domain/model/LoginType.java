package f3f.domain.model;

import lombok.Getter;

@Getter
public enum LoginType {
    //
    GENERAL_LOGIN(1,"LOCAL"),GOOGLE_LOGIN(2,"GOOGLE"),KAKAO_LOGIN(3,"KAKAO"),NAVER_LOGIN(4,"NAVER");

    private long id;
    private String type;

    LoginType(long id, String type) {
        this.id = id;
        this.type = type;
    }
}
