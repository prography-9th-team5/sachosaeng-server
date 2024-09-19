package prography.team5.server.user.service.dto;

import prography.team5.server.user.domain.User;

public record UserResponse(Long userId, String nickname, String userType) {

    public static UserResponse from(final User user) {
        return new UserResponse(
                user.getId(),
                user.getNickname(),
                user.getUserType().name()
        );
    }
}
