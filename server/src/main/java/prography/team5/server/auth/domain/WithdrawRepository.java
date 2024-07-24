package prography.team5.server.auth.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawRepository extends JpaRepository<Withdraw, Long> {

    boolean existsByEncryptedEmail(String encrypt);
}
