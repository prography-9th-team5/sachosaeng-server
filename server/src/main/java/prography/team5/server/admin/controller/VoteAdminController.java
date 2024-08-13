package prography.team5.server.admin.controller;

import io.swagger.v3.oas.annotations.Hidden;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import prography.team5.server.admin.service.VoteAdminService;
import prography.team5.server.admin.service.dto.SimpleVoteWithWriterResponse;
import prography.team5.server.admin.service.dto.VoteWithAdminNameRequest;
import prography.team5.server.admin.service.dto.VoteWithFullCategoriesResponse;
import prography.team5.server.auth.controller.AuthRequired;
import prography.team5.server.auth.service.dto.Accessor;
import prography.team5.server.card.service.dto.VoteIdResponse;
import prography.team5.server.card.service.dto.VoteRequest;
import prography.team5.server.category.service.CategoryService;
import prography.team5.server.category.service.dto.CategoryResponse;
import prography.team5.server.common.CommonApiResponse;

@RestController
@RequestMapping("/admin/votes")
@RequiredArgsConstructor
public class VoteAdminController {

    private final VoteAdminService voteAdminService;
    private final CategoryService categoryService;

    @GetMapping
    public ModelAndView votes(ModelAndView modelAndView) {
        final List<SimpleVoteWithWriterResponse> votes = voteAdminService.findAll();
        final List<CategoryResponse> categories = categoryService.findAll();
        modelAndView.addObject("votes", votes);
        modelAndView.addObject("categories", categories);
        modelAndView.setViewName("votes");
        return modelAndView;
    }

    @Hidden
    @PostMapping
    public ResponseEntity<CommonApiResponse<VoteIdResponse>> create(
            @RequestBody final VoteWithAdminNameRequest request
    ) {
        final VoteIdResponse response = voteAdminService.create(request);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    @Hidden
    @GetMapping("/{voteId}")
    public ResponseEntity<CommonApiResponse<VoteWithFullCategoriesResponse>> findById(
            @PathVariable(value = "voteId") final Long voteId
    ) {
        final VoteWithFullCategoriesResponse response = voteAdminService.findById(voteId);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    @Hidden
    @PutMapping("/{voteId}")
    public ResponseEntity<CommonApiResponse<VoteWithFullCategoriesResponse>> modifyById(
            @PathVariable(value = "voteId") final Long voteId,
            @RequestBody final VoteWithAdminNameRequest request
    ) {
        voteAdminService.modifyById(voteId, request);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다."));
    }
}
