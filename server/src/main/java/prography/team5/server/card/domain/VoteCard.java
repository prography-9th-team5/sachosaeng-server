package prography.team5.server.card.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
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
    private static final int MIN_OPTION_COUNT = 2;
    private static final int MAX_OPTION_COUNT = 4;

    @OneToMany(mappedBy = "voteCard", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<VoteOption> voteOptions = new ArrayList<>();
    private Long writerId;
    private String adminName;
    private boolean multipleChoiceAllowed;

    //todo: 연관콘텐츠

    public VoteCard(final String title, final List<Category> categories, final Long writerId) {
        super(title, categories);
        this.writerId = writerId;
    }

    public VoteCard(final String title, final List<Category> categories, final String adminName) {
        super(title, categories);
        if (Strings.isBlank(adminName)) {
            throw new SachosaengException(ErrorType.EMPTY_ADMIN_NAME);
        }
        this.adminName = adminName;
    }

    //todo: 옵션은 2개 이상 4개 이하
    public void addVoteOption(final String voteOption) {
        if (this.voteOptions.size() >= 4) {
            throw new SachosaengException(ErrorType.VOTE_OPTION_LIMIT);
        }
        this.voteOptions.add(new VoteOption(this, voteOption));
    }

    //todo: 투표 마감?
    public void chooseVoteOption(final long voteOptionId) {
        final VoteOption voteOption = voteOptions.stream()
                .filter(each -> each.getId() == voteOptionId)
                .findFirst()
                .orElseThrow(() -> new SachosaengException(ErrorType.INVALID_VOTE_OPTION_ID));
        voteOption.increase();
    }

    public void changeVoteOption(final Long fromVoteOptionId, final long ToVoteOptionId) {
        if (fromVoteOptionId == ToVoteOptionId) {
            throw new SachosaengException(ErrorType.SAME_VOTE_OPTION);
        }
        final VoteOption fromVoteOption = voteOptions.stream()
                .filter(each -> Objects.equals(each.getId(), fromVoteOptionId))
                .findFirst()
                .orElseThrow(() -> new SachosaengException(ErrorType.INVALID_VOTE_OPTION_ID));
        fromVoteOption.decrease();
        chooseVoteOption(ToVoteOptionId);
    }

    public long getCount() {
        return voteOptions.stream()
                .mapToLong(VoteOption::getCount)
                .sum();
    }

    public Long getHotCount() {
        final long sum = voteOptions.stream()
                .mapToLong(VoteOption::getCount)
                .sum();
        if(sum >= HOT_COUNT_FLOOR) {
            return sum;
        }
        return null;
    }

    public boolean isClosed() {
        return getCount() >= CLOSING_COUNT;
    }

    public void checkCategory(final Category category) {
        if (!this.categories.contains(category)) {
            throw new SachosaengException(ErrorType.CATEGORY_NOT_INCLUDED_IN_VOTE);
        }
    }
}
