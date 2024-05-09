package prography.team5.server.controller.auth;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeaderUtils {

    public static String extractToken(final String authorizationHeader, final String tokenType) {
        if (authorizationHeader == null) {
            throw new IllegalArgumentException();
        }
        final String[] split = authorizationHeader.split(" ");
        if (split.length == 2 && split[0].equalsIgnoreCase(tokenType)) {
            return split[1];
        }
        throw new IllegalArgumentException();
    }
}
