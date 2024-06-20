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

    //private String imageUrl; //todo: 이미지 등록 가능

    public InformationCard(final String title, final List<Category> categories, final String content) {
        super(title, categories);
        this.content = content;
    }
}
