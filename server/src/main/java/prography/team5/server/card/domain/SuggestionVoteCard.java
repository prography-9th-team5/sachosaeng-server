package prography.team5.server.card.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prography.team5.server.category.domain.Category;
import prography.team5.server.common.domian.TimeRecord;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@Entity
public class SuggestionVoteCard extends TimeRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private VoteCard voteCard;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Category category;
    private LocalDate viewDate;

    public SuggestionVoteCard(final VoteCard voteCard, final Category category, final LocalDate viewDate) {
        this.voteCard = voteCard;
        this.category = category;
        this.viewDate = viewDate;
    }
}
