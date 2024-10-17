package prography.team5.server.card.service.dto;

import java.util.List;

public record VoteCreationRequest(String title, Boolean isMultipleChoiceAllowed, List<String> voteOptions, List<Long> categoryIds){

}
