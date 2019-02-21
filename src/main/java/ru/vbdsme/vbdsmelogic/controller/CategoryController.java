package ru.vbdsme.vbdsmelogic.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.vbdsme.vbdsmelogic.domain.Category;
import ru.vbdsme.vbdsmelogic.service.category.CategoryService;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class CategoryController {
    public static final String URL = "/categories";

    private CategoryService categoryService;

    @GetMapping(URL)
    public List<Category> getAll() {
        return categoryService.getAll();
    }

    @PostMapping(URL)
    public Category save(@Valid @RequestBody Category category) {
        return categoryService.save(category);
    }

}
