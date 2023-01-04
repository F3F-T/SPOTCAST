package f3f.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import f3f.global.response.ErrorCode;
import f3f.global.response.GeneralException;
import f3f.global.response.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {


    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
        // 즉 토큰이 없거나 유효하지 않은 경우  or 로그인이 필요한 페이지에 접근하려고할 때
        sendResponse(response, authException);

    }

    private void sendResponse(HttpServletResponse response, AuthenticationException authException) throws IOException {

        String result = objectMapper.writeValueAsString(ResponseDTO.of(false,ErrorCode.JWT_ACCESSDENIED,authException.getMessage()));

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(result);
        response.setStatus(response.SC_UNAUTHORIZED);
    }

}