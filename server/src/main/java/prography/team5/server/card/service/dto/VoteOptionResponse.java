package prography.team5.server.card.service.dto;

import java.util.List;
import prography.team5.server.card.domain.VoteOption;

public record VoteOptionResponse(Long voteOptionId, String content, Long count) {

    public static List<VoteOptionResponse> toResponse(final List<VoteOption> voteOptions) {
        return voteOptions.stream()
                .map(each -> new VoteOptionResponse(each.getId(), each.getContent(), each.getCount()))
                .toList();
    }
}
