package prography.team5.server.util;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class RandomNicknameGeneratorTest {

    @Disabled
    @Test
    void 닉네임_생성기() {
        for (int i = 0; i < 20; i++) {
            final String nickname = RandomNicknameGenerator.generate();
            System.out.println("nickname = " + nickname);
        }
    }
}
