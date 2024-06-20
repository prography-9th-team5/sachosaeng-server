package prography.team5.server.auth.service;

import prography.team5.server.auth.service.dto.Accessor;

public interface AccessTokenManager {

    String provide(final long id);

    Accessor extract(final String token);
}
