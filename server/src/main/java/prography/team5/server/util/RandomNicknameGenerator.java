package prography.team5.server.util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomNicknameGenerator {

    private static final List<String> modifiers;
    private static final List<String> nouns;
    private static final Random random = new Random();

    static {
        modifiers = Arrays.asList(
                "귀여운", "유능한", "섹시한", "날렵한", "용감한",
                "지혜로운", "소심한", "야심찬", "자유로운", "센스쟁이",
                "우아한", "근면 성실한", "창의적인", "따뜻한", "청초한",
                "부드러운", "낭만을 찾는", "유머러스한", "도도한", "늑대같은",
                "행운 가득", "별에서 온", "달달한", "단호한", "방랑하는",
                "악랄한", "유쾌한", "활기찬", "매혹적인", "반짝이는"
        );
        nouns = Arrays.asList(
                "고양이", "강아지", "호랑이", "펭귄", "여우",
                "물고기", "사자", "기린", "햄스터", "거북이",
                "원숭이", "코끼리", "침팬지", "팬더", "늑대",
                "코알라", "참새", "기러기", "하마", "악어",
                "초파리", "바다거북", "수달", "비버", "병아리",
                "기니피그", "토끼", "일개미", "조랑말", "갯지렁이"
        );
    }

    public static String generate() {
        String modifier = modifiers.get(random.nextInt(modifiers.size()));
        String noun = nouns.get(random.nextInt(nouns.size()));
        return modifier + " " + noun;
    }
}
