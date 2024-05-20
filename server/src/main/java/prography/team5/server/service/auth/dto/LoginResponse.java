package prography.team5.server.service.auth.dto;

public record LoginResponse(Long userId, String accessToken, String refreshToken) {

}
