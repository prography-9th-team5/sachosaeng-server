package prography.team5.server.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.user.domain.User;
import prography.team5.server.user.domain.UserRepository;
import prography.team5.server.common.exception.ErrorType;
import prography.team5.server.common.exception.SachosaengException;
import prography.team5.server.user.service.dto.UserResponse;
import prography.team5.server.user.service.dto.UserTypeRequest;
import prography.team5.server.user.service.dto.NicknameRequest;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserResponse find(final long userId) {
        final User user = findUserById(userId);
        return UserResponse.from(user);
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

    @Transactional
    public void updateUserType(final long userId, final UserTypeRequest userTypeRequest) {
        final User user = findUserById(userId);
        user.updateUserType(userTypeRequest.userType());
    }
}
