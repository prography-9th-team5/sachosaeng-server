package prography.team5.server.domain.card;

import jakarta.persistence.Entity;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prography.team5.server.domain.category.Category;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class VoteCard extends Card{

    //todo: content가 곧 투표 기능

    private Long writerId;

    public VoteCard(final String title, final List<Category> categories, final Long writerId) {
        super(title, categories);
        this.writerId = writerId;
    }
}
