package prography.team5.server.service.dto;

import java.util.List;

public record VoteRequest(String title, List<String> voteOptions, List<Long> categoryIds) {

}
