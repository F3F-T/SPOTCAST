package f3f.global.oauth.handler;

import f3f.domain.user.dao.MemberRepository;
import f3f.domain.user.dao.RefreshTokenDao;
import f3f.domain.user.domain.Member;
import f3f.domain.user.dto.TokenDTO;
import f3f.global.constants.SecurityConstants;
import f3f.global.jwt.TokenProvider;
import f3f.global.oauth.OAuth2UserInfo;
import f3f.global.oauth.OAuth2UserInfoFactory;
import f3f.global.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static f3f.global.constants.SecurityConstants.REDIRECT_URI_PARAM_COOKIE_NAME;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {



    private final TokenProvider tokenProvider;

    private final RefreshTokenDao refreshTokenDao;

    private final MemberRepository memberRepository;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);
        log.info("TEST : {}" , targetUrl);
        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        String loginType = authToken.getAuthorizedClientRegistrationId().toUpperCase();

        OidcUser user = ((OidcUser) authentication.getPrincipal());

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(loginType, user.getAttributes());

//        Collection<? extends GrantedAuthority> authorities = ((OidcUser) authentication.getPrincipal()).getAuthorities();

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        log.info("TEST:{}",authorities);
        log.info("authentication.getName():{}",authentication.getName());
        log.info("authentication.getPrincipal():{}",authentication.getPrincipal().toString());
        log.info("authentication.getCredentials():{}",authentication.getCredentials());
        log.info("user : {}",user);


        Member findMember = memberRepository.findByEmail(userInfo.getEmail());

//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(findMember.getEmail(), findMember.getPassword());
//        Authentication memberAuthentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        //토큰 생성
        TokenDTO tokenDTO = tokenProvider.generateTokenDto(authentication);

//        saveRefreshTokenInStorage()

//        // DB 저장
//        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserId(userInfo.getId());
//        if (userRefreshToken != null) {
//            userRefreshToken.setRefreshToken(refreshToken.getToken());
//        } else {
//            userRefreshToken = new UserRefreshToken(userInfo.getId(), refreshToken.getToken());
//            userRefreshTokenRepository.saveAndFlush(userRefreshToken);
//        }
//
//        int cookieMaxAge = (int) refreshTokenExpiry / 60;
//
//        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
//        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", tokenDTO.getAccessToken())
                .build().toUriString();
    }

    /**
     * redis 에 refresh token 저장
     * @param refreshToken
     * @param memberId
     */
    private void saveRefreshTokenInStorage(String refreshToken, Long memberId) {
        refreshTokenDao.createRefreshToken(memberId,refreshToken);
    }
}
