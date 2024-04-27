package prography.team5.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.domain.User;
import prography.team5.server.domain.UserRepository;
import prography.team5.server.service.dto.JoinRequest;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {

    private final UserRepository userRepository;

    public long joinNewUser(final JoinRequest joinRequest) {
        final User user = new User(joinRequest.email());
        userRepository.save(user);
        return user.getId();
    }
}
