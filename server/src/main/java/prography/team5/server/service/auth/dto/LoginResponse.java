package prography.team5.server.service.auth.dto;

public record LoginResponse(Long id, String accessToken, String refreshToken) {

}
