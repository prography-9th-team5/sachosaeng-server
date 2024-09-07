package prography.team5.server.auth.service.dto;

import lombok.Setter;

@Setter
public class EmailRequest {

    String email;

    public void updateEmail(final String appleEmail) {
        email = appleEmail;
    }

    public String email() {
        return email;
    }
}
