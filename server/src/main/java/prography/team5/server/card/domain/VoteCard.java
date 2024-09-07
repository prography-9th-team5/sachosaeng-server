package prography.team5.server.card.domain;

import static org.apache.logging.log4j.util.Strings.isBlank;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import prography.team5.server.category.domain.Category;
import prography.team5.server.common.exception.ErrorType;
import prography.team5.server.common.exception.SachosaengException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class VoteCard extends Card {

    private static final long CLOSING_COUNT = 100;
    private static final long HOT_COUNT_FLOOR = 10;
    private static final int MIN_OPTION_SIZE = 2;
    private static final int MAX_OPTION_SIZE = 4;

    @OneToMany(mappedBy = "voteCard", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<VoteOption> voteOptions = new ArrayList<>();
    private Long writerId;
    private String adminName;
    private boolean isMultipleChoiceAllowed;
    private long participantCount;

    private VoteCard(final String title, final List<Category> categories, final boolean isMultipleChoiceAllowed, final String adminName) {
        super(title, categories);
        this.isMultipleChoiceAllowed = isMultipleChoiceAllowed;
        this.adminName = adminName;
    }

    public static VoteCard of(final String title, final List<Category> categories, final boolean isMultipleChoiceAllowed, final String adminName) {
        if(Strings.isBlank(title)) {
            throw new SachosaengException(ErrorType.EMPTY_TITLE);
        }
        if (categories.isEmpty()) {
            throw new SachosaengException(ErrorType.EMPTY_CATEGORY);
        }
        if (Strings.isBlank(adminName)) {
            throw new SachosaengException(ErrorType.EMPTY_ADMIN_NAME);
        }
        return new VoteCard(title, categories, isMultipleChoiceAllowed, adminName);
    }

    public void updateVoteOptions(final List<String> voteOptionTexts) {
        if(voteOptionTexts.size() < MIN_OPTION_SIZE || voteOptionTexts.size() > MAX_OPTION_SIZE) {
            throw new SachosaengException(ErrorType.VOTE_OPTION_LIMIT);
        }
        List<VoteOption> options = new ArrayList<>();
        for (String voteOptionText : voteOptionTexts) {
            options.add(new VoteOption(this, voteOptionText));
        }
        this.voteOptions = options;
    }

    //todo: 투표 마감 확인
    public void chooseVoteOption(final List<Long> voteOptionIds) {
        if(voteOptionIds.size() > 1 && !isMultipleChoiceAllowed) {
            throw new SachosaengException(ErrorType.MULTIPLE_CHOICE_NOT_ALLOWED);
        }
        for (Long voteOptionId : voteOptionIds) {
            final VoteOption voteOption = voteOptions.stream()
                    .filter(each -> Objects.equals(each.getId(), voteOptionId))
                    .findFirst()
                    .orElseThrow(() -> new SachosaengException(ErrorType.INVALID_VOTE_OPTION_ID));
            voteOption.increase();
        }
        participantCount++;
    }

    public void changeVoteOption(final List<Long> fromVoteOptionIds, final List<Long> ToVoteOptionIds) {
        for (Long fromVoteOptionId : fromVoteOptionIds) {
            final VoteOption fromVoteOption = voteOptions.stream()
                    .filter(each -> Objects.equals(each.getId(), fromVoteOptionId))
                    .findFirst()
                    .orElseThrow(() -> new SachosaengException(ErrorType.INVALID_VOTE_OPTION_ID));
            fromVoteOption.decrease();
        }
        if(ToVoteOptionIds.size() > 1 && !isMultipleChoiceAllowed) {
            throw new SachosaengException(ErrorType.MULTIPLE_CHOICE_NOT_ALLOWED);
        }
        for (Long voteOptionId : ToVoteOptionIds) {
            final VoteOption voteOption = voteOptions.stream()
                    .filter(each -> Objects.equals(each.getId(), voteOptionId))
                    .findFirst()
                    .orElseThrow(() -> new SachosaengException(ErrorType.INVALID_VOTE_OPTION_ID));
            voteOption.increase();
        }
    }

    public Long getHotParticipantCount() {
        if(participantCount >= HOT_COUNT_FLOOR) {
            return participantCount;
        }
        return null;
    }

    public boolean isClosed() {
        return participantCount >= CLOSING_COUNT;
    }

    public void checkCategory(final Category category) {
        if (!this.categories.contains(category)) {
            throw new SachosaengException(ErrorType.CATEGORY_NOT_INCLUDED_IN_VOTE);
        }
    }

    public boolean isSameCategory(final Category category) {
        return this.categories.contains(category);
    }

    public static long getHotCountFloor() {
        return HOT_COUNT_FLOOR;
    }

    public String findVoteOption(final Long voteOptionId) {
        final VoteOption voteOption = voteOptions.stream()
                .filter(each -> Objects.equals(each.getId(), voteOptionId))
                .findFirst()
                .orElseThrow();
        return voteOption.getContent();
    }

    public void updateAll(
            final String title,
            final List<Category> categories,
            final List<String> voteOptionTexts,
            final boolean multipleChoiceAllowed,
            final String adminName
    ) {
        if(participantCount != 0) {
            throw new RuntimeException("한명이라도 투표한건 변경 불가능");
        }
        if(!Objects.equals(this.title, title)) {
            if(title.isEmpty()) {
                throw new SachosaengException(ErrorType.EMPTY_TITLE);
            }
            this.title = title;
        }

        // 카테고리 업데이트
        if(!new HashSet<>(this.categories).equals(new HashSet<>(categories))) {
            if(categories.isEmpty()) {
                throw new SachosaengException(ErrorType.EMPTY_CATEGORY);
            }
            this.categories = categories;
        }

        // 투표 옵션 업데이트
        if(voteOptionTexts.size() < MIN_OPTION_SIZE || voteOptionTexts.size() > MAX_OPTION_SIZE) {
            throw new SachosaengException(ErrorType.VOTE_OPTION_LIMIT);
        }
        List<VoteOption> options = new ArrayList<>();
        for (String voteOptionText : voteOptionTexts) {
            options.add(new VoteOption(this, voteOptionText));
        }
        this.voteOptions.clear();
        this.voteOptions.addAll(options);

        if(this.isMultipleChoiceAllowed != multipleChoiceAllowed) {
            this.isMultipleChoiceAllowed = multipleChoiceAllowed;
        }
        if(!Objects.equals(this.adminName, adminName)) {
            if(isBlank(adminName)) {
                throw new SachosaengException(ErrorType.EMPTY_ADMIN_NAME);
            }
            this.adminName = adminName;
        }
    }
}
