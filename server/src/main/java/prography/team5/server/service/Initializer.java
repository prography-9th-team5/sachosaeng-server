package prography.team5.server.service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.team5.server.domain.card.InformationCard;
import prography.team5.server.domain.card.InformationCardRepository;
import prography.team5.server.domain.card.VoteCard;
import prography.team5.server.domain.card.VoteCardRepository;
import prography.team5.server.domain.category.Category;
import prography.team5.server.domain.category.CategoryRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class Initializer {

    private final CategoryRepository categoryRepository;

    @PostConstruct
    public void init() {
        final Category category1 = categoryRepository.save(new Category("경조사"));
        final Category category2 = categoryRepository.save(new Category("이직"));
    }
}
