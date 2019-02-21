package ru.vbdsme.vbdsmelogic.service.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vbdsme.vbdsmelogic.domain.Category;
import ru.vbdsme.vbdsmelogic.exception.CategoryAlreadyExistsException;
import ru.vbdsme.vbdsmelogic.exception.ThereNoCategoryException;
import ru.vbdsme.vbdsmelogic.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository repository;

    @Override
    public Category save(Category category) {
        Optional<Category> existedCategory = repository.findByName(category.getName());
        if (existedCategory.isPresent()) {
            throw new CategoryAlreadyExistsException();
        }

        return repository.save(category);
    }

    @Override
    public List<Category> getAll() {
        return repository.findAll();
    }

    @Override
    public Category getByName(String name) {
        return repository
                .findByName(name)
                .orElseThrow(ThereNoCategoryException::new);
    }
}
