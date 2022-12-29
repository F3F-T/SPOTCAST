package f3f.global.oauth;

import f3f.domain.model.LoginType;
import f3f.global.oauth.impl.FacebookOAuth2UserInfo;
import f3f.global.oauth.impl.GoogleOAuth2UserInfo;
import f3f.global.oauth.impl.KakaoOAuth2UserInfo;
import f3f.global.oauth.impl.NaverOAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String loginType, Map<String, Object> attributes) {
        switch (loginType) {
            case "GOOGLE": return new GoogleOAuth2UserInfo(attributes);
            case "FACEBOOK": return new FacebookOAuth2UserInfo(attributes);
            case "NAVER": return new NaverOAuth2UserInfo(attributes);
            case "KAKAO": return new KakaoOAuth2UserInfo(attributes);
            default: throw new IllegalArgumentException("Invalid Provider Type.");
        }
    }
}
