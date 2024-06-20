package prography.team5.server.domain.category;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prography.team5.server.domain.TimeRecord;

@Getter
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Category extends TimeRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Embedded
    private CategoryDesign categoryDesign;

    //todo: 유저타입들
    //todo: 연관콘텐츠

    public Category(final String name) {
        this.name = name;
    }

    public CategoryDesign getCategoryDesign() {
        if (this.categoryDesign == null) {
            this.categoryDesign = new CategoryDesign();
        }
        return this.categoryDesign;
    }
}
