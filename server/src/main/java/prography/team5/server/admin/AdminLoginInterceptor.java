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
        HttpSession session = request.getSession();

        String username = (String) session.getAttribute("user");
        if (!adminUsername.equals(username)) {
            session.setAttribute("redirectUrl", request.getRequestURI());
            // 잘못된 사용자 정보일 경우
            response.sendRedirect(adminRedirect + "/login-please");
            // 세션에 원래 요청 경로를 저장
            return false;
        }

        return true;
    }
}
