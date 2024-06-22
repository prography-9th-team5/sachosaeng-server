package prography.team5.server.admin.service.dto;

import java.util.List;

public record CategoryUpdateRequest(
        String name,
        List<String> userTypes
) {

}
