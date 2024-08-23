package prography.team5.server.card.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.util.List;
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
public class InformationCard extends Card {

    private String subtitle;

    @Column(nullable = false, columnDefinition = "longtext")
    private String content;

    private String referenceName;

    @Column(nullable = false, columnDefinition = "longtext")
    private String referenceUrl;

    private String adminName;

    public InformationCard(
            final String title,
            final List<Category> categories,
            final String subtitle,
            final String content,
            final String referenceName,
            final String referenceUrl,
            final String adminName
    ) {
        super(title, categories);
        validateReferenceName(referenceName);
        validateReferenceUrl(referenceUrl);
        this.subtitle = subtitle;
        this.content = content;
        this.referenceName = referenceName;
        this.referenceUrl = referenceUrl;
        this.adminName = adminName;
    }

    private void validateReferenceName(String referenceName) {
        if(Strings.isBlank(referenceName)) {
            throw new SachosaengException(ErrorType.INFORMATION_REFERENCE_EMPTY);
        }
    }

    private void validateReferenceUrl(String referenceUrl) {
        if(Strings.isBlank(referenceUrl)) {
            throw new SachosaengException(ErrorType.INFORMATION_REFERENCE_URL_EMPTY);
        }
    }

    public void checkCategory(final Category category) {
        if(!this.categories.contains(category)) {
            throw new SachosaengException(ErrorType.CATEGORY_NOT_INCLUDED_IN_INFORMATION);
        }
    }

    public void updateAll(
            final String title,
            final String subtitle,
            final String content,
            final List<Category> categories,
            final String referenceName,
            final String referenceUrl,
            final String adminName
    ) {
        validateTitle(title);
        validateCategory(categories);
        validateReferenceName(referenceName);
        validateReferenceUrl(referenceUrl);
        this.title = title;
        this.subtitle = subtitle;
        this.content = content;
        this.categories = categories;
        this.referenceName = referenceName;
        this.referenceUrl = referenceUrl;
        this.adminName = adminName;
    }
}
