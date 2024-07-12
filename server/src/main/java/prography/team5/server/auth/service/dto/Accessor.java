package prography.team5.server.auth.service.dto;

import static java.util.Objects.isNull;

public record Accessor(Long id) {

    public static Accessor createAnonymousAccessor() {
        return new Accessor(null);
    }

    public boolean isAnonymous() {
        if(isNull(id)) {
            return true;
        }
        return false;
    }
}
