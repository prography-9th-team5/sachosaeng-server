package prography.team5.server.auth.service.dto;

public record LoginResponse(Long userId, String accessToken, String refreshToken) {

}
