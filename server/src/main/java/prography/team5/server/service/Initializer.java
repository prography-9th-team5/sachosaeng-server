package prography.team5.server.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
