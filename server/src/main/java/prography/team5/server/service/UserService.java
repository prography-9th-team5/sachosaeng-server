package prography.team5.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.domain.user.User;
import prography.team5.server.domain.user.UserRepository;
import prography.team5.server.exception.ErrorType;
import prography.team5.server.exception.SachosaengException;
import prography.team5.server.service.dto.NicknameRequest;
import prography.team5.server.service.dto.UserResponse;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserResponse find(final long userId) {
        final User user = findUserById(userId);
        return new UserResponse(user.getId(), user.getNickname());
    }

    private User findUserById(final long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new SachosaengException(ErrorType.INVALID_USER_ID));
    }

    @Transactional
    public void updateNickname(final long userId, final NicknameRequest nicknameRequest) {
        final User user = findUserById(userId);
        user.updateNickname(nicknameRequest.nickname());
    }
}
