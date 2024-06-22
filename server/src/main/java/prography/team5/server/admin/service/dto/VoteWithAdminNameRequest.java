package prography.team5.server.admin.service.dto;

import java.util.List;

public record VoteWithAdminNameRequest(String title, List<String> voteOptions, List<Long> categoryIds, String adminName){

}
