package prography.team5.server.card.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MappedSuperclass;
import java.util.List;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import prography.team5.server.common.domian.TimeRecord;
import prography.team5.server.category.domain.Category;
import prography.team5.server.common.exception.ErrorType;
import prography.team5.server.common.exception.SachosaengException;

@Getter
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class Card extends TimeRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    protected String title;
    @ManyToMany
    protected List<Category> categories;

    protected Card(final String title, final List<Category> categories) {
        validateTitle(title);
        validateCategory(categories);
        this.title = title;
        this.categories = categories;
    }

    protected void validateCategory(List<Category> categories) {
        if (categories.isEmpty()) {
            throw new SachosaengException(ErrorType.CARD_CATEGORY_EMPTY);
        }
    }

    protected void validateTitle(String title) {
        if(Strings.isBlank(title)) {
            throw new SachosaengException(ErrorType.CARD_TITLE_EMPTY);
        }
    }
}
