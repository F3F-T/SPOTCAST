package f3f.global.util;

import f3f.global.response.ErrorCode;
import f3f.global.response.GeneralException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
public class SecurityUtil {

    private SecurityUtil() {
    }

    // SecurityContext 에 유저 정보가 저장되는 시점
    // Request 가 들어올 때 JwtFilter 의 doFilter 에서 저장
    public static Long getCurrentMemberId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("Security Context 에 인증 정보가 없습니다.");
        }
        if (authentication.getName().equals("anonymousUser")) {

            throw new GeneralException(ErrorCode.INVALID_REQUEST,"로그인이 필요한 서비스입니다.");
        } else {
            return Long.parseLong(authentication.getName());
        }
    }

    // SecurityContext 에 유저 정보가 저장되는 시점
    // Request 가 들어올 때 JwtFilter 의 doFilter 에서 저장
    public static String getCurrentMemberAuthority() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getAuthorities() == null) {
            throw new RuntimeException("Security Context 에 인증 정보가 없습니다.");
        }
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        return authorities;
    }
}