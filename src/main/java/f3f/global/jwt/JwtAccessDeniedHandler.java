package f3f.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
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


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        // 필요한 권한이 없이 접근하려 할때 403
        // ADMIN 페이지에 USER 가 접근하려고 할 때 or 로그인이 필요한 페이지에 접근하려고할 때
        log.info("accessDeniedException : " + accessDeniedException);
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
//    private void setResponse(HttpServletResponse response, ExceptionCode exceptionCode) throws IOException {
//        response.setContentType("application/json;charset=UTF-8");
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//
//        JSONObject responseJson = new JSONObject();
//        responseJson.put("message", exceptionCode.getMessage());
//        responseJson.put("code", exceptionCode.getCode());
//
//        response.getWriter().print(responseJson);
//    }
}