package prography.team5.server.admin.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import prography.team5.server.service.CategoryService;
import prography.team5.server.service.VoteService;
import prography.team5.server.service.dto.CategoryResponse;
import prography.team5.server.service.dto.VoteResponse;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final VoteService voteService;
    private final CategoryService categoryService;

    @GetMapping
    public ModelAndView home(ModelAndView modelAndView) {
        // modelAndView.addObject("message", "Welcome to Admin Page");
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/categories")
    public ModelAndView categories(ModelAndView modelAndView) {
        final List<CategoryResponse> categories = categoryService.findAll();
        modelAndView.addObject("categories", categories);
        modelAndView.setViewName("categories");
        return modelAndView;
    }

    @GetMapping("/votes")
    public ModelAndView votes(ModelAndView modelAndView) {
        final List<VoteResponse> votes = voteService.findAll(null, null, 20); //todo: 게시글 많을때도 옛날 게시글 조회되게!
        modelAndView.addObject("votes", votes);
        modelAndView.setViewName("votes");
        return modelAndView;
    }
}
