package f3f.domain.model;

public enum LoginType {
    //
    GENERAL_LOGIN(1,"general"),GOOGLE_LOGIN(2,"google"),KAKAO_LOGIN(3,"kakao"),NAVER_LOGIN(3,"naver");

    private long id;
    private String type;

    LoginType(long id, String type) {
        this.id = id;
        this.type = type;
    }
}
