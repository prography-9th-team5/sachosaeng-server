package prography.team5.server.admin.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import prography.team5.server.card.service.InformationService;
import prography.team5.server.card.service.dto.InformationResponse;

@RestController
@RequestMapping("/admin/information")
@RequiredArgsConstructor
public class InformationAdminController {

    private final InformationService informationService;

    @GetMapping
    public ModelAndView information(ModelAndView modelAndView) {
        final List<InformationResponse> information = informationService.findAll(null, null, 30);//todo: 게시글 많을때도 옛날 게시글 조회되게!
        modelAndView.addObject("information", information);
        modelAndView.setViewName("information");
        return modelAndView;
    }
}
