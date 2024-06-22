package prography.team5.server.card.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prography.team5.server.category.domain.Category;
import prography.team5.server.common.exception.ErrorType;
import prography.team5.server.common.exception.SachosaengException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class VoteCard extends Card {

    @OneToMany(mappedBy = "voteCard", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<VoteOption> voteOptions = new ArrayList<>();
    private Long writerId;

    //todo: 연관콘텐츠

    public VoteCard(final String title, final List<Category> categories, final Long writerId) {
        super(title, categories);
        this.writerId = writerId;
    }

    public void addVoteOption(final String voteOption) {
        if(this.voteOptions.size() >= 4) {
            throw new SachosaengException(ErrorType.VOTE_OPTION_LIMIT);
        }
        this.voteOptions.add(new VoteOption(this, voteOption));
    }

    public void chooseVoteOption(final long voteOptionId) {
        final VoteOption voteOption = voteOptions.stream()
                .filter(each -> each.getId() == voteOptionId)
                .findFirst()
                .orElseThrow(() -> new SachosaengException(ErrorType.INVALID_VOTE_OPTION_ID));
        voteOption.increase();
    }

    public long getCount() {
        return voteOptions.stream()
                .mapToLong(VoteOption::getCount)
                .sum();
    }
}
