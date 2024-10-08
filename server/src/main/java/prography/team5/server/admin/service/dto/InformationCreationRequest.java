package prography.team5.server.admin.service.dto;

import java.util.List;

public record InformationCreationRequest(
        String title,

        String subtitle,
        String content,
        List<Long> categoryIds,
        String referenceName,
        String referenceUrl,
        String adminName
) {
}
