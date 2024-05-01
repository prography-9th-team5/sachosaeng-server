package prography.team5.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.domain.User;
import prography.team5.server.domain.UserRepository;
import prography.team5.server.service.dto.EmailRequest;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {

    private final UserRepository userRepository;

    public long joinNewUser(final EmailRequest emailRequest) {
        final User user = new User(emailRequest.email());
        userRepository.save(user);
        return user.getId();
    }
}
