package prography.team5.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.domain.Member;
import prography.team5.server.domain.MemberRepository;
import prography.team5.server.service.dto.JoinRequest;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {

    private final MemberRepository memberRepository;

    public void joinNewMember(final JoinRequest joinRequest) {
        memberRepository.save(new Member(joinRequest.email()));
    }
}
