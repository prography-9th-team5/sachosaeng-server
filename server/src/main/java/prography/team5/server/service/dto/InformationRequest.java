package prography.team5.server.service.dto;

import java.util.List;

public record InformationRequest(String title, String content, List<Long> categoryIds) {

}
