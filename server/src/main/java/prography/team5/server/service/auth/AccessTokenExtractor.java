package prography.team5.server.service.auth;

import prography.team5.server.service.auth.dto.VerifiedUser;

public interface AccessTokenExtractor {

    VerifiedUser extract(final String token);
}
