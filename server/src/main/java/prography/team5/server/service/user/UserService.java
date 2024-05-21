package prography.team5.server.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.domain.user.User;
import prography.team5.server.domain.user.UserRepository;
import prography.team5.server.exception.ErrorType;
import prography.team5.server.exception.SachosaengException;
import prography.team5.server.service.user.dto.UserResponse;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserResponse find(final long userId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new SachosaengException(ErrorType.INVALID_USER_ID));
        return new UserResponse(user.getId(), user.getNickname());
    }
}
