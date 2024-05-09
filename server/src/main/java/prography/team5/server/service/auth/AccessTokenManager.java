package prography.team5.server.service.auth;

import prography.team5.server.service.auth.dto.VerifiedUser;

public interface AccessTokenManager {

    String provide(final long userId);

    VerifiedUser extract(final String token);
}
