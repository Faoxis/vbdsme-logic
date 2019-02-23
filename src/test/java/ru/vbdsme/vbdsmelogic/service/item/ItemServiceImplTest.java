package ru.vbdsme.vbdsmelogic.service.item;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.vbdsme.vbdsmelogic.domain.Category;
import ru.vbdsme.vbdsmelogic.domain.Item;
import ru.vbdsme.vbdsmelogic.exception.ItemAlreadyExistsException;
import ru.vbdsme.vbdsmelogic.repository.ItemRepository;
import ru.vbdsme.vbdsmelogic.service.category.CategoryService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private CategoryService categoryService;

    private ItemService itemService;

    private Item testedItem = new Item()
            .setId(1)
            .setName("J2EE")
            .setPrice(100_000)
            .setQuantity(42)
            .setCategories(
                    Stream.of(
                            new Category()
                                .setName("БДСМ для программиста"),

                            new Category()
                                .setName("Большая зарплата, скучная работа")
                    ).collect(Collectors.toSet())
            );


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        itemService = new ItemServiceImpl(itemRepository, categoryService);
    }

    @Test
    public void testSuccessSave() {
        testedItem
                .getCategories()
                .forEach(category -> when(categoryService.getByName(category.getName()))
                        .thenReturn(category));

        itemService.save(testedItem);
        verify(itemRepository, times(1))
                .findByName(testedItem.getName());
    }

    @Test(expected = ItemAlreadyExistsException.class)
    public void testAlreadyExistsItem() {
        when(itemRepository.findByName(testedItem.getName()))
                .thenReturn(Optional.of(testedItem));

        itemService.save(testedItem);
    }

    @Test
    public void testSuccessGetAll() {
        List<Item> items = Stream.of(testedItem).collect(Collectors.toList());
        when(itemRepository.findAll()).thenReturn(items);

        List<Item> resultItems = itemService.getAll();

        assertEquals(1, resultItems.size());
        verify(itemRepository, times(1))
                .findAll();
    }
}

