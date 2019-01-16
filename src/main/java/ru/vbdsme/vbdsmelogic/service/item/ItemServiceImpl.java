package ru.vbdsme.vbdsmelogic.service.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import ru.vbdsme.vbdsmelogic.domain.Item;
import ru.vbdsme.vbdsmelogic.exception.ItemAlreadyExistsException;
import ru.vbdsme.vbdsmelogic.exception.ThereNoCategoryException;
import ru.vbdsme.vbdsmelogic.repository.CategoryRepository;
import ru.vbdsme.vbdsmelogic.repository.ItemRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;
    private CategoryRepository categoryRepository;

    @Override
    public Item save(Item item) {
        Optional<Item> existedItem = itemRepository.findByName(item.getName());
        if (existedItem.isPresent()) throw new ItemAlreadyExistsException();

        item.setCategories(item
                .getCategories()
                .stream()
                .parallel()
                .map(category -> categoryRepository.findByName(category.getName()))
                .map(optionalCategory -> optionalCategory.orElseThrow(ThereNoCategoryException::new))
                .collect(Collectors.toSet())
        );

        return itemRepository.save(item);

    }

    @Override
    public List<Item> getAll() {
        return itemRepository.findAll();
    }
}
