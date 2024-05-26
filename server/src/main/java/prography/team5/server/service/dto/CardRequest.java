package prography.team5.server.service.dto;

import java.util.List;

public record CardRequest(String title, String content, List<Long> categoryIds) {

}
