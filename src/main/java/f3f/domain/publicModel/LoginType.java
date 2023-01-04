package f3f.domain.publicModel;

import lombok.Getter;

import java.util.Arrays;

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

    public static LoginType valueOfLabel(String label) {
        return Arrays.stream(values())
                .filter(value -> value.type.equals(label))
                .findAny()
                .orElse(null);
    }
}
