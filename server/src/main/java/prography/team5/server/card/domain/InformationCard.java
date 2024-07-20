package prography.team5.server.card.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prography.team5.server.category.domain.Category;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class InformationCard extends Card {

    @Column(nullable = false, columnDefinition = "longtext")
    private String content;

    private String referenceName;

    @Column(nullable = false, columnDefinition = "longtext")
    private String referenceUrl;

    public InformationCard(
            final String title,
            final List<Category> categories,
            final String content,
            final String referenceName,
            final String referenceUrl
    ) {
        super(title, categories);
        this.content = content;
        this.referenceName = referenceName;
        this.referenceUrl = referenceUrl;
    }
}
