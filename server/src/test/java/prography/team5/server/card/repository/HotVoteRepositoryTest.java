package prography.team5.server.card.repository;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class HotVoteRepositoryTest {

    @Test
    void LocalDate_테스트() {
        final LocalDate today = LocalDate.now();
        final LocalDate startDate = today.minusDays(6);
        System.out.println("today = " + today);
        System.out.println("startDate = " + startDate);
    }
}
