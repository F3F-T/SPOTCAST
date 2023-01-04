package f3f.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import f3f.global.response.ErrorCode;
import f3f.global.response.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtAccessDeniedHandler implements AccessDeniedHandler {


    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        // 필요한 권한이 없이 접근하려 할때 403
        // 즉 ADMIN 페이지에 USER 가 접근하려고 할 때
        sendResponse(response, accessDeniedException);

    }

    private void sendResponse(HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        String result = objectMapper.writeValueAsString(ResponseDTO.of(false,ErrorCode.AUTHORITY_FORBIDDEN,accessDeniedException.getMessage()));


        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(result);
        response.setStatus(response.SC_FORBIDDEN);
    }
}