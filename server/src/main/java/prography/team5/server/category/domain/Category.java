package prography.team5.server.category.domain;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prography.team5.server.common.domian.TimeRecord;
import prography.team5.server.user.domain.UserType;

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
    @ElementCollection(targetClass = UserType.class)
    @Enumerated(value = EnumType.STRING)
    private Set<UserType> userTypes = new HashSet<>();
    private int priority;

    public Category(final String name) {
        this.name = name;
    }

    public CategoryDesign getCategoryDesign() {
        if (this.categoryDesign == null) {
            this.categoryDesign = new CategoryDesign();
        }
        return this.categoryDesign;
    }

    public void addUserType(final UserType userType) {
        this.userTypes.add(userType);
    }

    public void update(final String name, final List<UserType> userTypes) {
        if(name != null && !this.name.equals(name)) this.name = name;
        this.userTypes.retainAll(userTypes);
        this.userTypes.addAll(userTypes);
    }
}
