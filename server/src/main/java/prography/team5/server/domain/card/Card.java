package prography.team5.server.domain.card;

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
import prography.team5.server.domain.TimeRecord;
import prography.team5.server.domain.category.Category;

@Getter
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class Card extends TimeRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @ManyToMany
    private List<Category> categories;

    protected Card(final String title, final List<Category> categories) {
        this.title = title;
        this.categories = categories;
    }
}
