package prography.team5.server.domain.card;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prography.team5.server.domain.category.Category;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class VoteCard extends Card{

    @OneToMany(mappedBy = "voteCard", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<VoteOption> voteOptions = new ArrayList<>();
    private Long writerId;

    public VoteCard(final String title, final List<Category> categories, final Long writerId) {
        super(title, categories);
        this.writerId = writerId;
    }

    public void addVoteOption(final String voteOption) {
        this.voteOptions.add(new VoteOption(this, voteOption));
    }
}
