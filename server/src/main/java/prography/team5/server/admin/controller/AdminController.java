package prography.team5.server.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
public class AdminController {

    @GetMapping("/admin")
    public ModelAndView home(ModelAndView modelAndView) {
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
