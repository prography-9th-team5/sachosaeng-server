package prography.team5.server.service.auth;

import prography.team5.server.service.auth.dto.Accessor;

public interface AccessTokenManager {

    String provide(final long id);

    Accessor extract(final String token);
}
