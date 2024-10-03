package prography.team5.server.admin.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
public class AdminHomeController {

    @Value("${auth.admin.username}")
    private String adminUsername;
    @Value("${auth.admin.password}")
    private String adminPassword;
    @Value("${auth.admin.redirect}")
    private String adminRedirect;

    @GetMapping("/admin")
    public ModelAndView home(ModelAndView modelAndView) {
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @GetMapping("/login-please")
    public ModelAndView loginForm(ModelAndView modelAndView) {
        modelAndView.setViewName("login-please");
        return modelAndView;
    }

    @PostMapping("/login-please")
    public String login(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (adminUsername.equals(username) && adminPassword.equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", username);
            session.setMaxInactiveInterval(3600);//1시간 = 3600
            return "redirect:" + adminRedirect + "/admin";
        }
        return "redirect:" + adminRedirect + "/login-please?fail";
    }
}
