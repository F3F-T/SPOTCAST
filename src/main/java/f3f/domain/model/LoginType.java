package f3f.domain.model;

import lombok.Getter;

@Getter
public enum LoginType {
    //
    GENERAL_LOGIN(1,"일반"),GOOGLE_LOGIN(2,"구글"),KAKAO_LOGIN(3,"카카오"),NAVER_LOGIN(4,"네이버");

    private long id;
    private String type;

    LoginType(long id, String type) {
        this.id = id;
        this.type = type;
    }
}
