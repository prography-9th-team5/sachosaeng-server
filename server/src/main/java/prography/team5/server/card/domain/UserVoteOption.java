package prography.team5.server.card.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@Entity
public class UserVoteOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long voteId;
    private Long voteOptionId;

    public UserVoteOption(final Long userId, final Long voteId, final Long voteOptionId) {
        this.userId = userId;
        this.voteId = voteId;
        this.voteOptionId = voteOptionId;
    }
}
