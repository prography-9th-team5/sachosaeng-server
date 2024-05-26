package prography.team5.server.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prography.team5.server.controller.dto.CommonApiResponse;
import prography.team5.server.docs.CardApiDocs;
import prography.team5.server.service.dto.CardIdResponse;
import prography.team5.server.service.dto.CardRequest;
import prography.team5.server.service.dto.CardResponse;
import prography.team5.server.service.CardService;
import prography.team5.server.service.dto.CategoryResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cards")
public class CardController implements CardApiDocs {

    private final CardService cardService;

    @GetMapping("/{cardId}")
    public ResponseEntity<CommonApiResponse<CardResponse>> findByCardId(@PathVariable(value = "cardId") final long cardId) {
        final CardResponse response = cardService.findByCardId(cardId);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    @PostMapping
    public ResponseEntity<CommonApiResponse<CardIdResponse>> add(@RequestBody final CardRequest cardRequest) {
        final CardIdResponse response = cardService.add(cardRequest);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }

    //todo: 한번에 조회할 사이즈... 등등
    @GetMapping
    public ResponseEntity<CommonApiResponse<List<CardResponse>>> findAll(
            @RequestParam(name = "cursor", required = false) final Long cursor,
            @RequestParam(name = "category-id", required = false) final Long categoryId
    ) {
        final List<CardResponse> response = cardService.findAll(cursor, categoryId);
        return ResponseEntity.ok()
                .body(new CommonApiResponse<>(0, "API 요청이 성공했습니다.", response));
    }
}
