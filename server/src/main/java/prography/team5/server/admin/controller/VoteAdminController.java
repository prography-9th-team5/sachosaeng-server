package prography.team5.server.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prography.team5.server.card.service.VoteService;

@RestController
@RequestMapping("/admin/votes")
@RequiredArgsConstructor
public class VoteAdminController {

    private final VoteService voteService;

/*    @GetMapping
    public ModelAndView votes(ModelAndView modelAndView) {
        final List<VoteResponse> votes = voteService.findAllContainContents(null, null, 30); //todo: 게시글 많을때도 옛날 게시글 조회되게!
        modelAndView.addObject("votes", votes);
        modelAndView.setViewName("votes");
        return modelAndView;
    }*/
}
