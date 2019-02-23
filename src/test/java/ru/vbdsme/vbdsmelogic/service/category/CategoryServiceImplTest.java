package ru.vbdsme.vbdsmelogic.service.category;

import org.junit.Before;
import org.junit.Test;
import ru.vbdsme.vbdsmelogic.domain.Category;
import ru.vbdsme.vbdsmelogic.exception.CategoryAlreadyExistsException;
import ru.vbdsme.vbdsmelogic.exception.ThereNoCategoryException;
import ru.vbdsme.vbdsmelogic.repository.CategoryRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CategoryServiceImplTest {

    private CategoryRepository categoryRepository;
    private CategoryService categoryService;
    private Category testedCategory = new Category();

    @Before
    public void setUp() {
        testedCategory.setName("БДСМ для всех");

        categoryRepository = mock(CategoryRepository.class);
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test(expected = CategoryAlreadyExistsException.class)
    public void testAlreadyExistsCategory() {
        when(categoryRepository.findByName(testedCategory.getName()))
                .thenReturn(Optional.of(testedCategory));

        categoryService.save(testedCategory);
    }

    @Test
    public void testSuccessCategory() {
        when(categoryRepository.findByName(any()))
                .thenReturn(Optional.empty());

        categoryService.save(testedCategory);
        verify(categoryRepository, times(1))
                .findByName(testedCategory.getName());
    }

    @Test
    public void testSuccessGetAll() {
        List<Category> expectedCategories = Collections.singletonList(testedCategory);

        when(categoryRepository.findAll())
                .thenReturn(expectedCategories);

        List<Category> resultCategories = categoryService.getAll();

        assertEquals(expectedCategories, resultCategories);
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    public void testSuccessGetByName() {
        when(categoryRepository.findByName(testedCategory.getName()))
                .thenReturn(Optional.of(testedCategory));

        Category resultCategory = categoryService.getByName(testedCategory.getName());
        assertEquals(testedCategory.getName(), resultCategory.getName());
        verify(categoryRepository, times(1))
                .findByName(testedCategory.getName());
    }

    @Test(expected = ThereNoCategoryException.class)
    public void testNotExistsCategoryName() {
        when(categoryRepository.findByName(testedCategory.getName()))
                .thenReturn(Optional.empty());

        categoryService.getByName(testedCategory.getName());
    }


}