package prography.team5.server.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminLoginInterceptor implements HandlerInterceptor {

    @Value("${auth.admin.username}")
    private String adminUsername;
    @Value("${auth.admin.redirect}")
    private String adminRedirect;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 세션이 없는 경우 null 반환
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            // 세션이 없거나 만료되었으면 로그인 페이지로 리다이렉트
            session = request.getSession();
            session.setAttribute("redirectUrl", request.getRequestURI());
            response.sendRedirect(adminRedirect + "/login-please");
            // 세션에 원래 요청 경로를 저장
            return false;
        }

        // 세션 값 검증
        String username = (String) session.getAttribute("user");
        if (!adminUsername.equals(username)) {
            session = request.getSession();
            session.setAttribute("redirectUrl", request.getRequestURI());
            // 잘못된 사용자 정보일 경우
            response.sendRedirect(adminRedirect + "/login-please");
            // 세션에 원래 요청 경로를 저장
            return false;
        }

        return true;
    }
}
