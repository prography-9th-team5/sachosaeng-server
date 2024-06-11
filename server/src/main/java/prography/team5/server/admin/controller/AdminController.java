package prography.team5.server.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public ModelAndView home(ModelAndView modelAndView) {
        modelAndView.addObject("message", "Welcome to Admin Page");
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
