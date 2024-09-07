package prography.team5.server.auth.service.dto;

import lombok.Setter;

@Setter
public class JoinRequest {

    String email;
    String userType;

    public void updateEmail(final String appleEmail) {
        email = appleEmail;
    }

    public String email() {
        return email;
    }

    public String userType() {
        return userType;
    }
}
