package ru.vbdsme.vbdsmelogic.service.category;

import ru.vbdsme.vbdsmelogic.domain.Category;

import java.util.List;

public interface CategoryService {
    Category save(Category category);
    List<Category> getAll();
    Category getByName(String name);
}
