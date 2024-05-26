package prography.team5.server.domain.card;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Entity
public class Card extends TimeRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, columnDefinition = "longtext")
    private String content;

    @ManyToMany
    @JoinTable(
            name = "card_category",
            joinColumns = @JoinColumn(name = "card_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    //todo: 투표기능
    //private String imageUrl; //todo: 이미지 등록 가능
    private Long writerId; //todo: 관리자가 아닌 유저가 직접 글을 등록하는 경우

    public Card(final String title, final String content, final List<Category> categories) {
        this.title = title;
        this.content = content;
        this.categories = categories;
    }
}
