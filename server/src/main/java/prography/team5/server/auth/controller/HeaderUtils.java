package prography.team5.server.auth.controller;

import lombok.extern.slf4j.Slf4j;
import prography.team5.server.common.exception.ErrorType;
import prography.team5.server.common.exception.SachosaengException;

@Slf4j
public class HeaderUtils {

    public static String extractToken(final String authorizationHeader, final String tokenType) {
        if (authorizationHeader == null) {
            throw new SachosaengException(ErrorType.NO_AUTHORIZATION_HEADER);
        }
        final String[] split = authorizationHeader.split(" ");
        if (split.length == 2 && split[0].equalsIgnoreCase(tokenType)) {
            return split[1];
        }
        throw new SachosaengException(ErrorType.INVALID_AUTHORIZATION_HEADER_FORM);
    }
}
