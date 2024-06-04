package prography.team5.server.service.dto;

import java.util.List;

public record VoteResponse(Long voteId, String title, List<VoteOptionResponse> voteOptions, List<CategoryResponse> categories) {

}
